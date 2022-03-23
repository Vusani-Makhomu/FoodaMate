package org.foodamate.careers.com;

import java.util.Arrays;

public class ParseAPIContents {
    private String apiData;
    private String[] parsedApiData;

    public ParseAPIContents(String contents) {
        apiData = contents;
    }

    public ParseAPIContents() {

    }

    public void setApiData(String apiData) {
        this.apiData = apiData;
    }

    private void removeCurlyBraces() {
        apiData = apiData.replace("{", "");
        apiData = apiData.replace("}", "");
        apiData = apiData.strip();
    }

    private String[] returnDataInArray() {
        return apiData.split(",");
    }

    public void parseApiData() {
        removeCurlyBraces();
        parsedApiData = returnDataInArray();
        for (int index=0;index<parsedApiData.length;index++) {
            parsedApiData[index] = parsedApiData[index].replace("\"", "").strip();
            parsedApiData[index] = parsedApiData[index].replace(":", "=");
        }
    }

    public String[] getParsedApiData() {
        return Arrays.copyOf(parsedApiData, parsedApiData.length);
    }
}
