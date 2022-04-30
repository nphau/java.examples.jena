/**
 * Created by nphau on 30/04/2022, 10:30
 * Copyright (c) 2021 FUNIX. All rights reserved.
 * Last modified 30/04/2021, 15:54
 */
package sg.nphau.java.jena;

import org.apache.jena.graph.Node;

import java.io.InputStream;
import java.util.Map;

public class Utils {
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
