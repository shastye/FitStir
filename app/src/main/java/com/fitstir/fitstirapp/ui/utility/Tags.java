package com.fitstir.fitstirapp.ui.utility;

import androidx.core.util.Pair;

import com.fitstir.fitstirapp.R;

import java.util.Map;
import java.util.Vector;

public class Tags {
    public enum Reminder_Channel_ID {
        WORKOUT_REMINDERS(0),
        MENTAL_WELLNESS_REMINDERS(1);

        private final int value;
        private Reminder_Channel_ID(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    public static Vector<Integer> Notification_IDs = new Vector<Integer>();
    public static final Vector<Pair<String, String>> NOTIFICATION_CHANNELS = new Vector<Pair<String, String>>() {{
            add(new Pair<String, String>("Workout Reminders", "Reminders to workout."));
            add(new Pair<String, String>("Mental Wellness Check-In Reminders", "Reminders to check in with regards to your mental health."));
    }};
    public static final String TIMED_NOTIFICATION_TAG = "TimedNotifications";
    public static final String LAST_ON_DESTROY_TAG = "LastOnDestroy";




    public static final Vector<SectionItem> WORKOUTS_SECTION = new Vector<SectionItem>() {{
        add(new SectionItem(R.drawable.ic_shoe_black_200dp, "Run Club"));
        add(new SectionItem(R.drawable.ic_bicep_black_200dp, "Upper Body"));
        add(new SectionItem(R.drawable.ic_legs_black_200dp, "Lower Body"));
        add(new SectionItem(R.drawable.ic_lifting_black_200dp, "Weight Lifting"));
        add(new SectionItem(R.drawable.ic_cir_black_200dp, "Circuit Workouts"));
        add(new SectionItem(R.drawable.ic_yoga_black_200dp, "Yoga"));
    }};

    public static final Vector<SectionItem> HEALTH_SECTION = new Vector<SectionItem>() {{
        add(new SectionItem(R.drawable.ic_cal_black_200dp, "Calorie Tracker"));
        add(new SectionItem(R.drawable.ic_guide_black_200dp, "Food Guide"));
        add(new SectionItem(R.drawable.ic_scale_black_200dp, "Weight Loss"));
        add(new SectionItem(R.drawable.ic_recipe_black_200dp, "Recipes"));
        add(new SectionItem(R.drawable.ic_loc_black_200dp, "Find Dietitian"));
        add(new SectionItem(R.drawable.ic_diary_black_200dp, "Diary"));
    }};
}
