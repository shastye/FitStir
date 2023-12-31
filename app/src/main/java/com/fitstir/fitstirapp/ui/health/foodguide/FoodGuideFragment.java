package com.fitstir.fitstirapp.ui.health.foodguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.databinding.FragmentFoodGuideBinding;
import com.fitstir.fitstirapp.ui.health.HealthViewModel;

public class FoodGuideFragment extends Fragment {

    private FragmentFoodGuideBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HealthViewModel healthViewModel =
                new ViewModelProvider(this).get(HealthViewModel.class);

        binding = FragmentFoodGuideBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here



        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}