package com.fitstir.fitstirapp.ui.health.calorietracker.dialogs;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.databinding.DialogGenericCalorieTrackerItemBinding;
import com.fitstir.fitstirapp.ui.health.calorietracker.CalorieTrackerViewModel;
import com.fitstir.fitstirapp.ui.health.calorietracker.ResponseInfo;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Hint;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Parsed;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Hit;
import com.fitstir.fitstirapp.ui.utility.classes.IBasicDialog;

import java.text.DecimalFormat;

public class AddSearchInfoDialog extends IBasicDialog {
    private CalorieTrackerViewModel calorieTrackerViewModel;
    private DecimalFormat decimalFormat;

    private TextView label, measure, calories, nutrients;
    private EditText resultID, date, quantity;
    private Spinner mealType;

    public AddSearchInfoDialog() { }

    public static AddSearchInfoDialog newInstance(int layoutID, int acceptButtonID, int cancelButtonID) {
        AddSearchInfoDialog frag = new AddSearchInfoDialog();

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

        calorieTrackerViewModel = new ViewModelProvider(requireActivity()).get(CalorieTrackerViewModel.class);
        DialogGenericCalorieTrackerItemBinding binding = DialogGenericCalorieTrackerItemBinding.bind(requireView());

        assert getArguments() != null;

        assert getView() != null;

        decimalFormat = new DecimalFormat("####.#");

        label = binding.dialogLabel;
        measure = binding.dialogMeasure;
        calories = binding.dialogCalories;
        nutrients = binding.dialogNutrients;
        resultID = binding.dialogResultID;
        date = binding.dialogDate;
        mealType = binding.dialogMealTypeSpinner;
        quantity = binding.dialogQuantity;

        ResponseInfo response = calorieTrackerViewModel.getClickedResult().getValue();

        int index = response.getResultID().indexOf("&");
        String tID = response.getResultID().substring(0, index);
        resultID.setText(tID);

        float calSum = calorieTrackerViewModel.getCalorieSum().getValue();
        float carbSum = calorieTrackerViewModel.getCarbSum().getValue();
        float fatSum = calorieTrackerViewModel.getFatSum().getValue();
        float protSum = calorieTrackerViewModel.getProteinSum().getValue();

        String tNutr = "Carbs " + decimalFormat.format(carbSum) + "g \u22C5 " +
                "Fat " + decimalFormat.format(fatSum) + "g \u22c5 " +
                "Protein " + decimalFormat.format(protSum) + "g";
        String tCal = ((int) calSum) + " calories / serving";

        calories.setText(tCal);
        nutrients.setText(tNutr);

        if (response.getItem() instanceof Parsed) {
            Parsed parsed = (Parsed) response.getItem();
            int servings = (int) parsed.getFood().getServingsPerContainer();
            int amount = response.getQuantity();

            if (servings == 0) {
                servings = 1;
            }
            if (amount == 0) {
                amount = 1;
            }

            label.setText(parsed.getFood().getLabel());
            String tUnits = (parsed.getQuantity() / servings * amount) + " " + parsed.getMeasure().getLabel();
            measure.setText(tUnits);
        } else if (response.getItem() instanceof Hint) {
            Hint hint = (Hint) response.getItem();
            int servings = (int) hint.getFood().getServingsPerContainer();
            int amount = response.getQuantity();

            if (servings == 0) {
                servings = 1;
            }
            if (amount == 0) {
                amount = 1;
            }

            label.setText(hint.getFood().getLabel());
            String tUnits = amount + " " + hint.getMeasures().get(0).getLabel();
            measure.setText(tUnits);
        } else if (response.getItem() instanceof Hit) {
            Hit hit = (Hit) response.getItem();
            int amount = response.getQuantity();
            int servings = (int) hit.getRecipe().getYield();

            if (servings == 0) {
                servings = 1;
            }
            if (amount == 0) {
                amount = 1;
            }

            label.setText(hit.getRecipe().getLabel());
            String tUnits = amount + " serving(s)";
            measure.setText(tUnits);
        }
    }

    @Override
    public void onAccept() {

    }

    @Override
    public void onCancel() {

    }
}
