package com.example.damanhacker.utlities;

import android.os.Build;

import com.example.damanhacker.database.DBHandler;
import com.example.damanhacker.intefaces.onResultList;
import com.example.damanhacker.model.DataModelMainData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternCheck {
    StringBuilder value = new StringBuilder();
    ArrayList<String> Valuelist = new ArrayList<>();

    public void pickDataP(DBHandler dbHandler, String pattern, onResultList onResult) {
        ArrayList<String> dateList = new SortingDate().sort(dbHandler.getDateList());
        ArrayList<String> list;
        for (int i = 0; i < dateList.size(); i++) {
            String date = dateList.get(i);
            ArrayList<DataModelMainData> listData = dbHandler.getDataProcess(date);
            list = new ArrayList<>();
            value.append(date).append("\n");
            value.append("\n");
            for (int k = 0; k < listData.size(); k++) {
                if (listData.get(k).getValue().equals("Small")) {
                    list.add("S");
                } else {
                    list.add("B");
                }
            }
            //getMatch(list, pattern);
            //getDuplicateNumber(listData);
            Valuelist.add(value.toString());
            value.setLength(0);
        }
        onResult.onItemText(Valuelist);
    }

    public void pickDataDuplicateNumber(DBHandler dbHandler) {
        ArrayList<String> dateList = new SortingDate().sort(dbHandler.getDateList());
        System.out.println("Date----->" + (dateList.get(dateList.size() - 2)));
        ArrayList<DataModelMainData> listData = dbHandler.getDataProcess((dateList.get(dateList.size() - 2)));
        //getDuplicateNumber(listData);
        getMaximumNumber(listData);

    }

    public void pickDataDuplicateNumberMax(DBHandler dbHandler) {
        ArrayList<String> dateList = new SortingDate().sort(dbHandler.getDateList());
        //getDuplicateNumber(listData);
        //  new findMaxCount().main(listData);
        ArrayList<DataModelMainData> listData = dbHandler.getDataProcess("14-10-2023");
        getDuplicateNumber(listData);

        // new findLeastNumber().main(listData);

 /*
        for (int i = 0; i < dateList.size(); i++) {
            System.out.println("Date----->" + (dateList.get(i)));
            ArrayList<DataModelMainData> listData = dbHandler.getDataProcess((dateList.get(i)));
            //new findMaxCount().main(listData, dateList.get(i));              getDuplicateNumber(listData);
            new findLeastNumber().main(listData);
            //getDuplicateNumber(listData);
        }*/
    }

    public ArrayList<String> getLast15Days() {
        ArrayList<String> dateList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Set the initial date

        for (int i = 0; i < 10; i++) {
            dateList.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, -1); // Subtract one day from the current date
        }
        return dateList;
    }

    void getDuplicateNumber(ArrayList<DataModelMainData> list) {

        Map<Integer, List<Integer>> numberToPositions = new HashMap<>();
        int size = list.size();
        for (int i = 0; i < list.size(); i++) {
            DataModelMainData data = list.get(i);
            int number = data.getNumber();
            if (!numberToPositions.containsKey(number)) {
                numberToPositions.put(number, new ArrayList<>());
            }
            numberToPositions.get(number).add(i + 1);
        }

        for (Map.Entry<Integer, List<Integer>> entry : numberToPositions.entrySet()) {
            int number = entry.getKey();
            List<Integer> positions = entry.getValue();
            if (positions.size() > 1 && number == 9) {
                //System.out.println("Number " + number + " is duplicated at positions: " + positions);
                //System.out.println("Number->" + number);

                for (int i = 0; i < positions.size() - 1; i++) {
                    int gap = positions.get(i + 1) - positions.get(i);
                    //if (gap >= 50) {
                    //System.out.println("Number -->" + number + ":Gap between " + new DateUtilities().getTime(positions.get(i)) + ":" + positions.get(i) + " and " + new DateUtilities().getTime(positions.get(i + 1)) + ":" + positions.get(i + 1) + " is " + gap);
                    //System.out.println(positions.get(i));
                    //}
                    //  System.out.println(i + "");
                    if ((i == positions.size() - 2)) {
                        // System.out.println("Last Number Number->" + positions.get(i) + ":" + i);
                        // System.out.println("Gap between " + positions.get(i) + " and " + positions.get(i + 1) + " is " + gap + ":Number->" + number + ":" + i);
                    }
                }
                for (int i = 0; i < positions.size(); i++) {
                    if ((i == positions.size() - 1)) {
                        int gap = size - positions.get(i);
                        //System.out.println("Leading  " + positions.get(i) + " is " + gap + ":number-->" + number);
                        //  System.out.println("Number is->" + number + " leading->" + gap);
                    }
                }
            }
        }
    }

    void getMatch(ArrayList<String> list, String pattern) {
        StringBuilder concatenated = new StringBuilder();
        for (String str : list) {
            concatenated.append(str);
        }
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(concatenated.toString());

        while (matcher.find()) {
            int startPos = matcher.start();
            int endPos = matcher.end();
            System.out.println("Match found at positions: " + startPos + " - " + (endPos - 1));
            value.append(startPos).append("-").append((endPos - 1)).append("\n");
        }
    }


    public void getMaximumNumber(ArrayList<DataModelMainData> numbers) {
        Map<Integer, Integer> countMap = new HashMap<>();
        int maxCount = 0;
        int maxRepeatedNumber = 0;

        for (DataModelMainData number : numbers) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                countMap.put(number.getNumber(), countMap.getOrDefault(number, 0) + 1);
            }
            int count = countMap.get(number);
            if (count > maxCount) {
                maxCount = count;
                maxRepeatedNumber = number.getNumber();
            }
        }

        if (maxCount > 1) {
            System.out.println("getMaximumNumber->The most repeated duplicate number is " + maxRepeatedNumber + " with a count of " + maxCount);
        } else {
            System.out.println("getMaximumNumber->No duplicates found or all numbers occur only once.");
        }

    }

}
