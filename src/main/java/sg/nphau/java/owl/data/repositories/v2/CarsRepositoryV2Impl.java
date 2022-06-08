/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.data.repositories.v2;

import org.springframework.stereotype.Service;
import sg.nphau.java.owl.data.models.BaseResponse;
import sg.nphau.java.owl.data.models.Car;
import sg.nphau.java.owl.data.repositories.BaseCarRepository;
import sg.nphau.java.owl.data.repositories.CarsOwlUtils;
import sg.nphau.java.owl.domain.v2.CarsRepositoryV2;
import sg.nphau.java.owl.utils.Utils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class CarsRepositoryV2Impl extends BaseCarRepository implements CarsRepositoryV2 {

    @Override
    public Future<BaseResponse<List<Car>>> getCars(@Nullable String type) {
        String filterType = Utils.nullOrDefault(type, GET_ALL);
        CompletableFuture<BaseResponse<List<Car>>> completableFuture = new CompletableFuture<>();
        String queryString = getQueryStringByType(type);
        CarsOwlUtils.execSelect(queryString, filterType, result ->
                completableFuture.complete(new BaseResponse<>(queryString, result)));
        return completableFuture;
    }
}