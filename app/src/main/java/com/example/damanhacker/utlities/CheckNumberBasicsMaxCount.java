package com.example.damanhacker.utlities;


import com.example.damanhacker.intefaces.onResultList;
import com.example.damanhacker.model.DataModelMainData;

import java.util.ArrayList;

public class CheckNumberBasicsMaxCount {
    int matchingClear = 0;
    int MaxRepeatedCount = 0;
    int loopMax = 0;
    int chkPoint = 0;
    int serialNumberPositionMoveForward = 0;
    ArrayList<DataModelMainData> dataList;
    ArrayList<String> finalResult = new ArrayList<>();
    onResultList onResultList_;

    public void patternCheckBasedOnSerialNumber(ArrayList<DataModelMainData> _) {
        this.dataList = _;
        for (int i = 0; i < 9; i++) {
            picSerialNumberBasics(i);
        }
    }

    public void patternCheckBasedOnSerialNumber(ArrayList<DataModelMainData> _, onResultList rslt) {
        this.dataList = _;
        onResultList_ = rslt;
        //for (int i = 0; i < 9; i++) {
        int i = 0;
        chkPoint = i;
        picSerialNumberBasics(i);
        //}
    }


    public void picSerialNumberBasics(int chkPos) {
        serialNumberPositionMoveForward = 0;

        while (serialNumberPositionMoveForward < dataList.size()) {
            DataModelMainData data = dataList.get(serialNumberPositionMoveForward);

            if (data.getNumber() == chkPos) {
                //System.out.println("valueMatching-->" + dataList.get(serialNumberPositionMoveForward).getNumber());
                getMatch(serialNumberPositionMoveForward);
            }
            serialNumberPositionMoveForward++;

        }
        if (onResultList_ != null) {
            onResultList_.onItemText(finalResult);
        }
        print("Total Numbers--->" + finalResult.size());
        for (int k = 0; k < finalResult.size(); k++) {
            System.out.println("\t" + k + "\n\n" + finalResult.get(k) + "\n");
        }

    }

    public void getMatch(int currentPosition) {
        matchingClear = 0;
        StringBuilder value = new StringBuilder();

        value.append("\t").append(new DateUtilities().getTime(dataList.get(currentPosition).getPeriod())).append(" --->>").append(chkPoint).append("\n\n");
        value.append(dataList.get(currentPosition).getPeriod()).append("    :    ").append(dataList.get(currentPosition).getNumber()).append("    :    ").append(dataList.get(currentPosition).getNumber()).append("\n");
        int matchValue = dataList.get(currentPosition).getNumber();
        currentPosition++;
        int currentValue;
        loopMax = 0;
        for (int i = currentPosition; i < dataList.size(); i++) {
            currentValue = dataList.get(i).getNumber();

            if ((valueMatching(currentValue, matchValue, i))) {
                loopMax++;
                matchingClear++;
                value.append(dataList.get(i).getPeriod()).append("    :    ").append(dataList.get(i).getNumber()).append("       ").append(currentValue).append("\n");
            } else {
                addValue(value.toString());
                value.setLength(0);
                serialNumberPositionMoveForward = (i - 1);
                matchingClear = 0;
                loopMax = 0;
                break;
            }
        }
    }


    public void addValue(String value) {

        if (matchingClear >= MaxRepeatedCount) {

            finalResult.add(value + "--Level ------->" + loopMax);
        }
        matchingClear = 0;
    }


    public void print(String str) {
        System.out.println(str);
    }

    public boolean valueMatching(int currentValue, int matchValue, int pos) {
        //if (!currentValue.equals(matchValue)) {
        return matchValue != currentValue;
    }
}
