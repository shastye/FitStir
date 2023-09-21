package com.fitstir.fitstirapp.ui.health.placesnearbyapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes.Place;

import java.util.ArrayList;

public class NearbySearchResponse {
    // required
    @JsonProperty("html_attributions")
    private ArrayList<String> htmlAttributions;
    @JsonProperty("results")
    private ArrayList<Place> results;
    @JsonProperty("status")
    private String status;

    public NearbySearchResponse() {
        htmlAttributions = new ArrayList<>();
        results = new ArrayList<>();
        status = "";
    }


    public ArrayList<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(ArrayList<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public ArrayList<Place> getResults() {
        return results;
    }

    public void setResults(ArrayList<Place> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
