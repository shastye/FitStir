package com.fitstir.fitstirapp.ui.goals;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GoalDataPair {
    public Date first;
    public double second;


    public GoalDataPair() {
        Calendar zero = Calendar.getInstance();
        zero.clear();

        this.first = zero.getTime();
        this.second = 0.0;
    }
    public GoalDataPair(Date first, double second) {
        this.first = first;
        this.second = second;
    }
    public GoalDataPair(GoalDataPair pair) {
        this.first = pair.first;
        this.second = pair.second;
    }

    public String getDateAsString() {
        Locale loc = new Locale.Builder().setLanguage("en").setRegion("US").build();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, loc);

        String date = dateFormat.format(this.first);
        return date;
    }
}
