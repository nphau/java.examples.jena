/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.data.repositories.v2;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sg.nphau.java.owl.domain.v1.CarsRepositoryV1;
import sg.nphau.java.owl.domain.v2.CarsRepositoryV2;
import sg.nphau.java.owl.shared.BaseTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CarsRepositoryV2ImplTest extends BaseTest {


    @Autowired
    private CarsRepositoryV1 carsRepositoryV1;


    @Autowired
    private CarsRepositoryV2 carsRepositoryV2;

}