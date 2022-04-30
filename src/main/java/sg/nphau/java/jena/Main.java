package sg.nphau.java.jena;

import sg.nphau.java.jena.riot.RiotExamples;
import sg.nphau.java.jena.sparql.SparqlExamples;

public class Main {

    public static void main(String[] args) {

        // SparQl
        //SparqlExamples.createModel();
        SparqlExamples.createModelWithResourceFactory();

        // Riot
        //RiotExamples.readModel();
        // RiotExamples.parseTriples();
        // RiotExamples.parseQuad();
    }


}
