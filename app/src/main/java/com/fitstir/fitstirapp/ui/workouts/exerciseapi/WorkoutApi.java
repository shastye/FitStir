package com.fitstir.fitstirapp.ui.workouts.exerciseapi;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WorkoutApi {
    private String bodyPart;
    private String equipment;
    private String exercise;
    private String target;
    private String directions;
    private String gifURL;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getGifURL() {
        return gifURL;
    }

    public void setGifURL(String gifURL) {
        this.gifURL = gifURL;
    }

    public WorkoutApi(){}

    public WorkoutApi(String bodyPart, String equipment, String exercise, String target, String directions, String gifURL, String image) {
        this.bodyPart = bodyPart;
        this.equipment = equipment;
        this.exercise = exercise;
        this.target = target;
        this.directions = directions;
        this.gifURL = gifURL;
        this.image = image;
    }

    public void fetchData(FirebaseFirestore db, ArrayList<WorkoutApi> arrayList, String string, workoutAdapter adapter){
        db = FirebaseFirestore.getInstance();
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

        workoutsViewModel.setExercise(exerciseName);
        workoutsViewModel.setBodyPart(bodyPart);
        workoutsViewModel.setDirections(instructions);
        workoutsViewModel.setTarget(target);
        workoutsViewModel.setImage(image);
        workoutsViewModel.setGifURL(gif);
        workoutsViewModel.setEquipment(equipment);

    }


}
