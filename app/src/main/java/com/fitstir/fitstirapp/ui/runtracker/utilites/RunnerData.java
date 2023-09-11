package com.fitstir.fitstirapp.ui.runtracker.utilites;

import android.animation.FloatArrayEvaluator;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fitstir.fitstirapp.ui.utility.classes.User;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.Date;

import kotlin.random.URandomKt;

public class RunnerData {

    private String imageRoute, completedDate;
    private ArrayList<RunnerData> runTracker;
    private double totalDistance, avgPace, burnedCalories;
    private Location location;
    private double completedRunInMinutes;
    private UserProfileData user;



    public String getImageRoute() {return imageRoute;}

    public ArrayList<RunnerData> getRunTracker() {return runTracker;}

    public double getTotalDistance() {return totalDistance;}

    public double getAvgPace() {return avgPace;}

    public double getBurnedCalories() {return burnedCalories;}

    public double getCompletedRunInMinutes() {return completedRunInMinutes;}

    public String getCompletedDate() {return completedDate;}

    public UserProfileData getUser() {return user;}

    public Location getLocation() {return location;}

    public RunnerData() {}

    public RunnerData( double totalDistance, double avgPace, double burnedCalories,
                       double completedRunInMinutes, String completedDate, Location location) {
        this.totalDistance = totalDistance;
        this.avgPace = avgPace;
        this.burnedCalories = burnedCalories;
        this.completedRunInMinutes = completedRunInMinutes;
        this.completedDate = completedDate;
        this.location =location;
    }

    public RunnerData(String imageRoute, ArrayList<RunnerData> runTracker, double totalDistance, double avgPace,
                      double burnedCalories, double completedRunInMinutes, String completedDate, UserProfileData user) {
        this.imageRoute = imageRoute;
        this.runTracker = runTracker;
        this.totalDistance = totalDistance;
        this.avgPace = avgPace;
        this.burnedCalories = burnedCalories;
        this.completedRunInMinutes = completedRunInMinutes;
        this.completedDate = completedDate;
        this.user = user;
    }

    public void setLocation(Location location) {this.location = location;}
    public void setImageRoute(String imageRoute) {this.imageRoute = imageRoute;}
    public void setRunTracker(ArrayList<RunnerData> runTracker) {this.runTracker = runTracker;}
    public void setTotalDistance(double totalDistance) {this.totalDistance = totalDistance;}
    public void setAvgPace(double avgPace) {this.avgPace = avgPace;}
    public void setBurnedCalories(double burnedCalories) {this.burnedCalories = burnedCalories;}
    public void setCompletedRunInMinutes(double completedRunInMinutes) {this.completedRunInMinutes = completedRunInMinutes;}
    public void setCompletedDate(String completedDate) {this.completedDate = completedDate;}
    public void setUser(UserProfileData user) {this.user = user;}
    public void addRunData(Context context, ArrayList<RunnerData> data, RunnerData currentRunner) {
        String dateTime = String.valueOf(currentRunner.getCompletedDate());

        RunnerData runner = new RunnerData(currentRunner.getTotalDistance(), currentRunner.getAvgPace(),
                currentRunner.getBurnedCalories(), currentRunner.getCompletedRunInMinutes(),
                currentRunner.getCompletedDate(), currentRunner.getLocation());

        data.add(runner);
        setRunTracker(data);

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference runRef = FirebaseDatabase.getInstance()
                .getReference("CompletedRun")
                .child(authUser.getUid())
                .child(dateTime);
                runRef.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(context, "Run saved successfully", Toast.LENGTH_LONG).show();
                    }
                });


    }
    public double calculateDistance(ArrayList<LatLng> poly, double distance) {
        for (int i = 0; i < poly.size() - 2; i++) {
            LatLng pos1 = poly.get(i);
            LatLng po2 = poly.get(i + 1);
            float result[] = new float[1];
            Location.distanceBetween(pos1.latitude, pos1.longitude
                    , po2.latitude, po2.longitude, result);

            distance += result[0];
        }
        double miles = distance / 1600;

        setTotalDistance(miles);

        return (float) miles;
    }

    public double calculateBurnedCalories( int weight, double time, int age, double totalDistance ) {

        if(totalDistance >= 0.1){
            double calBurnt = Math.round((age * 0.2757) + (weight * 0.03295) + (totalDistance * 1.0781)- 75.4991) * time/ 8.368;
            return calBurnt;
        }

        return 0;
    }

    public double calculateSpeed(double distanceCal, double curTime) {

        double avg = Math.round(((curTime / 1000f / 60 / 60)) / (distanceCal/ 1000f)/ 10);
        return avg;

    }
}
