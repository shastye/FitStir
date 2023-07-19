package com.fitstir.fitstirapp.ui.goals;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fitstir.fitstirapp.databinding.DialogCreateGoalBinding;
import com.fitstir.fitstirapp.databinding.FragmentViewGoalBinding;
import com.fitstir.fitstirapp.ui.utility.IBasicAlertDialog;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.Tags;

import java.util.Objects;

public class EditGoalDialog extends IBasicAlertDialog {

    private EditText titleEditText, valueEditText;
    private TextView unitTextView;
    private final Tags.Workout_Type[] typeEnumArray = Tags.Workout_Type.values();
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

        String[] spinnerOptions = new String[typeEnumArray.length];
        for (int i = 0; i < typeEnumArray.length; i++) {
            spinnerOptions[i] = typeEnumArray[i].getSpinnerTitle();
        }
        Spinner typeSpinner = Methods.getSpinnerWithAdapter(requireActivity(), getView(), binding.dialogCreateGoalTypeSpinner.getId(), spinnerOptions);
        typeSpinner.setSelection(GoalsViewModel.clickedGoal.getType().getValue());
        typeSpinner.setEnabled(false);

        valueEditText = binding.dialogCreateGoalValueEditText;
        valueEditText.setText(String.valueOf(GoalsViewModel.clickedGoal.getValue()));

        unitTextView = binding.dialogCreateGoalUnitTextView;
        unitTextView.setText(GoalsViewModel.clickedGoal.getType().getImperialUnit());
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
