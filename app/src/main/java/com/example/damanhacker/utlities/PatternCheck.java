package com.example.damanhacker.utlities;

import com.example.damanhacker.model.DataModelMain;
import com.example.damanhacker.model.returnData;

import java.util.ArrayList;

public class PatternCheck {
    StringBuilder value = new StringBuilder();

    ArrayList<String> finalResult = new ArrayList<>();
    public void patternCheckBasedOnSerialNumber() {
        ArrayList<DataModelMain> dataList = new Mapping().getDummyData();
        DataModelMain valueCurrent = dataList.get(0);
        pickData(1, dataList, valueCurrent.getColor());
    }

    public void pickData(int startPosition_, ArrayList<DataModelMain> list, String matchValue) {
        returnData returnDataVl = new returnData(startPosition_, matchValue);
        StringBuilder value = new StringBuilder(matchValue);
        int totalLoop = 0;
        int matchingClear = 0;

        for (int i = startPosition_; i < list.size(); i++) {
            totalLoop++;
            String currentColor = list.get(i).getColor();

            if (matchValue.equals(currentColor)) {
                value.append(currentColor);
                matchingClear++;
                if (i == list.size() - 1 && matchingClear > 1) {
                    finalResult.add(value.toString());
                    System.out.println("if->last position--" + totalLoop);
                }
            } else {
                if (matchingClear > 1) {
                    finalResult.add(value.toString());
                }
                matchingClear = 0;
                value.setLength(0); // Clear the StringBuilder
                matchValue = currentColor;
                value.append(matchValue);
            }
        }

        for (String result : finalResult) {
            System.out.println("Final--" + result);
        }
    }
}
