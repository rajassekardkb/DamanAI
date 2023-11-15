package com.example.damanhacker.utlities;


import com.example.damanhacker.intefaces.onResultList;
import com.example.damanhacker.model.DataModelMainData;

import java.util.ArrayList;

public class CheckWithMinNumberWithRelatedNumber {
    int matchingClear = 0;
    int masterMatchValue = 0;
    onResultList onResultList_;

    int serialNext = 0;
    int loopMax = 0;
    int serialCheck = 0;
    int serialNumberPositionMoveForward = 0;
    int serialNumberPosition = 0;
    boolean serialEnable = false;

    ArrayList<String> serialNumberList = new ArrayList<>();
    ArrayList<DataModelMainData> dataList;
    ArrayList<String> finalResult = new ArrayList<>();

    public void patternCheckBasedOnSerialNumber(ArrayList<String> listN_, ArrayList<DataModelMainData> _, onResultList onResult) {
        this.dataList = _;
        this.serialNumberList = listN_;
        onResultList_ = onResult;
        picSerialNumberBasics();
        finalResult.add("---------------------");
        if (onResultList_ != null) {
            onResultList_.onItemText(finalResult);
        }
    }

    public void picSerialNumberBasics() {
        System.out.println("NumberRelated size->" + serialNumberList.size());

        serialNumberPositionMoveForward = 0;
        while (serialNumberPositionMoveForward < serialNumberList.size()) {
            String[] values = serialNumberList.get(serialNumberPositionMoveForward).split(":");
            getMatch(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            serialNumberPositionMoveForward++;
        }
        System.out.println("NumberRelated Total Count->" + ":" + finalResult.size());
        for (int k = 0; k < finalResult.size(); k++) {
           // System.out.println("NumberRelated->" + finalResult.get(k));
        }
    }


    public void getMatch(int matchValue1, int matchValue2) {
        // if (dataList.size() == startPosition) {
        //   return;
        //}

        masterMatchValue = matchValue2;
        StringBuilder value = new StringBuilder();

        //value.append("").append("Position->").append((serialNumberPosition)).append(":Value->").append((masterMatchValue)).append(":Time->").append(new DateUtilities().getTime(dataList.get(i).getPeriod()));
        loopMax = 0;
        //System.out.println("CHECKMatch---->" + serialNumberPosition + ":" + dataList.get(serialNumberPosition).getPeriod() + ":" + dataList.get(serialNumberPosition).getNumber());

        for (int i = 0; i < dataList.size(); i++) {
            int currentValue = dataList.get(i).getNumber();
            System.out.println("Check Min Number-->" + matchValue1 + ":" + currentValue);
            if (matchValue1 == currentValue) {
                if (!matchSB(masterMatchValue, currentValue)) {
                    loopMax++;
                    value.append("\n").append(dataList.get(i).getPeriod()).append(" : ").append(dataList.get(i).getNumber()).append(" : ").append(getFValue(currentValue)).append(":").append(getPValue(currentValue));
                    matchingClear++;
                    if (i == dataList.size() - 1) {
                        addValue(value.toString());
                    }
                } else {
                    addValue(value.toString());
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
    }


    public void addValue(String value) {

        if (matchingClear >= 20) {

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

            if ((currentValue == 1) || (currentValue == 5)) {
                check = true;
            }

        } else if (matchValue == 1) {
            if ((currentValue == 8) || (currentValue == 3)) {
                check = true;
            }
        } else if (matchValue == 2) {
            if ((currentValue == 7) || (currentValue == 6)) {
                check = true;
            }
        } else if (matchValue == 3) {
            if ((currentValue == 6) || (currentValue == 5)) {
                check = true;
            }
        } else if (matchValue == 4) {
            if ((currentValue == 5) || (currentValue == 9)) {
                check = true;
            }
        } else if (matchValue == 5) {
            if ((currentValue == 4) || (currentValue == 0)) {
                check = true;
            }
        } else if (matchValue == 6) {
            if ((currentValue == 3) || (currentValue == 4)) {
                check = true;
            }
        } else if (matchValue == 7) {
            if ((currentValue == 2) || (currentValue == 3)) {
                check = true;
            }
        } else if (matchValue == 8) {
            if ((currentValue == 1) || (currentValue == 2)) {
                check = true;
            }
        } else if (matchValue == 9) {
            if ((currentValue == 0) || (currentValue == 1)) {
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

        return currentValue;
    }

    public boolean matchSB__(int matchValue, int currentValue) {
        return (matchValue <= 4 && getValue(currentValue).equals("Small")) || (matchValue >= 5 && getValue(currentValue).equals("Big"));
    }

    public int getPValue(int currentValue) {

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
