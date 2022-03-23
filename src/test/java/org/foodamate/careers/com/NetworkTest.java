package org.foodamate.careers.com;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class NetworkTest {

    private final APIConnection apiConnection = new APIConnection();

    @Test
    void apiRunning() {
        apiConnection.setUrl("http://sam-user-activity.eu-west-1.elasticbeanstalk.com/");
        apiConnection.retrieveAPIContents();
        assertTrue(apiConnection.isConnected());
    }

}
