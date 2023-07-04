package com.fitstir.fitstirapp.ui.utility;

import android.app.Activity;
import android.content.Intent;

import com.fitstir.fitstirapp.R;

public class ResetTheme {
    private static int themeID;

    public static void changeToTheme(Activity _activity, int _themeID) {
        themeID = _themeID;
        _activity.finish();
        _activity.startActivity(new Intent(_activity, _activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity _activity) {
        switch (themeID) {
            case 0:
                _activity.setTheme(R.style.Theme1);
                break;
            case 1:
                _activity.setTheme(R.style.Theme2);
                break;
        }
    }
}
