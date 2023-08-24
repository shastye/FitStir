package com.fitstir.fitstirapp.ui.health.calorietracker;

import com.fitstir.fitstirapp.ui.health.edamamapi.ISearchResult;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Parsed;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Calendar;

public class ResponseInfo {

    private String userID;
    private String resultID;
    private Calendar date;
    private String mealType;
    private ISearchResult item;
    private int quantity;


    public ResponseInfo() {
        this.userID = "";
        this.date = Calendar.getInstance();
        this.mealType = "";
        this.item = new Parsed();
        this.quantity = 0;

        this.resultID = RandomStringUtils.randomAlphanumeric(24); // ID is 24 characters long
    }
    public ResponseInfo(Calendar date) {
        this.userID = "";
        this.date = date;
        this.mealType = "";
        this.item = new Parsed();
        this.quantity = 0;

        this.resultID = RandomStringUtils.randomAlphanumeric(24); // ID is 24 characters long
    }
    public ResponseInfo(Calendar date, String mealType, ISearchResult item, int quantity) {
        this.userID = "";
        this.date = date;
        this.mealType = mealType;
        this.item = item;
        this.quantity = quantity;

        this.resultID = RandomStringUtils.randomAlphanumeric(24); // ID is 24 characters long
    }


    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public Calendar getDate() {
        return date;
    }
    public void setDate(Calendar date) {
        this.date = date;
    }
    public String getMealType() {
        return mealType;
    }
    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
    public ISearchResult getItem() {
        return item;
    }
    public void setItem(ISearchResult item) {
        this.item = item;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getResultID() {
        return resultID;
    }
    public void setResultID(String resultID) {
        this.resultID = resultID;
    }



    public boolean isDate(Calendar date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date.getTime());

        Calendar c2 = Calendar.getInstance();
        c2.setTime(this.date.getTime());

        int d1 = c1.get(Calendar.DAY_OF_YEAR);
        int d2 = c2.get(Calendar.DAY_OF_YEAR);

        return d1 == d2;
    }
}
