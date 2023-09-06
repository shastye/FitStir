package com.fitstir.fitstirapp.ui.runtracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentRunClubBinding;
import com.fitstir.fitstirapp.databinding.FragmentRunStatisticsBinding;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;


public class RunStatisticsFragment extends Fragment {

    private FragmentRunStatisticsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);

        binding = FragmentRunStatisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
}