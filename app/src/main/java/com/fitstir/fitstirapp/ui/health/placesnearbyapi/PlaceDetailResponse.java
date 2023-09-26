package com.fitstir.fitstirapp.ui.health.placesnearbyapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes.Place;

import java.util.ArrayList;

public class PlaceDetailResponse {
    @JsonProperty("html_attributions")
    private
    ArrayList<String> htmlAttributions;
    @JsonProperty("result")
    private Place result;
    @JsonProperty("status")
    private String status;



    public PlaceDetailResponse() { }



    public ArrayList<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(ArrayList<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public Place getResult() {
        return result;
    }

    public void setResult(Place result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
