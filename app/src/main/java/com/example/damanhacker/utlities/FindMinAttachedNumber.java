package com.example.damanhacker.utlities;


import com.example.damanhacker.intefaces.onResultList;
import com.example.damanhacker.model.DataModelMainData;

import java.util.ArrayList;

public class FindMinAttachedNumber {
    int numberOfCounts = 0;
    int number = 0;
    int gap = 0;
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
        int number = 9;
        StringBuilder value = new StringBuilder();
        //  for (int number = 0; number < 10; number++) {
        for (int i = 0; i < dataList.size(); i++) {
            DataModelMainData data = dataList.get(i);
            if (data.getNumber() == number) {
                numberOfCounts++;
                if (gap >= 20) {
                    value.append("P->").append(new DateUtilities().getTime(data.getPeriod())).append(" ").append("G->").append(gap).append("\n");
                }
                gap = 0;
            } else {
                gap++;
            }
        }
        // value.append("").append(number).append(":").append(numberOfCounts).append("\n").append("\n");
        numberOfCounts = 0;
        //  }
        addValue(value.toString());
        if (onResultList_ != null) {
            onResultList_.onItemText(finalResult);
        }
    }

    public void addValue(String value) {
        finalResult.add(value + "");
    }
}
