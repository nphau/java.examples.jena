/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.data.repositories;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Model;
import sg.nphau.java.owl.data.models.Car;
import sg.nphau.java.owl.ont.OntConfigs;
import sg.nphau.java.owl.utils.Executor;
import sg.nphau.java.owl.utils.QueryUtils;
import sg.nphau.java.owl.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CarsOwlUtils {

    private static final String PATH = OntConfigs.VEHICLES.path;
    private static final String BASE_QUERY = OntConfigs.VEHICLES.baseQuery;

    private static Model model = null;

    private static Model getModel() {
        if (model == null)
            model = Utils.getModel(PATH);
        return model;
    }

    public static String getAllByType(String rdfType) {
        return "SELECT ?" + rdfType + " ?Price ?MPG ?Year ?Seat" + " WHERE {" + " ?" + rdfType + " rdf:type :" + rdfType + "." + " ?" + rdfType + " :hasPriceValue ?Price." + " ?" + rdfType + " :hasCombinedMPGValue ?MPG." + " ?" + rdfType + " :hasManufacturedYear ?Year." + " ?" + rdfType + " :hasSeatingValue ?Seat." + "}";
    }

    public static String getAll(String rdfType) {
        return "SELECT ?" + rdfType + " ?Price ?MPG ?Year ?Seat" + " WHERE {" + " ?" + rdfType + " rdf:type owl:NamedIndividual." + " ?" + rdfType + " :hasPriceValue ?Price." + " ?" + rdfType + " :hasCombinedMPGValue ?MPG." + " ?" + rdfType + " :hasManufacturedYear ?Year." + " ?" + rdfType + " :hasSeatingValue ?Seat." + "}";
    }

    public static Car extractModel(String filterType, QuerySolution querySolution) {
        Car car = new Car();
        QueryUtils.execValue(querySolution, filterType, car::setName);
        QueryUtils.execValue(querySolution, "Price", v -> car.setPrice(Double.parseDouble(v)));
        QueryUtils.execValue(querySolution, "MPG", v -> car.setMpg(Integer.parseInt(v)));
        QueryUtils.execValue(querySolution, "Year", v -> car.setYear(Integer.parseInt(v)));
        QueryUtils.execValue(querySolution, "Seat", v -> car.setSeat(Integer.parseInt(v)));
        QueryUtils.execValue(querySolution, "EngineCapacity", v -> car.setEngineCapacity(Integer.parseInt(v)));
        QueryUtils.execValue(querySolution, "HorsePower", v -> car.setHorsePower(Integer.parseInt(v)));
        return car;
    }

    public static String constructQuery(String queryString) {
        return BASE_QUERY + queryString;
    }

    public static void execSelect(String queryString, String type, Executor<List<Car>> executor) {
        ArrayList<Car> cars = new ArrayList<>();
        Model model = getModel();
        QueryUtils.execSelect(model, CarsOwlUtils.constructQuery(queryString), result -> {
            cars.clear();
            while (result.hasNext()) {
                QuerySolution querySolution = result.nextSolution();
                cars.add(CarsOwlUtils.extractModel(type, querySolution));
            }
            executor.execute(cars);
        });
    }
}
