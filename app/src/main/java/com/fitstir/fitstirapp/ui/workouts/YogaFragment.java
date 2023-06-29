package com.fitstir.fitstirapp.ui.workouts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.databinding.FragmentYogaBinding;

public class YogaFragment extends Fragment {

    private FragmentYogaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WorkoutsViewModel workoutsViewModel =
                new ViewModelProvider(this).get(WorkoutsViewModel.class);

        binding = FragmentYogaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textYoga;
        //workoutsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

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