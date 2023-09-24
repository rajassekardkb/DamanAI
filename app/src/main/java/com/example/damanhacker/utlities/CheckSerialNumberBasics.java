package com.example.damanhacker.utlities;


import com.example.damanhacker.model.DataModelMainData;

import java.util.ArrayList;

public class CheckSerialNumberBasics {
    int matchingClear = 0;
    int MaxRepeatedCount = 7;
    int serialNext = 0;
    int loopMax = 0;
    int serialCheck = 0;
    int PatternSerialNumber = 0;
    boolean onlyFirstTime = false;
    int currentSerialNumber = 0;
    int serialNumberPositionMoveForward = 0;
    int serialNumberPosition = 0;
    boolean serialEnable = false;

    ArrayList<Integer> serialNumberList = new ArrayList<>();
    ArrayList<Integer> parentSerialNumber = new ArrayList<>();
    ArrayList<DataModelMainData> dataList;
    ArrayList<String> finalResult = new ArrayList<>();
    StringBuilder colorBuilder = new StringBuilder();
    StringBuilder periodBuilder = new StringBuilder();
    StringBuilder numberBuild = new StringBuilder();
    String matchValue = "";
    String currentColor = "";

    public void patternCheckBasedOnSerialNumber(ArrayList<DataModelMainData> _) {
        this.dataList = _;
        for (int i = 0; i < 10; i++) {
            //currentSerialNumber = PatternSerialNumber;
            currentSerialNumber = i;
            prepareSerialNumber(currentSerialNumber);
            picSerialNumberBasics();
            finalResult.add("---------------------");
        }



       /* for (int i = 0; i < dataList.size(); i++) {
            DataModelMainData data = dataList.get(i);

            getTime(data.getPeriod());
        }*/

    }


    public void picSerialNumberBasics() {
        serialNumberPositionMoveForward = 0;
        while (serialNumberPositionMoveForward < serialNumberList.size()) {
            serialNumberPosition = serialNumberList.get(serialNumberPositionMoveForward);

            getMatch(serialNumberPosition, (serialNumberPosition + 1));
            serialNumberPositionMoveForward++;
        }
        for (int k = 0; k < finalResult.size(); k++) {
            System.out.println(k + ":" + finalResult.get(k));
        }
    }


    public void getMatch(int startPosition, int matchPosition) {
        print("PositionCheck-->" + startPosition + ":" + matchPosition + ":" + dataList.size());
        if (dataList.size() == matchPosition) {
            return;
        }
        String matchValue = dataList.get(matchPosition).getValue();

        String currentColor;

        StringBuilder value = new StringBuilder();

        value.append("\n").append("Serial->").append(currentSerialNumber);
        value.append("\n").append(new DateUtilities().getTime(dataList.get(startPosition).getPeriod()));
        loopMax = 0;

        for (int i = startPosition; i < dataList.size(); i++) {
            currentColor = dataList.get(i).getValue();
            if (matchValue.equals(currentColor)) {
                loopMax++;
                value.append("\n").append(dataList.get(i).getPeriod()).append(" : ").append(dataList.get(i).getNumber()).append(" : ").append(currentColor).append("");
                matchingClear++;
                if (i == dataList.size() - 1) {
                    addValue(value.toString());
                }
            } else {
                addValue(value.toString());
                value.setLength(0);
                matchValue = currentColor;

                value.append(dataList.get(i).getPeriod()).append(" : ").append(dataList.get(i).getNumber()).append(" : ").append(matchValue).append("");
                int lp = loopMax + serialCheck;

                if (lp >= dataList.get(i).getPeriod()) {
                    loopMax = 0;
                    serialNumberPositionMoveForward++;
                }
                break;
            }
        }
    }


    public void addValue(String value) {

        if (matchingClear >= MaxRepeatedCount) {

            finalResult.add(value + "\n " + "->" + matchingClear);

        }

        matchingClear = 0;

    }


    public void print(String str) {

        System.out.println(str);

    }


    public void prepareSerialNumber(int serial) {
        serialCheck = serial;
        serialNumberList = new ArrayList<>();
        serialNext = (serialCheck - 1);
        serialNumberList.add(serialNext);

        for (int i = 0; i < dataList.size(); i++) {
            if (serialCheck == (dataList.get(i).getPeriod()) % 10) {
                serialEnable = true;
                serialNext += 10;
                if (serialNext < dataList.size()) {
                    serialNumberList.add(serialNext);
                }
            }
        }
        ArrayList<Integer> serialNumberListTemp = new ArrayList<>();

        for (int i = 0; i < serialNumberList.size(); i++) {

            int value = serialNumberList.get(i);
            if (value > 0) {
                serialNumberListTemp.add(value);

            }
        }
        //serialNumberList = new ArrayList<>();

        serialNumberList = serialNumberListTemp;
        for (int i = 0; i < serialNumberList.size(); i++) {
            //System.out.println("serialNumberList--:" + serialNumberList.get(i));
        }


    }


}
