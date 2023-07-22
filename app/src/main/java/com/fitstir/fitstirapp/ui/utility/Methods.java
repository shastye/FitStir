package com.fitstir.fitstirapp.ui.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.fitstir.fitstirapp.R;

public class Methods {
    @NonNull
    public static Spinner getSpinnerWithAdapter(@NonNull Activity activity, @NonNull View root, int spinnerID, String[] spinnerOptions) {
        Spinner spinner = (Spinner) root.findViewById(spinnerID);
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(activity, R.layout.spinner_text, spinnerOptions);
        langAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(langAdapter);

        return spinner;
    }

    public static void navigateToLogInActivity(@NonNull Context context) {
        Intent intent = context
                .getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        Intent mainIntent = Intent.makeRestartActivityTask(intent.getComponent());
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }

    public static int getThemeAttributeColor(int R_attr_color, @NonNull Context context) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R_attr_color, value, true);
        return value.data;
    }




}
