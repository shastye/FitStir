package com.fitstir.fitstirapp.ui.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.fitstir.fitstirapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.Objects;

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



    public static boolean clearApplicationData(@NonNull Activity _activity) {
        boolean success = true;

        File cache = _activity.getCacheDir();
        File appDir = new File(Objects.requireNonNull(cache.getParent()));
        if (appDir.exists()) {
            String[] children = appDir.list();
            assert children != null;
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

            assert children != null;
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }

        assert dir != null;
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
            user.delete().addOnSuccessListener(new  OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    success[0] = true;
                }
            });
        }

        return success[0];
    }


    public static int getThemeAttributeColor(int _R_attr_color, @NonNull Context _context) {
        TypedValue value = new TypedValue();
        _context.getTheme().resolveAttribute(_R_attr_color, value, true);
        return value.data;
    }


    

}
