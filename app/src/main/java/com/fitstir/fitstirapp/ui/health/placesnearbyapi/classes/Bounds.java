package com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bounds {
    @JsonProperty("northeast")
    private LatLngLiteral northeast;
    @JsonProperty("southwest")
    private LatLngLiteral southwest;
}
