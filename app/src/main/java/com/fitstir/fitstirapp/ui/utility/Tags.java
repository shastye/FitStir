package com.fitstir.fitstirapp.ui.utility;

import com.fitstir.fitstirapp.R;

import java.util.Vector;

public class Tags {
    public static String FIRST_NAME = "first_name";


    public static final Vector<SectionItem> WORKOUTS_SECTION = new Vector<SectionItem>() {{
        add(new SectionItem(R.drawable.ic_launcher_foreground, "Run Club"));
        add(new SectionItem(R.drawable.ic_launcher_foreground, "Upper Body"));
        add(new SectionItem(R.drawable.ic_launcher_foreground, "Lower Body"));
        add(new SectionItem(R.drawable.ic_launcher_foreground, "Weight Lifting"));
        add(new SectionItem(R.drawable.ic_launcher_foreground, "Circuit Workouts"));
        add(new SectionItem(R.drawable.ic_launcher_foreground, "Yoga"));
    }};

    public static final Vector<SectionItem> HEALTH_SECTION = new Vector<SectionItem>() {{
        add(new SectionItem(R.drawable.ic_launcher_foreground, "Calorie Tracker"));
        add(new SectionItem(R.drawable.ic_launcher_foreground, "Food Guide"));
        add(new SectionItem(R.drawable.ic_launcher_foreground, "Weight Loss"));
        add(new SectionItem(R.drawable.ic_launcher_foreground, "Recipes"));
        add(new SectionItem(R.drawable.ic_launcher_foreground, "Find Dietitian"));
        add(new SectionItem(R.drawable.ic_launcher_foreground, "Diary"));
    }};
}
