package com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class PlacePhoto {
    @JsonProperty("height")
    private Float height;
    @JsonProperty("html_attributions")
    private ArrayList<String> htmlAttributions;
    @JsonProperty("photo_reference")
    private String photoReference;
    @JsonProperty("width")
    private Float width;



    public PlacePhoto() { }



    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public ArrayList<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(ArrayList<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }
}
