package com.fitstir.fitstirapp.ui.connect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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


        return root;
    }
    public void getWorkoutPlan(){

        ConnectViewModel connectViewModel = new ViewModelProvider(requireActivity()).get(ConnectViewModel.class);

        boolean isCardio = Boolean.TRUE.equals(connectViewModel.getIsCardioGoal().getValue());
        boolean isStress = Boolean.TRUE.equals(connectViewModel.getIsManageStress().getValue());
        boolean isLoseWeight = Boolean.TRUE.equals(connectViewModel.getIsLoseWeight().getValue());
        boolean isDiet = Boolean.TRUE.equals(connectViewModel.getIsDiet().getValue());

        String name = connectViewModel.getUserName().getValue();
        String age = connectViewModel.getAge().getValue();
        int score = connectViewModel.getUserRegisterScore().getValue();
        int weight = Integer.parseInt(Objects.requireNonNull(connectViewModel.getWeight().getValue()));
        int goalWeight = Integer.parseInt(Objects.requireNonNull(connectViewModel.getGoalWeight().getValue()));
        int defaultCaloriesAmount = 2500;
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
                if(score >=52 && score <= 55){
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.defaultDays));
                }
                if(score>= 54 && score <=57){
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                }
                else{
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                }

            }
            if(weight_Difference >10 && weight_Difference <= 25){
                String features = "Calorie Tracker, Upper & Lower Body with Circuits";
                featureName.setText(features.toString().trim());
                if(score >=52 && score <= 55){
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
                }
                if(score>= 54 && score <=57){
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                }
                else{
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.defaultDays));
                }
            }
            if(weight_Difference > 25){
                if(score >=52 && score <= 55){
                    String features = "Calorie Tracker,Recipe,Circuit workouts, Run Club, Dietitian";
                    featureName.setText(features.toString().trim());
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.intenseDays));
                }
                if(score>= 54 && score <=57){
                    String features = "Calorie Tracker, Circuits workouts, Recipe Feature";
                    featureName.setText(features.toString().trim());
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMedDays));
                }
                else{
                    String features = "Calorie Tracker, Circuits, Run Club, Yoga";
                    featureName.setText(features.toString().trim());
                    days.setText(String.valueOf(Constants.WORKOUT_DAYS.loseMinDays));
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