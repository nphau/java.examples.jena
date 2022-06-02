/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.domain;

import java.util.ArrayList;

public class PropertiesRepository {

    private static final String KEY_ENGINES = "engines";
    private static final String KEY_CATEGORIES = "categories";

    public static ArrayList<String> CATEGORIES = new ArrayList<>();
    public static ArrayList<String> ENGINES = new ArrayList<>();

    static {
        ENGINES.add("4Cylinder");
        ENGINES.add("6Cylinder");
        ENGINES.add("8Cylinder");
        //
        CATEGORIES.add("Cars");
        CATEGORIES.add("HybridAndFuelCells");
        CATEGORIES.add("Crossovers");
        CATEGORIES.add("SUVs");
        CATEGORIES.add("Trucks");
        CATEGORIES.add("Minivan");
    }
}
