package com.fitstir.fitstirapp.ui.goals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitstir.fitstirapp.ui.utility.Tags;

import java.util.ArrayList;
import java.util.Calendar;

public class GoalsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Goal> clickedGoal = new MutableLiveData<>(new Goal());
    private final MutableLiveData<ArrayList<Goal>> goals = new MutableLiveData<>(new ArrayList<Goal>() {{
        add(getGenericGoal(0));
        add(getGenericGoal(1));
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

    private Goal getGenericGoal(int goalNum) {
        Goal g;
        Calendar calendar = Calendar.getInstance();

        switch(goalNum) {
            case 1:
                g = new Goal("Test1", Tags.Workout_Type.RUN_CLUB_DISTANCE, 16);

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
                g = new Goal("Test#", Tags.Workout_Type.RUN_CLUB_ENDURANCE, 14);

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