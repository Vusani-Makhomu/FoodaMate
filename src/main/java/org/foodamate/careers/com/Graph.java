package org.foodamate.careers.com;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private String[] apiData;
    private final List<String> resultGraph = new ArrayList<>();
    private String startDate;
    private String endDate;
    private final List<Long> userBaseValues = new ArrayList<>();
    private final List<String> dateValues = new ArrayList<>();
    private final List<Integer> percentageIncreaseList = new ArrayList<>();


    public Graph(String[] data, String startDate, String endDate) {
        apiData = data;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Graph() {

    }

    public void setApiData(String[] data) {
        this.apiData = data;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void extractUserBaseAndDateValues() {
        for (String eachDataSet : apiData) {
            String[] data = eachDataSet.split("=");
            String dataDate = data[0].strip();
            Long dataValue = Long.parseLong(data[1]);
            userBaseValues.add(dataValue);
            dateValues.add(dataDate);
        }
    }

    public void calculatePercentageIncrease() {
        double originalNumber = 0;
        if (userBaseValues.size() > 0) originalNumber = userBaseValues.get(0);
        for (int index=0; index<userBaseValues.size(); index++) {
            if (index+1 <= userBaseValues.size()) {
                double newNumber = userBaseValues.get(index);
                double increase = newNumber-originalNumber;
                double percentageIncrease = (increase/originalNumber) * 100;
                int roundedPercentageIncrease = (int) Math.ceil(percentageIncrease);
                percentageIncreaseList.add(roundedPercentageIncrease);
                originalNumber = newNumber;
            }
        }
    }

    public String returnNumAsterisks(int num) {
        return "*".repeat(num);
    }

    public void plotGraph() {
        boolean start = false;
        for (int index=0; index<percentageIncreaseList.size(); index++) {
            String currentDate = dateValues.get(index);
            if (currentDate.equals(startDate)) start = true;
            else if (start) {
                int percentageIncrease = percentageIncreaseList.get(index);
                String graphLine = dateValues.get(index) + ": " + (returnNumAsterisks(percentageIncrease)) + " " + percentageIncrease + "%";
                resultGraph.add(graphLine);
            }
            if (currentDate.equals(endDate)) start = false;

        }
    }
    public List<String> returnResultGraph() {
        return new ArrayList<>(resultGraph);
    }

    public String graphDateRangeInformation() {
        return "\n***Graph information***\n"+
                "Start Date: "+startDate+"\n"+
                "End Date: "+endDate+"\n\n";
    }
}