package com.fitstir.fitstirapp.ui.goals;

import java.util.Calendar;
import java.util.Date;

public class GoalDataPair {
    public Date first;
    public double second;


    GoalDataPair() {
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
}
