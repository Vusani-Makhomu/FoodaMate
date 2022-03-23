package org.foodamate.careers.com;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
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
            JsonElement element = JsonParser.parseString(contents);
            Object[] apiData = element.getAsJsonObject().entrySet().toArray();
            String[] apiStringData = Arrays.stream(apiData).map(Object::toString).toArray(String[]::new);
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
            System.out.println("***org.foodamate.careers.com.Graph information***");
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
            System.out.println("***org.foodamate.careers.com.Graph plotted successfully***");
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
