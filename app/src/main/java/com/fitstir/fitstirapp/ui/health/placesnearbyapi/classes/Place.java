package com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.appcompat.content.res.AppCompatResources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.health.placesnearbyapi.GooglePlaces_PhotoSearch;

import java.util.ArrayList;

public class Place {
    @JsonProperty("business_status")
    private String businessStatus;
    @JsonProperty("current_opening_hours")
    private PlaceOpeningHours currentOpeningHours;
    @JsonProperty("editorial_summary")
    private PlaceEditorialSummary editorialSummary;
    @JsonProperty("formatted_address")
    private String formattedAddress;
    @JsonProperty("formatted_phone_number")
    private String formattedPhoneNumber;
    @JsonProperty("geometry")
    private Geometry geometry;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("international_phone_number")
    private String internationalPhoneNumber;
    @JsonProperty("name")
    private String name;
    @JsonProperty("opening_hours")
    private PlaceOpeningHours openingHours;
    @JsonProperty("photos")
    private ArrayList<PlacePhoto> photos;
    @JsonProperty("place_id")
    private String placeId;
    @JsonProperty("price_level")
    private Float priceLevel;
    @JsonProperty("rating")
    private Float rating;
    @JsonProperty("url")
    private String url;
    @JsonProperty("user_ratings_total")
    private Float userRatingsTotal;
    @JsonProperty("website")
    private String website;



    public Place() { }


    public Drawable getImage(Context context) {
        try {
            GooglePlaces_PhotoSearch api = new GooglePlaces_PhotoSearch(photos.get(0).getPhotoReference());
            api.execute();

            return new BitmapDrawable(context.getResources(), api.getSearchResponse());
        } catch (Exception e) {
            return AppCompatResources.getDrawable(context, R.drawable.default_google_image);
        }
    }



    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public PlaceOpeningHours getCurrentOpeningHours() {
        return currentOpeningHours;
    }

    public void setCurrentOpeningHours(PlaceOpeningHours currentOpeningHours) {
        this.currentOpeningHours = currentOpeningHours;
    }

    public PlaceEditorialSummary getEditorialSummary() {
        return editorialSummary;
    }

    public void setEditorialSummary(PlaceEditorialSummary editorialSummary) {
        this.editorialSummary = editorialSummary;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Float getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(Float priceLevel) {
        this.priceLevel = priceLevel;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Float getUserRatingsTotal() {
        return userRatingsTotal;
    }

    public void setUserRatingsTotal(Float userRatingsTotal) {
        this.userRatingsTotal = userRatingsTotal;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public PlaceOpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(PlaceOpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public ArrayList<PlacePhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<PlacePhoto> photos) {
        this.photos = photos;
    }

    public String getInternationalPhoneNumber() {
        return internationalPhoneNumber;
    }

    public void setInternationalPhoneNumber(String internationalPhoneNumber) {
        this.internationalPhoneNumber = internationalPhoneNumber;
    }
}
