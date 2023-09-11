package com.fitstir.fitstirapp.ui.health.weightloss.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.databinding.DialogChangeCalorieGoalBinding;
import com.fitstir.fitstirapp.ui.health.weightloss.WeightLossViewModel;
import com.fitstir.fitstirapp.ui.health.weightloss.fragments.WeightLossFragment;
import com.fitstir.fitstirapp.ui.utility.classes.IBasicDialog;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeWeightGoalDialog extends IBasicDialog {
    private int currWeight;
    private WeightLossViewModel weightLossViewModel;
    private EditText changedGoal;

    public ChangeWeightGoalDialog() { }

    public static ChangeWeightGoalDialog newInstance(int layoutID, int acceptButtonID, int cancelButtonID, int currWeight) {
        ChangeWeightGoalDialog frag = new ChangeWeightGoalDialog();

        Bundle args = new Bundle();
        args.putInt("currWeight", currWeight);
        args.putInt("layoutID", layoutID);
        args.putInt("acceptButtonID", acceptButtonID);
        args.putInt("cancelButtonID", cancelButtonID);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

        weightLossViewModel = new ViewModelProvider(requireActivity()).get(WeightLossViewModel.class);
        DialogChangeCalorieGoalBinding binding = DialogChangeCalorieGoalBinding.bind(requireView());

        UserProfileData user = weightLossViewModel.getThisUser().getValue();

        assert getArguments() != null;
        currWeight = getArguments().getInt("currWeight");

        assert getView() != null;

        TextView title = binding.dialogCalgoalTitle;
        title.setText("Change Weight Goal:");

        String tUnit = "pounds";
        if (user.getUnitID() == 1) {
            tUnit = "kilograms";
        }
        String tCurrWeight = currWeight + " " + tUnit;
        String tP1 = "Your current weight is\n" + tCurrWeight
                + "\nwith a goal of";
        binding.dialogCalgoalMessageP1.setText(tP1);

        int goalWeight = user.getGoal_weight();
        String tP2 = goalWeight + " " + tUnit;
        binding.dialogCalgoalMessageP2.setText(tP2);

        changedGoal = binding.dialogCalgoalGoal;
        changedGoal.setHint(String.valueOf(goalWeight));

        binding.dialogCalgoalUnit.setText(tUnit);
    }

    @Override
    public void onAccept() {
        UserProfileData user = weightLossViewModel.getThisUser().getValue();
        int newGoal = Integer.parseInt(changedGoal.getText().toString());
        if (newGoal != user.getGoal_weight()) {
            user.setGoal_weight(newGoal);

            FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
            assert authUser != null;
            String goalID = weightLossViewModel.getGoalID().getValue();

            user.updateWeightGoal(newGoal);

            weightLossViewModel.setThisUser(user);
            weightLossViewModel.setWeightGoal(newGoal);
        }
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        Fragment parent = getParentFragment();
        if (parent instanceof WeightLossFragment) {
            ((WeightLossFragment) parent).onDismiss(dialog);
        }
    }
}
