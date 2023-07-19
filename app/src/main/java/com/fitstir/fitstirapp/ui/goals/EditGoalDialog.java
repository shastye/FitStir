package com.fitstir.fitstirapp.ui.goals;

import android.os.Bundle;
import android.widget.EditText;

import com.fitstir.fitstirapp.databinding.DialogCreateGoalBinding;
import com.fitstir.fitstirapp.databinding.FragmentViewGoalBinding;
import com.fitstir.fitstirapp.ui.utility.IBasicAlertDialog;

import java.util.Objects;

public class EditGoalDialog extends IBasicAlertDialog {

    private EditText titleEditText, typeEditText, valueEditText;
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

        assert getView() != null;
        DialogCreateGoalBinding binding = DialogCreateGoalBinding.bind(getView());

        titleEditText = binding.dialogCreateGoalTitleEditText;
        titleEditText.setText(GoalsViewModel.clickedGoal.getName());

        typeEditText = binding.dialogCreateGoalTypeEditText;
        typeEditText.setText(GoalsViewModel.clickedGoal.getType());
        typeEditText.setEnabled(false);

        valueEditText = binding.dialogCreateGoalValueEditText;
        valueEditText.setText(String.valueOf(GoalsViewModel.clickedGoal.getValue()));
    }

    @Override
    public void onAccept() {
        String title = titleEditText.getText().toString();
        String strValue = valueEditText.getText().toString().trim();

        int value = Integer.parseInt(strValue);

        int index = GoalsViewModel.goals.indexOf(GoalsViewModel.clickedGoal);
        GoalsViewModel.goals.remove(GoalsViewModel.clickedGoal);
        GoalsViewModel.clickedGoal.setName(title);
        GoalsViewModel.clickedGoal.setValue(value);

        baseFragment.bind();

        GoalsViewModel.goals.add(index, GoalsViewModel.clickedGoal);
    }

    @Override
    public void onCancel() {

    }
}
