package com.fitstir.fitstirapp.ui.workouts.exerciseapi.circuits;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CircuitModel {
    private String exercise;
    private Integer duration;
    private Integer sets;
    private Integer reps;
    private Integer calBurned;
    private String directions;
    private String gif;
    private String mapImage;
    private Integer totalBurn;


    public String getGif() {
        return gif;
    }
    public String getMapImage() {return mapImage;}
    public Integer getTotalBurn() {return totalBurn;}
    public String getExercise() {
        return exercise;
    }
    public Integer getDuration() {
        return duration;
    }
    public Integer getReps() {
        return reps;
    }
    public String getDirections() {
        return directions;
    }
    public Integer getCalBurned() {
        return calBurned;
    }
    public Integer getSets() {
        return sets;
    }

    public CircuitModel(String exercise, Integer duration, Integer sets, Integer reps, Integer calBurned, String directions, String gif,String mapImage, Integer totalBurn) {
        this.exercise = exercise;
        this.duration = duration;
        this.sets = sets;
        this.reps = reps;
        this.calBurned = calBurned;
        this.directions = directions;
        this.gif = gif;
        this.totalBurn = totalBurn;
        this.mapImage = mapImage;
    }

    public void setMapImage(String mapImage) {this.mapImage = mapImage;}
    public void setTotalBurn(Integer totalBurn) {this.totalBurn = totalBurn;}
    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public void setSets(Integer sets) {
        this.sets = sets;
    }
    public void setReps(Integer reps) {
        this.reps = reps;
    }
    public void setCalBurned(Integer calBurned) {
        this.calBurned = calBurned;
    }
    public void setDirections(String directions) {
        this.directions = directions;
    }
    public void setGif(String gif) {
        this.gif = gif;
    }

    public void fetchCircuitData(ArrayList<CircuitModel> circuit, circuitAdapter adapter, String string){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(string)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d :snapshots ){
                                CircuitModel circuits = d.toObject(CircuitModel.class);
                                circuit.add(circuits);
                            }
                            adapter.notifyDataSetChanged();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("error while loading",e.toString());
                    }
                });
    }
}
