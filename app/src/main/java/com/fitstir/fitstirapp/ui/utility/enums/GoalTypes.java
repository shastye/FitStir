package com.fitstir.fitstirapp.ui.utility.enums;

import android.util.Pair;

import androidx.annotation.NonNull;

import com.fitstir.fitstirapp.ui.goals.Goal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public enum GoalTypes { // TODO: Add more to represent all types
    WEIGHT_CHANGE("Weight Change", 0, "Pounds", "Kilograms"),

    RUN_CLUB_DISTANCE("Run Club - Distance", 1, "Miles", "Kilometers"),
    RUN_CLUB_ENDURANCE("Run Club - Endurance", 2, "Minutes", "Minutes"),

    UPPER_BODY_CALORIES("Upper Body - Calories", 3, "kCals", "kJ"),
    LOWER_BODY_CALORIES("Lower Body - Calories", 4, "kCals", "kJ"),
    WEIGHT_LIFTING_CALORIES("Weight Lifting - Calories", 5, "kCals", "kJ"),

    CIRCUIT_WORKOUTS_REPS("Circuit Workouts - Reps", 6, "Count", "Count"),
    CIRCUIT_WORKOUTS_ENDURANCE("Circuit Workouts - Endurance", 7, "Minutes", "Minutes");

    private final String spinnerTitle;
    private final int value;
    private final String imperialUnit;
    private final String metricUnit;

    private GoalTypes(String spinnerTitle, int value, String imperialUnit, String metricUnit) {
        this.spinnerTitle = spinnerTitle;
        this.value = value;
        this.imperialUnit = imperialUnit;
        this.metricUnit = metricUnit;
    }

    public String getSpinnerTitle() {
        return this.spinnerTitle;
    }
    public int getValue() {
        return this.value;
    }
    public String getImperialUnit() {
        return this.imperialUnit;
    }
    public String getMetricUnit() {
        return this.metricUnit;
    }

    public DatabaseReference getDatabaseReference(String userID) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        switch (this) {
            case RUN_CLUB_DISTANCE:
            case RUN_CLUB_ENDURANCE:
                return db.getReference("CompletedRun").child(userID);
                //      returns List of class items representing each run
                //      each Object will use { kid.getTotalDistance } and { kid.getCompletedRunInMinutes } and { kid.getCompletedDate }
            case UPPER_BODY_CALORIES:
            case LOWER_BODY_CALORIES:
            case WEIGHT_LIFTING_CALORIES:
                return db.getReference("CompletedWorkout").child(userID);
            //      returns List of class items representing each run
            //      each Object will use { kid.baby.getCompletedDate }
            case CIRCUIT_WORKOUTS_REPS:
            case CIRCUIT_WORKOUTS_ENDURANCE:
                return db.getReference("CompletedWorkout").child(userID);
            //      returns List of class items representing each run
            //      each Object will use { kid.baby.getReps } and { kid.baby.getDuration } and { kid.baby.getCompletedDate }
            default:
                return null;
        }
    }
}