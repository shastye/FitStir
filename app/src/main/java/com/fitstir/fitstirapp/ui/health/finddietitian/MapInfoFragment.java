package com.fitstir.fitstirapp.ui.health.finddietitian;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.databinding.FragmentMapInfoBinding;
import com.fitstir.fitstirapp.ui.health.HealthViewModel;
import com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes.Place;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Objects;

public class MapInfoFragment extends Fragment  {
    private FragmentMapInfoBinding binding;
    private Marker marker;
    private Place place;

    public MapInfoFragment(Marker marker, ArrayList<Place> places) {
        int index;
        try {
            index = Integer.parseInt(Objects.requireNonNull(marker.getSnippet()));
        } catch (NullPointerException | NumberFormatException e) {
            throw new RuntimeException(e);
        }

        this.place = places.get(index);

        this.marker = marker;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HealthViewModel healthViewModel =
                new ViewModelProvider(this).get(HealthViewModel.class);

        binding = FragmentMapInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here

        String title = marker.getTitle();
        Drawable image = place.getImage(requireContext());

        binding.placeImage.setImageDrawable(image);
        binding.placeTitle.setText(place.getName());
        try {
            String isOpen = "Closed";
            if (place.getCurrentOpeningHours().getOpenNow()) {
                isOpen = "Open";
            }
            binding.placeIsOpen.setText(isOpen);
        } catch (Exception e) {
            binding.placeIsOpen.setVisibility(View.GONE);
        }

        try {
            String hours = place.getCurrentOpeningHours().getPeriods().get(0).getOpen().getTime()
                    + "-"
                    + place.getCurrentOpeningHours().getPeriods().get(0).getClose().getTime();
            binding.placeHoursToday.setText(hours);
        } catch (Exception e) {
            binding.placeHoursToday.setVisibility(View.GONE);
        }

        try {
            String rating = (Math.round(place.getRating() * 10) / 10) + " ⭐";
            binding.placeRating.setText(rating);

            if (rating.equals("0 ⭐")) {
                binding.placeRatingRow.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            binding.placeRating.setVisibility(View.GONE);
        }

        if (place.getUrl() != null && !place.getUrl().equals("")) {
            binding.placeUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String url = place.getUrl();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        requireContext().startActivity(browserIntent);
                    } catch (NullPointerException e) {
                        binding.placeUrl.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            binding.placeUrl.setVisibility(View.GONE);
        }

        if (place.getWebsite() != null && !place.getUrl().equals("")) {
            binding.placeWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String url = place.getWebsite();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        requireContext().startActivity(browserIntent);
                    } catch (NullPointerException e) {
                        binding.placeWebsite.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            binding.placeWebsite.setVisibility(View.GONE);
        }



        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
