package com.fitstir.fitstirapp.ui.health.calorietracker;

import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Parsed;

public class CalorieTrackerDataPair {
    public String mealType;
    public Parsed item;


    CalorieTrackerDataPair() {
        this.mealType = "";
        this.item = new Parsed();
    }
    public CalorieTrackerDataPair(String mealType, Parsed item) {
        this.mealType = mealType;
        this.item = item;
    }
    public CalorieTrackerDataPair(CalorieTrackerDataPair pair) {
        this.mealType = pair.mealType;
        this.item = pair.item;
    }
}
