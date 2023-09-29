package com.example.damanhacker.utlities;


import static java.sql.DriverManager.println;

import com.example.damanhacker.intefaces.onResultList;
import com.example.damanhacker.model.DataModelMainData;

import java.util.ArrayList;

public class CheckNumberBasics {
    int matchingClear = 0;
    int MaxRepeatedCount = 3;
    int loopMax = 0;
    int serialNumberPositionMoveForward = 0;
    ArrayList<DataModelMainData> dataList;
    ArrayList<String> finalResult = new ArrayList<>();
    onResultList onResultList_;
    public void patternCheckBasedOnSerialNumber(ArrayList<DataModelMainData> _, onResultList onResult) {
        this.dataList = _;
        onResultList_ = onResult;
        println("patternCheckBasedOnSerialNumber-->"+dataList.size());

        picSerialNumberBasics();
    }
    public void patternCheckBasedOnSerialNumber(ArrayList<DataModelMainData> _) {

        this.dataList = _;
        picSerialNumberBasics();
    }


    public void picSerialNumberBasics() {
        int lpc = 0;
        while (serialNumberPositionMoveForward < dataList.size()) {
            DataModelMainData data = dataList.get(serialNumberPositionMoveForward);

            if (data.getNumber() == 6 ) {
                //System.out.println("valueMatching-->" + dataList.get(serialNumberPositionMoveForward).getNumber());
                lpc++;
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
        matchingClear = 0;
        StringBuilder value = new StringBuilder();

        value.append("\t").append(new DateUtilities().getTime(dataList.get(currentPosition).getPeriod())).append("\n\n");
        value.append(dataList.get(currentPosition).getPeriod()).append(" : ").append(dataList.get(currentPosition).getNumber()).append(" : ").append(dataList.get(currentPosition).getColor()).append("\n");
        String matchValue = dataList.get(currentPosition).getColor();
        currentPosition++;
        String currentValue;
        loopMax = 0;
        for (int i = currentPosition; i < dataList.size(); i++) {
            currentValue = dataList.get(i).getColor();

            if ((valueMatching(currentValue, matchValue, i))) {

                //System.out.println("valueMatching-->" + dataList.get(i).getNumber() + ":" + matchValue + ":" + currentColor);
                //System.out.println(matchValue + ":" + currentValue+":"+i);

                loopMax++;
                matchingClear++;
                value.append(dataList.get(i).getPeriod()).append(" : ").append(dataList.get(i).getNumber()).append(" : ").append(currentValue).append("\n");
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

    public boolean valueMatching(String currentValue, String matchValue, int pos) {
        boolean check = false;
        //if (!currentValue.equals(matchValue)) {
        currentValue = String.valueOf(currentValue.charAt(0));
        if (!matchValue.equals(currentValue)) {
            check = true;
        }
        return check;
    }
}
