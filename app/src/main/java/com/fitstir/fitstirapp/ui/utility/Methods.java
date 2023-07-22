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
    public static Spinner getSpinnerWithAdapter(@NonNull Activity _activity, @NonNull View _root, int _spinnerID, String[] _spinnerOptions) {
        Spinner spinner = (Spinner) _root.findViewById(_spinnerID);
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(_activity, R.layout.spinner_text, _spinnerOptions);
        langAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(langAdapter);

        return spinner;
    }

    public static void navigateToLogInActivity(@NonNull Context _context) {
        Intent intent = _context
                .getPackageManager()
                .getLaunchIntentForPackage(_context.getPackageName());
        Intent mainIntent = Intent.makeRestartActivityTask(intent.getComponent());
        _context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }

    public static int getThemeAttributeColor(int _R_attr_color, @NonNull Context _context) {
        TypedValue value = new TypedValue();
        _context.getTheme().resolveAttribute(_R_attr_color, value, true);
        return value.data;
    }




}
