package com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaceSpecialDay {
    @JsonProperty("date")
    private String date;
    @JsonProperty("exceptional_hours")
    private Boolean exceptionalHours;



    public PlaceSpecialDay() { }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getExceptionalHours() {
        return exceptionalHours;
    }

    public void setExceptionalHours(Boolean exceptionalHours) {
        this.exceptionalHours = exceptionalHours;
    }
}
