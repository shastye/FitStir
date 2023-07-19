package com.fitstir.fitstirapp.ui.goals;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.ui.utility.IBasicAlertDialog;
import com.fitstir.fitstirapp.databinding.DialogCreateGoalBinding;

public class CreateGoalDialog extends IBasicAlertDialog {

    private EditText titleEditText, typeEditText, valueEditText;
    private GoalsViewModel goalsViewModel;

    public CreateGoalDialog() { }

    public static CreateGoalDialog newInstance(int layoutID, int acceptButtonID, int cancelButtonID) {
        CreateGoalDialog frag = new CreateGoalDialog();

        Bundle args = new Bundle();
        args.putInt("layoutID", layoutID);
        args.putInt("acceptButtonID", acceptButtonID);
        args.putInt("cancelButtonID", cancelButtonID);

        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

        assert getView() != null;
        DialogCreateGoalBinding binding = DialogCreateGoalBinding.bind(getView());

        titleEditText = binding.dialogCreateGoalTitleEditText;
        typeEditText = binding.dialogCreateGoalTypeEditText;
        valueEditText = binding.dialogCreateGoalValueEditText;
    }

    @Override
    public void onAccept() {
        String title = titleEditText.getText().toString();
        String type = typeEditText.getText().toString();
        String strValue = valueEditText.getText().toString().trim();
        int value = Integer.parseInt(strValue);

        Goal thisGoal = new Goal(title, type, value);

        // TODO: pull from database
        //       get workouts that match {type}
        //       add data to goal

        GoalsViewModel.goals.add(thisGoal);
    }

    @Override
    public void onCancel() {

    }
}
