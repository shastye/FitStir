package com.fitstir.fitstirapp.ui.goals;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.databinding.DialogCreateGoalBinding;
import com.fitstir.fitstirapp.ui.utility.IBasicAlertDialog;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.Constants;

public class EditGoalDialog extends IBasicAlertDialog {

    private GoalsViewModel goalsViewModel;
    private EditText titleEditText, valueEditText;
    private TextView unitTextView;
    private final Constants.Workout_Type[] typeEnumArray = Constants.Workout_Type.values();
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
        DialogCreateGoalBinding binding = DialogCreateGoalBinding.bind(getView());

        titleEditText = binding.dialogCreateGoalTitleEditText;
        titleEditText.setText(goalsViewModel.getClickedGoal().getValue().getName());

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

        int index = goalsViewModel.getGoals().getValue().indexOf(goalsViewModel.getClickedGoal().getValue());
        goalsViewModel.getGoals().getValue().get(index).setName(title);
        goalsViewModel.getGoals().getValue().get(index).setValue(value);

        baseFragment.bind();
    }

    @Override
    public void onCancel() {

    }
}
