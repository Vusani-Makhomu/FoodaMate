package org.foodamate.careers.com;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Vusani Makhomu
 * @email: makhomuvusani23@gmail.com
 * This class represents the Graph object.
 * */
public class Graph {
    private String[] apiData;
    private final List<String> resultGraph = new ArrayList<>();
    private String startDateValue;
    private String endDateValue;
    private final List<Long> userBaseValues = new ArrayList<>();
    private final List<String> dateValues = new ArrayList<>();
    private final List<Integer> percentageIncreaseList = new ArrayList<>();
   private int currentHighestNumAsterisks;

    /**
     * Instantiates a new graph object containing api data, start date value, and end date value.
     * @param data Array containing api data in following format: [{date1}={user base value1}, {date2}={user base value2}]
     * @param startDateValue The start date in following format: dd-mm-yyyy
     * @param endDateValue The end date in the following format: dd-mm-yyyy
     * */
    public Graph(String[] data, String startDateValue, String endDateValue) {
        apiData = data;
        this.startDateValue = startDateValue;
        this.endDateValue = endDateValue;
    }

    /**
     * Instantiates a new graph object containing no information.
     * */
    public Graph() {

    }

    /**
     * Set the api data of a Graph object.
     * @param data The api data passed to the Graph object.
     * */
    public void setApiData(String[] data) {
        this.apiData = data;
    }

    /**
     * Set the start date value of a Graph.
     * @param startDateValue The start date value.
     * */
    public void setStartDateValue(String startDateValue) {
        this.startDateValue = startDateValue;
    }

    /**
     * Set the end date value/ of a Graph.
     * @param endDateValue The end date value.
     * */
    public void setEndDateValue(String endDateValue) {
        this.endDateValue = endDateValue;
    }

    /**
     * Api data is in the following format: [{date1}={user base value1}, {date2}={user base value2}]
     * This method extracts the date values and stores them in an array. It does the same with the user base values.
     * */
    public void extractUserBaseAndDateValues() {
        userBaseValues.clear();
        dateValues.clear();
        for (String eachDataSet : apiData) {
            String[] data = eachDataSet.split("=");
            String dataDate = data[0].strip();
            Long dataValue = Long.parseLong(data[1]);
            userBaseValues.add(dataValue);
            dateValues.add(dataDate);
        }
    }

    /**
     * @return A list of user base values.
     * */
    public List<Long> getUserBaseValues() {
        return userBaseValues;
    }

    /**
     * @return A list of date values.
     * */
    public List<String> getDateValues() {
        return dateValues;
    }

    /**
     * Using user base values, this method calculates the percentage increase.
     * Only the user base falling within the specified date range are considered.
     * */
    public void calculatePercentageIncrease() {
        percentageIncreaseList.clear();
        double originalNumber = 0;
        boolean start = false;
        for (int index=0; index<userBaseValues.size(); index++) {
            // Check if we have reached the start of the date range.
            if (dateValues.get(index).equals(startDateValue)) {
                // We have reached the start of the range. Make a note of it.
                start = true;
                originalNumber = userBaseValues.get(index);
            }
            if (index+1 <= userBaseValues.size() && start) {
                // Calculate the percentage increase
                double newNumber = userBaseValues.get(index);
                double increase = newNumber-originalNumber;
                double percentageIncrease = (increase/originalNumber) * 100;
                int roundedPercentageIncrease = (int) Math.ceil(percentageIncrease);
                percentageIncreaseList.add(roundedPercentageIncrease);
                originalNumber = newNumber;
            } else
                percentageIncreaseList.add(0);  // For values that fall outside the date range.

            // We have reached the end of the date range.
            if (dateValues.get(index).equals(endDateValue)) start = false;
        }
    }

    /**
     * @return The list that contains the values of the percentage increase.
     * */
    public List<Integer> getPercentageIncreaseList() {
        return new ArrayList<>(percentageIncreaseList);
    }

    /**
     * @param x The number of asterisks to be returned.
     * @return x number of asterisks.
     * */
    public String returnNumAsterisks(int x) {
        return "*".repeat(x);
    }

    /**
     * Responsible for plotting each line of the graph.
     * Plots each line and store it in list.
     * Only the user base falling within the specified date range is considered.
     * */
    public void plotGraph() {
        boolean start = false;
        resultGraph.clear();
        int lastAsterisks = 0;
        for (int index=0; index<userBaseValues.size(); index++) {
            String currentDateValue = dateValues.get(index);
            long userBaseValue = userBaseValues.get(index);
            if (currentDateValue.equals(startDateValue)) start = true;
            if (start) {
                int percentageIncrease = percentageIncreaseList.get(index);
                int currentAsterisks = lastAsterisks + ((int) Math.ceil(percentageIncrease*0.01));
                String graphLine = dateValues.get(index) + ": " + (returnNumAsterisks(currentAsterisks))+
                        " (" + userBaseValue + ") " + percentageIncrease + "%";
                lastAsterisks = currentAsterisks;
                resultGraph.add(graphLine);
            }
            if (currentDateValue.equals(endDateValue)) start = false;
        }
    }

    /**
     * @return A list containing each line of the graph.
     * */
    public List<String> getResultGraph() {
        return new ArrayList<>(resultGraph);
    }

    /**
     * @return Information about the graph date range.
     * */
    public String graphDateRangeInformation() {
        return "\n***Graph information***\n"+
                "Start Date: "+ startDateValue +"\n"+
                "End Date: "+ endDateValue +"\n";
    }
}