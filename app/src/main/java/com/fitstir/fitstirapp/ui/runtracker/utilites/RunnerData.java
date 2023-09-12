package com.fitstir.fitstirapp.ui.runtracker.utilites;


import android.content.Context;
import android.location.Location;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.google.android.gms.maps.model.LatLng;
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


public class RunnerData {
    private String imageRoute, completedDate;
    private double totalDistance, completedRunInMinutes;
    private String latitude, longitude, avgPace, burnedCalories;
    private UserProfileData user;


    public String getImageRoute() {return imageRoute;}
    public double getTotalDistance() {return totalDistance;}
    public String getAvgPace() {return avgPace;}
    public String getBurnedCalories() {return burnedCalories;}
    public double getCompletedRunInMinutes() {return completedRunInMinutes;}
    public String getCompletedDate() {return completedDate;}
    public UserProfileData getUser() {return user;}
    public String getLatitude() {return latitude;}
    public String getLongitude() {return longitude;}

    public RunnerData() {}

    public RunnerData( double totalDistance, String avgPace, String burnedCalories,
                       double completedRunInMinutes, String completedDate, String latitude, String longitude) {
        this.totalDistance = totalDistance;
        this.avgPace = avgPace;
        this.burnedCalories = burnedCalories;
        this.completedRunInMinutes = completedRunInMinutes;
        this.completedDate = completedDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public RunnerData(String imageRoute, String completedDate, double totalDistance, double completedRunInMinutes,
                      String latitude, String longitude, String avgPace, String burnedCalories, UserProfileData user) {
        this.imageRoute = imageRoute;
        this.completedDate = completedDate;
        this.totalDistance = totalDistance;
        this.completedRunInMinutes = completedRunInMinutes;
        this.latitude = latitude;
        this.longitude = longitude;
        this.avgPace = avgPace;
        this.burnedCalories = burnedCalories;
        this.user = user;
    }

    public void setLatitude(String latitude) {this.latitude = latitude;}
    public void setLongitude(String longitude) {this.longitude = longitude;}
    public void setAvgPace(String avgPace) {this.avgPace = avgPace;}
    public void setBurnedCalories(String burnedCalories) {this.burnedCalories = burnedCalories;}
    public void setImageRoute(String imageRoute) {this.imageRoute = imageRoute;}
    public void setTotalDistance(double totalDistance) {this.totalDistance = totalDistance;}
    public void setCompletedRunInMinutes(double completedRunInMinutes) {this.completedRunInMinutes = completedRunInMinutes;}
    public void setCompletedDate(String completedDate) {this.completedDate = completedDate;}
    public void setUser(UserProfileData user) {this.user = user;}
    public void addRunData(Context context, ArrayList<RunnerData> data, RunnerData currentRunner) {
        String dateTime = String.valueOf(currentRunner.getCompletedDate());

        RunnerData runner = new RunnerData(currentRunner.getTotalDistance(), currentRunner.getAvgPace(), currentRunner.getBurnedCalories(),
               currentRunner.getCompletedRunInMinutes(),
                currentRunner.getCompletedDate(),currentRunner.getLatitude(), currentRunner.getLongitude());

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference runRef = FirebaseDatabase.getInstance()
                .getReference("CompletedRun")
                .child(authUser.getUid())
                .child(dateTime);
                runRef.setValue(runner).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(context, "Run saved successfully", Toast.LENGTH_LONG).show();
                    }
                });


    }
    public void fetchRunData(ArrayList<RunnerData> data, RunHistoryAdapter adapter, Context context){
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReference("CompletedRun").child(authUser.getUid());
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    Toast.makeText(context, "No Runs Completed Yet..", Toast.LENGTH_LONG).show();
                }
                else{
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        RunnerData runnerData =  dataSnapshot.getValue(RunnerData.class);
                        data.add(runnerData);
                    }
                    adapter.notifyDataSetChanged();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

        if(totalDistance >= 0.05){
            double calBurnt = Math.round(75.4991 - (age * 0.2757) + (weight * 0.03295) + (totalDistance * 1.0781)) * time/ 8.368;
            setBurnedCalories(String.valueOf(calBurnt));
            return (float)calBurnt;
        }
        return 0;
    }
    public double calculateSpeed(double distanceCal, double curTime) {

        if(distanceCal > 0.05){
            double avg = Math.round(((distanceCal*1600)/curTime) /10 );
            setAvgPace(String.valueOf(avg));
            return (float)avg;
        }
        return 0;

    }
}
