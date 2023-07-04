package com.fitstir.fitstirapp.ui.settings;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitstir.fitstirapp.R;

public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public static int previousPage = -1;
    public static Bitmap avatar = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.profileimage_background);
    public static String name = "Sierra Clubb";
    public static int age = 26;
    public static int height_feet = 5;
    public static int height_inches = 2;
    public static int weight = 145;
    public static String email = "shastye.7x@gmail.com";



    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment");
    }
}