package com.fitstir.fitstirapp.ui.runtracker;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fitstir.fitstirapp.MainActivity;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentRunClubBinding;
import com.fitstir.fitstirapp.databinding.FragmentRunStatisticsBinding;
import com.fitstir.fitstirapp.ui.runtracker.utilites.RunViewModel;
import com.fitstir.fitstirapp.ui.runtracker.utilites.RunnerData;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RunStatisticsFragment extends Fragment {

    private FragmentRunStatisticsBinding binding;
    private LineChart statChart;
    private RunnerData runnerData;
    private TextView loc_Lat, loc_Lng,loc_Alt, loc_Bear, loc_Elapsed,
            loc_Speed, loc_Time, loc_Acc, calories,pace,runMessage, noRunMessage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RunViewModel viewRuns = new ViewModelProvider(requireActivity()).get(RunViewModel.class);

        binding = FragmentRunStatisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //region Initialization
        runnerData = new RunnerData();
        statChart = root.findViewById(R.id.stat_chart);
        loc_Acc = root.findViewById(R.id.tv_accuracy);
        loc_Alt = root.findViewById(R.id.tv_Altitude);
        loc_Bear = root.findViewById(R.id.tv_Bearing);
        loc_Elapsed = root.findViewById(R.id.tv_TimeMillis);
        loc_Lat = root.findViewById(R.id.tv_lat);
        loc_Lng = root.findViewById(R.id.tv_lng);
        loc_Speed = root.findViewById(R.id.tv_Speed);
        loc_Time = root.findViewById(R.id.tv_Time);
        calories = root.findViewById(R.id.tv_calBurned);
        pace = root.findViewById(R.id.tv_avgPace);
        runMessage = root.findViewById(R.id.run_message);
        noRunMessage = root.findViewById(R.id.no_run_Message);

        runMessage.setVisibility(View.INVISIBLE);
        noRunMessage.setVisibility(View.INVISIBLE);
        //endregion

        //region Text view settings
        String lat = String.valueOf(viewRuns.getLat().getValue());
        loc_Lat.setText(lat);
        String lng = String.valueOf(viewRuns.getLng().getValue());
        loc_Lng.setText(lng);
        String bearing = String.valueOf(viewRuns.getBearing().getValue());
        loc_Bear.setText(bearing);
        String alt = String.valueOf(viewRuns.getAltitude().getValue());
        loc_Alt.setText(alt);
        String acc = String.valueOf(viewRuns.getAccuracy().getValue());
        loc_Acc.setText(acc);
        String elapse = String.valueOf(viewRuns.getElapsedRealTime().getValue());
        loc_Elapsed.setText(elapse);
        String speed = String.valueOf(viewRuns.getSpeed().getValue());
        loc_Speed.setText(speed);
        String clockTime = String.valueOf(viewRuns.getTime().getValue());
        loc_Time.setText(clockTime);
        String cal = String.valueOf(viewRuns.getBurnedCalories().getValue());
        calories.setText(cal);
        String avgPace = String.valueOf(viewRuns.getAvgPace().getValue());
        pace.setText(avgPace);

        if(viewRuns.getAvgPace().getValue() < 0.1){
            runMessage.setVisibility(View.INVISIBLE);
            noRunMessage.setVisibility(View.VISIBLE);
        }
        else if(viewRuns.getAvgPace().getValue() > 0.2){
            runMessage.setVisibility(View.VISIBLE);
            noRunMessage.setVisibility(View.INVISIBLE);
        }
        //endregion

        chartMethod();
        return root;
    }

    public void chartMethod(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser authUser = auth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("CompletedRun").child(authUser.getUid());

        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot dataSnapshot = task.getResult();
                    if(dataSnapshot.exists()){

                        ArrayList<Entry> timeEntry = new ArrayList<>();
                        ArrayList<Entry> distanceEntry = new ArrayList<>();

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            double minutes = (double) data.child("completedRunInMinutes").getValue();
                            double distance = (double) data.child("totalDistance").getValue();
                            timeEntry.add(new Entry(timeEntry.size(), (float)minutes));
                            distanceEntry.add(new Entry(distanceEntry.size(), (float) distance));
                        }

                        LineDataSet minSet = new LineDataSet(timeEntry, "Run Times");
                        minSet.setColor(Color.GREEN);
                        minSet.setLineWidth(4f);
                        LineDataSet distSet = new LineDataSet(distanceEntry,"Run Distance");
                        distSet.setColor(Color.RED);
                        distSet.setLineWidth(4f);

                        LineData lineData = new LineData(minSet,distSet);
                        statChart.setData(lineData);
                        statChart.invalidate();


                    }
                    else{
                        Toast.makeText(requireActivity(),"No data to compare", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(requireActivity(), "No Runs Completed..Returning to previous screen", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.action_runStatisticsFragment_to_navigation_run_club);
                }
            }
        });
    }
}