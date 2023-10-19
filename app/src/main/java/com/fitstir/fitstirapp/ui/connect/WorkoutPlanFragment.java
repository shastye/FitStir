package com.fitstir.fitstirapp.ui.connect;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fitstir.fitstirapp.MainActivity;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentWorkoutPlanBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;

import java.util.Objects;


public class WorkoutPlanFragment extends Fragment {

    private FragmentWorkoutPlanBinding binding;

    private TextView age, userName, userAge, userWeight, userWeightGoal,
            featureName, calorieAmount, caloriesConsume_TV, caloriesBurn_TV, days;
    private Button ok_Dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWorkoutPlanBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        //initialization
        userName = root.findViewById(R.id.name_workout_dialog);
        userAge = root.findViewById(R.id.age_workout_dialog);
        userWeight = root.findViewById(R.id.weight_workout_dialog);
        userWeightGoal = root.findViewById(R.id.goalWeight_workout_dialog);
        featureName = root.findViewById(R.id.featureName_workout_dialog);
        calorieAmount = root.findViewById(R.id.calories_workout_dialog);
        caloriesConsume_TV = root.findViewById(R.id.calories_statement_Consume);
        caloriesBurn_TV = root.findViewById(R.id.calories_statement_Burn);
        days = root.findViewById(R.id.days_workout_dialog);
        ok_Dialog = root.findViewById(R.id.ok_workout_dialog);

        calculateRegisterScore();
        getWorkoutPlan();


        ok_Dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Welcome To FitStir", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getActivity(), MainActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(myIntent);
            }
        });
        return root;
    }
    public void getWorkoutPlan(){

        ConnectViewModel connectViewModel = new ViewModelProvider(requireActivity()).get(ConnectViewModel.class);

        boolean isCardio = Boolean.TRUE.equals(connectViewModel.getIsCardioGoal().getValue());
        boolean isStress = Boolean.TRUE.equals(connectViewModel.getIsManageStress().getValue());
        boolean isLoseWeight = Boolean.TRUE.equals(connectViewModel.getIsLoseWeight().getValue());
        boolean isDiet = Boolean.TRUE.equals(connectViewModel.getIsDiet().getValue());
        boolean isGain = Boolean.TRUE.equals(connectViewModel.getIsGain().getValue());
        boolean isMuscle = Boolean.TRUE.equals(connectViewModel.getIsGainMuscle().getValue());
        boolean isMaintain = Boolean.TRUE.equals(connectViewModel.getIsMaintain().getValue());
        boolean isFemale = Boolean.TRUE.equals(connectViewModel.getIsFemale().getValue());

        String name = connectViewModel.getUserName().getValue();
        String age = connectViewModel.getAge().getValue();
        int score = connectViewModel.getUserRegisterScore().getValue();
        int weight = Integer.parseInt(Objects.requireNonNull(connectViewModel.getWeight().getValue()));
        int goalWeight = Integer.parseInt(Objects.requireNonNull(connectViewModel.getGoalWeight().getValue()));
        double calculator = connectViewModel.gainWeightCalculator();
        double calLoseWeight = connectViewModel.loseWeightCalculator();

        int defaultCaloriesAmount;
        int weight_Difference = goalWeight - weight;
        if(weight_Difference< 0){
            weight_Difference = weight - goalWeight;
        }
        String user_Weight = String.valueOf(weight);
        String userGoalWeight = String.valueOf(goalWeight);
        String cardio = "Run Club , Yoga";
        String stress = "Yoga, Upper & Lower body Features";
        String diet = "Recipe Feature, Calorie Tracker";
        userName.setText(name);
        userAge.setText(age);
        userWeight.setText(user_Weight);
        userWeightGoal.setText(userGoalWeight);
        if(isFemale){

            defaultCaloriesAmount = 2000;

            if(isCardio){
                featureName.setText(cardio.toString().trim());
                calorieAmount.setText(String.valueOf(defaultCaloriesAmount));
                caloriesBurn_TV.setVisibility(View.INVISIBLE);
                caloriesConsume_TV.setVisibility(View.VISIBLE);
                if(weight > goalWeight || weight == goalWeight){
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                }
                else{
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                }
            }
            else if(isStress){
                featureName.setText(stress.toString().trim());
                calorieAmount.setText(String.valueOf(defaultCaloriesAmount));
                caloriesBurn_TV.setVisibility(View.INVISIBLE);
                caloriesConsume_TV.setVisibility(View.VISIBLE);
                if(weight > goalWeight && weight_Difference > 15){
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.defaultDays));
                }
                if(weight > goalWeight && weight_Difference > 25){
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                }
                else{
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.liteDays));
                }

            }
            else if(isDiet){
                featureName.setText(diet.toString().trim());
                calorieAmount.setText(String.valueOf(defaultCaloriesAmount));
                caloriesBurn_TV.setVisibility(View.INVISIBLE);
                caloriesConsume_TV.setVisibility(View.VISIBLE);
                days.setText(String.valueOf(Constants.WORKOUT_DAYS.liteDays));
            }
            else if (isLoseWeight) {

                if(weight_Difference <= 10){
                    String features = "Calorie Tracker, Upper & Lower Body";
                    featureName.setText(features.toString().trim());
                    if(score >=52 && score <= 56){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.defaultDays));
                        calorieAmount.setText(String.valueOf(defaultCaloriesAmount));
                    }
                    if(score>= 57 && score <=60){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                        calorieAmount.setText(String.valueOf(defaultCaloriesAmount));
                    }
                    else{
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        calorieAmount.setText(String.valueOf(defaultCaloriesAmount));
                    }

                }
                if(weight_Difference >10 && weight_Difference <= 25){
                    String features = "Calorie Tracker, Upper & Lower Body with Circuits";
                    featureName.setText(features.toString().trim());
                    if(score >=52 && score <= 56){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                        calorieAmount.setText(String.valueOf(defaultCaloriesAmount));
                    }
                    if(score>= 57 && score <=60){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        calorieAmount.setText(String.valueOf(calLoseWeight));
                    }
                    else{
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.defaultDays));
                        calorieAmount.setText(String.valueOf(calLoseWeight));
                    }
                }
                if(weight_Difference > 25){
                    if(score >=52 && score <= 56){
                        String features = "Calorie Tracker,Recipe,Circuit workouts, Run Club, Dietitian";
                        featureName.setText(features.toString().trim());
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.intenseDays));
                        double minIntensity = calLoseWeight - 100;
                        calorieAmount.setText(String.valueOf(minIntensity));
                    }
                    if(score>= 57 && score <=60){
                        String features = "Calorie Tracker, Circuits workouts, Recipe Feature";
                        featureName.setText(features.toString().trim());
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        double medIntensity = calLoseWeight - 150;
                        calorieAmount.setText(String.valueOf(medIntensity));
                    }
                    else{
                        String features = "Calorie Tracker, Circuits, Run Club, Yoga";
                        featureName.setText(features.toString().trim());
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                        double highIntensity = calLoseWeight - 200;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }
                }
            }
            else if (isGain) {

                if(weight_Difference <= 10){
                    String features = "Upper & Lower Body-free weights";
                    featureName.setText(features.toString().trim());
                    if(score >=7 && score <= 10){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                        calorieAmount.setText(String.valueOf(calculator));
                    }
                    if(score>= 11 && score <=12){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        double medIntensity = calculator + 50;
                        calorieAmount.setText(String.valueOf(medIntensity));
                    }
                    else{

                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMaxDays));
                        double highIntensity = calculator + 75;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }

                }
                if(weight_Difference >10 && weight_Difference <= 25){
                    String features = "Upper & Lower Body with Circuits, Yoga, Calorie Tracker";
                    featureName.setText(features.toString().trim());
                    if(score >=7 && score <= 10){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                        calorieAmount.setText(String.valueOf(calculator));
                    }
                    if(score>= 11 && score <=12){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        double medIntensity = calculator + 75;
                        calorieAmount.setText(String.valueOf(medIntensity));
                    }
                    else{
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMaxDays));
                        double highIntensity = calculator + 100;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }
                }
                if(weight_Difference > 25){
                    String features = "Calorie Tracker,Circuit workouts,Weight Lifting";
                    featureName.setText(features.toString().trim());
                    if(score >=7 && score <= 10){

                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.intenseDays));
                        double highIntensity = calculator + 100;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }
                    if(score>= 11 && score <=12){

                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        double highIntensity = calculator + 150;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }
                    else{
                        double highIntensity = calculator + 175;
                        calorieAmount.setText(String.valueOf(highIntensity));
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.intenseDays));
                    }
                }
            }
            else if(isMaintain){

                String features = "Upper & Lower Body-resistance bands, Run Club";
                featureName.setText(features.toString().trim());

                days.setText(String.valueOf(Constants.WORKOUT_DAYS.defaultDays));
                double maintain = calculator - 50;
                calorieAmount.setText(String.valueOf(maintain));
            }
            else if(isMuscle){
                String features = "Weight Lifting, Calorie Tracker";
                featureName.setText(features.toString().trim());
                if(weight_Difference <= 15){


                    if(score >=37 && score <= 39){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.defaultDays));
                        calorieAmount.setText(String.valueOf(defaultCaloriesAmount));
                    }
                    if(score>= 40 && score <=42){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                        calorieAmount.setText(String.valueOf(calculator));
                    }
                    else{

                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                        double highIntensity = calculator + 25;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }

                }
                if(weight_Difference >15 && weight_Difference <= 25){

                    if(score >=37 && score <= 39){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                        calorieAmount.setText(String.valueOf(calculator));
                    }
                    if(score>= 40 && score <=42){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        double medIntensity = calculator + 50;
                        calorieAmount.setText(String.valueOf(medIntensity));
                    }
                    else{
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        double highIntensity = calculator + 75;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }
                }
                if(weight_Difference > 25){

                    if(score >=37 && score <= 39){

                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.defaultDays));
                        double highIntensity = calculator + 100;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }
                    if(score>= 40 && score <=42){

                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        double highIntensity = calculator + 150;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }
                    else{
                        double highIntensity = calculator + 175;
                        calorieAmount.setText(String.valueOf(highIntensity));
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.intenseDays));
                    }
                }
            }
        }
        else{
            defaultCaloriesAmount = 2500;

            if(isCardio){
                featureName.setText(cardio.toString().trim());
                calorieAmount.setText(String.valueOf(defaultCaloriesAmount));
                caloriesBurn_TV.setVisibility(View.INVISIBLE);
                caloriesConsume_TV.setVisibility(View.VISIBLE);
                if(weight > goalWeight || weight == goalWeight){
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                }
                else{
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                }
            }
            else if(isStress){
                featureName.setText(stress.toString().trim());
                calorieAmount.setText(String.valueOf(defaultCaloriesAmount));
                caloriesBurn_TV.setVisibility(View.INVISIBLE);
                caloriesConsume_TV.setVisibility(View.VISIBLE);
                if(weight > goalWeight && weight_Difference > 15){
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.defaultDays));
                }
                if(weight > goalWeight && weight_Difference > 25){
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                }
                else{
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.liteDays));
                }

            }
            else if(isDiet){
                featureName.setText(diet.toString().trim());
                calorieAmount.setText(String.valueOf(defaultCaloriesAmount));
                caloriesBurn_TV.setVisibility(View.INVISIBLE);
                caloriesConsume_TV.setVisibility(View.VISIBLE);
                days.setText(String.valueOf(Constants.WORKOUT_DAYS.liteDays));
            }
            else if (isLoseWeight) {

                if(weight_Difference <= 10){
                    String features = "Calorie Tracker, Upper & Lower Body";
                    featureName.setText(features.toString().trim());
                    if(score >=52 && score <= 56){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.defaultDays));
                        calorieAmount.setText(String.valueOf(defaultCaloriesAmount));
                    }
                    if(score>= 57 && score <=60){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                        calorieAmount.setText(String.valueOf(defaultCaloriesAmount));
                    }
                    else{
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        calorieAmount.setText(String.valueOf(defaultCaloriesAmount));
                    }

                }
                if(weight_Difference >10 && weight_Difference <= 25){
                    String features = "Calorie Tracker, Upper & Lower Body with Circuits";
                    featureName.setText(features.toString().trim());
                    if(score >=52 && score <= 56){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                        calorieAmount.setText(String.valueOf(defaultCaloriesAmount));
                    }
                    if(score>= 57 && score <=60){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        calorieAmount.setText(String.valueOf(calLoseWeight));
                    }
                    else{
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.defaultDays));
                        calorieAmount.setText(String.valueOf(calLoseWeight));
                    }
                }
                if(weight_Difference > 25){
                    if(score >=52 && score <= 56){
                        String features = "Calorie Tracker,Recipe,Circuit workouts, Run Club, Dietitian";
                        featureName.setText(features.toString().trim());
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.intenseDays));
                        double minIntensity = calLoseWeight - 100;
                        calorieAmount.setText(String.valueOf(minIntensity));
                    }
                    if(score>= 57 && score <=60){
                        String features = "Calorie Tracker, Circuits workouts, Recipe Feature";
                        featureName.setText(features.toString().trim());
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        double medIntensity = calLoseWeight - 150;
                        calorieAmount.setText(String.valueOf(medIntensity));
                    }
                    else{
                        String features = "Calorie Tracker, Circuits, Run Club, Yoga";
                        featureName.setText(features.toString().trim());
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                        double highIntensity = calLoseWeight - 200;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }
                }
            }
            else if (isGain) {

                if(weight_Difference <= 10){
                    String features = "Upper & Lower Body-free weights";
                    featureName.setText(features.toString().trim());
                    if(score >=7 && score <= 10){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                        calorieAmount.setText(String.valueOf(calculator));
                    }
                    if(score>= 11 && score <=12){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        double medIntensity = calculator + 50;
                        calorieAmount.setText(String.valueOf(medIntensity));
                    }
                    else{

                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMaxDays));
                        double highIntensity = calculator + 75;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }

                }
                if(weight_Difference >10 && weight_Difference <= 25){
                    String features = "Upper & Lower Body with Circuits, Yoga, Calorie Tracker";
                    featureName.setText(features.toString().trim());
                    if(score >=7 && score <= 10){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                        calorieAmount.setText(String.valueOf(calculator));
                    }
                    if(score>= 11 && score <=12){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        double medIntensity = calculator + 75;
                        calorieAmount.setText(String.valueOf(medIntensity));
                    }
                    else{
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMaxDays));
                        double highIntensity = calculator + 100;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }
                }
                if(weight_Difference > 25){
                    String features = "Calorie Tracker,Circuit workouts,Weight Lifting";
                    featureName.setText(features.toString().trim());
                    if(score >=7 && score <= 10){

                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.intenseDays));
                        double highIntensity = calculator + 100;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }
                    if(score>= 11 && score <=12){

                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        double highIntensity = calculator + 150;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }
                    else{
                        double highIntensity = calculator + 175;
                        calorieAmount.setText(String.valueOf(highIntensity));
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.intenseDays));
                    }
                }
            }
            else if(isMaintain){

                String features = "Upper & Lower Body-resistance bands, Run Club";
                featureName.setText(features.toString().trim());

                days.setText(String.valueOf(Constants.WORKOUT_DAYS.defaultDays));
                double maintain = calculator - 50;
                calorieAmount.setText(String.valueOf(maintain));
            }
            else if(isMuscle){
                String features = "Weight Lifting, Calorie Tracker";
                featureName.setText(features.toString().trim());
                if(weight_Difference <= 15){


                    if(score >=37 && score <= 39){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.defaultDays));
                        calorieAmount.setText(String.valueOf(defaultCaloriesAmount));
                    }
                    if(score>= 40 && score <=42){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                        calorieAmount.setText(String.valueOf(calculator));
                    }
                    else{

                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                        double highIntensity = calculator + 25;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }

                }
                if(weight_Difference >15 && weight_Difference <= 25){

                    if(score >=37 && score <= 39){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                        calorieAmount.setText(String.valueOf(calculator));
                    }
                    if(score>= 40 && score <=42){
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        double medIntensity = calculator + 50;
                        calorieAmount.setText(String.valueOf(medIntensity));
                    }
                    else{
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        double highIntensity = calculator + 75;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }
                }
                if(weight_Difference > 25){

                    if(score >=37 && score <= 39){

                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.defaultDays));
                        double highIntensity = calculator + 100;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }
                    if(score>= 40 && score <=42){

                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                        double highIntensity = calculator + 150;
                        calorieAmount.setText(String.valueOf(highIntensity));
                    }
                    else{
                        double highIntensity = calculator + 175;
                        calorieAmount.setText(String.valueOf(highIntensity));
                        days.setText(String.valueOf(Constants.WORKOUT_DAYS.intenseDays));
                    }
                }
            }
        }


    }

    public void calculateRegisterScore(){
        ConnectViewModel connectViewModel = new ViewModelProvider(requireActivity()).get(ConnectViewModel.class);

        int activity = connectViewModel.getUserActivityScore().getValue();
        int level = connectViewModel.getUserLevelScore().getValue();
        int goals = connectViewModel.getUserGoals().getValue();

        int userScore = goals + activity + level;
        connectViewModel.setUserRegisterScore(userScore);
    }

}