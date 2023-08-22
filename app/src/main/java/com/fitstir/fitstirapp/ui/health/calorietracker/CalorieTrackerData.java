package com.fitstir.fitstirapp.ui.health.calorietracker;

import java.util.ArrayList;
import java.util.Calendar;

public class CalorieTrackerData {

    /*
    *
    *
    * date
    * meal type
    * parsed
    *
    *
    * array with date as main thing
    * each date has a meal type and parsed value
    *
    *
    *
    * */

    Calendar date;
    ArrayList<CalorieTrackerDataPair> data;

    CalorieTrackerData() {}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Calendar)) {
            return false;
        } else {
            Calendar date = (Calendar) o;

            int tDay = date.get(Calendar.DAY_OF_YEAR);
            int day = this.date.get(Calendar.DAY_OF_YEAR);

            return tDay == day;
        }
    }
}
