package com.fitstir.fitstirapp.ui.goals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitstir.fitstirapp.databinding.FragmentViewGoalBinding;
import com.fitstir.fitstirapp.ui.utility.Methods;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class GoalsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Goal> clickedGoal = new MutableLiveData<>(new Goal());
    private final MutableLiveData<ArrayList<Goal>> goals = new MutableLiveData<>(new ArrayList<Goal>() {{
        add(Methods.getGenericGoal(0));
        add(Methods.getGenericGoal(1));
    }});
    private final MutableLiveData<Integer> goalRange = new MutableLiveData<Integer>(30); // TODO: Get from settings in days

    public GoalsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is goals fragment");
    }

    public MutableLiveData<String> getText() { return this.mText; }
    public LiveData<Goal> getClickedGoal() { return clickedGoal; }
    public LiveData<ArrayList<Goal>> getGoals() { return goals; }
    public LiveData<Integer> getGoalRange() { return goalRange; }

    public void setClickedGoal(Goal goal) { clickedGoal.setValue(goal); }
    public void addGoal(Goal goal) {
        ArrayList<Goal> temp = goals.getValue();
        assert temp != null;
        temp.add(goal);
        goals.setValue(temp);
    }
    public void removeGoal(Goal goal) {
        ArrayList<Goal> temp = goals.getValue();
        assert temp != null;
        temp.remove(goal);
        goals.setValue(temp);
    }
    public void setGoalRange(int range) { goalRange.setValue(range); }
}