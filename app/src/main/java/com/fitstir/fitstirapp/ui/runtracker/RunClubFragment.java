package com.fitstir.fitstirapp.ui.runtracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentRunClubBinding;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.util.List;


public class RunClubFragment extends Fragment implements OnMapReadyCallback {
    private FragmentRunClubBinding binding;
    private View fragment;
    private ImageButton startRun;
    private ImageButton runHistory;
    private RecyclerView history_RV;
    private SupportMapFragment mapFragment;
    private Location mCurrentLocation;
    private FusedLocationProviderClient fusedClient;
    private GoogleMap mMap;
    private Boolean locationPermissionGranted = false;
    private Boolean isTracking = false;
    private Boolean isPaused = false;
    private Boolean isStopped = false;


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

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        createLocationRequest();

        //initial visibility changed upon location access
        fragment.setVisibility(View.INVISIBLE);
        startRun.setVisibility(View.INVISIBLE);
        history_RV.setVisibility(View.INVISIBLE);



        //click listeners
        startRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        runHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history_RV.setVisibility(View.VISIBLE);
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
                                                    == PackageManager.PERMISSION_GRANTED ) {
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
                                                                    .title("Location"));
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
                                        else if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
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

    public void createLocationRequest()
    {
        LocationRequest locationRequest = new LocationRequest.Builder(1000)
                .setIntervalMillis(5000)
                .setQuality(LocationRequest.QUALITY_HIGH_ACCURACY)
                .build();

        fusedClient = LocationServices.getFusedLocationProviderClient(getContext());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}