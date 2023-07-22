package com.fitstir.fitstirapp.ui.utility;

import com.fitstir.fitstirapp.R;

import java.util.ArrayList;

public class Constants {
    public static final String TIMED_NOTIFICATION_TAG = "TimedNotifications";
    public static final String LAST_ON_DESTROY_TAG = "LastOnDestroy";


    public static final long MILLISECS_PER_SEC = 1000L;
    public static final long MILLISECS_PER_DAY = 86400000L;




    public static final ArrayList<SectionGridAdapter.SectionItem> WORKOUTS_SECTION = new ArrayList<SectionGridAdapter.SectionItem>() {{
        add(new SectionGridAdapter.SectionItem(R.drawable.ic_shoe_black_200dp, "Run Club"));
        add(new SectionGridAdapter.SectionItem(R.drawable.ic_bicep_black_200dp, "Upper Body"));
        add(new SectionGridAdapter.SectionItem(R.drawable.ic_legs_black_200dp, "Lower Body"));
        add(new SectionGridAdapter.SectionItem(R.drawable.ic_lifting_black_200dp, "Weight Lifting"));
        add(new SectionGridAdapter.SectionItem(R.drawable.ic_cir_black_200dp, "Circuit Workouts"));
        add(new SectionGridAdapter.SectionItem(R.drawable.ic_yoga_black_200dp, "Yoga"));
    }};

    public static final ArrayList<SectionGridAdapter.SectionItem> HEALTH_SECTION = new ArrayList<SectionGridAdapter.SectionItem>() {{
        add(new SectionGridAdapter.SectionItem(R.drawable.ic_cal_black_200dp, "Calorie Tracker"));
        add(new SectionGridAdapter.SectionItem(R.drawable.ic_guide_black_200dp, "Food Guide"));
        add(new SectionGridAdapter.SectionItem(R.drawable.ic_scale_black_200dp, "Weight Loss"));
        add(new SectionGridAdapter.SectionItem(R.drawable.ic_recipe_black_200dp, "Recipes"));
        add(new SectionGridAdapter.SectionItem(R.drawable.ic_loc_black_200dp, "Find Dietitian"));
        add(new SectionGridAdapter.SectionItem(R.drawable.ic_diary_black_200dp, "Diary"));
    }};



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
