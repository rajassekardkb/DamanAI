package com.example.damanhacker.utlities;

import com.example.damanhacker.model.DataModelMain;
import com.example.damanhacker.model.returnData;

import java.util.ArrayList;

public class CheckSerialNumberBasics {
    int matchingClear = 0;
    int serialCheck = 6;
    int serialNext = 0;
    int serialNumberPosition = 0;
    boolean serialEnable = false;
    ArrayList<Integer> serialNumberList = new ArrayList<>();
    ArrayList<DataModelMain> dataList = new Mapping().getDummyData();
    ArrayList<String> finalResult = new ArrayList<>();
    StringBuilder colorBuilder = new StringBuilder();

    StringBuilder periodBuilder = new StringBuilder();
    StringBuilder numberBuild = new StringBuilder();

    String matchValue = "";
    String currentColor = "";


    public void patternCheckBasedOnSerialNumber() {
        prepareSerialNumber();
        DataModelMain valueCurrent = dataList.get(0);
        matchValue = valueCurrent.getColor();
        picSerialNumberBasics();

    }

    public void picSerialNumberBasics() {
        for (int j = 0; j < serialNumberList.size(); j++) {
            System.out.println("picSerialNumberBasics->" + j);
            serialNumberPosition = serialNumberList.get(j);
            getMatch(serialNumberPosition, j);
        }
        for (int k = 0; k < finalResult.size(); k++) {
            System.out.println(finalResult.get(k));
        }
    }

    public void pickData(int startPosition_) {
        returnData returnDataVl = new returnData(startPosition_, matchValue);
        colorBuilder = new StringBuilder(matchValue);
        int totalLoop = 0;
        matchingClear = 0;

        for (int i = startPosition_; i < dataList.size(); i++) {
            totalLoop++;
            //serialCheck = (list.get(i).getPeriod()) % 10;


            currentColor = dataList.get(i).getColor();
            retrieve(i, dataList.size(), dataList.get(i));
    /*        if (matchValue.equals(currentColor)) {
                value.append(currentColor);
                matchingClear++;
                if (i == list.size() - 1) {
                    addValue(value.toString());
                }
            } else {
                addValue(value.toString());
                value.setLength(0);
                matchValue = currentColor;
                value.append(matchValue);
            }*/
            //System.out.println("Serial--" + serialCheck + ":Next->" + serialNext);

        }
        // for (String result : finalResult) {
        //   System.out.println(result);
        // }
    }


    public void getMatch(int startPosition, int matchPosition) {
        String matchValue = dataList.get(matchPosition).getColor();
        String currentColor = "";
        StringBuilder value = new StringBuilder();
        for (int i = startPosition; i < dataList.size(); i++) {
            System.out.println("getMatch->" + startPosition);

            currentColor = dataList.get(i).getColor();
            if (matchValue.equals(currentColor)) {
                value
                        //.append("P->")
                        .append(dataList.get(i).getPeriod())
                        //.append(":")
                        // .append("N->")
                        .append(dataList.get(i).getNumber())
                        //.append(":")
                        // .append("C->")
                        .append(currentColor)
                        .append(" ")
                ;
                matchingClear++;
                if (i == dataList.size() - 1) {
                    addValue(value.toString());

                }
            } else {
                addValue(value.toString());
                value.setLength(0);
                matchValue = currentColor;
                value
                        //.append("P->")
                        .append(dataList.get(i).getPeriod())
                        //.append(":")
                        // .append("N->")
                        .append(dataList.get(i).getNumber())
                        //.append(":")
                        // .append("C->")
                        .append(matchValue)
                        .append(" ")
                ;
                return;
            }
        }
    }


    public void retrieve(int i, int listSize, DataModelMain data) {
        if (matchValue.equals(currentColor)) {
            colorBuilder.append(data.getPeriod()).append(":").append(data.getNumber()).append(":").append(currentColor).append("->");
            matchingClear++;
            if (i == listSize - 1) {
                addValue(colorBuilder.toString());
            }
        } else {
            addValue(colorBuilder.toString());
            colorBuilder.setLength(0);
            matchValue = currentColor;
            colorBuilder.append(data.getPeriod()).append(":").append(data.getNumber()).append(":").append(matchValue).append("->");

            //colorBuilder.append(matchValue);
        }
    }

    public void addValue(String value) {
        if (matchingClear >= 0) {
            finalResult.add(value);
        }
        matchingClear = 0;
    }

    public void addValue_(String value) {
        if (matchingClear >= 0) {
            finalResult.add(value);
        }
        matchingClear = 0;
    }

    public void print(String str) {
        System.out.println(str);
    }


    public void prepareSerialNumber() {
        serialNext = serialCheck;
        serialNumberList.add(serialNext);
        for (int i = 0; i < dataList.size(); i++) {
            if (serialCheck == (dataList.get(i).getPeriod()) % 10) {
                // System.out.println("Serial--" + serialCheck + ":Next->" + serialNext + ":" + i);

                serialEnable = true;
                serialNext += 10;
                serialNumberList.add(serialNext);
            }
        }

        for (int i = 0; i < serialNumberList.size(); i++) {
            //  System.out.println("serialNumberList--" + serialNumberList.get(i));

        }
    }


    public void checkRecursion() {
        int number = 16;

        //print first 10 numbers of fibonacci series
        System.out.println("Fibonacci Series: First 10 numbers:");
        for (int i = 0; i <= dataList.size(); i++) {
            System.out.println("Fibonacci ----------------------?" + fibonacci(i) + " ");
        }
    }

    int fibonacci(int n) {
        if (serialCheck > n) {
            return n;
        }
        return fibonacci(serialCheck);
        //return n;
    }
}
