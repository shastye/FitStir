package com.fitstir.fitstirapp.ui.workouts.exercises;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentViewWorkoutBinding;
import com.fitstir.fitstirapp.ui.workouts.WorkoutsViewModel;

public class ViewWorkoutFragment extends Fragment {

    private FragmentViewWorkoutBinding binding;
    private TextView exercise, body, targets, equipments, directions;
    private ImageView image, gifURL;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        WorkoutsViewModel workoutsViewModel = new ViewModelProvider(requireActivity()).get(WorkoutsViewModel.class);

        binding = FragmentViewWorkoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        body = root.findViewById(R.id.tv_BodyPart);
        exercise = root.findViewById(R.id.view_ExerciseName);
        targets = root.findViewById(R.id.tv_Target);
        equipments = root.findViewById(R.id.tv_Equipment);
        directions = root.findViewById(R.id.tv_Directions);
        image = root.findViewById(R.id.view_Gif);

        body.setText(workoutsViewModel.getBodyPart().getValue().trim());
        exercise.setText(workoutsViewModel.getExercise().getValue().trim());
        equipments.setText(workoutsViewModel.getEquipment().getValue().trim());
        directions.setText(workoutsViewModel.getDirections().getValue().trim());
        targets.setText(workoutsViewModel.getTarget().getValue().trim());

        Glide.with(requireActivity())
                .load(workoutsViewModel.getGifURL().getValue())
                .into(image);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}