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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentRunClubBinding;
import com.fitstir.fitstirapp.ui.runtracker.utilites.RunHistoryAdapter;
import com.fitstir.fitstirapp.ui.runtracker.utilites.RunViewModel;
import com.fitstir.fitstirapp.ui.runtracker.utilites.RunnerData;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.utility.RvInterface;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class RunClubFragment extends Fragment implements OnMapReadyCallback, RvInterface {
    //region Variables
    private FragmentRunClubBinding binding;
    private View fragment;
    private ImageButton startRun,runHistory,pauseRun, stopRun, statistics;
    private RecyclerView history_RV;
    private ArrayList<RunnerData> data;
    private ArrayList<LatLng> location;
    private RunHistoryAdapter adapter;
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
    private ArrayList<Polyline> pathPoly;
    private float totalDistance = 0;
    private float distanceCal = 0;
    private long pauseOffset = 0;
    private Chronometer timer;
    private Chronometer miniTimer;
    private RunnerData currentRunner;
    private CardView stats;
    private TextView distance, calories;
    private DecimalFormat decimalFormat;
    private SimpleDateFormat format;
    private Date currentDate;
    private RvInterface rvInterface;
    private ImageView holder;
    //endregion

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RunViewModel viewRuns = new ViewModelProvider(requireActivity()).get(RunViewModel.class);

        binding = FragmentRunClubBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // region Initialization
        currentRunner = new RunnerData();
        fragment = root.findViewById(R.id.mapView);
        startRun = root.findViewById(R.id.start_Run_BTN);
        runHistory = root.findViewById(R.id.run_History_BTN);
        history_RV = root.findViewById(R.id.run_History_RV);
        pauseRun = root.findViewById(R.id.pause_Run_BTN);
        stopRun = root.findViewById(R.id.stop_Run_BTN);
        statistics = root.findViewById(R.id.statistics_BTN);
        stats = root.findViewById(R.id.stat_holder);
        timer = root.findViewById(R.id.timer);
        miniTimer = root.findViewById(R.id.end_timer);
        distance = root.findViewById(R.id.total_distance);
        calories = root.findViewById(R.id.total_calories);
        holder = root.findViewById(R.id.pic_Holder);
        rvInterface = this;
        format = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        currentDate = new Date();
        decimalFormat = new DecimalFormat("###.##");
        pathPoly = new ArrayList<>();
        location = new ArrayList<>();
        pathPoint = new ArrayList<>();
        data = new ArrayList<>();

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
        miniTimer.setVisibility(View.INVISIBLE);
        stats.setVisibility(View.INVISIBLE);
//endregion

        //region Click Listeners
        startRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateLocationRequest();

                isTracking = true;
                startRun.setVisibility(View.INVISIBLE);
                miniTimer.setVisibility(View.INVISIBLE);
                pauseRun.setVisibility(View.VISIBLE);
                stopRun.setVisibility(View.VISIBLE);

                if(isTracking){
                    isPaused = false;
                    isTimerOn = true;
                    timer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    timer.start();
                    miniTimer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    miniTimer.start();
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

                    //finished elapsed timer
                    miniTimer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - miniTimer.getBase();
                }
            }
        });
        stopRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isStopped = true;
                isTimerOn = false;
                isTracking = false;

                fusedClient.removeLocationUpdates(locationCallback);

                pauseRun.setVisibility(View.INVISIBLE);
                stopRun.setVisibility(View.INVISIBLE);
                startRun.setVisibility(View.VISIBLE);
                miniTimer.setVisibility(View.VISIBLE);

                String date = format.format(currentDate);
                viewRuns.setRunDate(date);
                currentRunner.setCompletedDate(date);

                double  elapsedTime = SystemClock.elapsedRealtime() - timer.getBase();
                double converted =  ((elapsedTime / 1000f) / 60);
                currentRunner.setCompletedRunInMinutes(converted);
                viewRuns.setRunTimeInMinutes(converted);

                timer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;
                timer.stop();
                miniTimer.stop();

                //LatLng loc = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,13));

                //getting total distance, formatting and displaying to screen
                distanceCal += currentRunner.calculateDistance(pathPoint, totalDistance);
                //currentRunner.setTotalDistance(distanceCal);
                float formatted = Float.valueOf(decimalFormat.format(distanceCal));
                String totalDistance = String.valueOf(formatted);
                viewRuns.setRunDistance(Double.valueOf(totalDistance));
                distance.setText(totalDistance);

                //getting average pace of runner to set data and viewModel
                double avgPace = 0;
                avgPace += currentRunner.calculateSpeed(distanceCal,converted);
                //currentRunner.setAvgPace(avgPace);
                viewRuns.setAvgPace(avgPace);

                //getting saved user data to calculate calories burned
                //after calculation formatting for human-readable
                FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
                assert authUser != null;
                DatabaseReference userRef = FirebaseDatabase.getInstance()
                        .getReference("Users")
                        .child(authUser.getUid());
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserProfileData value = snapshot.getValue(UserProfileData.class);
                        viewRuns.setUser(value);
                        viewRuns.setWeight(value.get_Weight());
                        viewRuns.setAge(value.getAge());
                        int weight = Integer.parseInt(String.valueOf(viewRuns.getWeight().getValue()));
                        int age = Integer.parseInt(String.valueOf(viewRuns.getAge().getValue()));

                        float burned = 0;
                        burned += currentRunner.calculateBurnedCalories( weight, converted , age,distanceCal);
                        double formatting = Double.parseDouble(decimalFormat.format(burned));
                        String calBurned = String.valueOf(formatting);
                        calories.setText(calBurned);
                        viewRuns.setBurnedCalories(burned);
                        currentRunner.setBurnedCalories(burned);

                        if(distanceCal > 0.05)
                        {
                            currentRunner.addRunData(requireActivity(),currentRunner);
                            currentRunner.saveRouteImage(mMap,requireActivity(), viewRuns);
                            String image = currentRunner.getImageRoute();
                            viewRuns.setMapImage(image);
                            viewRuns.setActions(Constants.MAP_ACTION.RUN_ACTION);
                        }
                        else{
                            Toast.makeText(requireActivity(), "Run Cancelled no data saved", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        runHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ArrayList<RunnerData> runData = new ArrayList<>();

                if(history_RV.getVisibility() == View.VISIBLE){
                    history_RV.setVisibility(View.INVISIBLE);

                    history_RV.setLayoutManager(new LinearLayoutManager(getActivity()));
                    history_RV.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                    adapter = new RunHistoryAdapter(requireActivity(),data,rvInterface);
                    history_RV.setAdapter(adapter);


                    currentRunner.fetchRunData(data,adapter, requireActivity());
                }
                else if(history_RV.getVisibility() == View.INVISIBLE){
                    history_RV.setVisibility(View.VISIBLE);
                    history_RV.setLayoutManager(new LinearLayoutManager(getActivity()));
                    history_RV.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), LinearLayoutManager.VERTICAL));
                    adapter = new RunHistoryAdapter( requireActivity(),data,rvInterface);
                    history_RV.setAdapter(adapter);

                    currentRunner.fetchRunData(data,adapter, requireActivity());

                }

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
        RunViewModel viewRuns = new ViewModelProvider(requireActivity()).get(RunViewModel.class);

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

                                        currentRunner.setLatitude(mCurrentLocation.getLatitude());
                                        currentRunner.setLongitude(mCurrentLocation.getLongitude());
                                        viewRuns.setLat(mCurrentLocation.getLatitude());
                                        viewRuns.setLng(mCurrentLocation.getLongitude());

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
                                        }
                                        else if (mCurrentLocation == null) {
                                            LatLng curUser = new LatLng(37, -84.5);
                                            mMap.addMarker(new MarkerOptions()
                                                    .position(curUser)
                                                    .title("Location"));
                                            mMap.setBuildingsEnabled(true);
                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curUser, 19));
                                        }
                                        mMap.getUiSettings().setCompassEnabled(true);
                                        mMap.getUiSettings().setZoomControlsEnabled(true);
                                        mMap.getUiSettings().setMyLocationButtonEnabled(true);
                                        mMap.getUiSettings().setAllGesturesEnabled(true);
                                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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

            if(locationResult == null){
                return;
            }
            PolylineOptions opts = new PolylineOptions()
                    .width(10)
                    .color(Color.RED)
                    .geodesic(true)
                    .visible(true);

            for(Location location: locationResult.getLocations()) {
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                RunViewModel viewRuns = new ViewModelProvider(requireActivity()).get(RunViewModel.class);

                if(isTracking == true)
                {
                    pathPoint.add(loc);
                    Polyline route = mMap.addPolyline(opts);
                    route.setPoints(pathPoint);
                    pathPoly.add(route);
                    mCurrentLocation = locationResult.getLastLocation();

                    //set view model
                    viewRuns.setAltitude(mCurrentLocation.getAltitude());
                    viewRuns.setBearing(mCurrentLocation.getBearing());
                    viewRuns.setElapsedRealTime(mCurrentLocation.getElapsedRealtimeMillis());
                    viewRuns.setSpeed(mCurrentLocation.getSpeed());
                    viewRuns.setClockTime(mCurrentLocation.getTime());
                    viewRuns.setAccuracy(mCurrentLocation.getAccuracy());
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            }

            Log.d("Updated", "on Location Result" + locationResult);
            if(isStopped == true){
                LatLng loc = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,14));
            }

        }
    };
    //endregion

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onItemClick(int position) {
        RunViewModel viewRuns = new ViewModelProvider(requireActivity()).get(RunViewModel.class);

        currentRunner.getClickedItem(position,data);

        String mapImage = currentRunner.getImageRoute();
        double dist = currentRunner.getTotalDistance();
        double pace = currentRunner.getAvgPace();
        double time = currentRunner.getCompletedRunInMinutes();
        double lat = currentRunner.getLatitude();
        double lng = currentRunner.getLongitude();
        double cal = currentRunner.getBurnedCalories();
        String date = currentRunner.getCompletedDate();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference imageRef = storageReference.child("routes/"+user.getUid()).child(date);
        imageRef.getBytes(Constants.MEGA_BYTE).addOnCompleteListener(new OnCompleteListener<byte[]>() {
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
               byte[] photo = task.getResult();
                    String image = new String(photo, StandardCharsets.UTF_8);
                    viewRuns.setMapImage(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                     Toast.makeText(requireActivity(),"No data gathered", Toast.LENGTH_LONG).show();
            }
        });

        viewRuns.setActions(Constants.MAP_ACTION.HISTORY_ACTION);
        viewRuns.setMapImage(mapImage);
        viewRuns.setRunDistance(dist);
        viewRuns.setBurnedCalories((float)cal);
        viewRuns.setAvgPace(pace);
        viewRuns.setElapsedRealTime(time);
        viewRuns.setLat(lat);
        viewRuns.setLng(lng);
        Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main)
                .navigate(R.id.action_navigation_run_club_to_runStatisticsFragment);
    }
}