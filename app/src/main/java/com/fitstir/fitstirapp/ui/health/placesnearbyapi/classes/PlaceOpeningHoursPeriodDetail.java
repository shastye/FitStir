package com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaceOpeningHoursPeriodDetail {
    @JsonProperty("day")
    private Float day;
    @JsonProperty("time")
    private String time;
    @JsonProperty("date")
    private String date;
    @JsonProperty("truncated")
    private Boolean truncated;



    public PlaceOpeningHoursPeriodDetail() { }



    public Float getDay() {
        return day;
    }

    public void setDay(Float day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getTruncated() {
        return truncated;
    }

    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }
}
