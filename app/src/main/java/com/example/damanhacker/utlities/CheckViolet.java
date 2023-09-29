package com.example.damanhacker.utlities;


import com.example.damanhacker.intefaces.onResultList;
import com.example.damanhacker.model.DataModelMainData;

import java.util.ArrayList;

public class CheckViolet {
    int matchingClear = 0;
    int MaxRepeatedCount = 4;
    int loopMax = 0;
    int serialNumberPositionMoveForward = 0;
    ArrayList<DataModelMainData> dataList;
    ArrayList<String> finalResult = new ArrayList<>();
    onResultList onResultList_;

    public void patternCheckBasedOnSerialNumber(ArrayList<DataModelMainData> _) {
        this.dataList = _;
        picSerialNumberBasics();
    }

    public void patternCheckBasedOnSerialNumber(ArrayList<DataModelMainData> _, onResultList onResult) {
        this.dataList = _;
        onResultList_ = onResult;

        picSerialNumberBasics();
    }


    public void picSerialNumberBasics() {
        while (serialNumberPositionMoveForward < dataList.size()) {
            DataModelMainData data = dataList.get(serialNumberPositionMoveForward);
            if (data.getColor().equals("RV") || data.getColor().equals("GV")) {
                getMatch(serialNumberPositionMoveForward);
            }
            serialNumberPositionMoveForward++;
        }
        if (onResultList_ != null) {
            onResultList_.onItemText(finalResult);
        }
        for (int k = 0; k < finalResult.size(); k++) {
            System.out.println("\t" + k + "\n\n" + finalResult.get(k) + "\n");
        }

    }

    public void getMatch(int currentPosition) {
        //GV
        matchingClear = 0;
        StringBuilder value = new StringBuilder();
        int initial = currentPosition - 1;
        String matchValue = dataList.get(initial).getColor();
        if (matchValue.equals("RV") || matchValue.equals("GV")) {
            serialNumberPositionMoveForward = currentPosition;
            serialNumberPositionMoveForward++;
            matchingClear = 0;
            return;
        }
        value.append("\t").append(new DateUtilities().getTime(dataList.get(initial).getPeriod())).append("\n\n");
        value.append(dataList.get(initial).getPeriod()).append(" : ").append(dataList.get(initial).getNumber()).append(" : ").append(dataList.get(initial).getColor()).append(" : ").append(dataList.get(initial).getValue()).append("\n");
        initial++;
        value.append(dataList.get(initial).getPeriod()).append(" : ").append(dataList.get(initial).getNumber()).append(" : ").append(dataList.get(initial).getColor()).append(" : ").append(dataList.get(initial).getValue()).append("\n");
        currentPosition++;
        String currentColor;

        loopMax = 0;

        for (int i = currentPosition; i < dataList.size(); i++) {
            currentColor = dataList.get(i).getColor();
            if ((valueMatching(currentColor, matchValue))) {
                loopMax++;
                matchingClear++;
                value.append(dataList.get(i).getPeriod()).append(" : ").append(dataList.get(i).getNumber()).append(" : ").append(currentColor).append(" : ").append(dataList.get(i).getValue()).append("\n");
            } else {
                addValue(value.toString());
                value.setLength(0);
                serialNumberPositionMoveForward = i;
                serialNumberPositionMoveForward++;
                matchingClear = 0;
                loopMax = 0;
                break;
            }
        }
    }


    public void addValue(String value) {
        if (matchingClear > MaxRepeatedCount) {
            finalResult.add(value + "--Level ------->" + loopMax);
        }
        matchingClear = 0;
    }


    public void print(String str) {
        System.out.println(str);
    }

    public boolean valueMatching(String currentValue, String matchValue) {
        boolean check = false;
        //if (!currentValue.equals(matchValue)) {
        if (!currentValue.contains(matchValue)) {
            //System.out.println(currentValue + ":" + matchValue);
            check = true;
        }
        return check;
    }
}
