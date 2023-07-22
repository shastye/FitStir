package com.fitstir.fitstirapp.ui.utility.classes;

import android.app.Activity;
import android.content.Intent;

import com.fitstir.fitstirapp.R;

public class ResetTheme {
    private static int sThemeID;

    public static void changeToTheme(Activity activity, int themeID) {
        sThemeID = themeID;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void setInitialTheme(int themeID) {
        sThemeID = themeID;
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sThemeID) {
            case 0:
                activity.setTheme(R.style.Theme1);
                break;
            case 1:
                activity.setTheme(R.style.Theme2);
                break;
        }
    }
}
