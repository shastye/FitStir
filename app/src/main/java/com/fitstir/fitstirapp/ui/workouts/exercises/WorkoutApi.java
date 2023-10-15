package com.fitstir.fitstirapp.ui.workouts.exercises;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutApi {
    private String bodyPart, image, gifURL,
            directions, target, exercise, equipment, date;
    private double calories_Burned;


    public String getDate() {return date;}
    public Double getCalories_Burned() {return calories_Burned;}
    public String getImage() {
        return image;
    }
    public String getBodyPart() {
        return bodyPart;
    }
    public String getEquipment() {
        return equipment;
    }
    public String getExercise() {
        return exercise;
    }
    public String getTarget() {
        return target;
    }
    public String getDirections() {
        return directions;
    }
    public String getGifURL() {
        return gifURL;
    }

    public WorkoutApi(){}

    public WorkoutApi(String bodyPart, String target, String exercise,
                      String equipment, double calsBurned, String data) {
        this.bodyPart = bodyPart;
        this.target = target;
        this.exercise = exercise;
        this.equipment = equipment;
        this.calories_Burned = calsBurned;
        this.date = data;
    }

    public WorkoutApi(String bodyPart, String equipment, String exercise, String target, String directions, String gifURL, String image) {
        this.bodyPart = bodyPart;
        this.equipment = equipment;
        this.exercise = exercise;
        this.target = target;
        this.directions = directions;
        this.gifURL = gifURL;
        this.image = image;
    }

    public void setDate(String date) {this.date = date;}
    public void setCalories_Burned(double calories_Burned) {this.calories_Burned = calories_Burned;}
    public void setImage(String image) {
        this.image = image;
    }
    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }
    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
    public void setTarget(String target) {
        this.target = target;
    }
    public void setDirections(String directions) {
        this.directions = directions;
    }
    public void setGifURL(String gifURL) {
        this.gifURL = gifURL;
    }


    public void fetchData(ArrayList<WorkoutApi> arrayList, String string, workoutAdapter adapter){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(string).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d : list){
                                WorkoutApi bodyApi = d.toObject(WorkoutApi.class);
                                arrayList.add(bodyApi);
                            }
                            adapter.notifyDataSetChanged();
                        }else{
                            //
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("error while loading",e.toString());
                    }
                });
    }
    public void getWorkoutClicked(int position, ArrayList<WorkoutApi> list, WorkoutsViewModel workoutsViewModel){

        String exerciseName = list.get(position).getExercise().trim();
        String bodyPart = list.get(position).getBodyPart().trim();
        String instructions = list.get(position).getDirections().trim();
        String target = list.get(position).getTarget().trim();
        String image = list.get(position).getImage().trim();
        String gif = list.get(position).getGifURL().trim();
        String equipment = list.get(position).getEquipment().trim();
        int metValue = list.get(position).getCalories_Burned().intValue();


        workoutsViewModel.setExercise(exerciseName);
        workoutsViewModel.setBodyPart(bodyPart);
        workoutsViewModel.setDirections(instructions);
        workoutsViewModel.setTarget(target);
        workoutsViewModel.setImage(image);
        workoutsViewModel.setGifURL(gif);
        workoutsViewModel.setEquipment(equipment);
        workoutsViewModel.setCalBurned(metValue);

    }
    public ArrayList searchList(String text, ArrayList<WorkoutApi> workoutApiArrayList, workoutAdapter viewAdapter) {

        ArrayList<WorkoutApi> filter = new ArrayList<>();
        for(WorkoutApi workouts : workoutApiArrayList){
            if(workouts.getExercise().toLowerCase().contains(text.toLowerCase()) || workouts.getTarget().toLowerCase().contains(text.toLowerCase()) ){
                filter.add(workouts);
            }
        }
        if(filter.isEmpty()){
            Log.e("error","No data found" );
        }
        else{
            viewAdapter.setFilterList(filter);
        }
        return filter;
    }

}