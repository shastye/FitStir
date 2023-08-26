package com.fitstir.fitstirapp.ui.workouts.exerciseapi.circuits;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentUpperBodyBinding;
import com.fitstir.fitstirapp.databinding.FragmentViewCircuitBinding;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;

public class ViewCircuitFragment extends Fragment {
    private FragmentViewCircuitBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { WorkoutsViewModel workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);

        binding = FragmentViewCircuitBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here


        return root;
    }
}