/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.utils;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;

public class QueryUtils {
    public static void execSelect(Model model, String queryString, Executor<ResultSet> executor) {
        Query query = QueryFactory.create(queryString);
        ResultSet result;
        try (QueryExecution queryExecution = QueryExecutionFactory.create(query, model)) {
            result = queryExecution.execSelect();
            if (result != null)
                executor.execute(result);
        }
    }

    public static void execConstruct(Model model, String filePath, Executor<Model> executor) {
        Query query = QueryFactory.read(filePath);
        Model result;
        try (QueryExecution queryExecution = QueryExecutionFactory.create(query, model)) {
            result = queryExecution.execConstruct();
            if (result != null)
                executor.execute(result);
        }
    }

    public static void execValue(QuerySolution querySolution, String key, Executor<String> executor) {
        RDFNode node = querySolution.get(key);
        if (node != null) {
            if (node.isResource()) {
                executor.execute(querySolution.getResource(key).getLocalName());
            }
            if (node.isLiteral()) {
                executor.execute(querySolution.getLiteral(key).getString());
            }
        }
    }

}
