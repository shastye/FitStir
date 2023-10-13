package com.fitstir.fitstirapp.ui.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatSpinner;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.goals.Goal;
import com.fitstir.fitstirapp.ui.utility.enums.GoalTypes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Methods {
    @NonNull
    public static Spinner setSpinnerAdapter(@NonNull Context context, @NonNull AppCompatSpinner spinner, String[] spinnerOptions) {
        spinner.setBackground(AppCompatResources.getDrawable(context, R.drawable.spinner_outline));
        spinner.setPopupBackgroundDrawable(AppCompatResources.getDrawable(context, R.drawable.spinner_custom));

        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(context, R.layout.spinner_text, spinnerOptions);
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

    public static Bitmap getBitmapFromURL(String url) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Bitmap> bitmapFuture = executor.submit(() -> {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();

                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);

                return myBitmap;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            return bitmapFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getStringFromBitmap(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap getBitmapFromString(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static boolean isToday(Date date) {
        Calendar todayCal = Calendar.getInstance();
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);

        int tDOY = todayCal.get(Calendar.DAY_OF_YEAR);
        int dDOY = dateCal.get(Calendar.DAY_OF_YEAR);

        return tDOY == dDOY;
    }

    public static boolean firstIsSecond(Date first, Date second) {
        Calendar firstCal = Calendar.getInstance();
        firstCal.setTime(first);
        Calendar secondCal = Calendar.getInstance();
        secondCal.setTime(second);

        int firstDay = firstCal.get(Calendar.DAY_OF_YEAR);
        int secondDay = secondCal.get(Calendar.DAY_OF_YEAR);

        return firstDay == secondDay;
    }

    public static boolean firstIsAfterSecond(Date first, Date second) {
        Calendar firstCal = Calendar.getInstance();
        firstCal.setTime(first);
        Calendar secondCal = Calendar.getInstance();
        secondCal.setTime(second);

        int firstDay = firstCal.get(Calendar.DAY_OF_YEAR);
        int secondDay = secondCal.get(Calendar.DAY_OF_YEAR);

        return secondDay < firstDay;
    }

    public static void addGoalToFirebase(GoalTypes type, int value) {
        Goal thisGoal = new Goal(type, value);

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        assert authUser != null;
        FirebaseDatabase.getInstance()
                .getReference("GoalsData")
                .child(authUser.getUid())
                .child(thisGoal.getID())
                .setValue(thisGoal);
    }

    public static void addDataToGoal(GoalTypes type, double value) {
        FirebaseDatabase.getInstance()
                .getReference("GoalsData")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> children = snapshot.getChildren();
                        Goal goal = null;

                        for (DataSnapshot child : children) {
                            goal = child.getValue(Goal.class);

                            if (goal != null && goal.getType().equals(type)) {
                                if (goal.getData() == null) {
                                    goal.setData(new ArrayList<>());
                                }

                                goal.addData(Calendar.getInstance().getTime(), value);

                                break;
                            }
                        }

                        FirebaseDatabase.getInstance()
                                .getReference("GoalsData")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(goal.getID())
                                .setValue(goal);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
