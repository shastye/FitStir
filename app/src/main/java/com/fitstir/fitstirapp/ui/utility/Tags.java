package com.fitstir.fitstirapp.ui.utility;

import androidx.core.util.Pair;

import com.fitstir.fitstirapp.R;

import java.util.Map;
import java.util.ArrayList;

public class Tags {
    public enum Reminder_Channel_ID {
        COME_BACK_REMINDERS(0);

        private final int value;
        private Reminder_Channel_ID(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    public static ArrayList<Integer> Notification_IDs = new ArrayList<Integer>();
    public static final ArrayList<Pair<String, String>> NOTIFICATION_CHANNELS = new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>("Reminders", "Reminders to come back."));
    }};
    public static final String TIMED_NOTIFICATION_TAG = "TimedNotifications";
    public static final String LAST_ON_DESTROY_TAG = "LastOnDestroy";
    public static final long MILLISECS_PER_SEC = 1000L;
    public static final long MILLISECS_PER_DAY = 86400000L;




    public static final ArrayList<SectionItem> WORKOUTS_SECTION = new ArrayList<SectionItem>() {{
        add(new SectionItem(R.drawable.ic_shoe_black_200dp, "Run Club"));
        add(new SectionItem(R.drawable.ic_bicep_black_200dp, "Upper Body"));
        add(new SectionItem(R.drawable.ic_legs_black_200dp, "Lower Body"));
        add(new SectionItem(R.drawable.ic_lifting_black_200dp, "Weight Lifting"));
        add(new SectionItem(R.drawable.ic_cir_black_200dp, "Circuit Workouts"));
        add(new SectionItem(R.drawable.ic_yoga_black_200dp, "Yoga"));
    }};

    public static final ArrayList<SectionItem> HEALTH_SECTION = new ArrayList<SectionItem>() {{
        add(new SectionItem(R.drawable.ic_cal_black_200dp, "Calorie Tracker"));
        add(new SectionItem(R.drawable.ic_guide_black_200dp, "Food Guide"));
        add(new SectionItem(R.drawable.ic_scale_black_200dp, "Weight Loss"));
        add(new SectionItem(R.drawable.ic_recipe_black_200dp, "Recipes"));
        add(new SectionItem(R.drawable.ic_loc_black_200dp, "Find Dietitian"));
        add(new SectionItem(R.drawable.ic_diary_black_200dp, "Diary"));
    }};
}
