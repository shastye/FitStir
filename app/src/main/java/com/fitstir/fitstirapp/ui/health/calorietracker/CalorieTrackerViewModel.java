package com.fitstir.fitstirapp.ui.health.calorietracker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;

import java.util.ArrayList;
import java.util.Calendar;

public class CalorieTrackerViewModel extends ViewModel {

    private final MutableLiveData<UserProfileData> thisUser = new MutableLiveData<>(new UserProfileData());
    private final MutableLiveData<Calendar> selectedDate = new MutableLiveData<>(Calendar.getInstance()); //set to today initially
    private final MutableLiveData<String> dateString = new MutableLiveData<>("Today");
    private final MutableLiveData<ArrayList<CalorieTrackerData>> calorieTrackerData = new MutableLiveData<>(new ArrayList<>());



    private final MutableLiveData<String> mText;
    public CalorieTrackerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is explore fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }



    public MutableLiveData<UserProfileData> getThisUser() {
        return thisUser;
    }
    public void setThisUser(UserProfileData user) {
        thisUser.setValue(user);
    }

    public MutableLiveData<Calendar> getSelectedDate() {
        return selectedDate;
    }
    public void setSelectedDate(Calendar calendar) {
        selectedDate.setValue(calendar);
    }

    public MutableLiveData<ArrayList<CalorieTrackerData>> getCalorieTrackerData() {
        return calorieTrackerData;
    }
    public void setCalorieTrackerData(ArrayList<CalorieTrackerData> data) {
        calorieTrackerData.setValue(data);
    }

    public MutableLiveData<String> getDateString() {
        return dateString;
    }
    public void setDateString(String dateAsString) {
        dateString.setValue(dateAsString);
    }
}
