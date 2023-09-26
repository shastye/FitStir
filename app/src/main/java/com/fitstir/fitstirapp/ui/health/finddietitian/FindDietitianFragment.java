package com.fitstir.fitstirapp.ui.health.finddietitian;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentFindDietitianBinding;
import com.fitstir.fitstirapp.ui.health.placesnearbyapi.GooglePlaces_NearbySearch;
import com.fitstir.fitstirapp.ui.health.placesnearbyapi.NearbySearchResponse;
import com.fitstir.fitstirapp.ui.health.placesnearbyapi.classes.Place;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class FindDietitianFragment extends Fragment {

    private FragmentFindDietitianBinding binding;

    private ArrayList<Place> places;
    private LatLng currLatLng;
    private GoogleMap map;
    private Circle circle;
    private int grey;

    private float distanceMiles;
    private int minRating;
    private int maxRating;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFindDietitianBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here

        // Get Permissions
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            doMapThings();
        }

        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void doMapThings() {



        //region Map instantiation /////////////////////////////////////////////////



        MapInfoWindowFragment supportMapFragment = (MapInfoWindowFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // When map is loaded
                MapsInitializer.initialize(requireActivity());
                map = googleMap;
                distanceMiles = 15;
                minRating = 0;
                maxRating = 5;
                grey = 0x44000000;

                // Set map to current position and zoom
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                }
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                Location currLoc = new Location(locationManager.getBestProvider(criteria, false));
                currLoc = locationManager.getLastKnownLocation(Objects.requireNonNull(locationManager.getBestProvider(criteria, false)));
                currLatLng = new LatLng(currLoc.getLatitude(), currLoc.getLongitude());
                zoomToRadius(distanceMiles);

                // Show filters on button click
                binding.filterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = LayoutInflater.from(requireActivity());
                        View popUpView = inflater.inflate(R.layout.popup_map_filter, null);
                        PopupWindow popupWindow = new PopupWindow(popUpView, LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                        String[] minRatingOptions = new String[]{"Min", "0", "1", "2", "3", "4", "5"};
                        String[] maxRatingOptions = new String[]{"Max", "0", "1", "2", "3", "4", "5"};
                        Spinner minSpinner = Methods.getSpinnerWithAdapter(requireActivity(), popUpView, R.id.min_rating, minRatingOptions);
                        Spinner maxSpinner = Methods.getSpinnerWithAdapter(requireActivity(), popUpView, R.id.max_rating, maxRatingOptions);

                        SeekBar seekBar = popUpView.findViewById(R.id.distance_slider);
                        seekBar.setProgress((int) distanceMiles);
                        ((TextView) popUpView.findViewById(R.id.distance_from_bar)).setText(String.valueOf((int) distanceMiles));
                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                if (fromUser) {
                                    ((TextView) popUpView.findViewById(R.id.distance_from_bar)).setText(String.valueOf(progress));
                                }
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        });

                        AppCompatButton accept = popUpView.findViewById(R.id.map_accept_button);
                        accept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    int minIndex = minSpinner.getSelectedItemPosition();
                                    int maxIndex = maxSpinner.getSelectedItemPosition();

                                    if (minIndex > maxIndex) {
                                        if (maxIndex == 0) {
                                            minRating = minIndex - 1;
                                            maxRating = 5;
                                        } else {
                                            throw new IndexOutOfBoundsException("Min rating must be smaller than\nor equal to the max rating.");
                                        }
                                    } else if (minIndex == maxIndex) {
                                        if (minIndex == 0) {
                                            minRating = 0;
                                            maxRating = 5;
                                        } else {
                                            minRating = minIndex - 1;
                                            maxRating = maxIndex - 1;
                                        }
                                    } else { // minIndex < maxIndex
                                        if (minIndex == 0) {
                                            minRating = 0;
                                        } else {
                                            minRating = minIndex - 1;
                                        }
                                        maxRating = maxIndex - 1;
                                    }

                                    distanceMiles = seekBar.getProgress();

                                    popupWindow.dismiss();

                                    // TODO: FOR SHOWING ZOOM FUNCTIONALITY
                                                    /*circle.remove();
                                                    circle = map.addCircle(new CircleOptions()
                                                            .center(currLatLng)
                                                            .radius(distanceMiles * 1609.34f)
                                                            .strokeColor(Color.BLACK)
                                                            .fillColor(grey));*/
                                    zoomToRadius(distanceMiles);
                                } catch (IndexOutOfBoundsException e) {
                                    TextView errorText = (TextView) popUpView.findViewById(R.id.error);
                                    errorText.setError("");
                                    errorText.setText(e.getMessage());

                                    minSpinner.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            errorText.setText("");
                                            errorText.setError(null);
                                            return false;
                                        }
                                    });

                                    maxSpinner.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            errorText.setText("");
                                            errorText.setError(null);
                                            return false;
                                        }
                                    });
                                }
                            }
                        });

                        popupWindow.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
                    }
                });

                // Show dietitians on map on button click
                binding.searchButton.setOnClickListener(getSearchListener());

                // On marker click, inflate information
                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Nullable
                    @Override
                    public View getInfoContents(@NonNull Marker marker) {
                        InfoWindow infoWindow = new InfoWindow(
                                marker,
                                new InfoWindow.MarkerSpecification(0, 100),
                                new MapInfoFragment(marker, places)
                        );

                        supportMapFragment.infoWindowManager().toggle(infoWindow, true);
                        return null;
                    }

                    @Nullable
                    @Override
                    public View getInfoWindow(@NonNull Marker marker) {
                        return null;
                    }
                });
            }
        });



        //endregion ////////////////////////////////////////////////////////////////



    }

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {



                    if (result) {



                        doMapThings();



                    } else {
                        LayoutInflater inflater2 = LayoutInflater.from(requireActivity());
                        View popUpView = inflater2.inflate(R.layout.dialog_generic_alert, null);
                        PopupWindow popupWindow = new PopupWindow(popUpView, LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                        ((TextView) popUpView.findViewById(R.id.dialog_generic_message)).setText("Location permission is required for this section.");
                        ((TextView) popUpView.findViewById(R.id.dialog_generic_continue)).setText("Redirecting back to\nmain health page.");
                        ((Button) popUpView.findViewById(R.id.dialog_generic_cancel_button)).setVisibility(View.GONE);

                        ((Button) popUpView.findViewById(R.id.dialog_generic_accept_button)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.action_navigation_find_dietician_to_navigation_health);
                            }
                        });
                        popupWindow.showAtLocation(popUpView, Gravity.CENTER, 0,0);
                    }
                }
            }
    );

    private float getZoomLevel(float miles) {
        float meters = miles * 1609.34f;
        float scale = meters / 500.0f;
        return ((float) (16.2f - Math.log(scale) / Math.log(2)));
    }

    private void zoomToRadius (float miles) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currLatLng, getZoomLevel(miles)));
    }

    private View.OnClickListener getSearchListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get dietitians near user
                GooglePlaces_NearbySearch api = new GooglePlaces_NearbySearch(
                        String.valueOf(currLatLng.latitude),
                        String.valueOf(currLatLng.longitude),
                        String.valueOf(distanceMiles * 1609.37f), // in meters... 50,000 meters = max = 31.07 miles
                        "dietitian",
                        minRating,
                        maxRating
                );
                try {
                    api.execute();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                NearbySearchResponse response = api.getSearchResponse();
                places = response.getResults();


                // Update Screen
                binding.filterButton.setVisibility(View.GONE);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                params.removeRule(RelativeLayout.START_OF);
                params.addRule(RelativeLayout.ALIGN_PARENT_END);
                v.setLayoutParams(params);

                for (int i = 0; i < places.size(); i++) {
                    Place place = places.get(i);

                    float lat = place.getGeometry().getLocation().getLatitude();
                    float lng = place.getGeometry().getLocation().getLongitude();
                    LatLng location = new LatLng(lat, lng);

                    map.addMarker(new MarkerOptions()
                            .position(location)
                            .snippet(Integer.toString(i))
                    );
                }

                ((AppCompatButton) v).setText("Clear");
                v.setOnClickListener(getHideListener());
            }
        };
    }

    private View.OnClickListener getHideListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();

                binding.filterButton.setVisibility(View.VISIBLE);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                params.removeRule(RelativeLayout.ALIGN_PARENT_END);
                params.addRule(RelativeLayout.START_OF, binding.filterButton.getId());
                v.setLayoutParams(params);

                ((AppCompatButton) v).setText("Search");
                v.setOnClickListener(getSearchListener());
            }
        };
    }
}