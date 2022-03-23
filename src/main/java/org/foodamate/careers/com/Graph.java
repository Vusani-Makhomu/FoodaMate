package org.foodamate.careers.com;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private String[] graphData;
    private final List<String> resultGraph = new ArrayList<>();
    private String startDate;
    private String endDate;

    public Graph(String[] data, String startDate, String endDate) {
        graphData = data;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Graph() {

    }

    public void setGraphData(String[] data) {
        this.graphData = data;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long calculateGraphTotalValue() {
        long result = 0;
        boolean start = false;
        for (String eachDataSet : graphData) {
            String[] data = eachDataSet.split("=");
            String dataDate = data[0].strip();
            if (dataDate.equals(startDate)) start = true;
            if (start) {
                long dataValue = Long.parseLong(data[1]);
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

    public String graphDateRangeInformation() {
        return "\n***Graph information***\n"+
                "Start Date: "+startDate+"\n"+
                "End Date: "+endDate+"\n\n";
    }
}