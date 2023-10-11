package com.example.damanhacker.utlities;

import com.example.damanhacker.model.DataModelMainData;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternCheck {
    StringBuilder value = new StringBuilder();
    ArrayList<String> finalResult = new ArrayList<>();

    public void patternCheckBasedOnSerialNumber() {
        ArrayList<DataModelMainData> dataList = new Mapping().getDummyData();
        DataModelMainData valueCurrent = dataList.get(0);
    }

    public void pickDataP_(ArrayList<String> list, String pattern) {

        StringBuilder concatenated = new StringBuilder();
        for (String str : list) {
            concatenated.append(str);
        }
        // Check if the concatenated string matches the pattern
        if (concatenated.toString().contains(pattern)) {
            System.out.println("Pattern found in the ArrayList." + pattern);
        } else {
            System.out.println("Pattern not found in the ArrayList." + concatenated);
        }
    }

    public void pickDataP(ArrayList<String> list, String pattern) {
        System.out.println("Match found at p Here..."+list.size());
        StringBuilder concatenated = new StringBuilder();
        for (String str : list) {
            concatenated.append(str);
        }

        // Use regular expression and Matcher to find matches and positions
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(concatenated.toString());

        while (matcher.find()) {
            int startPos = matcher.start();
            int endPos = matcher.end();
            System.out.println("Match found at positions: " + startPos + " - " + (endPos - 1));
        }
    }

}
