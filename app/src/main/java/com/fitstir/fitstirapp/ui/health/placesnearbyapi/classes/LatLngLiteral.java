package com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LatLngLiteral {
    @JsonProperty("lat")
    private Float latitude;
    @JsonProperty("lng")
    private Float longitude;



    public LatLngLiteral() { }



    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}
