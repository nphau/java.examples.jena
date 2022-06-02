/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sg.nphau.java.owl.data.models.Car;
import sg.nphau.java.owl.domain.CarsRepository;
import sg.nphau.java.owl.domain.PropertiesRepository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

@RestController
@EnableAsync
public class CarsController {

    private final Gson gson;
    private final CarsRepository carsRepository;

    @Autowired
    public CarsController(CarsRepository carsRepository, Gson gson) {
        this.carsRepository = carsRepository;
        this.gson = gson;
    }

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/cars")
    @ResponseBody
    public List<Car> getCars(@RequestParam(name = "type", required = false) String type,
                             @RequestParam(name = "price", required = false) String price) {
        Future<List<Car>> future = carsRepository.getCars(type);
        try {
            List<Car> cars = future.get();
            if (price != null)
                cars = carsRepository.filterCarsByPrice(cars, price);
            return cars;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @GetMapping("/cars/categories")
    public List<String> getCategories() {
        return PropertiesRepository.CATEGORIES;
    }

    @GetMapping("/cars/engines")
    public List<String> getEngines() {
        return PropertiesRepository.ENGINES;
    }
}