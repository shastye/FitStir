package com.fitstir.fitstirapp.ui.settings;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private static int previousPage = -1;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment");
    }

    public static int getPreviousPage() { return previousPage; }
    public LiveData<String> getText() { return mText; }

    public static void setPreviousPage(int _previousPage) { previousPage = _previousPage; }


}