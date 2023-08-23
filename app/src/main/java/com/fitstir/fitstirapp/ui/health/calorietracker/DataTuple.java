package com.fitstir.fitstirapp.ui.health.calorietracker;

import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Parsed;

import java.util.Calendar;
import java.util.Date;

public class DataTuple {

    private Date date;
    private String mealType;
    private Parsed item;


    DataTuple() {
        this.date = Calendar.getInstance().getTime();
        this.mealType = "";
        this.item = new Parsed();
    }
    DataTuple(Date date) {
        this.date = date;
        this.mealType = "";
        this.item = new Parsed();
    }
    DataTuple(Date date, String mealType, Parsed item) {
        this.date = date;
        this.mealType = mealType;
        this.item = item;
    }


    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getMealType() {
        return mealType;
    }
    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
    public Parsed getItem() {
        return item;
    }
    public void setItem(Parsed item) {
        this.item = item;
    }

    public boolean isDate(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(this.date);

        int d1 = c1.get(Calendar.DAY_OF_YEAR);
        int d2 = c2.get(Calendar.DAY_OF_YEAR);

        return d1 == d2;
    }
}
