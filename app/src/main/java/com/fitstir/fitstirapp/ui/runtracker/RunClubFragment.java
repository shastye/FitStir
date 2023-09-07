package com.fitstir.fitstirapp.ui.runtracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentRunClubBinding;
import com.fitstir.fitstirapp.ui.runtracker.utilites.RunViewModel;
import com.fitstir.fitstirapp.ui.runtracker.utilites.RunnerData;
import com.fitstir.fitstirapp.ui.utility.Constants;
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
import java.util.List;


public class RunClubFragment extends Fragment implements OnMapReadyCallback {
    //region Variables
        private FragmentRunClubBinding binding;
        private View fragment;
        private ImageButton startRun,runHistory,pauseRun, stopRun, statistics;
        private RecyclerView history_RV;
        private SupportMapFragment mapFragment;
        private Location mCurrentLocation,prevLocation;
        private FusedLocationProviderClient fusedClient;
        private GoogleMap mMap;
        private Boolean locationPermissionGranted = false;
        private Boolean isTracking = false;
        private Boolean isPaused = false;
        private Boolean isStopped = false;
        private Boolean isTimerOn = false;
        private ArrayList<LatLng> pathPoint;
        private ArrayList<LatLng>  emptyPath;
        private double totalDistance = 0;
        private long pauseOffset = 0;
        private Chronometer timer;
        private RunnerData currentRunner;
        private CardView stats;
        private EditText distance;
        //endregion
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RunViewModel viewRuns = new ViewModelProvider(requireActivity()).get(RunViewModel.class);

        currentRunner = new RunnerData();
        binding = FragmentRunClubBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

     // region Initialization
        fragment = root.findViewById(R.id.mapView);
        startRun = root.findViewById(R.id.start_Run_BTN);
        runHistory = root.findViewById(R.id.run_History_BTN);
        history_RV = root.findViewById(R.id.run_History_RV);
        pauseRun = root.findViewById(R.id.pause_Run_BTN);
        stopRun = root.findViewById(R.id.stop_Run_BTN);
        statistics = root.findViewById(R.id.statistics_BTN);
        stats = root.findViewById(R.id.stat_holder);
        timer = root.findViewById(R.id.timer);
        distance = root.findViewById(R.id.total_distance);
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
        stats.setVisibility(View.INVISIBLE);
//endregion

     //region Click Listeners
        startRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateLocationRequest();

                isTracking = true;
                startRun.setVisibility(View.INVISIBLE);
                pauseRun.setVisibility(View.VISIBLE);
                stopRun.setVisibility(View.VISIBLE);

                if(isTracking){
                    isPaused = false;
                    isTimerOn = true;
                    timer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    timer.start();
                }
            }
        });

        pauseRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPaused = true;
                updateLocationRequest();
                startRun.setVisibility(View.VISIBLE);
                if(isPaused){
                    isTracking = false;
                    isTimerOn = false;
                    timer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - timer.getBase();
                }
            }
        });

        stopRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentRunner.setRunTime((int) timer.getBase());
                viewRuns.setRunTime(currentRunner.getRunTime());
                mMap.clear();
                fusedClient.removeLocationUpdates(locationCallback);
                isStopped = true;
                pauseRun.setVisibility(View.INVISIBLE);
                stopRun.setVisibility(View.INVISIBLE);
                startRun.setVisibility(View.VISIBLE);

                if(isStopped){
                    isTimerOn = false;
                    isTracking = false;
                    isPaused = false;

                        timer.setBase(SystemClock.elapsedRealtime());
                        pauseOffset = 0;
                        timer.stop();

               calculateDistance(prevLocation);
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
    //endregion

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
                                                        prevLocation = location;
                                                        mCurrentLocation = location;
                                                        fragment.setVisibility(View.VISIBLE);
                                                        startRun.setVisibility(View.VISIBLE);
                                                        timer.setVisibility(View.VISIBLE);
                                                        stats.setVisibility(View.VISIBLE);

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
    public void updateLocationRequest(){

        LocationRequest locationRequest = new LocationRequest.Builder(500)
                .setGranularity(Granularity.GRANULARITY_FINE)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setMinUpdateDistanceMeters(2)
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

    @SuppressLint("MissingPermission")
    public void currentLocationRequest(){

        LocationRequest locationRequest = new LocationRequest.Builder(500)
                .setGranularity(Granularity.GRANULARITY_FINE)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setMinUpdateDistanceMeters(2)
                .build();

        LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .build();

    }

    //region Location Callback
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                PolylineOptions opts = new PolylineOptions()
                        .width(8)
                        .color(Color.RED)
                        .geodesic(true)
                        .visible(true);
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
                        mCurrentLocation = locationResult.getLastLocation();
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,18));
                }
                Log.d("Updated", "on Location Result" + locationResult);

            }
        };
  //endregion

    public void calculateDistance(@NonNull Location location){


        double startingDistance = location.distanceTo(mCurrentLocation);

        //if less than 10 meters do not record
        if(startingDistance < 10.00){
            // do nothing
        }
        else{
            totalDistance += startingDistance;
            prevLocation = location;
            //distance.setText((int) totalDistance);
        }
        Toast.makeText(requireActivity(),"distance = " + totalDistance, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}