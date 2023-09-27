package com.fitstir.fitstirapp.ui.goals.dialogs;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.goals.Goal;
import com.fitstir.fitstirapp.ui.goals.GoalsViewModel;
import com.fitstir.fitstirapp.ui.utility.classes.IGenericAlertDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        Goal goalToDelete = goalsViewModel.getClickedGoal().getValue();
        goalsViewModel.removeGoal(goalToDelete);
        goalsViewModel.setClickedGoal(null);

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        assert authUser != null;
        DatabaseReference goalsReference = FirebaseDatabase.getInstance().getReference("GoalsData")
                .child(authUser.getUid());
        goalsReference.child(goalToDelete.getID()).removeValue();

        assert getParentFragment() != null;
        Navigation.findNavController(Objects.requireNonNull(getParentFragment().requireView())).navigate(R.id.navigation_goals);
    }

    @Override
    public void onCancel() {

    }
}
