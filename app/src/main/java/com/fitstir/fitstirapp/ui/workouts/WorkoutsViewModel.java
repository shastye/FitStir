package com.fitstir.fitstirapp.ui.workouts;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitstir.fitstirapp.ui.workouts.exercises.WorkoutApi;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.errorprone.annotations.MustBeClosed;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class WorkoutsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Integer> favoriteItemPosition= new MutableLiveData<>();
    private final MutableLiveData<Integer> pageCLicked = new MutableLiveData<>(0);
    private final MutableLiveData<String> exercise = new MutableLiveData<>(" ");
    private final MutableLiveData<String> bodyPart = new MutableLiveData<>(" ");
    private final MutableLiveData<String> target = new MutableLiveData<>(" ");
    private final MutableLiveData<String> directions = new MutableLiveData<>(" ");
    private final MutableLiveData<String> image = new MutableLiveData<>(" ");
    private final MutableLiveData<String> gifURL = new MutableLiveData<>(" ");
    private final MutableLiveData<String> equipment = new MutableLiveData<>(" ");
    private final MutableLiveData<Integer> sets = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> reps = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> duration = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> calBurned = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> totalBurned = new MutableLiveData<>(0);
    private final MutableLiveData<String> mapImage = new MutableLiveData<>(" ");
    private final MutableLiveData<String> vidURL = new MutableLiveData<>();
    private final MutableLiveData<String> altURL = new MutableLiveData<>();
    private final MutableLiveData<String> alt2URL = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<WorkoutApi>> workOuts = new MutableLiveData<>();


    public MutableLiveData<ArrayList<WorkoutApi>> getWorkOuts() {return workOuts;}
    public MutableLiveData<Integer> getFavoriteItemPosition() {return favoriteItemPosition;}
    public MutableLiveData<String> getAltURL() {return altURL;}
    public MutableLiveData<String> getAlt2URL() {return alt2URL;}
    public MutableLiveData<String> getVidURL() {return vidURL;}
    public final MutableLiveData<Integer> getTotalBurned() {return totalBurned;}
    public MutableLiveData<String> getMapImage() {return mapImage;}
    public final MutableLiveData<Integer> getPageCLicked() {return pageCLicked;}
    public LiveData<String> getText() { return mText;}
    public MutableLiveData<String> getTarget() { return target;}
    public MutableLiveData<String> getDirections() { return directions;}
    public MutableLiveData<String> getImage() { return image;}
    public MutableLiveData<String> getGifURL() { return gifURL;}
    public MutableLiveData<String> getEquipment() { return equipment;}
    public MutableLiveData<String> getBodyPart() { return bodyPart;}
    public MutableLiveData<String> getExercise() { return exercise;}
    public MutableLiveData<Integer> getSets() {return sets;}
    public MutableLiveData<Integer> getReps() {return reps;}
    public MutableLiveData<Integer> getDuration() {return duration;}
    public MutableLiveData<Integer> getCalBurned() {return calBurned;}

    public WorkoutsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }
    public void setWorkouts(ArrayList<WorkoutApi> workOut){this.workOuts.setValue(workOut);}
    public void setFavoriteItemPosition(int pos){this.favoriteItemPosition.setValue(pos);}
    public void setPageCLicked(Integer page){this.pageCLicked.setValue(page);}
    public void setSets(Integer set){this.sets.setValue(set);}
    public void setReps(Integer rep){this.reps.setValue(rep);}
    public void setDuration(Integer time){this.duration.setValue(time);}
    public void setCalBurned(Integer cal){this.calBurned.setValue(cal);}
    public void setBodyPart(String body){this.bodyPart.setValue(body);}
    public void setTarget(String target){this.target.setValue(target);}
    public void setDirections(String directions){this.directions.setValue(directions);}
    public void setImage(String image){this.image.setValue(image);}
    public void setEquipment(String equip){this.equipment.setValue(equip);}
    public void setGifURL(String gif){this.gifURL.setValue(gif);}
    public void setExercise(String name){this.exercise.setValue(name);}
    public void setVidURl(String url){this.vidURL.setValue(url);}
    public void setAltURl(String url){this.altURL.setValue(url);}
    public void setAlt2URL(String url){this.alt2URL.setValue(url);}
    public void setTotalBurned(Integer totalBurned){this.totalBurned.setValue(totalBurned);}

    public void getClickedItem(int position, ArrayList<WorkoutsViewModel> list){

        String bodyPart = String.valueOf(list.get(position).getBodyPart());
        String directions = String.valueOf(list.get(position).getDirections());
        String exerciseName = String.valueOf(list.get(position).getExercise());
        String equipment = String.valueOf(list.get(position).getEquipment());
        String gifURL = String.valueOf(list.get(position).getGifURL());
        String target = String.valueOf(list.get(position).getTarget());
        String image = String.valueOf(list.get(position).getImage());


        setExercise(exerciseName);
        setDirections(directions);
        setBodyPart(bodyPart);
        setEquipment(equipment);
        setGifURL(gifURL);
        setTarget(target);
        setImage(image);

    }
    public void saveFavoriteWorkout(WorkoutApi workout, Context context, String path, String childTitle){

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dataRef = FirebaseDatabase.getInstance()
                .getReference(path)
                .child(authUser.getUid())
                .child(childTitle.trim());
        dataRef.setValue(workout).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(context, "Saved successfully", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                int maxAttempts = 4;

                for (int i = 0; i < maxAttempts;){
                    if(i >= 0){
                        i++;
                        Toast.makeText(context, "Saved Failed...Please try again", Toast.LENGTH_LONG).show();


                    } else if ( i > 3 && i != 0 ) {
                        i++;
                        Toast.makeText(context, "Check title for errors", Toast.LENGTH_LONG).show();

                    }
                    else{
                        i++;
                        Toast.makeText(context, "Sorry for the issues...Restart the application", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });
    }

}