/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.domain.v2;

import sg.nphau.java.owl.data.models.BaseResponse;
import sg.nphau.java.owl.data.models.Car;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.Future;

public interface CarsRepositoryV2 {

    Future<BaseResponse<List<Car>>> getCars(@Nullable String type);
    
}
