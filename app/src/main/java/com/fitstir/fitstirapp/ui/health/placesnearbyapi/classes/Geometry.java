package com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Geometry {
    @JsonProperty("location")
    private LatLngLiteral location;
    @JsonProperty("viewport")
    private Bounds viewport;



    public Geometry() { }



    public LatLngLiteral getLocation() {
        return location;
    }

    public void setLocation(LatLngLiteral location) {
        this.location = location;
    }

    public Bounds getViewport() {
        return viewport;
    }

    public void setViewport(Bounds viewport) {
        this.viewport = viewport;
    }
}
