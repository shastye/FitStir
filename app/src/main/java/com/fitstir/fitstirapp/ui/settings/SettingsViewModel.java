package com.fitstir.fitstirapp.ui.settings;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitstir.fitstirapp.R;

public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public static int previousPage = -1;

    public static View dialogRoot;

    public static Bitmap avatar = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.profileimage_background);
    public static String name = "Sierra Clubb";
    public static int age = 26;
    public static int height_feet = 5;
    public static int height_inches = 2;
    public static int weight = 145;
    public static String email = "shastye.7x@gmail.com";

    public static int themeID = 0;
    public static int rangeID = 0;
    public static int intervalID = 0;
    public static int unitID = 0;



    public SettingsViewModel() {
        // TODO: Get user id from firestore
        int userID = 7777;

        mText = new MutableLiveData<>();
        mText.setValue("User ID: " + userID);
    }

    public LiveData<String> getText() {
        return mText;
    }
}