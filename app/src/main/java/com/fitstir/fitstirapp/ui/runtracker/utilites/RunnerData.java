package com.fitstir.fitstirapp.ui.runtracker.utilites;


import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import com.bumptech.glide.Glide;

import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fitstir.fitstirapp.ui.utility.Constants;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
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
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;


public class RunnerData {
    private String completedDate, imageRoute;
    private double totalDistance, completedRunInMinutes, latitude, longitude, avgPace, burnedCalories;
    private String user;
    private ArrayList<RunnerData> runner;



    public String getImageRoute() {return imageRoute;}
    public double getTotalDistance() {return totalDistance;}
    public double getAvgPace() {return avgPace;}
    public double getBurnedCalories() {return burnedCalories;}
    public double getCompletedRunInMinutes() {return completedRunInMinutes;}
    public String getCompletedDate() {return completedDate;}
    public String getUser() {return user;}
    public double getLatitude() {return latitude;}
    public double getLongitude() {return longitude;}

    public ArrayList<RunnerData> getRunner() {return runner;}

    public RunnerData() {}

    public RunnerData( double totalDistance, double avgPace, double burnedCalories,
                       double completedRunInMinutes, String completedDate, double latitude, double longitude) {
        this.totalDistance = totalDistance;
        this.avgPace = avgPace;
        this.burnedCalories = burnedCalories;
        this.completedRunInMinutes = completedRunInMinutes;
        this.completedDate = completedDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public RunnerData(String imageRoute, String completedDate, double totalDistance, double completedRunInMinutes,
                      double latitude, double longitude, double avgPace, double burnedCalories, String user) {
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

    public void setLatitude(double latitude) {this.latitude = latitude;}
    public void setLongitude(double longitude) {this.longitude = longitude;}
    public void setAvgPace(double avgPace) {this.avgPace = avgPace;}
    public void setBurnedCalories(double burnedCalories) {this.burnedCalories = burnedCalories;}
    public void setImageRoute(String imageRoute) {this.imageRoute = imageRoute;}
    public void setTotalDistance(double totalDistance) {this.totalDistance = totalDistance;}
    public void setCompletedRunInMinutes(double completedRunInMinutes) {this.completedRunInMinutes = completedRunInMinutes;}
    public void setCompletedDate(String completedDate) {this.completedDate = completedDate;}
    public void setUser(String user) {this.user = user;}

    public void setRunner(ArrayList<RunnerData> runner) {this.runner = runner;}

    public void addRunData(Context context, RunnerData currentRunner) {
        String dateTime = String.valueOf(currentRunner.getCompletedDate());

        RunnerData runner = new RunnerData(currentRunner.getTotalDistance(), currentRunner.getAvgPace(), currentRunner.getBurnedCalories(),
                currentRunner.getCompletedRunInMinutes(),
                currentRunner.getCompletedDate(),currentRunner.getLatitude(), currentRunner.getLongitude());

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference runRef = FirebaseDatabase.getInstance()
                .getReference("CompletedRun")
                .child(authUser.getUid())
                .child(dateTime);
        setUser(authUser.getUid());
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
    public void saveRouteImage(GoogleMap map, Context context, RunViewModel viewRun){

        map.snapshot(new GoogleMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(@Nullable Bitmap bitmap) {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();


                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference();

                StorageReference routeImage = storageReference.child("routes/"+user.getUid()).child(getCompletedDate());

                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);

                byte[] data = bao.toByteArray();
                String image = Base64.getEncoder().encodeToString(data);

                setImageRoute(image);
                viewRun.setMapImage(image);
                routeImage.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        map.clear();
                        Toast.makeText(context, "Route Image Saved", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Error Uploading Image!" +e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    public void getClickedItem(int position, ArrayList<RunnerData> list){

        double lat = list.get(position).getLatitude();
        double lng = list.get(position).getLongitude();
        double distance = list.get(position).getTotalDistance();
        float cal = (float) list.get(position).getBurnedCalories();
        String date = list.get(position).getCompletedDate();
        double pace = list.get(position).getAvgPace();
        double time = list.get(position).getCompletedRunInMinutes();

        setLatitude(lat);
        setLongitude(lng);
        setBurnedCalories(cal);
        setTotalDistance(distance);
        setCompletedDate(date);
        setAvgPace(pace);
        setCompletedRunInMinutes(time);
        setCompletedDate(date);

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
            setBurnedCalories(calBurnt);
            return (float)calBurnt;
        }
        return 0;
    }
    public double calculateSpeed(double distanceCal, double curTime) {

        if(distanceCal > 0.05){
            double avg = Math.round(((distanceCal*1600)/curTime) /10 );
            setAvgPace(avg);
            return (float)avg;
        }
        return 0;

    }

}