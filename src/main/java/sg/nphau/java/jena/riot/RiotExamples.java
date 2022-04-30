package sg.nphau.java.jena.riot;

import org.apache.jena.atlas.lib.SinkNull;
import org.apache.jena.atlas.lib.SinkPrint;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RIOT;
import org.apache.jena.riot.RiotReader;
import org.apache.jena.sparql.core.Quad;
import sg.nphau.java.jena.Utils;

import java.io.InputStream;

public class RiotExamples {

    public static void parseTriples() {
        InputStream in = Utils.getResourceAsStream("data/data.ttl");
        RiotReader.parseTriples(in, Lang.TURTLE, null, new SinkPrint<>(System.out));
    }

    public static void readModel() {
        InputStream in = Utils.getResourceAsStream("data/data.ttl");

        RIOT.init();

        Model model = ModelFactory.createDefaultModel(); // creates an in-memory Jena Model
        model.read(in, null, "TURTLE"); // parses an InputStream assuming RDF in Turtle format

        // Write the Jena Model in Turtle, RDF/XML and N-Triples format
        System.out.println("\n---- Turtle ----");
        model.write(System.out, "TURTLE");
        System.out.println("\n---- RDF/XML ----");
        model.write(System.out, "RDF/XML");
        System.out.println("\n---- RDF/XML Abbreviated ----");
        model.write(System.out, "RDF/XML-ABBREV");
        System.out.println("\n---- N-Triples ----");
        model.write(System.out, "N-TRIPLES");
        System.out.println("\n---- RDF/JSON ----");
        model.write(System.out, "RDF/JSON");
    }

    public static void parseQuad() {
        InputStream in = Utils.getResourceAsStream("data/data.nq");
        SinkQuadStats sink = new SinkQuadStats(new SinkNull<Quad>());
        RiotReader.parseQuads(in, Lang.NQUADS, null, sink);

        System.out.println("---- Classes ----");
        Utils.print(sink.getClasses());

        System.out.println("---- Properties ----");
        Utils.print(sink.getProperties());

        System.out.println("---- Namespaces ----");
        Utils.print(sink.getNamespaces());

        System.out.println();
        Utils.printPerGraph(sink.getClassesPerGraph());

        System.out.println();
        Utils.printPerGraph(sink.getPropertiesPerGraph());

        System.out.println();
        Utils.printPerGraph(sink.getNamespacesPerGraph());
    }
}
