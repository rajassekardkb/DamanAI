package com.example.damanhacker.utlities;


import static com.example.damanhacker.utlities.UtlString.MAXPATTERN;

import android.content.Context;

import com.example.damanhacker.database.DBHandler;
import com.example.damanhacker.intefaces.onResultList;
import com.example.damanhacker.model.DataModelMainData;

import java.util.ArrayList;

public class CheckSerialNumberBasicsReport {
    int matchingClear = 0;
    onResultList onResultList_;
    StringBuilder value = new StringBuilder();

    int serialNext = 0;
    int loopMax = 0;
    int loopMaxInc = 0;
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
    String DATE = "";
    String currentColor = "";

    public void patternCheckBasedOnSerialNumberReport(ArrayList<String> listDate, onResultList onResult, int pattern, Context con) {
        onResultList_ = onResult;
        DBHandler dBHandler = new DBHandler(con);
        currentSerialNumber = pattern;

        for (int i = 0; i < listDate.size(); i++) {
            DATE = listDate.get(i);
            this.dataList = dBHandler.getDataProcess(DATE);
            prepareSerialNumber(currentSerialNumber);
            value.append("\n").append("DATE->").append(DATE).append("-").append("SERIAL->").append(currentSerialNumber).append("-ListSize").append(dataList.size());
            picSerialNumberBasics();
            addValue(value.toString());
            loopMaxInc = 0;
            // finalResult.add("---------------------");
        }
        if (onResultList_ != null) {
            onResultList_.onItemText(finalResult);
        }
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
        // print("PositionCheck-->" + startPosition + ":" + matchPosition + ":" + dataList.size());
        if (dataList.size() == matchPosition) {
            return;
        }
        // String matchValue = String.valueOf(dataList.get(matchPosition).getColor().charAt(0));

        String matchValue = dataList.get(matchPosition).getValue();


        loopMax = 0;

        for (int i = startPosition; i < dataList.size(); i++) {
            // String currentValue = String.valueOf(dataList.get(i).getColor().charAt(0));
            String currentValue = dataList.get(i).getValue();
            if (matchValue.equals(currentValue)) {
                loopMax++;
                //value.append("\n").append(dataList.get(i).getPeriod()).append(" : ").append(dataList.get(i).getNumber()).append(" : ").append(currentValue).append("");
                matchingClear++;
                if (i == dataList.size() - 1) {
                    addInc(dataList.get(i).getPeriod());
                }
            } else {
                addInc(dataList.get(i).getPeriod());
                matchValue = currentValue;

                //value.append(dataList.get(i).getPeriod()).append(" : ").append(dataList.get(i).getNumber()).append(" : ").append(matchValue).append("");
                int lp = loopMax + serialCheck;

                if (lp >= dataList.get(i).getPeriod()) {
                    loopMax = 0;
                    serialNumberPositionMoveForward++;
                }
                break;
            }
        }
    }


    public void addValue(String value_) {
        finalResult.add(value_ + "\n " + "->" + "cnt" + loopMaxInc);
        value.setLength(0);
    }

    public void addInc(int value_) {

        if (matchingClear >= MAXPATTERN) {
            loopMaxInc++;
            value.append("\n").append(new DateUtilities().getTime(value_)).append(":").append(value_).append(":Count->").append(matchingClear);
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
            System.out.println("serialNumberList--:" + serialNumberList.get(i));
        }


    }


}
