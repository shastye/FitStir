package com.fitstir.fitstirapp.ui.health.finddietician;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentFindDietitianBinding;
import com.fitstir.fitstirapp.ui.health.HealthViewModel;
import com.fitstir.fitstirapp.ui.health.placesnearbyapi.GooglePlaces_NearbySearch;
import com.fitstir.fitstirapp.ui.health.placesnearbyapi.NearbySearchResponse;
import com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Objects;

public class FindDietitianFragment extends Fragment {

    private FragmentFindDietitianBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HealthViewModel healthViewModel =
                new ViewModelProvider(this).get(HealthViewModel.class);

        binding = FragmentFindDietitianBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFindDietitian;
        //healthViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Addition Text Here

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // When map is loaded
                MapsInitializer.initialize(requireActivity());

                // Get Permissions
                while (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                }

                while (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
                }

                // Set map to current position and zoom
                googleMap.setMyLocationEnabled(true);
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                Location currLoc = new Location(locationManager.getBestProvider(criteria, false));
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    currLoc = locationManager.getLastKnownLocation(Objects.requireNonNull(locationManager.getBestProvider(criteria, false)));
                }
                LatLng latLng = new LatLng(currLoc.getLatitude(), currLoc.getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                // Get dietitians near user
                GooglePlaces_NearbySearch api = new GooglePlaces_NearbySearch(
                        String.valueOf(latLng.latitude),
                        String.valueOf(latLng.longitude),
                        String.valueOf((int) getRadiusInMiles(googleMap)), // in meters... 50,000 meters = max = 31.07 miles
                        "dietitian"
                );
                try {
                    api.execute();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                NearbySearchResponse response = api.getSearchResponse();

                // Show dietitians on map
                ArrayList<Place> places = response.getResults();
                for (int i = 0; i < places.size(); i++) {
                    Place place = places.get(i);

                    float lat = place.getGeometry().getLocation().getLatitude();
                    float lng = place.getGeometry().getLocation().getLongitude();
                    LatLng location = new LatLng(lat, lng);

                    googleMap.addMarker(new MarkerOptions()
                            .position(location)
                            .snippet(Integer.toString(i))
                    );
                }

                // On marker click, inflate information
                final float scale = requireContext().getResources().getDisplayMetrics().density;
                final int pixels =  (int)(59 * scale + 0.5f);   // HOW GET 59?

                googleMap.setInfoWindowAdapter(new InfoWindowAdapter(requireContext(), places));
            }
        });


        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        // PERMISSION GRANTED
                    } else {
                        // PERMISSION NOT GRANTED
                        // TODO: Show error dialog and return to home
                    }
                }
            }
    );

    private float getRadiusInMiles(GoogleMap googleMap) {
        VisibleRegion visibleRegion = googleMap.getProjection().getVisibleRegion();

        float[] diagonalDistance = new float[1];
        Location.distanceBetween(
                visibleRegion.farLeft.latitude,
                visibleRegion.farLeft.longitude,
                visibleRegion.nearRight.latitude,
                visibleRegion.nearRight.longitude,
                diagonalDistance
        );

        return diagonalDistance[0] / 2.0f;
    }
}