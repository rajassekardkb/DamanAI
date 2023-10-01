package com.example.damanhacker.utlities;


import com.example.damanhacker.intefaces.onResultList;
import com.example.damanhacker.model.DataModelMainData;

import java.util.ArrayList;

public class FindDuplicateNumberCount {
    int numberOfCounts = 0;
    int totalCount = 0;
    ArrayList<DataModelMainData> dataList;
    ArrayList<String> finalResult = new ArrayList<>();
    onResultList onResultList_;

    public void patternCheckBasedOnSerialNumber(ArrayList<DataModelMainData> _) {
        this.dataList = _;
    }

    public void patternCheckBasedOnSerialNumber(ArrayList<DataModelMainData> _, onResultList onResult) {
        this.dataList = _;
        totalCount = _.size();
        onResultList_ = onResult;
        finalResult = new ArrayList<>();
        addValue("TotalPeriod->" + totalCount);

        picSerialNumberBasics();
    }

    public void picSerialNumberBasics() {
        numberOfCounts = 0;
        StringBuilder value = new StringBuilder();
        for (int number = 0; number < 10; number++) {
            for (int i = 0; i < dataList.size(); i++) {
                if (dataList.get(i).getNumber() == number) {
                    numberOfCounts++;
                }
            }
            value.append("").append(number).append(":").append(numberOfCounts).append("\n").append("\n");
            numberOfCounts = 0;
        }
        addValue(value.toString());
        if (onResultList_ != null) {
            onResultList_.onItemText(finalResult);
        }
    }

    public void addValue(String value) {
        finalResult.add(value + "");
    }
}
