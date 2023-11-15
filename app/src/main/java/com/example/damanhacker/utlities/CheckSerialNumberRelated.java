package com.example.damanhacker.utlities;


import com.example.damanhacker.database.DBHandler;
import com.example.damanhacker.intefaces.onResultList;
import com.example.damanhacker.model.DataModelMainData;
import com.example.damanhacker.model.ReportData;
import com.example.damanhacker.model.getData;
import com.example.damanhacker.model.outPutResponse;

import java.util.ArrayList;
import java.util.Collections;

public class CheckSerialNumberRelated {
    int matchingClear = 0;
    int masterMatchValue = 0;
    onResultList onResultList_;
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

    public void init(DBHandler dbHandler, onResultList onResult) {
        ArrayList<String> dateList = new SortingDate().sort(dbHandler.getDateList());
        Collections.reverse(dateList);
        for (int i = 0; i < dateList.size(); i++) {
            String date = dateList.get(i);
            finalResult.add(date + "---------------------");
            masterDate = date;
            childList = new ArrayList<>();

            ArrayList<DataModelMainData> listData = dbHandler.getDataProcess(date);
            ArrayList<getData> list_ = new PatternCheck().numberAttachedValue(listData);
            patternCheckBasedOnSerialNumber(list_, listData, onResult);

            outPutResponse otp = new outPutResponse(date, childList);
            outPutResult.add(otp);
        }
        if (onResultList_ != null) {
            onResultList_.onItemText(finalResult);
        }
    }

    public void patternCheckBasedOnSerialNumber(ArrayList<getData> listN_, ArrayList<DataModelMainData> _, onResultList onResult) {
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
            serialNumberPositionMoveForward++;
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
        currentPeriod = dataList.get(startPosition).getPeriod();
        int pr = currentPeriod - previousPeriod;
        previousPeriod = currentPeriod;
        value.append("").append("P->").append((serialNumberPosition)).append(":Value->").append((masterMatchValue)).append(":").append(new DateUtilities().getTime(dataList.get(startPosition).getPeriod()));
        int period = dataList.get(startPosition).getPeriod();
        int number = masterMatchValue;
        String time = new DateUtilities().getTime(dataList.get(startPosition).getPeriod());
        int level = matchingClear;
        int gap = 0;
        ReportData data = new ReportData(period, number, time, level, gap);
        loopMax = 0;
        //System.out.println("CHECKMatch---->" + serialNumberPosition + ":" + dataList.get(serialNumberPosition).getPeriod() + ":" + dataList.get(serialNumberPosition).getNumber());
        for (int i = startPosition; i < dataList.size(); i++) {

            int currentValue = dataList.get(i).getNumber();
            if (!matchSB(masterMatchValue, currentValue)) {
                loopMax++;
                //value.append("\n").append(dataList.get(i).getPeriod()).append(" : ").append(dataList.get(i).getNumber()).append(" : ").append(getFValue(currentValue)).append(":").append(getPValue(currentValue));
                matchingClear++;
                if (i == dataList.size() - 1) {
                    addValue(data);
                    addValue_(value + (":P->" + pr));
                }
            } else {
                addValue(data);
                addValue_(value + (":P->" + pr));
                value.setLength(0);
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

            childList.add(new ReportData(data.getPeriod(), data.getNumber(), data.getTime(), matchingClear, loopMax));

        }

        matchingClear = 0;

    }


    public void addValue_(String value) {

        if (matchingClear >= 5) {

            finalResult.add(value + ":Level Of->" + matchingClear);

        }

        matchingClear = 0;

    }


    public void print(String str) {

        System.out.println(str);

    }

    public boolean match(int matchValue, int currentValue) {
        boolean check = false;
        if (matchValue == 0) {

            if ((currentValue == 9) || currentValue == 1) {
                check = true;
            }

        } else if (matchValue == 1) {
            if ((currentValue == 0) || currentValue == 2) {
                check = true;
            }
        } else if (matchValue == 2) {
            if ((currentValue == 1) || currentValue == 3) {
                check = true;
            }
        } else if (matchValue == 3) {
            if ((currentValue == 2) || currentValue == 4) {
                check = true;
            }
        } else if (matchValue == 4) {
            if ((currentValue == 3) || currentValue == 5) {
                check = true;
            }
        } else if (matchValue == 5) {
            if ((currentValue == 4) || currentValue == 6) {
                check = true;
            }
        } else if (matchValue == 6) {
            if ((currentValue == 5) || currentValue == 7) {
                check = true;
            }
        } else if (matchValue == 7) {
            if ((currentValue == 6) || currentValue == 8) {
                check = true;
            }
        } else if (matchValue == 8) {
            if ((currentValue == 7) || currentValue == 9) {
                check = true;
            }
        } else if (matchValue == 9) {
            if ((currentValue == 8) || currentValue == 0) {
                check = true;
            }
        }

        return check;
    }

    public boolean matchOposite(int matchValue, int currentValue) {
        boolean check = false;
        if (matchValue == 0) {
            if ((currentValue == 9) || (currentValue == 1) || (currentValue == 5)) {
                check = true;
            }

        } else if (matchValue == 1) {
            if ((currentValue == 0) || (currentValue == 2) || (currentValue == 9)) {
                check = true;
            }
        } else if (matchValue == 2) {
            if ((currentValue == 1) || (currentValue == 3) || (currentValue == 6)) {
                check = true;
            }
        } else if (matchValue == 3) {
            if ((currentValue == 2) || (currentValue == 4) || (currentValue == 5)) {
                check = true;
            }
        } else if (matchValue == 4) {
            if ((currentValue == 3) || (currentValue == 5) || (currentValue == 4)) {
                check = true;
            }
        } else if (matchValue == 5) {
            if ((currentValue == 4) || (currentValue == 6) || (currentValue == 3)) {
                check = true;
            }
        } else if (matchValue == 6) {
            if ((currentValue == 5) || (currentValue == 7) || (currentValue == 2)) {
                check = true;
            }
        } else if (matchValue == 7) {
            if ((currentValue == 6) || (currentValue == 8) || (currentValue == 1)) {
                check = true;
            }
        } else if (matchValue == 8) {
            if ((currentValue == 7) || (currentValue == 9) || (currentValue == 0)) {
                check = true;
            }
        } else if (matchValue == 9) {
            if ((currentValue == 0) || (currentValue == 1) || (currentValue == 9)) {
                check = true;
            }
        }
        //  System.out.println("MAtch---->" + check + ":" + matchingClear);
        return check;
    }

    public boolean matchSB_(int matchValue, int currentValue) {
        boolean check = false;
        if (matchValue == 0) {

            if (getValue(currentValue).equals("Big")) {
                check = true;
            }

        } else if (matchValue == 1) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        } else if (matchValue == 2) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        } else if (matchValue == 3) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        } else if (matchValue == 4) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        } else if (matchValue == 5) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        } else if (matchValue == 6) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        } else if (matchValue == 7) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        } else if (matchValue == 8) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        } else if (matchValue == 9) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        }
        //System.out.println("MAtch---->" + check + ":" + matchingClear);
        return check;
    }

    public boolean matchSB(int matchValue, int currentValue) {
        boolean check = false;
        if (matchValue == 0) {

            if (getValue(currentValue).equals("Small")) {
                check = true;
            }

        } else if (matchValue == 1) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        } else if (matchValue == 2) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        } else if (matchValue == 3) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        } else if (matchValue == 4) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        } else if (matchValue == 5) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        } else if (matchValue == 6) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        } else if (matchValue == 7) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        } else if (matchValue == 8) {
            if (getValue(currentValue).equals("Big")) {
                check = true;
            }
        } else if (matchValue == 9) {
            if (getValue(currentValue).equals("Small")) {
                check = true;
            }
        }
        //System.out.println("MAtch---->" + check + ":" + matchingClear);
        return check;
    }

    public boolean matchOpositeTEST(int matchValue, int currentValue) {
        boolean check = false;
        if (matchValue == 0) {

            if ((currentValue == 1) || (currentValue == 5)) {
                check = true;
            }
        }

        //  System.out.println("MAtch---->" + check + ":" + matchingClear);
        return check;
    }

    public int getFValue(int currentValue) {
        if (currentValue == 0) {
            currentValue = 9;
        }
        return currentValue;
    }

    public boolean matchSB__(int matchValue, int currentValue) {
        return (matchValue <= 4 && getValue(currentValue).equals("Small")) || (matchValue >= 5 && getValue(currentValue).equals("Big"));
    }

    public int getPValue(int currentValue) {
        if (currentValue == 9) {
            currentValue = 0;
        }
        return currentValue;
    }

    public String getValue(int number) {
        String value = "";
        switch (number) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                value = "Small";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                value = "Big";
                break;
        }
        return value;
    }

}
