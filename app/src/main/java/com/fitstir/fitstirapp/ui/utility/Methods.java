package com.fitstir.fitstirapp.ui.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.fitstir.fitstirapp.R;

import java.util.Vector;

public class Methods {
    public static Spinner getSpinnerWithAdapter(Activity _activity, View _root, int _spinnerID, String[] _spinnerOptions) {
        Spinner spinner = (Spinner) _root.findViewById(_spinnerID);
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(_activity, R.layout.spinner_text, _spinnerOptions);
        langAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(langAdapter);

        return spinner;
    }

    public static boolean isEmpty(Vector<EditText> _vet) {
        boolean isEmpty = false;

        for (EditText et : _vet) {
            isEmpty = (et.getText().toString().trim().length() == 0);

            if (isEmpty) {
                break;
            }
        }

        return isEmpty;
    }

    public static Vector<Bitmap> convertPNGtoBitmap(View _root, int[] _drawables) {
        Vector<Bitmap> bitmaps = new Vector<>();

        for (int drawable : _drawables) {
            bitmaps.add(BitmapFactory.decodeResource(_root.getContext().getResources(), drawable));
        }

        return bitmaps;
    }
}
