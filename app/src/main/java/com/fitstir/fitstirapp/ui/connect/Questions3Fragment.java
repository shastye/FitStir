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
import com.fitstir.fitstirapp.databinding.FragmentQuestions2Binding;
import com.fitstir.fitstirapp.databinding.FragmentQuestions3Binding;
import com.fitstir.fitstirapp.ui.utility.Constants;

import java.util.Objects;


public class Questions3Fragment extends Fragment {

    private FragmentQuestions3Binding binding;
    private int non_Active = Constants.ACTIVITY.NON_ACTIVE;
    private int light_Active = Constants.ACTIVITY.LIGHT_ACTIVE;
    private int activeScore = Constants.ACTIVITY.ACTIVE;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel = new ViewModelProvider(requireActivity()).get(ConnectViewModel.class);

        binding = FragmentQuestions3Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //additional text here


        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        //navigate to next questions
        ImageView nonActive = root.findViewById(R.id.button_non_active);
        nonActive.setOnClickListener(v->{
            connectViewModel.setActiveScore(1.375);
            connectViewModel.setUserActivityScore(non_Active);
            Navigation.findNavController(v).navigate(R.id.action_questions3Fragment_to_questions4Fragment);
        });
        ImageView lightActive = root.findViewById(R.id.button_light_active);
        lightActive.setOnClickListener(v->{

            connectViewModel.setActiveScore(1.55);
            connectViewModel.setUserActivityScore(light_Active);
            Navigation.findNavController(v).navigate(R.id.action_questions3Fragment_to_questions4Fragment);
        });
        ImageView active = root.findViewById(R.id.button_active);
        active.setOnClickListener(v->{

            connectViewModel.setActiveScore(1.725);
            connectViewModel.setUserActivityScore(activeScore);
            Navigation.findNavController(v).navigate(R.id.action_questions3Fragment_to_questions4Fragment);
        });

        //back to change previous choice
        Button back = root.findViewById(R.id.button_back3);
        back.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.navigation_question);
        });

        return root;
    }

}
