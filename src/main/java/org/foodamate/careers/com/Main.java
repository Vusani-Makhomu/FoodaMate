package org.foodamate.careers.com;

import java.util.Arrays;
import java.util.List;

/**
* @author: Vusani Makhomu
* @email: makhomuvusani23@gmail.com
 * This is the main class.
* */

public class Main {

    /**
     * Contains the help message which explains the usage of the program.
     * @return Returns the message.
     * */
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

    /**
     * This is the main method.
     * */
    public static void main(String[] args) {

        // Checks if the user entered the help flag. Prints out the help message and stops executing if they did so.
        if (args.length == 1) {
            if (args[0].equals("help")) {
                System.out.println(helpMessage());
                return;
            }
        }

        System.out.println("***Fetching data from  API***");
        String apiUrl = "http://sam-user-activity.eu-west-1.elasticbeanstalk.com/";

        /*
        * 1. Instantiate the api connection.
        * 2. Retrieve the contents of the api.
        * 3. Check if the api is running.
        * 4. If 3 is false, let the user know by printing an api error message.
        * */

        // 1.
        APIConnection apiConnection = new APIConnection(apiUrl);

        // 2.
        apiConnection.retrieveAPIContents();

        // 3.
        if (apiConnection.isConnected()) {

            /*
            * 1. Instantiate the object to parse api contents and then parse the api contents.
            * 2. Store the parsed api contents.
            * 3. Check if the API had any user base data.
            * 4. If 3. is true, let the user know and halt execution.
            * 5. Check if the user specified the date range.
            * 6. If 5. is true, use the dates that the user specified. Else use the first and last date in the api data.
            * 7. Instantiate a graph object with the parsed api data, the start and end date.
            * 8. Extract the user base values and the date values from the parsed api data.
            * 9. Calculate the percentage increase of the user base values.
            * 10. Print out the graph date range information to the console.
            * 11. Plot the graph.
            * 12. Retrieve the plotted graph from the graph object.
            * 13. Check if graph was plotted for specified date range. If not, let user know and halt execution.
            * 14. Display the plotted graph on the console.
            * */
            System.out.println("***Data fetched successfully***");

            // 1.
            ParseAPIContents parseAPIContents = new ParseAPIContents(apiConnection.getApiContents());
            parseAPIContents.parseApiData();

            // 2.
            String[] apiStringData = parseAPIContents.getParsedApiData();

            // 3.
            if (apiStringData.length == 0) {

                // 4.
                System.out.println("No user base data was available. Try again later.");
                return;
            }

            // 5.
            boolean specifiedDateRange = args.length != 0;
            String startDate="";
            String endDate="";

           // 6.
            if (specifiedDateRange) {
                startDate = args[0];
                endDate = args[1];
            } else {
                startDate = apiStringData[0].split("=")[0].strip();
                endDate = apiStringData[apiStringData.length-1].split("=")[0].strip();
            }

            // 7.
            Graph graph = new Graph(apiStringData, startDate, endDate);

            // 8.
            graph.extractUserBaseAndDateValues();

            // 9.
            graph.calculatePercentageIncrease();

            // 10.
            System.out.println(graph.graphDateRangeInformation());

            // 11.
            graph.plotGraph();

            // 12.
            List<String> resultGraph = graph.getResultGraph();

            // 13.
            if (resultGraph.size() == 0) {
                System.out.println("Graph couldn't be plotted");
                System.out.println("Reason: No user base data for specified date range.");
                return;
            }

            // 14.
            for (String graphLine: resultGraph) {
                System.out.println(graphLine);
            }
        }
        // 4.
        else {
            System.out.println();
            System.out.println(apiConnection.errorMessage());
            System.out.println();
        }
    }
}




