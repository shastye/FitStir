package com.fitstir.fitstirapp.ui.health.calorietracker.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.databinding.DialogChangeCalorieGoalBinding;
import com.fitstir.fitstirapp.ui.health.calorietracker.CalorieTrackerViewModel;
import com.fitstir.fitstirapp.ui.health.calorietracker.fragments.CalorieTrackerFragment;
import com.fitstir.fitstirapp.ui.utility.classes.IBasicDialog;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.fitstir.fitstirapp.ui.utility.classes.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeCalorieGoalDialog extends IBasicDialog {
    private int goal;
    private CalorieTrackerViewModel calorieTrackerViewModel;
    private EditText changedGoal;

    public ChangeCalorieGoalDialog() { }

    public static ChangeCalorieGoalDialog newInstance(int layoutID, int acceptButtonID, int cancelButtonID, int goal) {
        ChangeCalorieGoalDialog frag = new ChangeCalorieGoalDialog();

        Bundle args = new Bundle();
        args.putInt("goal", goal);
        args.putInt("layoutID", layoutID);
        args.putInt("acceptButtonID", acceptButtonID);
        args.putInt("cancelButtonID", cancelButtonID);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

        calorieTrackerViewModel = new ViewModelProvider(requireActivity()).get(CalorieTrackerViewModel.class);
        DialogChangeCalorieGoalBinding binding = DialogChangeCalorieGoalBinding.bind(requireView());

        assert getArguments() != null;
        goal = getArguments().getInt("goal");

        assert getView() != null;
        changedGoal = binding.dialogCalgoalGoal;
        TextView suggestedGoal = binding.dialogCalgoalMessageP2;

        changedGoal.setText(String.valueOf(calorieTrackerViewModel.getCalorieTrackerGoal().getValue()));
        String tSugg = calorieTrackerViewModel.getSuggestedGoal().getValue() + " calories";
        suggestedGoal.setText(tSugg);
    }

    @Override
    public void onAccept() {
        calorieTrackerViewModel.setCalorieTrackerGoal(Integer.parseInt(changedGoal.getText().toString()));

        Users user = calorieTrackerViewModel.getThisUser().getValue();
        user.setCalorieTrackerGoal(calorieTrackerViewModel.getCalorieTrackerGoal().getValue());
        calorieTrackerViewModel.setThisUser(user);

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        assert authUser != null;
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(authUser.getUid());
        userReference.setValue(calorieTrackerViewModel.getThisUser().getValue());
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        Fragment parent = getParentFragment();
        if (parent instanceof CalorieTrackerFragment) {
            ((CalorieTrackerFragment) parent).onDismiss(dialog);
        }
    }
}
