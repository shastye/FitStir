package com.fitstir.fitstirapp.ui.health.weightloss;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitstir.fitstirapp.ui.goals.GoalDataPair;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.fitstir.fitstirapp.ui.utility.classes.Users;

import java.util.ArrayList;

public class WeightLossViewModel extends ViewModel {
    private final MutableLiveData<Users> thisUser = new MutableLiveData<>(new Users());
    private final MutableLiveData<Integer> weightGoal = new MutableLiveData<>(0);

    private final MutableLiveData<String> goalID = new MutableLiveData<>("");
    private final MutableLiveData<ArrayList<GoalDataPair>> weightData = new MutableLiveData<>(new ArrayList<>());

    private final MutableLiveData<String> mText;

    public WeightLossViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is explore fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }



    public MutableLiveData<Users> getThisUser() {
        return thisUser;
    }
    public void setThisUser(Users user) {
        this.thisUser.setValue(user);
    }

    public MutableLiveData<String> getGoalID() {
        return goalID;
    }
    public void setGoalID(String goalID) {
        this.goalID.setValue(goalID);
    }

    public MutableLiveData<Integer> getWeightGoal() {
        return weightGoal;
    }
    public void setWeightGoal(Integer weightGoal) {
        this.weightGoal.setValue(weightGoal);
    }

    public MutableLiveData<ArrayList<GoalDataPair>> getWeightData() {
        return weightData;
    }
    public void setWeightData(ArrayList<GoalDataPair> weightData) {
        this.weightData.setValue(weightData);
    }
}
