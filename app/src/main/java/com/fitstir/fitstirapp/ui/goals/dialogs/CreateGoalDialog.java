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

import java.util.ArrayList;

public class CreateGoalDialog extends IGenericGoalDialog {

    private GoalsViewModel goalsViewModel;
    private EditText valueEditText;
    private Spinner typeSpinner;
    private TextView unitTextView;
    private final GoalTypes[] typeEnumArray = GoalTypes.values();

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

        boolean isUnique = true;
        ArrayList<Goal> goals = goalsViewModel.getGoals().getValue();
        for (Goal goal : goals) {
            if (typeEnumArray[type] == goal.getType()) {
                isUnique = false;
            }
        }

        if (isUnique) {
            Goal thisGoal = new Goal(typeEnumArray[type], value);

            // TODO: pull from database
            //       get workouts that match {type}
            //       add data to goal

            goalsViewModel.addGoal(thisGoal);

            FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
            assert authUser != null;
            DatabaseReference goalsReference = FirebaseDatabase.getInstance().getReference("GoalsData")
                    .child(authUser.getUid());
            goalsReference.child(thisGoal.getID()).setValue(thisGoal);
        } else {
            LayoutInflater inflater2 = LayoutInflater.from(requireActivity());
            View popUpView = inflater2.inflate(R.layout.dialog_generic_alert, null);
            PopupWindow popupWindow = new PopupWindow(popUpView, LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

            ((TextView) popUpView.findViewById(R.id.dialog_generic_message)).setText("There is already a goal of this type.");
            ((TextView) popUpView.findViewById(R.id.dialog_generic_continue)).setText("Only one goal of each\ntype is allowed.");
            ((Button) popUpView.findViewById(R.id.dialog_generic_cancel_button)).setVisibility(View.GONE);

            ((Button) popUpView.findViewById(R.id.dialog_generic_accept_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            popupWindow.showAtLocation(popUpView, Gravity.CENTER, 0,0);
        }
    }

    @Override
    public void onCancel() {

    }
}
