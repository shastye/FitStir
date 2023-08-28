package com.fitstir.fitstirapp.ui.workouts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WorkoutsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
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

}