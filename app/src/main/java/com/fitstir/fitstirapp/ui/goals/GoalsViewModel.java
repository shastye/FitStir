package com.fitstir.fitstirapp.ui.goals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitstir.fitstirapp.databinding.FragmentViewGoalBinding;
import com.fitstir.fitstirapp.ui.utility.Methods;

import java.util.ArrayList;
import java.util.Arrays;

public class GoalsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    public static Goal clickedGoal = new Goal();
    public static ArrayList<Goal> goals = new ArrayList<Goal>() {{
        add(Methods.getGenericGoal(0));
        add(Methods.getGenericGoal(1));
    }};
    public static int goalRange = 30; // TODO: Get from settings in days

    public GoalsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is goals fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}