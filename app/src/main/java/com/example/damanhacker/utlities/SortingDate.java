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
        Collections.sort(dates);
        for (Date date : dates) {
            String formattedDate = dateFormat.format(date);
            datesVal.add(formattedDate);
        }
        return datesVal;
    }
}
