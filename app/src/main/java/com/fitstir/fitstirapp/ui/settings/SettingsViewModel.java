package com.fitstir.fitstirapp.ui.settings;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.settings.fragments.SettingsBlankFragment;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.fitstir.fitstirapp.ui.utility.classes.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.Objects;

public class SettingsViewModel extends ViewModel {

    private static int sThemeID = 0;
    private final MutableLiveData<Integer> previousPage = new MutableLiveData<>(0);

    private final MutableLiveData<Boolean> stillInSettings = new MutableLiveData<>(false);
    private final MutableLiveData<Fragment> cameFromFragment = new MutableLiveData<>(new SettingsBlankFragment());
    private final MutableLiveData<Boolean> cameFromProfile = new MutableLiveData<>(false);

    private final MutableLiveData<Users> thisUser = new MutableLiveData<>(new Users());
    private final MutableLiveData<Bitmap> avatar = new MutableLiveData<>(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.profileimage_background));



    public SettingsViewModel() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = "Error: No user logged in...\nContact customer support.";
        if (user != null) {
            userID = user.getUid();
        }
    }


    public MutableLiveData<Boolean> getStillInSettings() { return stillInSettings; }
    public MutableLiveData<Fragment> getCameFromFragment() { return cameFromFragment; }
    public MutableLiveData<Boolean> getCameFromProfile() { return cameFromProfile; }

    public LiveData<Integer> getPreviousPage() { return previousPage; }
    public MutableLiveData<Users> getThisUser() { return thisUser; }



    public LiveData<Bitmap> getAvatar() { return avatar; }

    public int getThemeID_inMain() { return sThemeID; }


    public void setStillInSettings(boolean stillInSettings) { this.stillInSettings.setValue(stillInSettings); }
    public void setCameFromFragment(Fragment previousFragment) { this.cameFromFragment.setValue(previousFragment); }
    public void setCameFromProfile(boolean cameFromProfile) { this.cameFromProfile.setValue(cameFromProfile); }

    public void setPreviousPage(int previousPage) { this.previousPage.setValue(previousPage); }
    public void setAvatar(Bitmap avatar) { this.avatar.setValue(avatar); }

    public void setThemeID(int themeID) {
        sThemeID = themeID;
    }
    public void setThisUser(Users user) { this.thisUser.setValue(user); }



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

        FirebaseDatabase.getInstance()
            .getReference("CalorieTrackingData")
            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
            .removeValue();

        FirebaseDatabase.getInstance()
                .getReference("CompletedRun")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .removeValue();

        FirebaseDatabase.getInstance()
                .getReference("CustomRoutines")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .removeValue();

        FirebaseDatabase.getInstance()
                .getReference("DiaryData")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .removeValue();

        FirebaseDatabase.getInstance()
                .getReference("FavoriteItemYoga")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .removeValue();

        FirebaseDatabase.getInstance()
                .getReference("GoalsData")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .removeValue();

        FirebaseDatabase.getInstance()
                .getReference("LikedRecipes")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .removeValue();

        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });

        return true;
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