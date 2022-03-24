package org.foodamate.careers.com;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class APIConnectionTest {

    private final static APIConnection apiConnection = new APIConnection("http://sam-user-activity.eu-west-1.elasticbeanstalk.com/");

    @BeforeAll
    static void retrieveAPIContents() {
        apiConnection.retrieveAPIContents();
    }

    @Test
    @DisplayName("Able to connect to the API? ")
    void ableToConnectWithApi() {
        assertTrue(apiConnection.isConnected());
    }

}
