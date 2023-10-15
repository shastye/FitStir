package com.fitstir.fitstirapp.ui.workouts.exercises.circuits;

import android.util.Log;

import androidx.annotation.NonNull;

import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;
import com.fitstir.fitstirapp.ui.workouts.exercises.WorkoutApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CircuitModel {
    private String exercise, directions, gif, mapImage, date ;
    private int duration, sets, reps, calBurn, totalBurn;


    public String getDate() {return date;}
    public String getGif() {
        return gif;
    }
    public String getMapImage() {return mapImage;}
    public int getTotalBurn() {return totalBurn;}
    public String getExercise() {
        return exercise;
    }
    public int getDuration() {
        return duration;
    }
    public int getReps() {
        return reps;
    }
    public String getDirections() {
        return directions;
    }
    public int getCalBurned() {return calBurn;}
    public int getSets() {
        return sets;
    }

    public CircuitModel(){}

    public CircuitModel(String exercise, int totalBurn, String date) {
        this.exercise = exercise;
        this.totalBurn = totalBurn;
        this.date = date;
    }

    public CircuitModel(String exercise, int duration, int sets, int reps, int calBurned, String directions, String gif, String mapImage, int totalBurn) {
        this.exercise = exercise;
        this.duration = duration;
        this.sets = sets;
        this.reps = reps;
        this.calBurn = calBurned;
        this.directions = directions;
        this.gif = gif;
        this.totalBurn = totalBurn;
        this.mapImage = mapImage;
    }

    public void setDate(String date) {this.date = date;}
    public void setMapImage(String mapImage) {this.mapImage = mapImage;}
    public void setTotalBurn(int totalBurn) {this.totalBurn = totalBurn;}
    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setSets(int sets) {
        this.sets = sets;
    }
    public void setReps(int reps) {
        this.reps = reps;
    }
    public void setCalBurned(int calBurned) {
        this.calBurn = calBurned;
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
                                setExercise(circuits.getExercise());
                                setTotalBurn(circuits.getTotalBurn());
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
    public void getWorkoutClicked(int position, ArrayList<CircuitModel> list, WorkoutsViewModel workoutsViewModel){

        String exerciseName = list.get(position).getExercise().trim();
        int totalBurned = list.get(position).getTotalBurn();

        workoutsViewModel.setExercise(exerciseName);
        workoutsViewModel.setTotalBurned(totalBurned);

    }
}
