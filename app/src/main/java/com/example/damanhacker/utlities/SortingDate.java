package com.example.damanhacker.utlities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SortingDate {

    public  ArrayList<String> sort(ArrayList<String> datees) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // Parse the date strings and store them as Date objects in a list
        List<Date> dates = new ArrayList<>();
        ArrayList<String> datesVal = new ArrayList<>();
        for (String dateString : datees) {
            try {
                Date date = dateFormat.parse(dateString);
                dates.add(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Sort the dates in ascending order
        Collections.sort(dates);

        // Format and print the sorted dates
        for (Date date : dates) {
            String formattedDate = dateFormat.format(date);
           // System.out.println(formattedDate);
            datesVal.add(formattedDate);
        }
        return datesVal;
    }

}
