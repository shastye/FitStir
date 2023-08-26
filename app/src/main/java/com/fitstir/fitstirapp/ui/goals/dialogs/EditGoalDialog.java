package com.fitstir.fitstirapp.ui.goals.dialogs;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.ui.goals.Goal;
import com.fitstir.fitstirapp.ui.goals.GoalsViewModel;
import com.fitstir.fitstirapp.ui.goals.fragments.ViewGoalFragment;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.classes.IGenericGoalDialog;
import com.fitstir.fitstirapp.ui.utility.enums.WorkoutTypes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditGoalDialog extends IGenericGoalDialog {

    private GoalsViewModel goalsViewModel;
    private EditText titleEditText, valueEditText;
    private TextView unitTextView;
    private final WorkoutTypes[] typeEnumArray = WorkoutTypes.values();
    private ViewGoalFragment baseFragment;

    public void setBaseFragment(ViewGoalFragment fragment) { baseFragment = fragment; }

    public EditGoalDialog() { }

    public static EditGoalDialog newInstance(int layoutID, int acceptButtonID, int cancelButtonID, ViewGoalFragment fragment) {
        EditGoalDialog frag = new EditGoalDialog();

        Bundle args = new Bundle();
        args.putInt("layoutID", layoutID);
        args.putInt("acceptButtonID", acceptButtonID);
        args.putInt("cancelButtonID", cancelButtonID);
        frag.setBaseFragment(fragment);

        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

        goalsViewModel = new ViewModelProvider(requireActivity()).get(GoalsViewModel.class);

        assert getView() != null;

        titleEditText = binding.dialogCreateGoalTitleEditText;
        titleEditText.setText(goalsViewModel.getClickedGoal().getValue().getName());
        titleEditText.setEnabled(false);

        String[] spinnerOptions = new String[typeEnumArray.length];
        for (int i = 0; i < typeEnumArray.length; i++) {
            spinnerOptions[i] = typeEnumArray[i].getSpinnerTitle();
        }
        Spinner typeSpinner = Methods.getSpinnerWithAdapter(requireActivity(), getView(), binding.dialogCreateGoalTypeSpinner.getId(), spinnerOptions);
        typeSpinner.setSelection(goalsViewModel.getClickedGoal().getValue().getType().getValue());
        typeSpinner.setEnabled(false);

        valueEditText = binding.dialogCreateGoalValueEditText;
        valueEditText.setText(String.valueOf(goalsViewModel.getClickedGoal().getValue().getValue()));

        unitTextView = binding.dialogCreateGoalUnitTextView;
        unitTextView.setText(goalsViewModel.getClickedGoal().getValue().getType().getImperialUnit());
    }

    @Override
    public void onAccept() {
        String title = titleEditText.getText().toString();
        String strValue = valueEditText.getText().toString().trim();

        int value = Integer.parseInt(strValue);

        Goal clickedGoal = goalsViewModel.getClickedGoal().getValue();
        int index = goalsViewModel.getGoals().getValue().indexOf(clickedGoal);
        goalsViewModel.getGoals().getValue().get(index).setName(title);
        goalsViewModel.getGoals().getValue().get(index).setValue(value);

        if (clickedGoal.getName().equals("Weight Goal")) {
            goalsViewModel.getThisUser().getValue().setGoal_weight(value);

            FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(authUser.getUid())
                    .child("goal_weight")
                    .setValue(value);
        }

        baseFragment.bind();
    }

    @Override
    public void onCancel() {

    }
}
