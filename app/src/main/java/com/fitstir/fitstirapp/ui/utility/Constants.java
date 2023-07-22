package com.fitstir.fitstirapp.ui.utility;

import com.fitstir.fitstirapp.R;

import java.util.ArrayList;

public class Constants {
    public static final String TIMED_NOTIFICATION_TAG = "TimedNotifications";
    public static final String LAST_ON_DESTROY_TAG = "LastOnDestroy";


    public static final long MILLISECS_PER_SEC = 1000L;
    public static final long MILLISECS_PER_DAY = 86400000L;






    public enum Workout_Type { // TODO: Add more to represent all types
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

        private Workout_Type(String spinnerTitle, int value, String imperialUnit, String metricUnit) {
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
    }
}
