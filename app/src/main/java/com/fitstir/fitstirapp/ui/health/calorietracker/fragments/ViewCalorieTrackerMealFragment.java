package com.fitstir.fitstirapp.ui.health.calorietracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentViewCalorieTrackerMealBinding;
import com.fitstir.fitstirapp.ui.health.calorietracker.CalorieTrackerViewModel;
import com.fitstir.fitstirapp.ui.health.calorietracker.DataTuple;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Parsed;

import java.util.ArrayList;

public class ViewCalorieTrackerMealFragment extends Fragment {

    private FragmentViewCalorieTrackerMealBinding binding;
    private CalorieTrackerViewModel calorieTrackerViewModel;

    private MealAdapter mealAdapter;
    private RecyclerView mealDataRecyclerView;
    private TextView mealLabelTextView, mealNutrTextView, mealCalTextView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calorieTrackerViewModel = new ViewModelProvider(requireActivity()).get(CalorieTrackerViewModel.class);

        binding = FragmentViewCalorieTrackerMealBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // ADDITIONS HERE

        ArrayList<DataTuple> data = calorieTrackerViewModel.getClickedArray().getValue();
        float calSum = calorieTrackerViewModel.getCalorieSum().getValue();
        float carbSum = calorieTrackerViewModel.getCarbSum().getValue();
        float fatSum = calorieTrackerViewModel.getFatSum().getValue();
        float protSum = calorieTrackerViewModel.getProteinSum().getValue();

        String tNutr = "Carbs " + carbSum + "g \u22C5 Fat " + fatSum + "g \u22c5 Protein " + protSum + "g";

        mealDataRecyclerView = binding.mealSectionItemRv;
        mealDataRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        mealLabelTextView = binding.mealSectionLabel;
        mealNutrTextView = binding.mealSectionBig3;
        mealCalTextView = binding.mealSectionCalories;

        mealLabelTextView.setText(data.get(0).getMealType());
        mealNutrTextView.setText(tNutr);
        mealCalTextView.setText(String.valueOf(calSum));

        // Hide dropdown button on toolbar and show correct date
        root.findViewById(R.id.calendar_toolbar_dropdown_button).setVisibility(View.GONE);
        ((TextView) root.findViewById(R.id.calendar_date_label)).setText(calorieTrackerViewModel.getDateString().getValue());

        updateUI();

        //END

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateUI() {
        ArrayList<DataTuple> data = calorieTrackerViewModel.getClickedArray().getValue();
        mealAdapter = new MealAdapter(data);
        mealDataRecyclerView.setAdapter(mealAdapter);
    }

    private class MealHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private DataTuple data;
        private final TextView dataLabelTextView, dataUnitsTextView, dataCalTextView;

        public MealHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.layout_calorie_tracker_data_section, parent, false));
            itemView.setOnClickListener(this);

            dataLabelTextView = itemView.findViewById(R.id.data_section_label);
            dataUnitsTextView = itemView.findViewById(R.id.data_section_units);
            dataCalTextView = itemView.findViewById(R.id.data_section_calories);
        }

        public void bind(DataTuple data) {
            this.data = data;

            Parsed parsed = data.getItem();

            dataLabelTextView.setText(parsed.getFood().getLabel());
            String tUnits = parsed.getQuantity() + " " + parsed.getMeasure().getLabel();
            dataUnitsTextView.setText(tUnits);
            dataCalTextView.setText(String.valueOf(parsed.getFood().getNutrients().getENERC_KCAL()));
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class MealAdapter extends RecyclerView.Adapter<MealHolder> {
        private final ArrayList<DataTuple> dataArray;

        public MealAdapter(ArrayList<DataTuple> dataArray) {
            this.dataArray = dataArray;
        }

        @NonNull
        @Override
        public ViewCalorieTrackerMealFragment.MealHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(requireActivity());
            return new MealHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MealHolder holder, int position) {
            DataTuple data = this.dataArray.get(position);
            holder.bind(data);
        }

        @Override
        public int getItemCount() {
            return dataArray.size();
        }
    }
}