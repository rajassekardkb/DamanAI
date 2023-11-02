package com.example.damanhacker.utlities;


import com.example.damanhacker.intefaces.onResultList;
import com.example.damanhacker.model.DataModelMainData;
import com.example.damanhacker.model.getData;

import java.util.ArrayList;

public class CheckSerialNumberRelated {
    int matchingClear = 0;
    int masterMatchValue = 0;
    onResultList onResultList_;

    int serialNext = 0;
    int loopMax = 0;
    int serialCheck = 0;
    int serialNumberPositionMoveForward = 0;
    int serialNumberPosition = 0;
    boolean serialEnable = false;

    ArrayList<getData> serialNumberList = new ArrayList<>();
    ArrayList<DataModelMainData> dataList;
    ArrayList<String> finalResult = new ArrayList<>();

    public void patternCheckBasedOnSerialNumber(ArrayList<getData> listN_, ArrayList<DataModelMainData> _, onResultList onResult) {
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
            serialNumberPosition = serialNumberList.get(serialNumberPositionMoveForward).getPosition();
            masterMatchValue = serialNumberList.get(serialNumberPositionMoveForward).getValue();
            getMatch(serialNumberPosition, (serialNumberPosition + 1));
            serialNumberPositionMoveForward++;
        }
        System.out.println("NumberRelated Total Count->" + ":" + finalResult.size());
        for (int k = 0; k < finalResult.size(); k++) {
            System.out.println("NumberRelated->" + finalResult.get(k));
        }
    }


    public void getMatch(int startPosition, int matchPosition) {
        if (dataList.size() == matchPosition) {
            return;
        }

        StringBuilder value = new StringBuilder();

        value.append("\n").append("Position->").append(serialNumberPosition).append(":Value->").append(masterMatchValue);
        value.append("\n").append(new DateUtilities().getTime(dataList.get(startPosition).getPeriod()));
        loopMax = 0;

        for (int i = startPosition; i < dataList.size(); i++) {

            int currentValue = dataList.get(i).getNumber();

            if (!match(masterMatchValue, currentValue)) {
                loopMax++;
                value.append("\n").append(dataList.get(i).getPeriod()).append(" : ").append(dataList.get(i).getNumber()).append(" : ").append(getFValue(currentValue)).append(":").append(getPValue(currentValue));
                matchingClear++;
                if (i == dataList.size() - 1) {
                    addValue(value.toString());
                }
            } else {
                addValue(value.toString());
                value.setLength(0);
                value.append("\n").append(dataList.get(i).getPeriod()).append(" : ").append(dataList.get(i).getNumber()).append(" : ").append(getFValue(currentValue)).append(":").append(getPValue(currentValue));
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

        if (matchingClear >= 10) {

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

    public int getFValue(int currentValue) {
        if (currentValue == 0) {
            currentValue = 9;
        }
        return currentValue;
    }


    public int getPValue(int currentValue) {
        if (currentValue == 9) {
            currentValue = 0;
        }
        return currentValue;
    }


}
