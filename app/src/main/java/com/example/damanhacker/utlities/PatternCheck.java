package com.example.damanhacker.utlities;

import android.os.Build;

import com.example.damanhacker.database.DBHandler;
import com.example.damanhacker.intefaces.onResultList;
import com.example.damanhacker.model.DataModelMainData;
import com.example.damanhacker.model.getData;

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
                if (String.valueOf(listData.get(k).getColor().charAt(0)).equals("R")) {
                    list.add("R");
                } else {
                    list.add("G");
                }
            }
            getMinMax(list, pattern, listData);
            //getDuplicateNumber(listData);
            Valuelist.add(value.toString());
            value.setLength(0);
        }
        onResult.onItemText(Valuelist);
    }

    public void checkColorPattern(DBHandler dbHandler, String pattern, onResultList onResult) {
        ArrayList<String> dateList = new SortingDate().sort(dbHandler.getDateList());
        ArrayList<String> list;
        for (int i = 0; i < dateList.size(); i++) {
            String date = dateList.get(i);
            ArrayList<DataModelMainData> listData = dbHandler.getDataProcess(date);
            list = new ArrayList<>();
            value.append(date).append("\n");
            value.append("\n");
            for (int k = 0; k < listData.size(); k++) {
                if (String.valueOf(listData.get(k).getColor().charAt(0)).equals("R")) {
                    list.add("R");
                } else {
                    list.add("G");
                }
            }
            getMinMax(list, pattern, listData);
            //getDuplicateNumber(listData);
            Valuelist.add(value.toString());
            value.setLength(0);
        }
        onResult.onItemText(Valuelist);
    }

    public void checkNumberPattern(DBHandler dbHandler, String pattern, onResultList onResult) {
        ArrayList<String> dateList = new SortingDate().sort(dbHandler.getDateList());
        ArrayList<String> list;
        for (int i = 0; i < dateList.size(); i++) {
            String date = dateList.get(i);
            ArrayList<DataModelMainData> listData = dbHandler.getDataProcess(date);
            list = new ArrayList<>();
            value.append(date).append("\n");
            value.append("\n");
            for (int k = 0; k < listData.size(); k++) {
                list.add(listData.get(k).getNumber() + "");
            }
            getMinMax(list, pattern, listData);
            //getDuplicateNumber(listData);
            Valuelist.add(value.toString());
            value.setLength(0);
        }
        onResult.onItemText(Valuelist);
    }

    public void checkValuePattern(DBHandler dbHandler, String pattern, onResultList onResult) {
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
            getMinMax(list, pattern, listData);
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
        //getMaximumNumber(listData);

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

        for (int i = 0; i < 30; i++) {
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

    void getMinMax(ArrayList<String> list, String pattern, ArrayList<DataModelMainData> listData) {
        StringBuilder concatenated = new StringBuilder();
        for (String str : list) {
            concatenated.append(str);
        }
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(concatenated.toString());

        while (matcher.find()) {
            int startPos = matcher.start();
            int endPos = matcher.end();
            //System.out.println("Match found at positions: " + startPos + " - " + (endPos - 1));
            int number = 1001;
            if ((startPos) > 2) {
                number = listData.get(startPos - 1).getNumber();
            }
            value.append("SNO->").append(startPos + 1).append(":").append(new DateUtilities().getTime(startPos + 1)).append("->Number ").append((listData.get(startPos).getNumber())).append("->n ").append(number + "").append("\n");
        }
    }

    void getMatch(ArrayList<DataModelMainData> list, String pattern, ArrayList<DataModelMainData> listData) {
        // Populate your customArrayList with your data here...

        int numberToFind = 1;
        int minGap = Integer.MAX_VALUE;
        int maxGap = 0;
        int lastIndex = -1;

        for (int i = 0; i < list.size(); i++) {
            int value = list.get(i).getNumber();
            if (value == numberToFind) {
                if (lastIndex != -1) {
                    int currentGap = i - lastIndex;
                    minGap = Math.min(minGap, currentGap);
                    maxGap = Math.max(maxGap, currentGap);
                }
                lastIndex = i;
            }
        }
        value.append("Minimum gap for ").append(numberToFind).append(": ").append(minGap).append("\n");
        value.append("Maximum gap for ").append(numberToFind).append(": ").append(maxGap).append("\n");

        System.out.println("Minimum gap for " + numberToFind + ": " + minGap);
        System.out.println("Maximum gap for " + numberToFind + ": " + maxGap);
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

    public void getWiningPattern(ArrayList<DataModelMainData> customArrayList) {


        // Create a new ArrayList to store the retrieved elements
        List<DataModelMainData> retrievedElements = new ArrayList<>();
        ArrayList<Integer> start = new ArrayList<>();
        start = getStartIndex();
        for (int k = 0; k < start.size(); k++) {

            int startIndex = start.get(k);
            int endIndex = start.get(k) + 20;
            for (int i = startIndex; i < endIndex && i < customArrayList.size(); i++) {
                retrievedElements.add(customArrayList.get(i));
            }
        }

        // Display the retrieved elements
        for (DataModelMainData element : retrievedElements) {
            System.out.println(element.getColor());
            // value.append("Pattern gap for ").append(numberToFind).append(": ").append(minGap).append("\n");

        }

    }

    public ArrayList<Integer> getStartIndex() {
        ArrayList<Integer> start = new ArrayList<>();
        start.add(420);
        start.add(600);
        start.add(720);
        start.add(840);
        start.add(960);
        start.add(1110);
        start.add(1230);
        start.add(1320);
        return start;
    }

    public ArrayList<Integer> getEndIndex() {
        ArrayList<Integer> start = new ArrayList<>();
        start.add(420);
        start.add(600);
        start.add(720);
        start.add(840);
        start.add(960);
        start.add(1110);
        start.add(1230);
        start.add(1320);
        return start;
    }

    public void findContinuousOddEvenSequences(ArrayList<DataModelMainData> arrayList) {
        List<Integer> oddSequence = new ArrayList<>();
        List<Integer> evenSequence = new ArrayList<>();
        int currentIndex = 0;

        for (DataModelMainData number : arrayList) {
            if (number.getNumber() % 2 == 0) {
                oddSequence.clear();
                evenSequence.add(number.getNumber());
            } else {
                evenSequence.clear();
                oddSequence.add(number.getNumber());
            }

            if (evenSequence.size() == 4) {
                System.out.println("Continuous Even Sequence: " + evenSequence + " (Starting index: " + (currentIndex - evenSequence.size() + 1) + ")");
            }

            if (oddSequence.size() == 4) {
                System.out.println("Continuous Odd Sequence: " + oddSequence + " (Starting index: " + (currentIndex - oddSequence.size() + 1) + ")");
            }

            currentIndex++;
        }
    }

    public ArrayList<String> findMinNumber() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                list.add(i + ":" + j);
                //System.out.println("findMinNumber--->" + i + ":" + j);
            }
        }
        return list;
    }


    public ArrayList<getData> numberAttachedValue(ArrayList<DataModelMainData> arrayList) {

        ArrayList<getData> repeatedValues = new ArrayList<>();
        int i = 0;

        while (i < arrayList.size()) {
            int currentValue = arrayList.get(i).getNumber();
            int period = arrayList.get(i).getPeriod();
            int sequenceStartIndex = i;

            while (i < arrayList.size() && arrayList.get(i).getNumber() == currentValue) {
                i++;
            }

            if (i - sequenceStartIndex > 1) {
                getData data = new getData();
                data.setValue(currentValue);
                data.setPosition(sequenceStartIndex + 1);
                data.setPeriod(period);
                repeatedValues.add(data);
            }
        }

        for (int j = 0; j < repeatedValues.size(); j++) {
            getData data = repeatedValues.get(j);

            // System.out.println("Repeated value: " + data.getValue() + ":Position->" + data.getPosition());

        }
        return repeatedValues;
    }

    public ArrayList<getData> numberAttachedValue_(ArrayList<DataModelMainData> arrayList) {

        ArrayList<getData> repeatedValues = new ArrayList<>();
        int i = 0;

        while (i < arrayList.size()) {
            int currentValue = arrayList.get(i).getNumber();
            int period = arrayList.get(i).getPeriod();
            int sequenceStartIndex = i;

            while (i < arrayList.size() && arrayList.get(i).getNumber() == currentValue) {
                i++;
            }

            if (i - sequenceStartIndex >= 4) {
                getData data = new getData();
                data.setValue(currentValue);
                data.setPosition(sequenceStartIndex + 1);
                data.setPeriod(period);
                repeatedValues.add(data);
            }
        }

        for (int j = 0; j < repeatedValues.size(); j++) {
            getData data = repeatedValues.get(j);

            // System.out.println("Repeated value: " + data.getValue() + ":Position->" + data.getPosition());

        }
        return repeatedValues;
    }

}
