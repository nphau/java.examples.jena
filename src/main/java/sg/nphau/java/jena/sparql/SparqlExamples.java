package sg.nphau.java.jena.sparql;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;

public class SparqlExamples {

    public static void createModel() {
        Model model = ModelFactory.createDefaultModel();
        model.createResource("http://example.org/alice", FOAF.Person)
                .addProperty(FOAF.name, "Alice")
                .addProperty(FOAF.mbox, model.createResource("mailto:alice@example.org"))
                .addProperty(FOAF.knows, model.createResource("http://example.org/bob"));
        model.write(System.out, "TURTLE");
    }

    public static void createModelWithResourceFactory() {
        Model model = ModelFactory.createDefaultModel();
        Resource alice = ResourceFactory.createResource("http://example.org/alice");
        Resource bob = ResourceFactory.createResource("http://example.org/bob");
        model.add(alice, RDF.type, FOAF.Person);
        model.add(alice, FOAF.name, "Alice");
        model.add(alice, FOAF.mbox, ResourceFactory.createResource("mailto:alice@example.org"));
        model.add(alice, FOAF.knows, bob);
        model.write(System.out, "TURTLE");
    }

}
