package com.fitstir.fitstirapp.ui.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.fitstir.fitstirapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.Objects;
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



    public static boolean clearApplicationData(Activity _activity) {
        boolean success = true;

        File cache = _activity.getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    success = deleteDir(new File(appDir, s));

                    if (!success) {
                        return false;
                    }
                }
            }
        }

        return success;
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    public static boolean deleteFromDatabase() {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        final boolean[] success = {false};

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        success[0] = true;
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("deleteFromDatabase Error", "An error occurred: " + error.getMessage());
            }
        };
        databaseReference.addValueEventListener(listener);

        return success[0];
    }

    public static boolean deleteUser() {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final boolean[] success = {false};

        if (user != null) {
            OnCompleteListener<Void> listener = new  OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        success[0] = true;
                    }
                }
            };
            user.delete().addOnCompleteListener(listener);
        }

        return success[0];
    }

    public static void navigateToLogInActivity(Context _context) {
        Intent intent = _context
                .getPackageManager()
                .getLaunchIntentForPackage(_context.getPackageName());
        Intent mainIntent = Intent.makeRestartActivityTask(intent.getComponent());
        _context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }
}
