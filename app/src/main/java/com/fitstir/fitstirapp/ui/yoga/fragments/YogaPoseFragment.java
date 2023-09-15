package com.fitstir.fitstirapp.ui.yoga.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentYogaPoseBinding;
import com.fitstir.fitstirapp.ui.health.HealthViewModel;
import com.fitstir.fitstirapp.ui.yoga.models.YogaViewModel;


public class YogaPoseFragment extends Fragment {

    private FragmentYogaPoseBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Call  yoga view model
        YogaViewModel yogaView = new ViewModelProvider(this).get(YogaViewModel.class);

        // Inflate the layout for this fragment
        binding = FragmentYogaPoseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        return root;
    }
}