package com.fitstir.fitstirapp.ui.goals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GoalsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    public static Goal clickedGoal;

    public GoalsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is goals fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}