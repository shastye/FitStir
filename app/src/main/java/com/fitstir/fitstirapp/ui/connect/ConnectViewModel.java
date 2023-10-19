package com.fitstir.fitstirapp.ui.connect;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConnectViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Boolean> isFemale = new MutableLiveData<>(false);
    private final MutableLiveData<Double> activityScore = new MutableLiveData<>(0.0);
    private final MutableLiveData<String> userName = new MutableLiveData<>();
    private final MutableLiveData<String> age = new MutableLiveData<>();
    private final MutableLiveData<String> weight = new MutableLiveData<>();
    private final MutableLiveData<String> goalWeight = new MutableLiveData<>();
    private final MutableLiveData<String> calories = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isGain = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isMaintain = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isGainMuscle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoseWeight = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isCardioGoal = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isManageStress = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isDiet = new MutableLiveData<>(false);
    private final MutableLiveData<Integer> userRegisterScore = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> userLevelScore = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> userActivityScore = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> userGoals = new MutableLiveData<>(0);
    private final MutableLiveData<String> userHeight = new MutableLiveData<>();


    public MutableLiveData<Boolean> getIsGain() {
        return isGain;
    }
    public MutableLiveData<Boolean> getIsMaintain() {
        return isMaintain;
    }
    public MutableLiveData<Boolean> getIsGainMuscle() {
        return isGainMuscle;
    }
    public MutableLiveData<Boolean> getIsFemale() {
        return isFemale;
    }
    public MutableLiveData<Double> getActivityScore() {
        return activityScore;
    }
    public MutableLiveData<String> getUserHeight() {
        return userHeight;
    }
    public MutableLiveData<String> getmText() {
        return mText;
    }
    public MutableLiveData<Boolean> getIsDiet() {
        return isDiet;
    }
    public MutableLiveData<Integer> getUserGoals() {
        return userGoals;
    }
    public MutableLiveData<Boolean> getIsCardioGoal() {
        return isCardioGoal;
    }
    public MutableLiveData<Boolean> getIsManageStress() {
        return isManageStress;
    }
    public LiveData<String> getText() {
        return mText;
    }
    public MutableLiveData<Boolean> getIsLoseWeight() {
        return isLoseWeight;
    }
    public MutableLiveData<Integer> getUserRegisterScore() {
        return userRegisterScore;
    }
    public MutableLiveData<Integer> getUserLevelScore() {
        return userLevelScore;
    }
    public MutableLiveData<Integer> getUserActivityScore() {
        return userActivityScore;
    }
    public MutableLiveData<String> getUserName() {
        return userName;
    }
    public MutableLiveData<String> getAge() {
        return age;
    }
    public MutableLiveData<String> getWeight() {
        return weight;
    }
    public MutableLiveData<String> getGoalWeight() {
        return goalWeight;
    }
    public MutableLiveData<String> getCalories() {
        return calories;
    }



    public ConnectViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }


    public void setIsFemale(Boolean sex){isFemale.setValue(sex);}
    public void setActiveScore(double activeScore){activityScore.setValue(activeScore);}
    public void setUserHeight(String height){userHeight.setValue(height);}
    public void setIsDiet(boolean diet){isDiet.setValue(diet);}
    public void setUserGoals(int goal){userGoals.setValue(goal);}
    public void setIsCardioGoal(boolean cardio){isCardioGoal.setValue(cardio);}
    public void setIsManageStress(boolean stress){isManageStress.setValue(stress);}
    public void setIsLoseWeight(boolean loseWeight) {isLoseWeight.setValue(loseWeight);}
    public void setUserRegisterScore(int score) {userRegisterScore.setValue(score);}
    public void setUserLevelScore(int score) {userLevelScore.setValue(score);}
    public void setUserActivityScore(int score) {userActivityScore.setValue(score);}
    public void setUserName(String name){userName.setValue(name);}
    public void setAge(String userAge) {age.setValue(userAge);}
    public void setWeight(String userWeight){weight.setValue(userWeight);}
    public void setGoalWeight(String userGoal){goalWeight.setValue(userGoal);}
    public void setCalories(String cal){calories.setValue(cal);}
    public void setIsGain(Boolean gain){isGain.setValue(gain);}
    public void setIsGainMuscle(Boolean muscle){isGainMuscle.setValue(muscle);}
    public void setIsMaintain(Boolean maintain){isMaintain.setValue(maintain);}




    public double calculateBMR(){

        //get user weight in kg and user height in cm
        //convert weight and height

        int weight = Integer.parseInt(getWeight().getValue());
        double weight_In_Kg = weight * 0.45359237;

        int height = Integer.parseInt(getUserHeight().getValue());
        double height_In_Cm = 30.48 * height;

        int age = Integer.parseInt(getAge().getValue());
        //metabolic equation

        if(getIsFemale().getValue())
        {
            return Math.round(655.1 + (9.5 * weight_In_Kg) + (1.8 * height_In_Cm) - (4.6 * age));
        }
        else{

            return Math.round(66.4 + (13.7 * weight_In_Kg) + (5.0 * height_In_Cm) - (6.7 * age));
        }
    }
    public double loseWeightCalculator(){

        double BMR = calculateBMR();
        double TDEE = BMR * getActivityScore().getValue();
        double calorieIntake = TDEE - 750;

        return calorieIntake;
    }
    public double gainWeightCalculator(){

        double BMR = calculateBMR();
        double TDEE = BMR * getActivityScore().getValue();
        double calorieIntake = TDEE + 275;

        return calorieIntake;
    }
}