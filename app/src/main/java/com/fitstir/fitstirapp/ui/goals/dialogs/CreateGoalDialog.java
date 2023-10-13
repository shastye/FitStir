package com.fitstir.fitstirapp.ui.goals.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.goals.Goal;
import com.fitstir.fitstirapp.ui.goals.GoalsViewModel;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.classes.IGenericGoalDialog;
import com.fitstir.fitstirapp.ui.utility.enums.GoalTypes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateGoalDialog extends IGenericGoalDialog {

    private GoalsViewModel goalsViewModel;
    private EditText valueEditText;
    private Spinner typeSpinner;
    private TextView unitTextView;
    private ArrayList<GoalTypes> uniqueOptions;

    private Context context;
    private void setContext(Context context) { this.context = context; }

    public CreateGoalDialog() { }

    public static CreateGoalDialog newInstance(int layoutID, int acceptButtonID, int cancelButtonID, Context context) {
        CreateGoalDialog frag = new CreateGoalDialog();

        Bundle args = new Bundle();
        args.putInt("layoutID", layoutID);
        args.putInt("acceptButtonID", acceptButtonID);
        args.putInt("cancelButtonID", cancelButtonID);
        frag.setContext(context);

        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

        goalsViewModel = new ViewModelProvider(requireActivity()).get(GoalsViewModel.class);

        assert getView() != null;

        GoalTypes[] typeEnumArray = GoalTypes.values();
        uniqueOptions = new ArrayList<>(Arrays.asList(typeEnumArray));

        ArrayList<Goal> goals = goalsViewModel.getGoals().getValue();
        for (int k = 0; k < goals.size(); k++) {
            for (int i = 0; i < typeEnumArray.length; i++) {
                if (goals.get(k).getType().equals(typeEnumArray[i])) {
                    // index i is NOT unique
                    uniqueOptions.remove(typeEnumArray[i]);
                }
            }
        }

        String[] spinnerOptions = new String[uniqueOptions.size()];
        for (int i = 0; i < uniqueOptions.size(); i++) {
            spinnerOptions[i] = uniqueOptions.get(i).getSpinnerTitle();
        }
        unitTextView = binding.dialogCreateGoalUnitTextView;

        typeSpinner = Methods.setSpinnerAdapter(requireContext(), binding.dialogCreateGoalTypeSpinner, spinnerOptions);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unitTextView.setText(uniqueOptions.get(position).getImperialUnit());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        valueEditText = binding.dialogCreateGoalValueEditText;
    }

    @Override
    public void onAccept() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        int type = typeSpinner.getSelectedItemPosition();
        String strValue = valueEditText.getText().toString().trim();

        int value;
        try {
            value = Integer.parseInt(strValue);
        } catch (NumberFormatException e) {
            value = 0;
        }

        Goal thisGoal = new Goal(uniqueOptions.get(type), value);
        goalsViewModel.addGoal(thisGoal);

        Methods.addGoalToFirebase(uniqueOptions.get(type), value);

        // TODO: pull from database
        //       get workouts that match {type}
        //       add data to goal

        // Methods.addDataToGoal(uniqueOptions.get(type), newData);
    }

    @Override
    public void onCancel() {

    }
}
