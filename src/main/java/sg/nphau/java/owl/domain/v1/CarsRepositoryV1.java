/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.domain.v1;

import sg.nphau.java.owl.data.models.Car;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.Future;

public interface CarsRepositoryV1 {

    List<Car> getCarsByPrice(@Nullable String price);

    List<Car> filterCarsByPrice(List<Car> cars, String price);

    List<Car> filterCarsByYear(List<Car> cars, String year);

    List<Car> filterCarsBySeat(List<Car> cars, String seat);

    Future<List<Car>> getCars(@Nullable String type);


}
