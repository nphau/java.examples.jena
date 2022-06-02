/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.data.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sg.nphau.java.owl.data.models.Car;
import sg.nphau.java.owl.domain.CarsRepository;
import sg.nphau.java.owl.shared.BaseTest;

import java.util.List;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarsRepositoryImplTest extends BaseTest {

    @Autowired
    private CarsRepository carsRepository;

    @Test
    public void whenFindCarByType_thenReturnCars() {
        Future<List<Car>> future = carsRepository.getCars(null);
        try {
            List<Car> cars = future.get();
            Assert.assertTrue(cars.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindCarByPrice_thenReturnCars() {
        try {
            List<Car> cars = carsRepository.getCarsByPrice("27150");
            Assert.assertEquals(2, cars.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
