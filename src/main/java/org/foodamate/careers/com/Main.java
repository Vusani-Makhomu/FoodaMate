package org.foodamate.careers.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* @author: Vusani Makhomu
* @email: makhomuvusani23@gmail.com
* */

public class Main {

    public static void main(String[] args) {

        if (args.length == 1) {
            if (args[0].equals("help")) {
                System.out.println("usage of org.foodamate.careers.com.Main.java {start-date} {end-date}");
                System.out.println("Where {start-date} and {end-date} are optional arguments.");
                System.out.println("One could choose to run the program without these arguments. The command would be as follows:");
                System.out.println("javac org.foodamate.careers.com.Main.java");
                System.out.println("If one decides to run the program with these commands, the command would be as follows:");
                System.out.println("javac org.foodamate.careers.com.Main.java 01-04-2022 10-08-2022");
                System.out.println("Where 01-04-2022 represent the start date and 10-08-2022 represent the end date.");
                System.out.println("Note that this date range is inclusive. Meaning that 10-08-2022 will be included in the plotted graph.");
                return;
            }
        }

        System.out.println("***Fetching data from  API***");
        System.out.println();
        String api = "http://sam-user-activity.eu-west-1.elasticbeanstalk.com/";
        try(java.io.InputStream is = new java.net.URL(api).openStream()) {
            System.out.println("***Data fetched successfully***");
            String contents = new String(is.readAllBytes());
            ParseAPIContents parseAPIContents = new ParseAPIContents(contents);
            parseAPIContents.parseApiData();
            String[] apiStringData = parseAPIContents.getParsedApiData();
            boolean specifiedDateRange = args.length != 0;
            String startDate="";
            String endDate="";
            if (specifiedDateRange) {
                startDate = args[0];
                endDate = args[1];
            } else {
                startDate = apiStringData[0].split("=")[0].strip();
                endDate = apiStringData[apiStringData.length-1].split("=")[0].strip();
            }
            System.out.println();
            System.out.println("***Graph information***");
            System.out.println("Start Date: "+startDate);
            System.out.println("End Date: "+endDate);
            System.out.println();
            Graph graph = new Graph(apiStringData, startDate, endDate);
            System.out.println("***Plotting graph***");
            System.out.println();
            graph.plotGraph();
            List<String> resultGraph = graph.returnResultGraph();
            for (String graphLine: resultGraph) {
                System.out.println(graphLine);
            }
            System.out.println();
            System.out.println("***Graph plotted successfully***");
            System.out.println();
        } catch (IOException e) {
            System.out.println("***An error occurred. Program failed to fetch data from API***");
            System.out.println("***Please try again.***");
            System.out.println();
            System.out.println("***If error persists, please check if the API is running:"+api+"***");
            System.out.println();
        }
    }
}

class Graph {
    private final String[] graphData;
    private final List<String> resultGraph = new ArrayList<>();
    private final String startDate;
    private final String endDate;

    public Graph(String[] data, String startDate, String endDate) {
        graphData = data;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public double calculateGraphTotalValue() {
        double result = 0;
        boolean start = false;
        for (String eachDataSet : graphData) {
            String[] data = eachDataSet.split("=");
            String dataDate = data[0].strip();
            if (dataDate.equals(startDate)) start = true;
            if (start) {
                double dataValue = Double.parseDouble(data[1]);
                result += dataValue;
            }
            if (dataDate.equals(endDate)) start = false;
        }
        return result;
    }

    public String returnNumAsterisks(int num) {
        return "*".repeat(num);
    }

    public void plotGraph() {
        double totalValue = calculateGraphTotalValue();
        boolean start = false;
        for (String eachDataSet: graphData) {
            String[] data = eachDataSet.split("=");
            String dataDate = data[0].strip();
            if (dataDate.equals(startDate)) start = true;
            if (start) {
                double dataValue = Double.parseDouble(data[1]);
                double percentage = (dataValue/totalValue)*100;
                int intPercentage = (int) Math.ceil(percentage);
                String graphPlotLine = dataDate + ": " + (returnNumAsterisks(intPercentage)) + " " + (intPercentage) + "%";
                resultGraph.add(graphPlotLine);
            }
            if (dataDate.equals(endDate)) start = false;
        }
    }
    public List<String> returnResultGraph() {
        return new ArrayList<>(resultGraph);
    }
}

class ParseAPIContents {
    private String apiData;
    private String[] parsedApiData;

    public ParseAPIContents(String contents) {
        apiData = contents;
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
