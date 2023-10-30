package com.example.damanhacker.utlities;



import com.example.damanhacker.model.DataModelMainData;

import java.util.ArrayList;

public class CheckNumberBasicsServer {
    int matchingClear = 0;
    int loopMax = 0;
    int serialNumberPositionMoveForward = 0;
    int number = 0;
    ArrayList<DataModelMainData> dataList;
    ArrayList<String> finalResult = new ArrayList<>();

    public void patternCheckBasedOnSerialNumber(ArrayList<DataModelMainData> liatData) {
        this.dataList = liatData;
        for (int i = 0; i < 10; i++) {
            //System.out.println("valueMatching-->"+i);
            this.number = i;
            serialNumberPositionMoveForward = 0;
            picSerialNumberBasics();

        }
    }


    public void picSerialNumberBasics() {
        while (serialNumberPositionMoveForward < dataList.size()) {
            DataModelMainData data = dataList.get(serialNumberPositionMoveForward);
            if (data.getNumber() == this.number) {
                //System.out.println("valueMatching-->" + data.getNumber() + ":" + serialNumberPositionMoveForward + ":" + number);

                getMatch(serialNumberPositionMoveForward);
            }
            serialNumberPositionMoveForward++;

        }

        for (int k = 0; k < finalResult.size(); k++) {
            System.out.println("Notification Prepare--->" + "\t" + k + "\n\n" + finalResult.get(k) + "\n");
        }

    }

    public void getMatch(int currentPosition) {
        matchingClear = 0;
        StringBuilder value = new StringBuilder();

        value.append("\t").append(getTime(dataList.get(currentPosition).getPeriod())).append("\t").append("Number-->").append(number).append("\t").append(dataList.size()).append("\n\n");
        value.append(dataList.get(currentPosition).getPeriod()).append(" : ").append(dataList.get(currentPosition).getNumber()).append(" : ").append(dataList.get(currentPosition).getColor()).append("\n");
        String matchValue = dataList.get(currentPosition).getColor();
        currentPosition++;
        String currentValue;
        loopMax = 0;
        for (int i = currentPosition; i < dataList.size(); i++) {
            currentValue = dataList.get(i).getColor();

            if ((valueMatching(currentValue, matchValue, i))) {
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
        if (matchingClear >= 7) {
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

    public String getTime(int period) {
        String _hours;
        String _minutes;

        int hours = period / 60;
        int minutes = period % 60;
        String format = (period < 780) ? "AM" : "PM";

        if (hours > 12) {
            hours -= 12;
        } else if (hours == 0) {
            hours = 12;
        }

        if (hours < 10) {
            _hours = "0" + hours;
        } else {
            _hours = String.valueOf(hours);
        }

        if (minutes < 10) {
            _minutes = "0" + minutes;
        } else {
            _minutes = String.valueOf(minutes);
        }

        return _hours + ":" + _minutes + ":" + format;
    }

}
