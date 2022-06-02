/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.data.repositories;

import com.google.gson.Gson;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Model;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sg.nphau.java.owl.domain.CarsRepository;
import sg.nphau.java.owl.data.models.Car;
import sg.nphau.java.owl.ont.OntConfigs;
import sg.nphau.java.owl.domain.PropertiesRepository;
import sg.nphau.java.owl.utils.QueryUtils;
import sg.nphau.java.owl.utils.Utils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class CarsRepositoryImpl implements CarsRepository {

    private static final String PATH = OntConfigs.VEHICLES.path;
    private static final String BASE_QUERY = OntConfigs.VEHICLES.baseQuery;

    @Override
    public List<Car> getCarsByPrice(@Nullable String price) {
        try {
            final String filterType = Utils.nullOrDefault(price, "?Price");
            final double filterPrice = Double.parseDouble(filterType);
            List<Car> cars = getCars(null).get();
            return cars.stream().filter(car -> car.getPrice() == filterPrice).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Car> filterCarsByPrice(List<Car> cars, String price) {
        try {
            final double filterPrice = Double.parseDouble(price);
            return cars.stream().filter(car -> car.getPrice() == filterPrice).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Async
    @Override
    public Future<List<Car>> getCars(@Nullable String type) {

        final String filterType = Utils.nullOrDefault(type, "all");
        Model model = Utils.getModel(PATH);
        ArrayList<Car> cars = new ArrayList<>();
        CompletableFuture<List<Car>> completableFuture = new CompletableFuture<>();

        if (PropertiesRepository.ENGINES.contains(filterType)) {
            String queryString = "SELECT ?" + filterType + " ?EngineCapacity ?HorsePower" +
                    "WHERE {" +
                    "?" + filterType + " rdf:type :" + filterType + "." +
                    "?" + filterType + " :hasEngineCapacityValue ?EngineCapacity." +
                    "?" + filterType + " :hasEngineHorsePowerValue ?HorsePower." +
                    "}";
            QueryUtils.execSelect(model, constructQuery(queryString), result -> {
                cars.clear();
                while (result.hasNext()) {
                    QuerySolution querySolution = result.nextSolution();
                    cars.add(extractModel(filterType, querySolution));
                }
                completableFuture.complete(cars);
            });
        } else {
            String queryString = "SELECT ?" + filterType + " ?Price ?MPG ?Year ?Seat" +
                    "WHERE {" +
                    "?" + filterType + " rdf:type :" + filterType + "." +
                    "?" + filterType + " :hasPriceValue ?Price." +
                    "?" + filterType + " :hasCombinedMPGValue ?MPG." +
                    "?" + filterType + " :hasManufacturedYear ?Year." +
                    "?" + filterType + " :hasSeatingValue ?Seat." +
                    "}";
            if ("all".equals(filterType)) {
                queryString = "SELECT ?" + filterType + " ?Price ?MPG ?Year ?Seat" +
                        "WHERE {" +
                        "?" + filterType + " rdf:type owl:NamedIndividual." +
                        "?" + filterType + " :hasPriceValue ?Price." +
                        "?" + filterType + " :hasCombinedMPGValue ?MPG." +
                        "?" + filterType + " :hasManufacturedYear ?Year." +
                        "?" + filterType + " :hasSeatingValue ?Seat." +
                        "}";
            }
            QueryUtils.execSelect(model, constructQuery(queryString), result -> {
                while (result.hasNext()) {
                    QuerySolution querySolution = result.nextSolution();
                    cars.add(extractModel(filterType, querySolution));
                }
                completableFuture.complete(cars);
            });
        }
        return completableFuture;
    }

    private Car extractModel(String filterType, QuerySolution querySolution) {
        Car car = new Car();
        QueryUtils.execValue(querySolution, filterType, car::setName);
        QueryUtils.execValue(querySolution, "Price", v -> car.setPrice(Double.parseDouble(v)));
        QueryUtils.execValue(querySolution, "MPG", v -> car.setMpg(Integer.parseInt(v)));
        QueryUtils.execValue(querySolution, "Year", v -> car.setYear(Integer.parseInt(v)));
        QueryUtils.execValue(querySolution, "Seat", v -> car.setSeat(Integer.parseInt(v)));
        QueryUtils.execValue(querySolution, "EngineCapacity", v -> car.setEngineCapacity(Integer.parseInt(v)));
        QueryUtils.execValue(querySolution, "HorsePower", v -> car.setHorsePower(Integer.parseInt(v)));
        System.out.println(new Gson().toJson(car));
        return car;
    }

    private String constructQuery(String queryString) {
        return BASE_QUERY + queryString;
    }
}
