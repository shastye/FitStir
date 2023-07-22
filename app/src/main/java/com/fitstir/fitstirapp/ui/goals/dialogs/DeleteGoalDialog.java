package com.fitstir.fitstirapp.ui.goals.dialogs;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.goals.GoalsViewModel;
import com.fitstir.fitstirapp.ui.utility.classes.IGenericAlertDialog;

import java.util.Objects;

public class DeleteGoalDialog extends IGenericAlertDialog {
    private GoalsViewModel goalsViewModel;

    public DeleteGoalDialog() { }

    public static DeleteGoalDialog newInstance(int layoutID, int acceptButtonID, int cancelButtonID, String message) {
        DeleteGoalDialog frag = new DeleteGoalDialog();

        Bundle args = new Bundle();
        args.putString("message", message);
        args.putInt("layoutID", layoutID);
        args.putInt("acceptButtonID", acceptButtonID);
        args.putInt("cancelButtonID", cancelButtonID);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

        goalsViewModel = new ViewModelProvider(requireActivity()).get(GoalsViewModel.class);

        assert getArguments() != null;
        String message = getArguments().getString("message");

        assert getView() != null;
        TextView messageView = binding.dialogGenericMessage;
        messageView.setText(message);
    }

    @Override
    public void onAccept() {
        goalsViewModel.removeGoal(goalsViewModel.getClickedGoal().getValue());
        goalsViewModel.setClickedGoal(null);

        assert getParentFragment() != null;
        Navigation.findNavController(Objects.requireNonNull(getParentFragment().requireView())).navigate(R.id.navigation_goals);
    }

    @Override
    public void onCancel() {

    }
}
