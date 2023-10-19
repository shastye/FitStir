package com.fitstir.fitstirapp.ui.connect;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.MainActivity;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentQuestionBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;

import java.util.Objects;

public class QuestionFragment extends Fragment {

    private FragmentQuestionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel =
                new ViewModelProvider(requireActivity()).get(ConnectViewModel.class);

        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        // navigate to correct message
        Button loseWeight = root.findViewById(R.id.button_lose_weight);
        loseWeight.setOnClickListener(v -> {
            connectViewModel.setUserGoals(Constants.GOAL.LOSE_WEIGHT);
            connectViewModel.setIsLoseWeight(true);
            Navigation.findNavController(v).navigate(R.id.action_navigation_question_to_messageBoard2Fragment2);
        });
        Button gainWeight = root.findViewById(R.id.button_gain_weight);
        gainWeight.setOnClickListener(v -> {
            connectViewModel.setIsGain(true);
            connectViewModel.setUserGoals(Constants.GOAL.GAIN_WEIGHT);
            Navigation.findNavController(v).navigate(R.id.action_navigation_question_to_messageBoardFragment2);
        });
        Button maintain = root.findViewById(R.id.button_Maintain_weight);
        maintain.setOnClickListener(v -> {
            connectViewModel.setIsMaintain(true);
            connectViewModel.setUserGoals(Constants.GOAL.MAINTAIN);
            Navigation.findNavController(v).navigate(R.id.action_navigation_question_to_messageBoardFragment2);
        });
        Button gainMuscle = root.findViewById(R.id.button_gain_muscle);
        gainMuscle.setOnClickListener(v -> {
            connectViewModel.setIsGainMuscle(true);
            connectViewModel.setUserGoals(Constants.GOAL.GAIN_MUSCLE);
            Navigation.findNavController(v).navigate(R.id.action_navigation_question_to_messageBoardFragment2);
        });
        Button diet = root.findViewById(R.id.button_modify_diet);
        diet.setOnClickListener(v -> {
            connectViewModel.setUserGoals(Constants.GOAL.MODIFY_DIET);
            connectViewModel.setIsDiet(true);
            Navigation.findNavController(v).navigate(R.id.action_navigation_question_to_messageBoard2Fragment2);
        });
        Button stress = root.findViewById(R.id.button_manage_stress);
        stress.setOnClickListener(v -> {
            connectViewModel.setIsManageStress(true);
            connectViewModel.setUserGoals(Constants.GOAL.MANAGE_STRESS);
            Navigation.findNavController(v).navigate(R.id.action_navigation_question_to_messagBoard3Fragment);
        });
        Button cardio = root.findViewById(R.id.button_cardio);
        cardio.setOnClickListener(v -> {
            connectViewModel.setIsCardioGoal(true);
            connectViewModel.setUserGoals(Constants.GOAL.INCREASE_CARDIO);
            Navigation.findNavController(v).navigate(R.id.action_navigation_question_to_messagBoard3Fragment);
        });

        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}