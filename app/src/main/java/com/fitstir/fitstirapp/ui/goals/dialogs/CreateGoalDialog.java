package com.fitstir.fitstirapp.ui.goals.dialogs;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.ui.goals.Goal;
import com.fitstir.fitstirapp.ui.goals.GoalsViewModel;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.classes.IGenericGoalDialog;
import com.fitstir.fitstirapp.ui.utility.enums.WorkoutTypes;

public class CreateGoalDialog extends IGenericGoalDialog {

    private GoalsViewModel goalsViewModel;
    private EditText titleEditText, valueEditText;
    private Spinner typeSpinner;
    private TextView unitTextView;
    private final WorkoutTypes[] typeEnumArray = WorkoutTypes.values();

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

        goalsViewModel = new ViewModelProvider(requireActivity()).get(GoalsViewModel.class);

        assert getView() != null;

        titleEditText = binding.dialogCreateGoalTitleEditText;
        String[] spinnerOptions = new String[typeEnumArray.length];
        for (int i = 0; i < typeEnumArray.length; i++) {
            spinnerOptions[i] = typeEnumArray[i].getSpinnerTitle();
        }
        unitTextView = binding.dialogCreateGoalUnitTextView;
        typeSpinner = Methods.getSpinnerWithAdapter(requireActivity(), getView(), binding.dialogCreateGoalTypeSpinner.getId(), spinnerOptions);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unitTextView.setText(typeEnumArray[position].getImperialUnit());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        valueEditText = binding.dialogCreateGoalValueEditText;
    }

    @Override
    public void onAccept() {
        String title = titleEditText.getText().toString();
        int type = typeSpinner.getSelectedItemPosition();
        String strValue = valueEditText.getText().toString().trim();
        int value = Integer.parseInt(strValue);

        Goal thisGoal = new Goal(title, typeEnumArray[type], value);

        // TODO: pull from database
        //       get workouts that match {type}
        //       add data to goal

        goalsViewModel.addGoal(thisGoal);
    }

    @Override
    public void onCancel() {

    }
}
