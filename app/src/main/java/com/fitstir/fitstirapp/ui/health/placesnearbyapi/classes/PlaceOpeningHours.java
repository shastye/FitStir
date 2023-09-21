package com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class PlaceOpeningHours {
    @JsonProperty("open_now")
    private Boolean openNow;
    @JsonProperty("periods")
    private ArrayList<PlaceOpeningHoursPeriod> periods;
    @JsonProperty("special_days")
    private ArrayList<PlaceSpecialDay> specialDays;
    @JsonProperty("type")
    private String type;
    @JsonProperty("weekday_text")
    private ArrayList<String> weekdayText;



    public PlaceOpeningHours() { }



    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public ArrayList<PlaceOpeningHoursPeriod> getPeriods() {
        return periods;
    }

    public void setPeriods(ArrayList<PlaceOpeningHoursPeriod> periods) {
        this.periods = periods;
    }

    public ArrayList<PlaceSpecialDay> getSpecialDays() {
        return specialDays;
    }

    public void setSpecialDays(ArrayList<PlaceSpecialDay> specialDays) {
        this.specialDays = specialDays;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(ArrayList<String> weekdayText) {
        this.weekdayText = weekdayText;
    }
}
