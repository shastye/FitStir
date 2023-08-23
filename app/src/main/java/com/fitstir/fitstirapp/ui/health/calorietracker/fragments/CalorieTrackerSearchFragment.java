package com.fitstir.fitstirapp.ui.health.calorietracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.databinding.FragmentCalorieTrackerSearchBinding;
import com.fitstir.fitstirapp.ui.health.calorietracker.CalorieTrackerViewModel;

public class CalorieTrackerSearchFragment extends Fragment {

    private FragmentCalorieTrackerSearchBinding binding;
    private CalorieTrackerViewModel calorieTrackerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calorieTrackerViewModel = new ViewModelProvider(this).get(CalorieTrackerViewModel.class);

        binding = FragmentCalorieTrackerSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // ADDITIONS HERE



        // END

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}