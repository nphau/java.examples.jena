/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.ont;

public enum OntConfigs {

    VEHICLES(
            "data/owl/v3/vehicle.rdf",
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
                    "PREFIX : <http://www.semanticweb.org/isuru/ontologies/2020/10/toyota_vehicles#>" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
    );

    public final String path;
    public final String baseQuery;

    OntConfigs(String path, String baseQuery) {
        this.path = path;
        this.baseQuery = baseQuery;
    }
}
