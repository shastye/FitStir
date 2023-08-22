package com.fitstir.fitstirapp.ui.workouts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WorkoutsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;



    private final MutableLiveData<String> exercise = new MutableLiveData<>(" ");
    private final MutableLiveData<String> bodyPart = new MutableLiveData<>(" ");
    private final MutableLiveData<String> target = new MutableLiveData<>(" ");
    private final MutableLiveData<String> directions = new MutableLiveData<>(" ");
    private final MutableLiveData<String> image = new MutableLiveData<>(" ");
    private final MutableLiveData<String> gifURL = new MutableLiveData<>(" ");
    private final MutableLiveData<String> equipment = new MutableLiveData<>(" ");
    public LiveData<String> getText() { return mText;}
    public MutableLiveData<String> getTarget() { return target;}
    public MutableLiveData<String> getDirections() { return directions;}
    public MutableLiveData<String> getImage() { return image;}
    public MutableLiveData<String> getGifURL() { return gifURL;}
    public MutableLiveData<String> getEquipment() { return equipment;}
    public MutableLiveData<String> getBodyPart() { return bodyPart;}
    public MutableLiveData<String> getExercise() { return exercise;}


    public WorkoutsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }
    public void setBodyPart(String body){this.bodyPart.setValue(body);}
    public void setTarget(String target){this.target.setValue(target);}
    public void setDirections(String directions){this.directions.setValue(directions);}
    public void setImage(String image){this.image.setValue(image);}
    public void setEquipment(String equip){this.equipment.setValue(equip);}
    public void setGifURL(String gif){this.gifURL.setValue(gif);}
    public void setExercise(String name){this.exercise.setValue(name);}

}