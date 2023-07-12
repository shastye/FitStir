package com.fitstir.fitstirapp.ui.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentGoalsBinding;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.Tags;

public class GoalsFragment extends Fragment {

    private FragmentGoalsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GoalsViewModel goalsViewModel =
                new ViewModelProvider(this).get(GoalsViewModel.class);

        binding = FragmentGoalsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGoals;
        goalsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Addition Text Here

        Button notify = root.findViewById(R.id.notify_button);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Methods.createNotification(
                        requireActivity(),
                        Tags.Reminder_Channel_ID.WORKOUT_REMINDERS.getValue(),
                        R.drawable.ic_bicep_black_200dp,
                        "Testing",
                        "This is a test.",
                        "This is still a test..........................",
                        R.id.navigation_workouts);
            }
        });

        // End
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}