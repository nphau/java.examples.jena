/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.controllers.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import sg.nphau.java.owl.controllers.BaseController;
import sg.nphau.java.owl.data.models.BaseResponse;
import sg.nphau.java.owl.data.models.Car;
import sg.nphau.java.owl.data.models.Engine;
import sg.nphau.java.owl.domain.v1.CarsRepositoryV1;
import sg.nphau.java.owl.domain.v2.CarsRepositoryV2;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

@RestController
@EnableAutoConfiguration
@Component
@EnableAsync
public class CarsControllerV2 extends BaseController {

    private final String VERSION = "v2";

    @Autowired
    private CarsRepositoryV2 carsRepositoryV2;

    @Autowired
    private CarsRepositoryV1 carsRepositoryV1;

    @ResponseBody
    @GetMapping(value = "/" + VERSION + "/cars")
    public BaseResponse<List<Car>> getCars(@RequestParam(name = "type", required = false) String type,
                                           @RequestParam(name = "price", required = false) String price,
                                           @RequestParam(name = "year", required = false) String year,
                                           @RequestParam(name = "seat", required = false) String seat) {
        Future<BaseResponse<List<Car>>> future = carsRepositoryV2.getCars(type);
        try {
            BaseResponse<List<Car>> response = future.get();
            if (price != null) {
                List<Car> cars = response.getData();
                cars = carsRepositoryV1.filterCarsByPrice(cars, price);
                response.setData(cars);
            }
            if (year != null) {
                List<Car> cars = response.getData();
                cars = carsRepositoryV1.filterCarsByYear(cars, year);
                response.setData(cars);
            }
            if (seat != null) {
                List<Car> cars = response.getData();
                cars = carsRepositoryV1.filterCarsBySeat(cars, seat);
                response.setData(cars);
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BaseResponse<>("", Collections.emptyList());
    }

    @ResponseBody
    @GetMapping(value = "/" + VERSION + "/cars/engines")
    public BaseResponse<List<Engine>> getEngines(@RequestParam(name = "type", required = false) String type) {
        Future<BaseResponse<List<Engine>>> future = carsRepositoryV2.getEngines(type);
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BaseResponse<>("", Collections.emptyList());
    }

}
