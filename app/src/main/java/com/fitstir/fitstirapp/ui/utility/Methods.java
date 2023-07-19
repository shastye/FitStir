package com.fitstir.fitstirapp.ui.utility;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.util.Pair;
import androidx.navigation.NavDeepLinkBuilder;

import com.fitstir.fitstirapp.MainActivity;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.goals.Goal;
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
import java.util.Calendar;
import java.util.Objects;
import java.util.Vector;

public class Methods {
    private static int NOTIFICATION_ID = 0;

    @NonNull
    public static Spinner getSpinnerWithAdapter(@NonNull Activity _activity, @NonNull View _root, int _spinnerID, String[] _spinnerOptions) {
        Spinner spinner = (Spinner) _root.findViewById(_spinnerID);
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(_activity, R.layout.spinner_text, _spinnerOptions);
        langAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(langAdapter);

        return spinner;
    }



    public static boolean clearApplicationData(@NonNull Activity _activity) {
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

    public static void createNotification(@NonNull Context _context, int _channel_id, int _drawableID, String _title, String _small_content, String _extra_content, int _navID) {
        NavDeepLinkBuilder navBuilder = new NavDeepLinkBuilder(_context.getApplicationContext());
        navBuilder.setComponentName(MainActivity.class);
        navBuilder.setGraph(R.navigation.main_navigation);
        navBuilder.setDestination(_navID);
        PendingIntent pendingIntent = navBuilder.createPendingIntent();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(_context, String.valueOf(_channel_id))
                .setSmallIcon(_drawableID)
                .setContentTitle(_title)
                .setContentText(_small_content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(_extra_content))
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(_context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        Tags.Notification_IDs.add(NOTIFICATION_ID++);
    }
    
    public static Goal getGenericGoal(int goalNum) {
        Goal g;
        Calendar calendar = Calendar.getInstance();
        
        switch(goalNum) {
            case 1:
                g = new Goal("Test1", "Type1", 16);
                
                calendar.set(Calendar.MONTH, Calendar.MAY);
                calendar.set(Calendar.DAY_OF_MONTH, 10);
                calendar.set(Calendar.YEAR, 2023);
                g.addData(calendar.getTime(), 1.1);
                calendar.add(Calendar.DATE, 2);
                g.addData(calendar.getTime(), 3.6);
                calendar.add(Calendar.DATE, 1);
                g.addData(calendar.getTime(), 4.7);
                calendar.add(Calendar.DATE, 5);
                g.addData(calendar.getTime(), 7.0);
                calendar.add(Calendar.DATE, 2);
                g.addData(calendar.getTime(), 8.9);
                calendar.add(Calendar.DATE, 6);
                g.addData(calendar.getTime(), 7.5);
                calendar.add(Calendar.DATE, 6);
                g.addData(calendar.getTime(), 7.7);
                calendar.add(Calendar.DATE, 6);
                g.addData(calendar.getTime(), 7.0);
                calendar.add(Calendar.DATE, 6);
                g.addData(calendar.getTime(), 7.9);
                calendar.add(Calendar.DATE, 6);
                g.addData(calendar.getTime(), 7.2);
                calendar.add(Calendar.DATE, 5);
                g.addData(calendar.getTime(), 7.9);
                calendar.add(Calendar.DATE, 2);
                g.addData(calendar.getTime(), 8.4);
                calendar.add(Calendar.DATE, 2);
                g.addData(calendar.getTime(), 12.9);
                calendar.add(Calendar.DATE, 2);
                g.addData(calendar.getTime(), 11.1);
                calendar.add(Calendar.DATE, 2);
                g.addData(calendar.getTime(), 8.6);
                calendar.add(Calendar.DATE, 2);
                g.addData(calendar.getTime(), 14.7);
                break;
            default:
                g = new Goal("Test#", "Type#", 14);
                
                calendar.set(Calendar.MONTH, Calendar.JUNE);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.YEAR, 2023);
                g.addData(calendar.getTime(), 3.6);
                calendar.add(Calendar.DATE, 1);
                g.addData(calendar.getTime(), 4.7);
                calendar.add(Calendar.DATE, 2);
                g.addData(calendar.getTime(), 8.9);
                calendar.add(Calendar.DATE, 6);
                g.addData(calendar.getTime(), 7.5);
                calendar.add(Calendar.DATE, 3);
                g.addData(calendar.getTime(), 7.7);
                calendar.add(Calendar.DATE, 4);
                g.addData(calendar.getTime(), 7.0);
                calendar.add(Calendar.DATE, 6);
                g.addData(calendar.getTime(), 7.9);
                calendar.add(Calendar.DATE, 6);
                g.addData(calendar.getTime(), 7.2);
                calendar.add(Calendar.DATE, 5);
                g.addData(calendar.getTime(), 7.9);
                calendar.add(Calendar.DATE, 2);
                g.addData(calendar.getTime(), 8.4);
                calendar.add(Calendar.DATE, 2);
                g.addData(calendar.getTime(), 12.9);
                calendar.add(Calendar.DATE, 2);
                g.addData(calendar.getTime(), 11.1);
                calendar.add(Calendar.DATE, 2);
                g.addData(calendar.getTime(), 8.6);
                break;
        }
        
        return g;
    }
}
