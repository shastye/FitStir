package com.fitstir.fitstirapp.ui.health.finddietitian;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.databinding.FragmentMapInfoBinding;
import com.fitstir.fitstirapp.ui.health.HealthViewModel;
import com.fitstir.fitstirapp.ui.health.edamamapi.Link;
import com.fitstir.fitstirapp.ui.health.placesnearbyapi.GooglePlaces_PlaceDetails;
import com.fitstir.fitstirapp.ui.health.placesnearbyapi.NearbySearchResponse;
import com.fitstir.fitstirapp.ui.health.placesnearbyapi.PlaceDetailResponse;
import com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes.Place;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class MapInfoFragment extends Fragment  {

    private FragmentMapInfoBinding binding;

    private Marker marker;
    private Place place;
    private PlaceDetailResponse response;



    public MapInfoFragment(Marker marker, ArrayList<Place> places) {
        int index;
        try {
            index = Integer.parseInt(Objects.requireNonNull(marker.getSnippet()));
        } catch (NullPointerException | NumberFormatException e) {
            throw new RuntimeException(e);
        }

        String placeId = places.get(index).getPlaceId();
        GooglePlaces_PlaceDetails api = new GooglePlaces_PlaceDetails(placeId);
        try {
            api.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.response = api.getSearchResponse();
        this.place = response.getResult();

        this.marker = marker;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HealthViewModel healthViewModel =
                new ViewModelProvider(this).get(HealthViewModel.class);

        binding = FragmentMapInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here

        Drawable image = place.getImage(requireContext());
        binding.placeImage.setImageDrawable(image);

        binding.placeTitle.setText(place.getName());

        String attrString = binding.placeAttr.getText().toString();
        if (response.getHtmlAttributions().size() == 0) {
            attrString += "None";
        } else {
            for (int i = 0; i < response.getHtmlAttributions().size(); i++) {
                attrString += response.getHtmlAttributions().get(i);

                if (i != response.getHtmlAttributions().size() - 1) {
                    attrString += ", ";
                }
            }
        }
        binding.placeAttr.setText(attrString);

        try {
            String isOpen = "Closed";
            if (place.getCurrentOpeningHours().getOpenNow()) {
                isOpen = "Open";
            }
            binding.placeIsOpen.setText(isOpen);
        } catch (Exception e1) {
            try {
                String isOpen = "Closed";
                if (place.getOpeningHours().getOpenNow()) {
                    isOpen = "Open";
                }
                binding.placeIsOpen.setText(isOpen);
            } catch (Exception e2) {
                binding.placeIsOpen.setVisibility(View.GONE);
            }
        }

        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int index;
        switch (dayOfWeek) {
            case 1:
                index = 6;
                break;
            case 7:
                index = 5;
                break;
            default:
                index = dayOfWeek - 2;
                break;
        }
        try {
            String hours = place.getCurrentOpeningHours().getWeekdayText().get(index);
            hours = hours.substring(hours.indexOf(": ") + 2);
            hours = hours.replace(" – ", "\nto\n");
            binding.placeHoursToday.setText(hours);
        } catch (Exception e1) {
            try {
                String hours = place.getOpeningHours().getWeekdayText().get(index);
                hours = hours.substring(hours.indexOf(": ") + 2);
                hours = hours.replace(" – ", "\nto\n");
                binding.placeHoursToday.setText(hours);
            } catch (Exception e2) {
                binding.placeHoursToday.setVisibility(View.GONE);
            }
        }

        if (place.getInternationalPhoneNumber() != null && !place.getInternationalPhoneNumber().equals("")) {
            binding.placePhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String phoneNumber = place.getInternationalPhoneNumber();
                        phoneNumber = phoneNumber.replace(" ", "");
                        phoneNumber = phoneNumber.replace("-", "");
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                        requireContext().startActivity(callIntent);
                    } catch (Exception e) {
                        binding.placePhone.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            binding.placeUrl.setVisibility(View.GONE);
        }

        try {
            String rating = (Math.round(place.getRating() * 10) / 10) + " ⭐";

            Float numRated = place.getUserRatingsTotal();

            String ratingString = rating;
            if (numRated == null) {
                binding.placeRatingRow.setVisibility(View.GONE);
            } else if (numRated != 0) {
                ratingString = rating + " (" + numRated.intValue() + ")";
            }
            binding.placeRating.setText(ratingString);
        } catch (Exception e) {
            binding.placeRatingRow.setVisibility(View.GONE);
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
