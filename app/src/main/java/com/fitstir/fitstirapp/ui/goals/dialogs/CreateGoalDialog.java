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

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.goals.Goal;
import com.fitstir.fitstirapp.ui.goals.GoalDataPair;
import com.fitstir.fitstirapp.ui.goals.GoalsViewModel;
import com.fitstir.fitstirapp.ui.goals.fragments.GoalsFragment;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.classes.IGenericGoalDialog;
import com.fitstir.fitstirapp.ui.utility.enums.GoalTypes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CreateGoalDialog extends IGenericGoalDialog {

    private GoalsViewModel goalsViewModel;
    private EditText valueEditText;
    private Spinner typeSpinner;
    private TextView unitTextView;
    private ArrayList<GoalTypes> uniqueOptions;

    private Context context;
    private void setContext(Context context) { this.context = context; }

    private GoalsFragment.GoalAdapter adapter;
    private void setAdapter(GoalsFragment.GoalAdapter adapter) { this.adapter = adapter; }

    public CreateGoalDialog() { }

    public static CreateGoalDialog newInstance(int layoutID, int acceptButtonID, int cancelButtonID, Context context, GoalsFragment.GoalAdapter adapter) {
        CreateGoalDialog frag = new CreateGoalDialog();

        Bundle args = new Bundle();
        args.putInt("layoutID", layoutID);
        args.putInt("acceptButtonID", acceptButtonID);
        args.putInt("cancelButtonID", cancelButtonID);
        frag.setContext(context);
        frag.setAdapter(adapter);

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

        uniqueOptions.get(type).getDatabaseReference(FirebaseAuth.getInstance().getUid())
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Iterable<DataSnapshot> children = snapshot.getChildren();

                    Date date = null;
                    Double newData = null;
                    for (DataSnapshot child : children) {
                        try {
                            Map<String, Object> kid = (Map<String, Object>) child.getValue();
                            assert kid != null;

                            String dateString = (String) kid.get("completedDate");
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                            Calendar returnedDate = Calendar.getInstance();
                            date = format.parse(dateString);
                            returnedDate.setTime(date);

                            switch (uniqueOptions.get(type)) {
                                case RUN_CLUB_DISTANCE:
                                    newData = (Double) kid.get("totalDistance");
                                    break;
                                case RUN_CLUB_ENDURANCE:
                                    newData = (Double) kid.get("completedRunInMinutes");
                                    break;
                                default:
                                    newData = null;
                                    break;
                            }

                            if (newData != null && newData != 0.0) {
                                thisGoal.addData(date, newData);
                            }
                        } catch (ParseException | ClassCastException | NullPointerException e1) {
                            Iterable<DataSnapshot> kids = child.getChildren();

                            for (DataSnapshot kid : kids) {
                                try {
                                    Map<String, Object> baby = (Map<String, Object>) kid.getValue();
                                    assert baby != null;

                                    String dateString = (String) baby.get("date");
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    Calendar returnedDate = Calendar.getInstance();
                                    date = format.parse(dateString);
                                    returnedDate.setTime(date);

                                    String target = "";
                                    String equipment = "";
                                    switch (uniqueOptions.get(type)) {
                                        case UPPER_BODY_CALORIES:
                                            target = (String) baby.get("bodyPart");
                                            equipment = (String) baby.get("equipment");

                                            if (target.equals("Chest") || target.equals("Upper Arms") || target.equals("Lower Arms") || target.equals("Upper Body")) {
                                                if (equipment.equals("Body Weight") || equipment.equals("Resistance Band") || equipment.equals("Stability Ball")
                                                        || equipment.equals("Bosu Ball") || equipment.equals("Medicine Ball")
                                                        || equipment.equals("Kettlebell") || equipment.equals("Parallel Bar/Chair")) {
                                                    newData = (Double) ((Long) baby.get("calories_Burned")).doubleValue();
                                                }
                                            }
                                            break;
                                        case LOWER_BODY_CALORIES:
                                            target = (String) baby.get("bodyPart");
                                            equipment = (String) baby.get("equipment");

                                            if (target.equals("Upper Legs") || target.equals("Lower Legs")) {
                                                if (equipment.equals("Body Weight") || equipment.equals("Resistance Band") || equipment.equals("Pull-up Bar")
                                                        || equipment.equals("Body Weight/Assisted") || equipment.equals("Band") || equipment.equals("Kettlebell")) {
                                                    newData = (Double) ((Long) baby.get("calories_Burned")).doubleValue();
                                                }
                                            }
                                            break;
                                        case WEIGHT_LIFTING_CALORIES:
                                            target = (String) baby.get("bodyPart");
                                            equipment = (String) baby.get("equipment");

                                            if (target.equals("Chest") || target.equals("Upper Arms") || target.equals("Lower Arms") || target.equals("Upper Body")
                                                    || target.equals("Upper Legs") || target.equals("Lower Legs") || target.equals("Back") || target.equals("Upper Back")) {
                                                if (equipment.equals("Barbell") || equipment.equals("Cable") || equipment.equals("Dumbbell")
                                                        || equipment.equals("Machine") || equipment.equals("Cable Machine")
                                                        || equipment.equals("Press Machine") || equipment.equals("Smith Machine")) {
                                                    newData = (Double) ((Long) baby.get("calories_Burned")).doubleValue();
                                                }
                                            }
                                            break;
                                        case CIRCUIT_WORKOUTS_REPS:
                                            newData = (Double) ((Long) baby.get("reps")).doubleValue();
                                            break;
                                        case CIRCUIT_WORKOUTS_ENDURANCE:
                                            newData = (Double) ((Long) baby.get("duration")).doubleValue();
                                            newData = newData / 60.0;
                                            break;
                                        default:
                                            newData = null;
                                            break;
                                    }

                                    if (newData != null && newData != 0.0) {
                                        thisGoal.addData(date, newData);
                                    }
                                } catch (ParseException | ClassCastException | NullPointerException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }
                    }

                    Methods.addGoalToFirebase(refactorGoalData(thisGoal));
                    goalsViewModel.addGoal(refactorGoalData(thisGoal));
                    adapter.notifyItemInserted(adapter.getItemCount());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    @Override
    public void onCancel() {

    }

    private Goal refactorGoalData(Goal g) {
        Goal goal = new Goal(g);
        ArrayList<GoalDataPair> data = new ArrayList<>(goal.getData());
        ArrayList<GoalDataPair> refactoredData = new ArrayList<>();

        Date prev = null;
        Date curr = null;
        Double sum = 0.0;

        if (data.size() == 0 || data.size() == 1) {
            goal.setData(data);
        } else {
            for (GoalDataPair pair : data) {
                curr = pair.first;

                if (prev != null) {
                    if (data.get(data.size() - 1).equals(pair)) {
                        if (Methods.firstIsSecond(prev, curr)) {
                            sum += pair.second;
                            refactoredData.add(new GoalDataPair(prev, sum));
                        } else {
                            refactoredData.add(new GoalDataPair(prev, sum));
                            refactoredData.add(new GoalDataPair(pair));
                        }
                    } else if (Methods.firstIsSecond(prev, curr)) {
                        sum += pair.second;
                    } else {
                        refactoredData.add(new GoalDataPair(prev, sum));

                        sum = pair.second;
                        prev = curr;
                    }
                } else {
                    prev = curr;
                    sum += pair.second;
                }
            }
        }

        goal.setData(refactoredData);
        return goal;
    }
}
