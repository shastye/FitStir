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
    RUN_CLUB_ENDURANCE("Run Club - Endurance", 2, "Hours", "Hours"),
    UPPER_BODY_WEIGHT("Upper Body - Weight", 3, "Pounds", "Kilograms"),
    UPPER_BODY_REPS("Upper Body - Reps", 4, "Count", "Count"),
    LOWER_BODY_WEIGHT("Lower Body - Weight", 5, "Pounds", "Kilograms"),
    LOWER_BODY_REPS("Lower Body - Reps", 6, "Count", "Count"),
    WEIGHT_LIFTING_WEIGHT("Weight Lifting - Weight", 7, "Pounds", "Kilograms"),
    WEIGHT_LIFTING_REPS("Weight Lifting - Reps", 8, "Count", "Count"),
    CIRCUIT_WORKOUTS_WEIGHT("Circuit Workouts - Weight", 9, "Pounds", "Kilograms"),
    CIRCUIT_WORKOUTS_ENDURANCE("Circuit Workouts - Endurance", 10, "Hours", "Hours"),
    YOGA_DURATION("Yoga - Duration", 11, "Hours", "Hours"),
    YOGA_POSITION_COUNT("Yoga - Num Positions", 12, "Count", "Count");

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
            case CIRCUIT_WORKOUTS_WEIGHT:
            case CIRCUIT_WORKOUTS_ENDURANCE:
                return null;
            case UPPER_BODY_WEIGHT:
            case UPPER_BODY_REPS:
            case LOWER_BODY_REPS:
            case LOWER_BODY_WEIGHT:
            case WEIGHT_LIFTING_REPS:
            case WEIGHT_LIFTING_WEIGHT:
                return null;
            case YOGA_DURATION:
            case YOGA_POSITION_COUNT:
            default:
                return null;
        }
    }
}