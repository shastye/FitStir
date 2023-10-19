package com.fitstir.fitstirapp.ui.connect;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentQuestionBinding;
import com.fitstir.fitstirapp.databinding.FragmentQuestions2Binding;
import com.fitstir.fitstirapp.ui.utility.Constants;

import java.util.Objects;

public class Questions2Fragment extends Fragment {

    private FragmentQuestions2Binding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel = new ViewModelProvider(requireActivity()).get(ConnectViewModel.class);

        binding = FragmentQuestions2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //additional text here
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        //navigate to next questions
        ImageView toneUp = root.findViewById(R.id.button_tone_up);
        toneUp.setOnClickListener(v->{
            connectViewModel.setUserLevelScore(Constants.INTENSITY.TONE_UP);
            Navigation.findNavController(v).navigate(R.id.action_questions2Fragment_to_questions3Fragment);
        });
        ImageView bulkUp = root.findViewById(R.id.button_bulk_up);
        bulkUp.setOnClickListener(v->{
            connectViewModel.setUserLevelScore(Constants.INTENSITY.BULK_UP);
            Navigation.findNavController(v).navigate(R.id.action_questions2Fragment_to_questions3Fragment);
        });
        ImageView strength = root.findViewById(R.id.button_strength);
        strength.setOnClickListener(v->{
            connectViewModel.setUserLevelScore(Constants.INTENSITY.STRENGTHEN);
            Navigation.findNavController(v).navigate(R.id.action_questions2Fragment_to_questions3Fragment);
        });

        //back to change previous choice
        Button back1 = root.findViewById(R.id.button_back2);
        back1.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.navigation_question);
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}