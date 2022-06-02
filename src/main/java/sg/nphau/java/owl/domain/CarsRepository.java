/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.domain;

import sg.nphau.java.owl.data.models.Car;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.Future;

public interface CarsRepository {

    List<Car> getCarsByPrice(@Nullable String price);

    List<Car> filterCarsByPrice(List<Car> cars, String price);

    Future<List<Car>> getCars(@Nullable String type);


}
