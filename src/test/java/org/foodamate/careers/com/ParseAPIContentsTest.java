package org.foodamate.careers.com;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ParseAPIContentsTest {

    private static final APIConnection apiConnection = new APIConnection("http://sam-user-activity.eu-west-1.elasticbeanstalk.com/");
    private static ParseAPIContents parseAPIContents;

    @BeforeAll
    static void retrieveAndSetAPIContents() {
        apiConnection.retrieveAPIContents();
        parseAPIContents = new ParseAPIContents(apiConnection.getApiContents());
    }

    @Test
    @DisplayName("Correctly parse api data? [Test 1]")
    void correctlyParseAPIData() {
        parseAPIContents.parseApiData();
        String[] expectedParsedAPIData = new String[]{
                "01-01-2022=300", "02-01-2022=500", "03-01-2022=700", "04-01-2022=1300", "05-01-2022=2000",
                "06-01-2022=3000", "07-01-2022=3500", "08-01-2022=4000", "09-01-2022=4500", "10-01-2022=5000",
                "11-01-2022=20000", "12-01-2022=35000", "13-01-2022=46000", "14-01-2022=70000", "15-01-2022=90000"
        };
        assertEquals(expectedParsedAPIData, parseAPIContents.getParsedApiData());
    }

    @Test
    @DisplayName("Correctly parse api data? [Test 2]")
    void incorrectlyParseAPIData() {
        String[] expectedParsedAPIData = new String[]{
                "01-01-2022=300"
        };
        assertNotEquals(expectedParsedAPIData, parseAPIContents.getParsedApiData());
    }
}
