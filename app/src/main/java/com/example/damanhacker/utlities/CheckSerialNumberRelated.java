package com.example.damanhacker.utlities;


import com.example.damanhacker.database.DBHandler;
import com.example.damanhacker.intefaces.onResultListCustom;
import com.example.damanhacker.model.DataModelMainData;
import com.example.damanhacker.model.ReportData;
import com.example.damanhacker.model.getData;
import com.example.damanhacker.model.outPutResponse;

import java.util.ArrayList;
import java.util.Collections;

public class CheckSerialNumberRelated {
    int matchingClear = 0;
    int masterMatchValue = 0;
    String matchValue;
    int matchPattern = 0;
    int matchPatternSecondary = 0;
    boolean addOrEven;

    boolean matchCheck;
    boolean swap;
    onResultListCustom onResultList_;
    int serialNext = 0;
    String masterDate = "";
    int loopMax = 0;
    int serialCheck = 0;
    int serialNumberPositionMoveForward = 0;
    int serialNumberPosition = 0;
    int masterPeriod = 0;
    int previousPeriod = 0;
    int currentPeriod = 0;

    boolean serialEnable = false;

    ArrayList<getData> serialNumberList = new ArrayList<>();
    ArrayList<DataModelMainData> dataList;
    ArrayList<String> finalResult = new ArrayList<>();
    ArrayList<outPutResponse> outPutResult = new ArrayList<>();
    ArrayList<ReportData> childList = new ArrayList<>();
    matchingValues matching;

    public void init(DBHandler dbHandler, onResultListCustom onResult) {
        matching = new matchingValues();
        ArrayList<String> dateList = new SortingDate().sort(dbHandler.getDateList());
        Collections.reverse(dateList);
        for (int i = 0; i < dateList.size(); i++) {
            String date = dateList.get(i);
            finalResult.add(date + "---------------------");

            previousPeriod = 0;
            masterDate = date;
            childList = new ArrayList<>();

            ArrayList<DataModelMainData> listData = dbHandler.getDataProcess(date);
            ArrayList<getData> list_ = new PatternCheck().numberAttachedValue(listData);
            patternCheckBasedOnSerialNumber(list_, listData, onResult);

            outPutResponse otp = new outPutResponse(date, childList);
            outPutResult.add(otp);
        }
        if (onResultList_ != null) {
            onResultList_.onItemText(outPutResult);
        }
    }

    public void patternCheckBasedOnSerialNumber(ArrayList<getData> listN_, ArrayList<DataModelMainData> _, onResultListCustom onResult) {
        this.dataList = _;
        this.serialNumberList = listN_;
        onResultList_ = onResult;
        picSerialNumberBasics();
//        finalResult.add("---------------------");
    }


    public void picSerialNumberBasics() {
        System.out.println("NumberRelated size->" + serialNumberList.size());
        serialNumberPositionMoveForward = 0;
        while (serialNumberPositionMoveForward < serialNumberList.size()) {
            serialNumberPosition = serialNumberList.get(serialNumberPositionMoveForward).getPosition();
            masterMatchValue = serialNumberList.get(serialNumberPositionMoveForward).getValue();
            masterPeriod = serialNumberList.get(serialNumberPositionMoveForward).getPeriod();
            getMatch((serialNumberPosition + 1));
            matchValue = matching.getValueSB(masterMatchValue);
            serialNumberPositionMoveForward++;

            addOrEven = masterMatchValue % 2 != 0;

        }
        System.out.println("NumberRelated Total Count->" + ":" + finalResult.size());
      /*  for (int k = 0; k < finalResult.size(); k++) {
            System.out.println("NumberRelated->" + finalResult.get(k));
        }  */
        for (int k = 0; k < outPutResult.size(); k++) {
            System.out.println("NumberRelated->date:" + outPutResult.get(k).getDate());
            ArrayList<ReportData> listChild = outPutResult.get(k).getList();

            for (int l = 0; l < listChild.size(); l++) {
                ReportData data = listChild.get(l);
                System.out.println("" + data.toString() + "\n");
            }

        }
    }

    public void getMatch(int startPosition) {
        if (dataList.size() == startPosition) {
            return;
        }

        StringBuilder value = new StringBuilder();

        value.append("").append("P->").append((serialNumberPosition)).append(":Value->").append((masterMatchValue)).append(":").append(new DateUtilities().getTime(dataList.get(startPosition).getPeriod()));
        int period = dataList.get(startPosition).getPeriod();
        int number = masterMatchValue;
        matchValue = matching.getValue(masterMatchValue);
        String time = new DateUtilities().getTime(dataList.get(startPosition).getPeriod());
        int level = matchingClear;
        int gap = 0;
        ReportData data = new ReportData(period, number, time, level, gap);
        loopMax = 0;
        matchCheck = false;
        //System.out.println("CHECKMatch---->" + serialNumberPosition + ":" + dataList.get(serialNumberPosition).getPeriod() + ":" + dataList.get(serialNumberPosition).getNumber());
        for (int i = startPosition; i < dataList.size(); i++) {

            int currentValue = dataList.get(i).getNumber();
            String currentValue_ = dataList.get(i).getValue();

            if (matching.valueMatching(matchValue, currentValue_)) {
                loopMax++;
                //value.append("\n").append(dataList.get(i).getPeriod()).append(" : ").append(dataList.get(i).getNumber()).append(" : ").append(getFValue(currentValue)).append(":").append(getPValue(currentValue));
                matchingClear++;
                matchPattern++;
                if (i == dataList.size() - 1) {
                    addValue(data);
                    addValue_(value.toString());
                }
            } else {
                addValue(data);
                addValue_(value.toString());
                value.setLength(0);
                matchPattern = 0;
                //value.append("\n").append(dataList.get(i).getPeriod()).append(" : ").append(dataList.get(i).getNumber()).append(" : ").append(getFValue(currentValue)).append(":").append(getPValue(currentValue));
                int lp = loopMax + serialCheck;
                if (lp >= dataList.get(i).getPeriod()) {
                    loopMax = 0;
                    serialNumberPositionMoveForward++;
                }
                break;
            }
        }
    }

    public void getMatch_(int startPosition) {
        if (dataList.size() == startPosition) {
            return;
        }

        StringBuilder value = new StringBuilder();

        value.append("").append("P->").append((serialNumberPosition)).append(":Value->").append((masterMatchValue)).append(":").append(new DateUtilities().getTime(dataList.get(startPosition).getPeriod()));
        int period = dataList.get(startPosition).getPeriod();
        int number = masterMatchValue;
        matchValue = matching.getValue(masterMatchValue);
        String time = new DateUtilities().getTime(dataList.get(startPosition).getPeriod());
        int level = matchingClear;
        int gap = 0;
        ReportData data = new ReportData(period, number, time, level, gap);
        loopMax = 0;
        matchCheck = false;
        //System.out.println("CHECKMatch---->" + serialNumberPosition + ":" + dataList.get(serialNumberPosition).getPeriod() + ":" + dataList.get(serialNumberPosition).getNumber());
        for (int i = startPosition; i < dataList.size(); i++) {

            int currentValue = dataList.get(i).getNumber();
            String currentValue_ = dataList.get(i).getValue();

            if (matching.valueMatching(matchValue, currentValue_)) {
                loopMax++;
                //value.append("\n").append(dataList.get(i).getPeriod()).append(" : ").append(dataList.get(i).getNumber()).append(" : ").append(getFValue(currentValue)).append(":").append(getPValue(currentValue));
                matchingClear++;
                matchPattern++;

                if (!matchCheck) {
                    //  System.out.println("If Match Pattern---->" + matchValue);
                    //matchValue = matching.convertOpositeValue(matchValue);
                    matchCheck = true;
                    matchPattern = 0;
                }
                if (matchPattern == 1) {
                    swap = false;

                    //System.out.println("If Match Pattern---->" + matchPattern+":"+matchValue);
                    matchValue = matching.convertOpositeValue(matchValue);
                    matchPattern = 0;
                    matchPatternSecondary = 0;
                    matchCheck = false;
                }
                matchPatternSecondary++;
                if (i == dataList.size() - 1) {
                    addValue(data);
                    addValue_(value.toString());
                }
            } else {
                addValue(data);
                addValue_(value.toString());
                value.setLength(0);
                matchPattern = 0;
                //value.append("\n").append(dataList.get(i).getPeriod()).append(" : ").append(dataList.get(i).getNumber()).append(" : ").append(getFValue(currentValue)).append(":").append(getPValue(currentValue));
                int lp = loopMax + serialCheck;
                if (lp >= dataList.get(i).getPeriod()) {
                    loopMax = 0;
                    serialNumberPositionMoveForward++;
                }
                break;
            }
        }
    }

    public void addValue(ReportData data) {

        if (matchingClear >= 5) {
            currentPeriod = data.getPeriod();
            int pr = currentPeriod - previousPeriod;
            previousPeriod = currentPeriod;
            childList.add(new ReportData(data.getPeriod(), data.getNumber(), data.getTime(), matchingClear, pr));
        }

        matchingClear = 0;

    }


    public void addValue_(String value) {

        if (matchingClear >= 9) {

            finalResult.add(value + ":Level Of->" + matchingClear);

        }

        matchingClear = 0;

    }


    public void print(String str) {

        System.out.println(str);

    }

}
