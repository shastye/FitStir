package com.fitstir.fitstirapp.ui.health.finddietician;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentFindDietitianBinding;
import com.fitstir.fitstirapp.ui.health.HealthViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Array;
import java.util.Arrays;

public class FindDietitianFragment extends Fragment {

    private FragmentFindDietitianBinding binding;
    private GoogleMap map;

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
                map = googleMap;

                MapsInitializer.initialize(requireActivity());

                while (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                }


                googleMap.setMyLocationEnabled(true);
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();

                Location currLoc = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                if (currLoc != null) {
                    LatLng latLng = new LatLng(currLoc.getLatitude(), currLoc.getLongitude());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }

                    /*googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            // When clicked on map
                            // Initialize marker options
                            MarkerOptions markerOptions = new MarkerOptions();
                            // Set position of marker
                            markerOptions.position(latLng);
                            // Set title of marker
                            markerOptions.title(latLng.latitude+" : "+latLng.longitude);
                            // Remove all marker
                            googleMap.clear();
                            // Animating to zoom the marker
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                            // Add marker on map
                            googleMap.addMarker(markerOptions);
                        }
                    });*/
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
                        int y = 0;
                    }
                }
            }
    );
}