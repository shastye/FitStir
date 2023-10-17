package com.fitstir.fitstirapp.ui.health.weightloss.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.ui.goals.GoalDataPair;
import com.fitstir.fitstirapp.ui.health.weightloss.WeightLossViewModel;
import com.fitstir.fitstirapp.ui.health.weightloss.fragments.WeightLossFragment;
import com.fitstir.fitstirapp.ui.utility.classes.IGenericAlertDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DeleteConformationDialog extends IGenericAlertDialog {
    private String message;
    private WeightLossViewModel weightLossViewModel;
    private GoalDataPair data;

    public DeleteConformationDialog() { }

    public static DeleteConformationDialog newInstance(int layoutID, int acceptButtonID, int cancelButtonID, String dateString, GoalDataPair data) {
        DeleteConformationDialog frag = new DeleteConformationDialog();

        Bundle args = new Bundle();
        args.putString("dateString", dateString);
        args.putInt("layoutID", layoutID);
        args.putInt("acceptButtonID", acceptButtonID);
        args.putInt("cancelButtonID", cancelButtonID);
        frag.data = data;
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

        weightLossViewModel = new ViewModelProvider(requireActivity()).get(WeightLossViewModel.class);

        assert getArguments() != null;
        String dateString = getArguments().getString("dateString");
        message = "This will permanently delete your weight data for\n" + dateString + ".";

        assert getView() != null;

        TextView messageView = binding.dialogGenericMessage;
        messageView.setText(message);
    }

    @Override
    public void onAccept() {
        ArrayList<GoalDataPair> dataArray = weightLossViewModel.getWeightData().getValue();

        if (dataArray.remove(data)) {
            FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
            assert authUser != null;
            String goalID = weightLossViewModel.getGoalID().getValue();

            FirebaseDatabase.getInstance()
                    .getReference("GoalsData")
                    .child(authUser.getUid())
                    .child(goalID)
                    .child("data")
                    .setValue(dataArray);

            weightLossViewModel.setWeightData(dataArray);
            weightLossViewModel.getThisUser().getValue().set_Weight((int) dataArray.get(dataArray.size() - 1).second);
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
