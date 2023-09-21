package com.fitstir.fitstirapp.ui.health.finddietician;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Objects;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View window;
    private final Context context;
    private final ArrayList<Place> places;

    private final RelativeLayout ratingRow;
    private final ShapeableImageView image;
    private final TextView title, isOpen, hoursToday, rating;
    private final ImageButton url, website;

    public InfoWindowAdapter(Context context, ArrayList<Place> places) {
        this.context = context;
        this.places = places;
        this.window = LayoutInflater.from(context).inflate(R.layout.popup_map_info, null);

        this.ratingRow = this.window.findViewById(R.id.place_rating_row);
        this.image = this.window.findViewById(R.id.place_image);
        this.title = this.window.findViewById(R.id.place_title);
        this.isOpen = this.window.findViewById(R.id.place_is_open);
        this.hoursToday = this.window.findViewById(R.id.place_hours_today);
        this.rating = this.window.findViewById(R.id.place_rating);
        this.url = this.window.findViewById(R.id.place_url);
        this.website = this.window.findViewById(R.id.place_website);
    }

    private void bind(Marker marker) {
        String title;
        int index;
        try {
            title = marker.getTitle();
            index = Integer.parseInt(Objects.requireNonNull(marker.getSnippet()));
        } catch (NullPointerException | NumberFormatException e) {
            throw new RuntimeException(e);
        }

        Place place = places.get(index);
        Drawable image = place.getImage(context);

        this.image.setImageDrawable(image);
        this.title.setText(place.getName());

        try {
            String isOpen = "Closed";
            if (place.getCurrentOpeningHours().getOpenNow()) {
                isOpen = "Open";
            }
            this.isOpen.setText(isOpen);
        } catch (Exception e) {
            this.isOpen.setVisibility(View.GONE);
        }

        try {
            String hours = place.getCurrentOpeningHours().getPeriods().get(0).getOpen().getTime()
                    + "-"
                    + place.getCurrentOpeningHours().getPeriods().get(0).getClose().getTime();
            this.hoursToday.setText(hours);
        } catch (Exception e) {
            this.hoursToday.setVisibility(View.GONE);
        }

        try {
            String rating = (Math.round(place.getRating() * 10) / 10) + " ⭐";
            this.rating.setText(rating);

            if (rating.equals("0 ⭐")) {
                this.ratingRow.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            this.rating.setVisibility(View.GONE);
        }

        try {
            this.url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = place.getUrl();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(browserIntent);
                }
            });
        } catch (Exception e) {
            this.url.setVisibility(View.GONE);
        }

        try {
            this.website.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = place.getWebsite();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(browserIntent);
                }
            });

        } catch (Exception e) {
            this.website.setVisibility(View.GONE);
        }
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        bind(marker);
        return this.window;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        bind(marker);
        return this.window;
    }
}
