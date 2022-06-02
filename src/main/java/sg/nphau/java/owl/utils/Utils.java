/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.utils;

import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Map;

public class Utils {

    public static Model getModel(ClassLoader loader, String path) {
        FileManager.get().addLocatorClassLoader(loader);
        return FileManager.get().loadModel(path);
    }

    public static Model getModel(String filePath) {
        Model model = ModelFactory.createDefaultModel();
        return model.read(filePath);
    }

    public static String nullOrDefault(@Nullable String value, String defaultValue) {
        if (value == null)
            return defaultValue;
        return value;
    }

    public static InputStream getResourceAsStream(String filename) {
        return Utils.class.getClassLoader().getResourceAsStream(filename);
    }

    public static void print(Map<Node, Integer> map) {
        for (Node node : map.keySet()) {
            System.out.println(node + " = " + map.get(node));
        }
    }

    public static void printPerGraph(Map<Node, Map<Node, Integer>> map) {
        for (Node graph : map.keySet()) {
            System.out.println("---- " + graph + " ----");
            print(map.get(graph));
        }
    }
}
