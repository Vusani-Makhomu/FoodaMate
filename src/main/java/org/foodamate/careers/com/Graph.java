package org.foodamate.careers.com;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private String[] apiData;
    private final List<String> resultGraph = new ArrayList<>();
    private String startDateValue;
    private String endDateValue;
    private final List<Long> userBaseValues = new ArrayList<>();
    private final List<String> dateValues = new ArrayList<>();
    private final List<Integer> percentageIncreaseList = new ArrayList<>();


    public Graph(String[] data, String startDateValue, String endDateValue) {
        apiData = data;
        this.startDateValue = startDateValue;
        this.endDateValue = endDateValue;
    }

    public Graph() {

    }

    public void setApiData(String[] data) {
        this.apiData = data;
    }

    public void setStartDateValue(String startDateValue) {
        this.startDateValue = startDateValue;
    }

    public void setEndDateValue(String endDateValue) {
        this.endDateValue = endDateValue;
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
            String currentDateValue = dateValues.get(index);
            long userBaseValue = userBaseValues.get(index);
            if (currentDateValue.equals(startDateValue)) start = true;
            if (start) {
                int percentageIncrease = percentageIncreaseList.get(index);
                String graphLine = dateValues.get(index) + ": " + (returnNumAsterisks(divide(userBaseValue)))+
                        " (" + userBaseValue + ") " + percentageIncrease + "%";
                resultGraph.add(graphLine);
            }
            if (currentDateValue.equals(endDateValue)) start = false;

        }
    }

    public int divide(long num) {
        String[] digits = String.valueOf(num).split("");
        int result;
        if (digits.length >= 3) {
            result = Integer.parseInt(digits[0] + digits[1]);
        } else {
            StringBuilder stringResult = new StringBuilder();
            for (String digit: digits) stringResult.append(digit);
           result = Integer.parseInt(stringResult.toString());
        }
        return result;
    }
    public List<String> returnResultGraph() {
        return new ArrayList<>(resultGraph);
    }

    public String graphDateRangeInformation() {
        return "\n***Graph information***\n"+
                "Start Date: "+ startDateValue +"\n"+
                "End Date: "+ endDateValue +"\n";
    }
}