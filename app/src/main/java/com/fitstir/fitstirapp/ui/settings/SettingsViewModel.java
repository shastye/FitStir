package com.fitstir.fitstirapp.ui.settings;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    private static int sThemeID = 0;

    private final MutableLiveData<Integer> previousPage = new MutableLiveData<>(0);
    private final MutableLiveData<Bitmap> avatar = new MutableLiveData<>(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.profileimage_background));
    private final MutableLiveData<String> name = new MutableLiveData<>(" ");
    private final MutableLiveData<Integer> age = new MutableLiveData<>(0 );
    private final MutableLiveData<Integer> height_feet = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> height_inches = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> weight = new MutableLiveData<>(0);
    private final MutableLiveData<String> email = new MutableLiveData<>(" ");

    private final MutableLiveData<Integer> themeID = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> rangeID = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> intervalID = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> unitID = new MutableLiveData<>(0);

    public SettingsViewModel() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = "Error: No user logged in...\nContact customer support.";
        if (user != null) {
            userID = user.getUid();
        }

        mText = new MutableLiveData<>();
        mText.setValue("User ID: " + userID);
    }

    public LiveData<String> getText() { return mText; }


    public LiveData<Integer> getPreviousPage() { return previousPage; }
    public LiveData<Bitmap> getAvatar() { return avatar; }
    public LiveData<String> getName() { return name; }
    public LiveData<Integer> getAge() { return age; }
    public LiveData<Integer> getHeightInFeet() { return height_feet; }
    public LiveData<Integer> getHeightInInches() { return height_inches; }
    public LiveData<Integer> getWeight() { return weight; }
    public LiveData<String> getEmail() { return email; }
    public LiveData<Integer> getThemeID() { return themeID; }
    public int getThemeID_inMain() { return sThemeID; }
    public LiveData<Integer> getRangeID() { return rangeID; }
    public LiveData<Integer> getIntervalID() { return intervalID; }
    public LiveData<Integer> getUnitID() { return unitID; }

    public void setPreviousPage(int previousPage) { this.previousPage.setValue(previousPage); }
    public void setAvatar(Bitmap avatar) { this.avatar.setValue(avatar); }
    public void setName(String name) { this.name.setValue(name); }
    public void setAge(int age) { this.age.setValue(age); }
    public void setHeightInFeet(int height_feet) { this.height_feet.setValue(height_feet); }
    public void setHeightInInches(int height_inches) { this.height_inches.setValue(height_inches); }
    public void setWeight(int weight) { this.weight.setValue(weight); }
    public void setEmail(String email) { this.email.setValue(email); }
    public void setThemeID(int themeID) {
        this.themeID.setValue(themeID);
        sThemeID = themeID;
    }
    public void setRangeID(int rangeID) { this.rangeID.setValue(rangeID); }
    public void setIntervalID(int intervalID) { this.intervalID.setValue(intervalID); }
    public void setUnitID(int unitID) { this.unitID.setValue(unitID); }



    public boolean clearApplicationData(@NonNull Activity activity) {
        boolean success = true;

        File cache = activity.getCacheDir();
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

    public boolean deleteDir(File dir) {
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

    public boolean deleteFromDatabase() {
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

    public boolean deleteUser() {

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
}