package com.fitstir.fitstirapp.ui.health.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.databinding.FragmentViewRecipeBinding;
import com.fitstir.fitstirapp.ui.health.HealthViewModel;

public class ViewRecipeFragment extends Fragment {

    private FragmentViewRecipeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HealthViewModel healthViewModel =
                new ViewModelProvider(requireActivity()).get(HealthViewModel.class);

        binding = FragmentViewRecipeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textViewRecipe;
        //healthViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

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