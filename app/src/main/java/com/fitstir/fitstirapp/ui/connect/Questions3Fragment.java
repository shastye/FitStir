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

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentQuestions2Binding;
import com.fitstir.fitstirapp.databinding.FragmentQuestions3Binding;

import java.util.Objects;


public class Questions3Fragment extends Fragment {

    private FragmentQuestions3Binding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel =
                new ViewModelProvider(this).get(ConnectViewModel.class);

        binding = FragmentQuestions3Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //additional text here



        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        //navigate to next questions
        Button nonActive = root.findViewById(R.id.button_non_active);
        nonActive.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_questions3Fragment_to_questions4Fragment);
        });
        Button lightActive = root.findViewById(R.id.button_light_active);
        lightActive.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_questions3Fragment_to_questions4Fragment);
        });
        Button active = root.findViewById(R.id.button_active);
        active.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_questions3Fragment_to_questions4Fragment);
        });

        //back to change previous choice
        Button back = root.findViewById(R.id.button_back3);
        back.setOnClickListener(v->{
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
