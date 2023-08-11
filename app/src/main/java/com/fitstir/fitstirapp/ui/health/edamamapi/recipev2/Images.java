package com.fitstir.fitstirapp.ui.health.edamamapi.recipev2;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Images {
    @JsonProperty("THUMBNAIL")
    private Image THUMBNAIL;
    @JsonProperty("SMALL")
    private Image SMALL;
    @JsonProperty("REGULAR")
    private Image REGULAR;
    @JsonProperty("LARGE")
    private Image LARGE;

    public Images() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Images)) {
            return false;
        } else {
            Images images = (Images) o;
            return Objects.equals(THUMBNAIL, images.THUMBNAIL) && Objects.equals(SMALL, images.SMALL) && Objects.equals(REGULAR, images.REGULAR) && Objects.equals(LARGE, images.LARGE);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"THUMBNAIL\"=" + THUMBNAIL + "," +
                "\"SMALL\"=" + SMALL + "," +
                "\"REGULAR\"=" + REGULAR + "," +
                "\"LARGE\"=" + LARGE +
                "}";
    }

    public Image getTHUMBNAIL() { return THUMBNAIL; }
    public Image getSMALL() { return SMALL; }
    public Image getREGULAR() { return REGULAR; }
    public Image getLARGE() { return LARGE; }

    public void setTHUMBNAIL(Image THUMBNAIL) { this.THUMBNAIL = THUMBNAIL; }
    public void setSMALL(Image SMALL) { this.SMALL = SMALL; }
    public void setREGULAR(Image REGULAR) { this.REGULAR = REGULAR; }
    public void setLARGE(Image LARGE) { this.LARGE = LARGE; }
}
