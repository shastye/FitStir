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

public class UpdateWeightDialog extends IBasicDialog {
    private int currWeight;
    private WeightLossViewModel weightLossViewModel;
    private EditText changedWeight;

    public UpdateWeightDialog() { }

    public static UpdateWeightDialog newInstance(int layoutID, int acceptButtonID, int cancelButtonID, int currWeight) {
        UpdateWeightDialog frag = new UpdateWeightDialog();

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
        title.setText("Update Current Weight:");

        String tUnit = "pounds";
        if (user.getUnitID() == 1) {
            tUnit = "kilograms";
        }
        String tCurrWeight = currWeight + " " + tUnit;
        String tP1 = "Your current weight is";
        binding.dialogCalgoalMessageP1.setText(tP1);

        String tP2 = currWeight + " " + tUnit;
        binding.dialogCalgoalMessageP2.setText(tP2);

        changedWeight = binding.dialogCalgoalGoal;
        changedWeight.setHint(String.valueOf(currWeight));

        binding.dialogCalgoalUnit.setText(tUnit);
    }

    @Override
    public void onAccept() {
        UserProfileData user = weightLossViewModel.getThisUser().getValue();

        int newWeight = Integer.parseInt(changedWeight.getText().toString());
        if (newWeight != currWeight) {

            // Determine if movement in correct direction

            int d1 = currWeight - newWeight;
            // if d1 < 0; curr < newWeight
            // elseif d1 > 0; newWeight < curr
            int d2 = user.getGoal_weight() - newWeight;
            // if d2 < 0; goal < newWeight
            // elseif d2 > 0; newWeight < goal

            boolean c1 = d2 > 0 && d1 < 0;
            boolean c2 = d2 < 0 && d1 > 0;
            // Correct direction if:
            // curr < newWeight < goal
            //          OR
            // goal < newWeight < curr



            user.set_Weight(newWeight);

            FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
            assert authUser != null;

            user.addWeightData(newWeight);
            user.set_Weight(newWeight);

            weightLossViewModel.setThisUser(user);

            int index = weightLossViewModel.getWeightData().getValue().size() - 1;
            weightLossViewModel.getWeightData().getValue().get(index).second = newWeight;



            if (c1 || c2) {
                // TODO: SHOW CONGRATS for getting closer to goal
            } else {
                // TODO: SHOW CONGRATS for reaching goal
            }
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
