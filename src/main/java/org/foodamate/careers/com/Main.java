package org.foodamate.careers.com;

import java.util.List;

/*
* @author: Vusani Makhomu
* @email: makhomuvusani23@gmail.com
* */

public class Main {

    public static String helpMessage() {
        return "\nUsage of Main.java {start-date} {end-date}\n\n"+
                "Where {start-date} and {end-date} are optional arguments.\n"+
                "One could choose to run the program without these arguments. The command would be as follows:\n"+
                "javac org.foodamate.careers.com.Main.java\n\n"+
                "If one decides to run the program with these commands, the command would be as follows:\n"+
                "javac org.foodamate.careers.com.Main.java 01-04-2022 10-08-2022\n\n"+
                "Where 01-04-2022 represent the start date and 10-08-2022 represent the end date.\n"+
                "Note that this date range is inclusive. Meaning that 10-08-2022 will be included in the plotted graph.\n\n";
    }

    public static void main(String[] args) {

        if (args.length == 1) {
            if (args[0].equals("help")) {
                System.out.println(helpMessage());
                return;
            }
        }

        System.out.println("***Fetching data from  API***");
        System.out.println();
        String apiUrl = "http://sam-user-activity.eu-west-1.elasticbeanstalk.com/";
        APIConnection apiConnection = new APIConnection(apiUrl);
        apiConnection.retrieveAPIContents();
        if (apiConnection.isConnected()) {
            System.out.println("***Data fetched successfully***");
            System.out.println();
            ParseAPIContents parseAPIContents = new ParseAPIContents(apiConnection.getApiContents());
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
            Graph graph = new Graph(apiStringData, startDate, endDate);
            System.out.println(graph.graphDateRangeInformation());
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
        }
        else {
            System.out.println();
            System.out.println(apiConnection.errorMessage());
            System.out.println();
        }
    }
}




