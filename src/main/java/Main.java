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
            if (args[0] == "help") {
                System.out.println("The user is looking for help");
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
