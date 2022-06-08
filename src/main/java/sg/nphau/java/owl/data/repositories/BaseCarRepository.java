/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.data.repositories;

import sg.nphau.java.owl.domain.PropertiesRepository;
import sg.nphau.java.owl.utils.Utils;

import javax.annotation.Nullable;

public abstract class BaseCarRepository {

    protected static final String GET_ALL = "all";

    protected String getQueryStringByType(@Nullable String type) {
        String filterType = Utils.nullOrDefault(type, GET_ALL);
        String queryString = CarsOwlUtils.getAllByType(filterType);
        if (PropertiesRepository.ENGINES.contains(filterType)) {
            queryString = "SELECT ?" + filterType + " ?EngineCapacity ?HorsePower" + "WHERE {" + "?" + filterType + " rdf:type :" + filterType + "." + "?" + filterType + " :hasEngineCapacityValue ?EngineCapacity." + "?" + filterType + " :hasEngineHorsePowerValue ?HorsePower." + "}";
        } else if (GET_ALL.equals(filterType)) {
            queryString = CarsOwlUtils.getAll(GET_ALL);
        }
        return queryString;
    }
}
