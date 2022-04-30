package sg.nphau.java.jena.riot;

import org.apache.jena.atlas.lib.Sink;
import org.apache.jena.atlas.lib.SinkWrapper;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.vocabulary.RDF;

import java.util.HashMap;
import java.util.Map;

public class SinkQuadStats extends SinkWrapper<Quad> {

    private long count = 0;
    private Map<Node, Integer> classes = new HashMap<>();
    private Map<Node, Integer> properties = new HashMap<>();
    private Map<Node, Integer> namespaces = new HashMap<>();
    private Map<Node, Map<Node, Integer>> classesPerGraph = new HashMap<>();
    private Map<Node, Map<Node, Integer>> propertiesPerGraph = new HashMap<>();
    private Map<Node, Map<Node, Integer>> namespacesPerGraph = new HashMap<>();

    public SinkQuadStats(Sink<Quad> sink) {
        super(sink);
    }

    public long getCount() {
        return count;
    }

    @Override
    public void send(Quad quad) {
        count++;

        Node g = quad.getGraph();

        Node p = quad.getPredicate();
        Node ns = NodeFactory.createURI(p.getNameSpace());
        increment(properties, p);
        increment(namespaces, ns);
        increment(propertiesPerGraph, g, p);
        increment(namespacesPerGraph, g, ns);

        if (p.equals(RDF.type.asNode())) {
            Node o = quad.getObject();
            if (o.isURI()) {
                ns = NodeFactory.createURI(o.getNameSpace());
                increment(classes, o);
                increment(namespaces, ns);
                increment(classesPerGraph, g, o);
                increment(namespacesPerGraph, g, ns);
            }
        }

        super.send(quad);
    }

    private void increment(Map<Node, Integer> map, Node key) {
        if (map.containsKey(key)) {
            int c = map.get(key) + 1;
            map.put(key, c);
        } else {
            map.put(key, 1);
        }
    }

    private void increment(Map<Node, Map<Node, Integer>> map, Node graph, Node key) {
        if (map.containsKey(graph)) {
            increment(map.get(graph), key);
        } else {
            map.put(graph, new HashMap<>());
            increment(map.get(graph), key);
        }
    }

    public Map<Node, Integer> getProperties() {
        return properties;
    }

    public Map<Node, Integer> getClasses() {
        return classes;
    }

    public Map<Node, Integer> getNamespaces() {
        return namespaces;
    }

    public Map<Node, Map<Node, Integer>> getPropertiesPerGraph() {
        return propertiesPerGraph;
    }

    public Map<Node, Map<Node, Integer>> getClassesPerGraph() {
        return classesPerGraph;
    }

    public Map<Node, Map<Node, Integer>> getNamespacesPerGraph() {
        return namespacesPerGraph;
    }
}