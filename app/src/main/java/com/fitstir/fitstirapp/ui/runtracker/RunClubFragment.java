package com.fitstir.fitstirapp.ui.runtracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentRunClubBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Granularity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.ButtCap;
import com.google.android.gms.maps.model.Cap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RunClubFragment extends Fragment implements OnMapReadyCallback {
    private FragmentRunClubBinding binding;
    private View fragment;
    private ImageButton startRun,runHistory,pauseRun, stopRun, statistics;
    private RecyclerView history_RV;
    private SupportMapFragment mapFragment;
    private Location mCurrentLocation;
    private FusedLocationProviderClient fusedClient;
    private GoogleMap mMap;
    private Boolean locationPermissionGranted = false;
    private Boolean isTracking = false;
    private Boolean isPaused = false;
    private Boolean isStopped = false;
    private Boolean isTimerOn = false;
    private ArrayList<LatLng> pathPoint;
    private ArrayList<LatLng>  emptyPath;
    private ArrayList<Float> distance;
    private float totalDistance;
    private Chronometer timer;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);

        binding = FragmentRunClubBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // additional code
        fragment = root.findViewById(R.id.mapView);
        startRun = root.findViewById(R.id.start_Run_BTN);
        runHistory = root.findViewById(R.id.run_History_BTN);
        history_RV = root.findViewById(R.id.run_History_RV);
        pauseRun = root.findViewById(R.id.pause_Run_BTN);
        stopRun = root.findViewById(R.id.stop_Run_BTN);
        statistics = root.findViewById(R.id.statistics_BTN);
        timer = root.findViewById(R.id.timer);
        pathPoint = new ArrayList<>();
        emptyPath = new ArrayList<>();

        //map setup and location tracking service
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
        fusedClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        //initial visibility changed upon location access
        fragment.setVisibility(View.INVISIBLE);
        startRun.setVisibility(View.INVISIBLE);
        history_RV.setVisibility(View.INVISIBLE);
        pauseRun.setVisibility(View.INVISIBLE);
        stopRun.setVisibility(View.INVISIBLE);
        timer.setVisibility(View.INVISIBLE);


        updateLocationRequest();


        //click listeners
        startRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocationRequest();

                isTracking = true;

                startRun.setVisibility(View.INVISIBLE);
                pauseRun.setVisibility(View.VISIBLE);
                stopRun.setVisibility(View.VISIBLE);

                if(isTracking == true){
                    isPaused = false;
                    isTimerOn = true;
                    timer.setBase(SystemClock.elapsedRealtime() - Constants.PAUSE_OFFSET);
                    timer.start();

                }
                //TODO: start timer, distance calculations, cals burning
            }
        });
        pauseRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPaused = true;
                updateLocationRequest();
                startRun.setVisibility(View.VISIBLE);
                if(isPaused == true){
                    isTracking = false;
                    //TODO: Handle timer and collecting data
                }
            }
        });
        stopRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fusedClient.removeLocationUpdates(locationCallback);
                isStopped = true;
                pauseRun.setVisibility(View.INVISIBLE);
                stopRun.setVisibility(View.INVISIBLE);
                startRun.setVisibility(View.VISIBLE);
                if(isStopped == true){
                    //TODO: save data and store to current user in firebase
                }
            }
        });
        runHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history_RV.setVisibility(View.VISIBLE);
            }
        });
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_run_club_to_runStatisticsFragment);
            }
        });

        // End

        return root;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap){
        mMap = googleMap;

        Dexter.withContext(getContext())
                        .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                                .withListener(new MultiplePermissionsListener() {
                                    @Override
                                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                                        if(multiplePermissionsReport.areAllPermissionsGranted()){
                                            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                                                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireActivity(),
                                                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                                locationPermissionGranted = true;
                                                mMap.setMyLocationEnabled(true);
                                                fusedClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                                                    @Override
                                                    public void onSuccess(Location location) {
                                                        mCurrentLocation = location;
                                                        fragment.setVisibility(View.VISIBLE);
                                                        startRun.setVisibility(View.VISIBLE);

                                                        if(mCurrentLocation != null){
                                                            LatLng curUser = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                                                            mMap.addMarker(new MarkerOptions()
                                                                    .position(curUser)
                                                                    .title("Current Location"));
                                                            mMap.setBuildingsEnabled(true);
                                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curUser, 18));

                                                        } else if (mCurrentLocation == null) {
                                                            LatLng curUser = new LatLng(37, -84.5);
                                                            mMap.addMarker(new MarkerOptions()
                                                                    .position(curUser)
                                                                    .title("Location"));
                                                            mMap.setBuildingsEnabled(true);
                                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curUser, 18));
                                                        }

                                                        mMap.getUiSettings().setCompassEnabled(true);
                                                        mMap.getUiSettings().setZoomControlsEnabled(true);
                                                        mMap.getUiSettings().setMyLocationButtonEnabled(true);
                                                        mMap.getUiSettings().setAllGesturesEnabled(true);
                                                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                                    }

                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.e("Error", e.getLocalizedMessage());
                                                    }
                                                });
                                            }
                                        }
                                        else if (!multiplePermissionsReport.areAllPermissionsGranted()) {
                                            Toast.makeText(requireActivity(), "Run Club Feature:: Must have location access to operate" ,
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                                        permissionToken.cancelPermissionRequest();
                                    }
                                }).check();
    }
    @SuppressLint("MissingPermission")
    public void updateLocationRequest()
    {

        LocationRequest locationRequest = new LocationRequest.Builder(5000)
                .setGranularity(Granularity.GRANULARITY_FINE)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setMinUpdateDistanceMeters(10)
                .build();

        LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(getContext());
        settingsClient.checkLocationSettings(locationSettingsRequest).addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                if(task.isSuccessful()){
                    fusedClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                }
                else{
                    if(task.getException() instanceof ResolvableApiException){

                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) task.getException();
                            resolvableApiException.startResolutionForResult(requireActivity(), Constants.REQUEST_CODE_PERMISSION);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                    }
                    else{

                    }
                }
            }
        });

    }


    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            //route options for tracking
            Cap endCap = new ButtCap();

            PolylineOptions opts = new PolylineOptions()
                    .width(8)
                    .color(Color.RED)
                    .geodesic(true)
                    .visible(true)
                    .endCap(endCap);

            //route for empty space
            PolylineOptions empty = new PolylineOptions()
                    .visible(false)
                    .color(Color.TRANSPARENT)
                    .endCap(endCap);;

            if(locationResult == null){
                return;
            }
            for(Location location: locationResult.getLocations()) {
             LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

                if(isTracking == true)
                 {
                     pathPoint.add(loc);
                     Polyline route = mMap.addPolyline(opts);
                     route.setPoints(pathPoint);

                 }
                if(isPaused == true)
                 {
                     emptyPath.add(loc);
                     Polyline emptyRoute = mMap.addPolyline(empty);
                     emptyRoute.setPoints(emptyPath);

                 }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,18));
            }
            Log.d("Updated", "on Location Result" + locationResult);
        }
    };
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}