/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.jena;

import org.apache.jena.graph.Node;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.serializer.SerializationContext;
import org.apache.jena.sparql.util.FmtUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import sg.nphau.java.owl.utils.QueryUtils;
import sg.nphau.java.owl.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SparqlExamples {
    private static final String PATH = "data/owl/v3/vehicle.rdf";

    public static void main(String[] args) {
        query();
    }

    public static void query() {
        Model model = Utils.getModel(SparqlExamples.class.getClassLoader(), PATH);
        String baseQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
                "PREFIX : <http://www.semanticweb.org/isuru/ontologies/2020/10/toyota_vehicles#>" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";

        String queryString = "SELECT ?6Cylinder ?EngineCapacity ?HorsePower" +
                "WHERE {" +
                "?6Cylinder rdf:type :6Cylinder ." +
                "?6Cylinder :hasEngineCapacityValue ?EngineCapacity." +
                "?6Cylinder :hasEngineHorsePowerValue ?HorsePower." +
                "}";

        QueryUtils.execSelect(model, baseQuery + queryString, result -> {
            while (result.hasNext()) {
                QuerySolution querySolution = result.nextSolution();
                System.out.println(querySolution.toString());
                QueryUtils.execValue(querySolution, "6Cylinder", System.out::println);
                QueryUtils.execValue(querySolution, "MPG", System.out::println);
                QueryUtils.execValue(querySolution, "Year", System.out::println);
                QueryUtils.execValue(querySolution, "Seat", System.out::println);
                QueryUtils.execValue(querySolution, "EngineCapacity", System.out::println);
                QueryUtils.execValue(querySolution, "HorsePower", System.out::println);
            }
        });
    }

    public static void getAllCars() {
        Model model = Utils.getModel(SparqlExamples.class.getClassLoader(), PATH);
        String baseQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
                "PREFIX : <http://www.semanticweb.org/isuru/ontologies/2020/10/toyota_vehicles#>" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";

        String queryString = "SELECT ?Vehicle ?Price ?MPG ?Year ?Seat" +
                "WHERE {" +
                "?Vehicle rdf:type owl:NamedIndividual." +
                "?Vehicle :hasPriceValue 23500.0." +
                "?Vehicle :hasCombinedMPGValue ?MPG." +
                "?Vehicle :hasManufacturedYear ?Year." +
                "?Vehicle :hasSeatingValue ?Seat." +
                "}";
        QueryUtils.execSelect(model, baseQuery + queryString, result -> {
            while (result.hasNext()) {
                QuerySolution querySolution = result.nextSolution();
                System.out.println(querySolution.toString());
                QueryUtils.execValue(querySolution, "Vehicle", System.out::println);
                QueryUtils.execValue(querySolution, "MPG", System.out::println);
                QueryUtils.execValue(querySolution, "Year", System.out::println);
                QueryUtils.execValue(querySolution, "Seat", System.out::println);
                QueryUtils.execValue(querySolution, "EngineCapacity", System.out::println);
                QueryUtils.execValue(querySolution, "HorsePower", System.out::println);
            }
        });
    }

    public static void queryExample01() {
        Model model = Utils.getModel(SparqlExamples.class.getClassLoader(), "data/data.ttl");
        String queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
                + "SELECT ?name WHERE { "
                + "    ?person foaf:mbox <mailto:alice@example.org> . "
                + "    ?person foaf:name ?name . "
                + "}";
        QueryUtils.execSelect(model, queryString, result -> {
            QuerySolution querySolution = result.nextSolution();
            Literal name = querySolution.getLiteral("name");
            System.out.println(name);
        });
    }

    public static void queryExample02() {
        Model model = Utils.getModel(SparqlExamples.class.getClassLoader(), "data/data.ttl");
        String queryString = "SELECT * { ?s ?p ?o }";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution execution = QueryExecutionFactory.create(query, model)) {
            ResultSetRewindable results = ResultSetFactory.makeRewindable(execution.execSelect());

            System.out.println("---- XML ----");
            ResultSetFormatter.outputAsXML(System.out, results);
            results.reset();

            System.out.println("---- Text ----");
            ResultSetFormatter.out(System.out, results);
            results.reset();

            System.out.println("\n---- CSV ----");
            ResultSetFormatter.outputAsCSV(System.out, results);
            results.reset();

            System.out.println("\n---- TSV ----");
            ResultSetFormatter.outputAsTSV(System.out, results);
            results.reset();

            System.out.println("\n---- JSON ----");
            ResultSetFormatter.outputAsJSON(System.out, results);
            results.reset();
        }
    }

    public static void queryExample03() {
        Model model = Utils.getModel(SparqlExamples.class.getClassLoader(), "data/data.ttl");
        File path = new File("src/main/resources/data/queries");
        File[] files = path.listFiles((dir, name) -> name.startsWith("construct-") && name.endsWith(".sparql"));
        if (files != null) {
            Arrays.sort(files);
            for (File file : files) {
                System.out.println("Executing " + file.getName() + " ...");
                QueryUtils.execConstruct(model, file.getAbsolutePath(), model::add);
            }
            System.out.println("Output data:");
            model.write(System.out, "TURTLE");
        }
    }

    public static void queryExample04() {
        Model model = Utils.getModel(SparqlExamples.class.getClassLoader(), "data/data.ttl");
        Query query = QueryFactory.create("SELECT * WHERE { ?s ?p ?o }");
        QueryExecution execution = QueryExecutionFactory.create(query, model);
        Workbook workbook = new SXSSFWorkbook(100);
        Sheet sh = workbook.createSheet();
        int rows = 0;
        int columns = 0;
        try (FileOutputStream fileOutputStream = new FileOutputStream("target/report.xlsx")) {
            ResultSet resultSet = execution.execSelect();
            List<String> varNames = resultSet.getResultVars();
            List<Var> vars = new ArrayList<Var>(varNames.size());

            // first row with var names
            Row row = sh.createRow(rows++);
            for (String varName : varNames) {
                Var var = Var.alloc(varName);
                Cell cell = row.createCell(columns++);
                cell.setCellValue(var.toString());
                vars.add(var);
            }

            // other rows with bindings
            while (resultSet.hasNext()) {
                Binding bindings = resultSet.nextBinding();
                row = sh.createRow(rows++);
                columns = 0;
                for (Var var : vars) {
                    Node n = bindings.get(var);
                    if (n != null) {
                        Cell cell = row.createCell(columns++);
                        String value = FmtUtils.stringForNode(n, (SerializationContext) null);
                        cell.setCellValue(value);
                    }
                }
            }
            workbook.write(fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
