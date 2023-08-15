package com.fitstir.fitstirapp.ui.workouts.exerciseapi.upperbody;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.fitstir.fitstirapp.R;

public class UpperBodyApi {
    String bodyPart;
    String equipment;
    String exercise;
    String target;
    String directions;
    String gifURL;
    String image;

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

    public UpperBodyApi(){}

    public UpperBodyApi(String bodyPart, String equipment, String exercise, String target, String directions, String gifURL, String image) {
        this.bodyPart = bodyPart;
        this.equipment = equipment;
        this.exercise = exercise;
        this.target = target;
        this.directions = directions;
        this.gifURL = gifURL;
        this.image = image;
    }


}
