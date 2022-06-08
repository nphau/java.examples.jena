/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.data.repositories.v1;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sg.nphau.java.owl.data.models.Car;
import sg.nphau.java.owl.data.repositories.BaseCarRepository;
import sg.nphau.java.owl.data.repositories.CarsOwlUtils;
import sg.nphau.java.owl.domain.v1.CarsRepositoryV1;
import sg.nphau.java.owl.utils.Utils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class CarsRepositoryV1Impl extends BaseCarRepository implements CarsRepositoryV1 {

    @Override
    public List<Car> getCarsByPrice(@Nullable String price) {
        try {
            List<Car> cars = getCars(GET_ALL).get();
            return filterCarsByPrice(cars, price);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Car> filterCarsByYear(List<Car> cars, String year) {
        try {
            final int filterYear = Integer.parseInt(year);
            return cars.stream().filter(car -> car.getYear() == filterYear).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Car> filterCarsBySeat(List<Car> cars, String seat) {
        try {
            final int filterSeat = Integer.parseInt(seat);
            return cars.stream().filter(car -> car.getSeat() == filterSeat).collect(Collectors.toList());
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
        String filterType = Utils.nullOrDefault(type, GET_ALL);
        String queryString = getCarQueryString(type);
        CompletableFuture<List<Car>> completableFuture = new CompletableFuture<>();
        CarsOwlUtils.execSelectCar(queryString, filterType, completableFuture::complete);
        return completableFuture;
    }

}
