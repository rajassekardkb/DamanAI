package com.example.damanhacker.utlities;


import static com.example.damanhacker.utlities.UtlString.MAXPATTERN;

import com.example.damanhacker.intefaces.onResultList;
import com.example.damanhacker.model.DataModelMainData;

import java.util.ArrayList;

public class SerialNumberSinglePatternColor {
    int matchingClear = 0;
    int number = 0;

    int matchPattern = 0;
    int loopMax = 0;
    int serialNumberPositionMoveForward = 0;
    ArrayList<DataModelMainData> dataList;
    ArrayList<String> finalResult = new ArrayList<>();
    onResultList onResultList_;

    public void patternCheckBasedOnSerialNumber(ArrayList<DataModelMainData> _) {
        this.dataList = _;
        picSerialNumberBasics();
    }

    public void patternCheckBasedOnSerialNumber(ArrayList<DataModelMainData> _, onResultList onResult, int number_) {
        this.dataList = _;
        onResultList_ = onResult;
        this.number = number_;
        picSerialNumberBasics();
    }


    public void picSerialNumberBasics() {
        while (serialNumberPositionMoveForward < dataList.size()) {
            DataModelMainData data = dataList.get(serialNumberPositionMoveForward);
            //if (data.getPeriod() % 10 == number) {
            if (data.getNumber() == number) {
                getMatch(serialNumberPositionMoveForward);
            }
            serialNumberPositionMoveForward++;
        }
        if (onResultList_ != null) {
            onResultList_.onItemText(finalResult);
        }
        for (int k = 0; k < finalResult.size(); k++) {
            //  System.out.println("\t" + k + "\n\n" + finalResult.get(k) + "\n");
        }

    }

    public void getMatch(int currentPosition) {
        matchingClear = 0;
        int matchPosition = currentPosition;
        if (dataList.size() == matchPosition) return;
        StringBuilder value = new StringBuilder();
        String matchValue = String.valueOf(dataList.get(matchPosition).getColor().charAt(0));
        String currentValue;
        loopMax = 0;
        matchingClear = 0;
        matchPosition++;
        value.append("\n").append(new DateUtilities().getTime(dataList.get(currentPosition).getPeriod())).append(" : SNO=").append(number).append("\n\n");
        //value.append(dataList.get(currentPosition).getPeriod()).append(" : ").append(dataList.get(currentPosition).getNumber()).append(" : ").append(matchValue).append("\n");
        value.append(new DateUtilities().getTime(dataList.get(currentPosition).getPeriod())).append(" : ").append(dataList.get(currentPosition).getPeriod()).append(" : ").append(dataList.get(currentPosition).getNumber()).append(" : ").append(matchValue).append("\n");

        for (int i = matchPosition; i < dataList.size(); i++) {
            currentValue = String.valueOf(dataList.get(i).getColor().charAt(0));
            // value.append(dataList.get(i).getPeriod()).append(" : ").append(dataList.get(i).getNumber()).append(" : ").append(matchValue).append("\n");

            if ((valueMatching(currentValue, matchValue))) {
                loopMax++;
                matchingClear++;
                matchPattern++;

                value.append(new DateUtilities().getTime(dataList.get(i).getPeriod())).append(" : ").append(dataList.get(i).getPeriod()).append(" : ").append(dataList.get(i).getNumber()).append(" : ").append(currentValue).append("\n");
                if (matchPattern == 1) {
                    //System.out.println("If Match Pattern---->" + matchPattern+":"+matchValue);
                    matchValue = convertOpositeValue(matchValue);

                }
            } else {
                //System.out.println("else-->" + matchValue + ":" + currentValue + ":" + dataList.get(i).getPeriod() + ":" + matchPattern);
                matchPattern = 0;
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
        if (matchingClear >= MAXPATTERN) {
            finalResult.add(value + "--Level ------->" + loopMax);
        }
        matchingClear = 0;
    }


    public void print(String str) {
        System.out.println(str);
    }

    public String convertOpositeValuee(String str) {
        String returnValue;
        if (str.equals("Small")) {
            returnValue = "Big";
        } else {
            returnValue = "Small";
        }
        matchPattern = 0;
        return returnValue;
    }

    public String convertOpositeValue(String str_) {
        String str = String.valueOf(str_.charAt(0));

        String returnValue;
        if (str.equals("R")) {
            returnValue = "G";
        } else {
            returnValue = "R";
        }
        matchPattern = 0;
        return returnValue;
    }


    public boolean valueMatching(String currentValue, String matchValue) {
        // System.out.println(currentValue + ":" + matchValue);
        return !matchValue.equals(currentValue);
    }
}
