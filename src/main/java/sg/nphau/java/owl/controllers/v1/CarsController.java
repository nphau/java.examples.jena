/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sg.nphau.java.owl.controllers.BaseController;
import sg.nphau.java.owl.data.models.Car;
import sg.nphau.java.owl.domain.v1.CarsRepositoryV1;
import sg.nphau.java.owl.domain.PropertiesRepository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

@RestController
@EnableAutoConfiguration
@Component
@EnableAsync
public class CarsController extends BaseController {

    @Autowired
    private CarsRepositoryV1 carsRepositoryV1;

    @GetMapping("/cars")
    @ResponseBody
    public List<Car> getCars(@RequestParam(name = "type", required = false) String type,
                             @RequestParam(name = "price", required = false) String price) {
        Future<List<Car>> future = carsRepositoryV1.getCars(type);
        try {
            List<Car> cars = future.get();
            if (price != null)
                cars = carsRepositoryV1.filterCarsByPrice(cars, price);
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
