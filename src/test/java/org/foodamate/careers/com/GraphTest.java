package org.foodamate.careers.com;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GraphTest {
    private final static APIConnection apiConnection = new APIConnection("http://sam-user-activity.eu-west-1.elasticbeanstalk.com/");
    static ParseAPIContents parseAPIContents = new ParseAPIContents();
    private final static Graph graph = new Graph();

    @BeforeAll
    static void setGraphData() {
        apiConnection.retrieveAPIContents();
        parseAPIContents.setApiData(apiConnection.getApiContents());
        parseAPIContents.parseApiData();
        graph.setApiData(parseAPIContents.getParsedApiData());
    }

    @Test
    void correctNumAsterisks() {
        assertEquals("**********", graph.returnNumAsterisks(10));
    }

    @Test
    void incorrectNumAsterisks() {
        assertNotEquals("***", graph.returnNumAsterisks(10));
    }

//    @Test
//    void correctGraphTotal() {
//        graph.setStartDateValue("01-01-2022");
//        graph.setEndDateValue("15-01-2022");
//        assertEquals(285800.0, graph.calculateGraphTotalValue());
//    }
//
//    @Test
//    void incorrectGraphTotal() {
//        graph.setStartDateValue("01-01-2022");
//        graph.setEndDateValue("15-01-2022");
//        assertNotEquals(0.0, graph.calculateGraphTotalValue());
//    }

}
