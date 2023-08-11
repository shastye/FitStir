package com.fitstir.fitstirapp.ui.health.edamamapi.recipev2;

import java.util.Objects;

public class Image {
    private String url;
    private int width;
    private int height;

    public Image() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Image)) {
            return false;
        } else {
            Image image = (Image) o;
            int index = image.url.indexOf('?');
            return width == image.width && height == image.height && Objects.equals(url.substring(0,index), image.url.substring(0,index));
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"url\"=\"" + url + "\"," +
                "\"width\"=" + width + "," +
                "\"height\"=" + height +
                "}";
    }

    public String getUrl() { return url; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setUrl(String url) { this.url = url; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
}
