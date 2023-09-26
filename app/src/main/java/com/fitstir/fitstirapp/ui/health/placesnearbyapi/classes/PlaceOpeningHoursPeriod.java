package com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaceOpeningHoursPeriod {
    @JsonProperty("open")
    private PlaceOpeningHoursPeriodDetail open;
    @JsonProperty("close")
    private PlaceOpeningHoursPeriodDetail close;



    public PlaceOpeningHoursPeriod() { }



    public PlaceOpeningHoursPeriodDetail getOpen() {
        return open;
    }

    public void setOpen(PlaceOpeningHoursPeriodDetail open) {
        this.open = open;
    }

    public PlaceOpeningHoursPeriodDetail getClose() {
        return close;
    }

    public void setClose(PlaceOpeningHoursPeriodDetail close) {
        this.close = close;
    }
}
