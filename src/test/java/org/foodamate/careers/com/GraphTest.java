package org.foodamate.careers.com;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GraphTest {
    private final static APIConnection apiConnection = new APIConnection("http://sam-user-activity.eu-west-1.elasticbeanstalk.com/");
    static ParseAPIContents parseAPIContents = new ParseAPIContents();
    static Graph graph;

    @BeforeAll
    static void setGraphData() {
        apiConnection.retrieveAPIContents();
        parseAPIContents.setApiData(apiConnection.getApiContents());
        parseAPIContents.parseApiData();
    }

    @Test
    @DisplayName("Correct number of asterisks are returned? [Test 1]")
    void correctNumAsterisks() {
        graph = new Graph();
        assertEquals("**********", graph.returnNumAsterisks(10));
    }

    @Test
    @DisplayName("Correct number of asterisks are returned? [Test 2]")
    void incorrectNumAsterisks() {
        graph = new Graph();
        assertNotEquals("***", graph.returnNumAsterisks(10));
    }

    @Test
    @DisplayName("Correctly extracts user base and date values? [Test 1]")
    void correctlyExtractUserBaseAndDataValues() {
        String[] apiStringData = parseAPIContents.getParsedApiData();
        String startDate = apiStringData[0].split("=")[0].strip();
        String endDate = apiStringData[apiStringData.length-1].split("=")[0].strip();
        graph = new Graph(apiStringData, startDate, endDate);
        graph.extractUserBaseAndDateValues();
        List<Long> expectedUserBaseValues = List.of(
                300L, 500L, 700L, 1300L, 2000L, 3000L, 3500L, 4000L,
                4500L, 5000L, 20000L, 35000L, 46000L, 70000L, 90000L
        );
        List<String> expectedDateValues = List.of(
                "01-01-2022", "02-01-2022", "03-01-2022", "04-01-2022",
                "05-01-2022", "06-01-2022", "07-01-2022", "08-01-2022",
                "09-01-2022", "10-01-2022", "11-01-2022", "12-01-2022",
                "13-01-2022", "14-01-2022", "15-01-2022"
        );
        assertEquals(expectedUserBaseValues, graph.getUserBaseValues());
        assertEquals(expectedDateValues, graph.getDateValues());
    }

    @Test
    @DisplayName("Correctly extracts user base and date values? [Test 2]")
    void incorrectlyExtractUserBaseAndDataValues() {
        String[] apiStringData = parseAPIContents.getParsedApiData();
        String startDate = apiStringData[0].split("=")[0].strip();
        String endDate = apiStringData[apiStringData.length-1].split("=")[0].strip();
        graph = new Graph(apiStringData, startDate, endDate);
        graph.extractUserBaseAndDateValues();
        List<Long> expectedUserBaseValues = List.of(
                300L
        );
        List<String> expectedDateValues = List.of(
                "01-01-2022"
        );
        assertNotEquals(expectedUserBaseValues, graph.getUserBaseValues());
        assertNotEquals(expectedDateValues, graph.getDateValues());
    }

    @Test
    @DisplayName("Correctly calculates percentage increase of user base values? [Test 1]")
    void correctlyCalculatesPercentageIncrease() {
        String[] apiStringData = parseAPIContents.getParsedApiData();
        String startDate = apiStringData[0].split("=")[0].strip();
        String endDate = apiStringData[apiStringData.length-1].split("=")[0].strip();
        graph = new Graph(apiStringData, startDate, endDate);
        graph.extractUserBaseAndDateValues();
        graph.calculatePercentageIncrease();
        List<Integer> expectedPercentageIncrease = List.of(
                0, 67, 40, 86, 54, 50, 17, 15,
                13, 12, 300, 75, 32, 53, 29
        );
        assertEquals(expectedPercentageIncrease, graph.getPercentageIncreaseList());
    }

    @Test
    @DisplayName("Correctly calculates percentage increase of user base values? [Test 2]")
    void incorrectlyCalculatesPercentageIncrease() {
        String[] apiStringData = parseAPIContents.getParsedApiData();
        String startDate = apiStringData[0].split("=")[0].strip();
        String endDate = apiStringData[apiStringData.length-1].split("=")[0].strip();
        graph = new Graph(apiStringData, startDate, endDate);
        graph.extractUserBaseAndDateValues();
        graph.calculatePercentageIncrease();
        List<Integer> expectedPercentageIncrease = List.of(
                0
        );
        assertNotEquals(expectedPercentageIncrease, graph.getPercentageIncreaseList());
    }

    @Test
    @DisplayName("Correctly extracts first 2 digits? [Test 1]")
    void correctlyExtractFirstTwoDigits() {
        graph = new Graph();
        assertEquals(10, graph.extractFirstTwoDigits(100L));
        assertEquals(10, graph.extractFirstTwoDigits(1000L));
        assertEquals(15, graph.extractFirstTwoDigits(150L));
        assertEquals(15, graph.extractFirstTwoDigits(1500L));
        assertEquals(23, graph.extractFirstTwoDigits(230L));
        assertEquals(20, graph.extractFirstTwoDigits(20L));
    }

    @Test
    @DisplayName("Correctly extracts first 2 digits? [Test 2]")
    void incorrectlyDivideByNumberMultipleFromHundred() {
        graph = new Graph();
        assertNotEquals(1, graph.extractFirstTwoDigits(100L));
        assertNotEquals(1, graph.extractFirstTwoDigits(1000L));
        assertNotEquals(1, graph.extractFirstTwoDigits(150L));
        assertNotEquals(10, graph.extractFirstTwoDigits(1500L));
        assertNotEquals(2, graph.extractFirstTwoDigits(230L));
        assertNotEquals(2, graph.extractFirstTwoDigits(20L));
    }

    @Test
    @DisplayName("Correctly plots graph when not given date range? [Test 1]")
    void correctlyPlotsGraphWithoutDateRange() {
        String[] apiStringData = parseAPIContents.getParsedApiData();
        String startDate = apiStringData[0].split("=")[0].strip();
        String endDate = apiStringData[apiStringData.length-1].split("=")[0].strip();
        graph = new Graph(apiStringData, startDate, endDate);
        List<String> expectedGraph = List.of(
           "01-01-2022: ****************************** (300) 0%",
           "02-01-2022: ************************************************** (500) 67%",
           "03-01-2022: ********************************************************************** (700) 40%",
           "04-01-2022: ************* (1300) 86%",
           "05-01-2022: ******************** (2000) 54%",
           "06-01-2022: ****************************** (3000) 50%",
           "07-01-2022: *********************************** (3500) 17%",
           "08-01-2022: **************************************** (4000) 15%",
           "09-01-2022: ********************************************* (4500) 13%",
           "10-01-2022: ************************************************** (5000) 12%",
           "11-01-2022: ******************** (20000) 300%",
           "12-01-2022: *********************************** (35000) 75%",
           "13-01-2022: ********************************************** (46000) 32%",
           "14-01-2022: ********************************************************************** (70000) 53%",
           "15-01-2022: ****************************************************************************************** (90000) 29%"
        );
        graph.extractUserBaseAndDateValues();
        graph.calculatePercentageIncrease();
        graph.plotGraph();
        assertEquals(expectedGraph, graph.getResultGraph());
    }

    @Test
    @DisplayName("Correctly plots graph when not given date range? [Test 2]")
    void incorrectlyPlotsGraphWithoutDateRange() {
        String[] apiStringData = parseAPIContents.getParsedApiData();
        String startDate = "01-01-2022";
        String endDate = "15-01-2022";
        graph = new Graph(apiStringData, startDate, endDate);
        List<String> expectedGraph = List.of(
                "01-01-2022: ****************************** (300) 0%"
        );
        graph.extractUserBaseAndDateValues();
        graph.calculatePercentageIncrease();
        graph.plotGraph();
        assertNotEquals(expectedGraph, graph.getResultGraph());
    }

    @Test
    @DisplayName("Correctly plots graph when given date range? [Test 1]")
    void correctlyPlotsGraphGivenDateRange() {
        String[] apiStringData = parseAPIContents.getParsedApiData();
        String startDate = "10-01-2022";
        String endDate = "15-01-2022";
        graph = new Graph(apiStringData, startDate, endDate);
        List<String> expectedGraph = List.of(
              "10-01-2022: ************************************************** (5000) 12%",
              "11-01-2022: ******************** (20000) 300%",
              "12-01-2022: *********************************** (35000) 75%",
              "13-01-2022: ********************************************** (46000) 32%",
              "14-01-2022: ********************************************************************** (70000) 53%",
              "15-01-2022: ****************************************************************************************** (90000) 29%"
        );
        graph.extractUserBaseAndDateValues();
        graph.calculatePercentageIncrease();
        graph.plotGraph();
        assertEquals(expectedGraph, graph.getResultGraph());
    }

    @Test
    @DisplayName("Correctly plots graph when given date range? [Test 2]")
    void incorrectlyPlotsGraphGivenDateRange() {
        String[] apiStringData = parseAPIContents.getParsedApiData();
        String startDate = "10-01-2022";
        String endDate = "15-01-2022";
        graph = new Graph(apiStringData, startDate, endDate);
        List<String> expectedGraph = List.of(
                "10-01-2022: ************************************************** (5000) 12%",
                "11-01-2022: ******************** (20000) 300%"
        );
        graph.extractUserBaseAndDateValues();
        graph.calculatePercentageIncrease();
        graph.plotGraph();
        assertNotEquals(expectedGraph, graph.getResultGraph());
    }
}
