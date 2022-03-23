package org.foodamate.careers.com;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class APIConnection {
    private boolean connected;
    private String apiContents;
    private String url;

    public APIConnection(String url) {
        this.url = url;
    }
    public APIConnection() {}

    public void setUrl(String url) {
        this.url = url;
    }

    public void retrieveAPIContents() {
        try (InputStream inputStream = new URL(url).openStream()) {
            apiContents = new String(inputStream.readAllBytes());
            connected = true;
        } catch (IOException e) {
            connected = false;
        }
    }

    public boolean isConnected() {
        return  connected;
    }

    public String getApiContents() {
        return apiContents;
    }

    public String errorMessage() {
        return "***An error occurred. Program failed to fetch data from API***\n"+
                "***Please try again.***\n"+
                "***If error persists, please check if the API is running:"+url+"***";
    }
}
