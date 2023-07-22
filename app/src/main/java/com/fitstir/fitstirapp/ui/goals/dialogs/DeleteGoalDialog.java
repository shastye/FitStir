package com.fitstir.fitstirapp.ui.goals.dialogs;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.goals.GoalsViewModel;
import com.fitstir.fitstirapp.ui.utility.classes.IBasicAlertDialog;

import java.util.Objects;

public class DeleteGoalDialog extends IBasicAlertDialog {
    private String message;
    private int messageID;
    private GoalsViewModel goalsViewModel;

    public DeleteGoalDialog() { }

    public static DeleteGoalDialog newInstance(int layoutID, int acceptButtonID, int cancelButtonID, int messageID, String message) {
        DeleteGoalDialog frag = new DeleteGoalDialog();

        Bundle args = new Bundle();
        args.putString("message", message);
        args.putInt("messageID", messageID);
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
        message = getArguments().getString("message");
        messageID = getArguments().getInt("messageID");

        assert getView() != null;

        TextView messageView = getView().findViewById(messageID);
        messageView.setText(message);
    }

    @Override
    public void onAccept() {
        goalsViewModel.removeGoal(goalsViewModel.getClickedGoal().getValue());
        goalsViewModel.setClickedGoal(null);
        Navigation.findNavController(Objects.requireNonNull(getParentFragment().getView())).navigate(R.id.navigation_goals);
    }

    @Override
    public void onCancel() {

    }
}
