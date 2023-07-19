package com.fitstir.fitstirapp.ui.goals;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.utility.IBasicAlertDialog;
import com.fitstir.fitstirapp.ui.utility.Methods;

import java.util.Objects;

public class DeleteGoalDialog extends IBasicAlertDialog {
    private String message;
    private int messageID;

    public DeleteGoalDialog() { }

    public static DeleteGoalDialog newInstance(int _layoutID, int _acceptButtonID, int _cancelButtonID, int _messageID, String _message) {
        DeleteGoalDialog frag = new DeleteGoalDialog();

        Bundle args = new Bundle();
        args.putString("message", _message);
        args.putInt("messageID", _messageID);
        args.putInt("layoutID", _layoutID);
        args.putInt("acceptButtonID", _acceptButtonID);
        args.putInt("cancelButtonID", _cancelButtonID);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

        assert getArguments() != null;
        message = getArguments().getString("message");
        messageID = getArguments().getInt("messageID");

        assert getView() != null;

        TextView messageView = getView().findViewById(messageID);
        messageView.setText(message);
    }

    @Override
    public void onAccept() {
        GoalsViewModel.goals.remove(GoalsViewModel.clickedGoal);
        GoalsViewModel.clickedGoal = null;
        Navigation.findNavController(Objects.requireNonNull(getParentFragment().getView())).navigate(R.id.navigation_goals);
    }

    @Override
    public void onCancel() {

    }
}
