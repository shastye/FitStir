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
import com.fitstir.fitstirapp.ui.health.calorietracker.ResponseInfo;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Hint;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Parsed;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Hit;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ViewCalorieTrackerMealFragment extends Fragment {

    private FragmentViewCalorieTrackerMealBinding binding;
    private CalorieTrackerViewModel calorieTrackerViewModel;

    private MealAdapter mealAdapter;
    private RecyclerView mealDataRecyclerView;
    private TextView mealLabelTextView, mealNutrTextView, mealCalTextView;


    private DecimalFormat decimalFormat;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calorieTrackerViewModel = new ViewModelProvider(requireActivity()).get(CalorieTrackerViewModel.class);

        binding = FragmentViewCalorieTrackerMealBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // ADDITIONS HERE

        decimalFormat = new DecimalFormat("####.#");

        ArrayList<ResponseInfo> data = calorieTrackerViewModel.getClickedArray().getValue();
        float calSum = calorieTrackerViewModel.getCalorieSum().getValue();
        float carbSum = calorieTrackerViewModel.getCarbSum().getValue();
        float fatSum = calorieTrackerViewModel.getFatSum().getValue();
        float protSum = calorieTrackerViewModel.getProteinSum().getValue();

        String tNutr = "Carbs " + decimalFormat.format(carbSum) + "g \u22C5 " +
                "Fat " + decimalFormat.format(fatSum) + "g \u22c5 " +
                "Protein " + decimalFormat.format(protSum) + "g";

        mealDataRecyclerView = binding.mealSectionItemRv;
        mealDataRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        mealLabelTextView = binding.mealSectionLabel;
        mealNutrTextView = binding.mealSectionBig3;
        mealCalTextView = binding.mealSectionCalories;

        mealLabelTextView.setText(data.get(0).getMealType());
        mealNutrTextView.setText(tNutr);
        mealCalTextView.setText(String.valueOf((int) calSum));

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

        UserProfileData user = calorieTrackerViewModel.getThisUser().getValue();
        assert user != null;
        user.setCalorieTrackerGoal(calorieTrackerViewModel.getCalorieTrackerGoal().getValue());
        calorieTrackerViewModel.setThisUser(user);

        ArrayList<ResponseInfo> data = calorieTrackerViewModel.getCalorieTrackerData().getValue();

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        assert authUser != null;
        DatabaseReference dataReference = FirebaseDatabase.getInstance().
                getReference("CalorieTrackingData").child(authUser.getUid());

        for (int i = 0; i < data.size(); i++) {
            DatabaseReference dataID = dataReference.child(data.get(i).getResultID());

            dataID.child("resultID").setValue(data.get(i).getResultID());
            dataID.child("date").setValue(data.get(i).getDate());
            dataID.child("mealType").setValue(data.get(i).getMealType());
            dataID.child("quantity").setValue(data.get(i).getQuantity());

            DatabaseReference itemRef = dataID.child("item");
            if (data.get(i).getItem() instanceof Parsed) {
                itemRef.child("food").setValue(((Parsed) data.get(i).getItem().getItem()).getFood());
                itemRef.child("quantity").setValue(((Parsed) data.get(i).getItem().getItem()).getQuantity());
                itemRef.child("measure").setValue(((Parsed) data.get(i).getItem().getItem()).getMeasure());
            } else if (data.get(i).getItem() instanceof Hint) {
                itemRef.child("food").setValue(((Hint) data.get(i).getItem().getItem()).getFood());
                itemRef.child("measures").setValue(((Hint) data.get(i).getItem().getItem()).getMeasures());
            } else if (data.get(i).getItem() instanceof Hit) {
                itemRef.child("recipe").setValue(((Hit) data.get(i).getItem().getItem()).getRecipe());
                itemRef.child("_links").setValue(((Hit) data.get(i).getItem().getItem()).get_links());
            }
        }

        binding = null;
    }

    private void updateUI() {
        ArrayList<ResponseInfo> data = calorieTrackerViewModel.getClickedArray().getValue();
        mealAdapter = new MealAdapter(data);
        mealDataRecyclerView.setAdapter(mealAdapter);
    }

    private class MealHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ResponseInfo data;
        private final TextView dataLabelTextView, dataUnitsTextView, dataCalTextView;

        public MealHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.layout_calorie_tracker_data_section, parent, false));
            itemView.setOnClickListener(this);

            dataLabelTextView = itemView.findViewById(R.id.data_section_label);
            dataUnitsTextView = itemView.findViewById(R.id.data_section_units);
            dataCalTextView = itemView.findViewById(R.id.data_section_calories);
        }

        public void bind(ResponseInfo data) throws IOException {
            this.data = data;

            Parsed parsed = new Parsed();
            Hint hint = new Hint();
            Hit hit = new Hit();

            if (data.getItem() instanceof Parsed) {
                parsed = (Parsed) data.getItem();
                int amount = data.getQuantity();

                dataLabelTextView.setText(parsed.getFood().getLabel());
                String tUnits = (parsed.getQuantity() * amount) + " " + parsed.getMeasure().getLabel();
                dataUnitsTextView.setText(tUnits);
                dataCalTextView.setText(String.valueOf((int) (parsed.getFood().getNutrients().getENERC_KCAL() * amount)));
            } else if (data.getItem() instanceof Hint) {
                hint = (Hint) data.getItem();
                int servings = (int) hint.getFood().getServingsPerContainer();
                int amount = data.getQuantity();

                dataLabelTextView.setText(hint.getFood().getLabel());
                String tUnits = amount + " " + hint.getMeasures().get(0).getLabel();
                dataUnitsTextView.setText(tUnits);
                dataCalTextView.setText(String.valueOf((int) (hint.getFood().getNutrients().getENERC_KCAL() / servings * amount)));
            } else if (data.getItem() instanceof Hit) {
                hit = (Hit) data.getItem();
                int amount = data.getQuantity();
                int servings = (int) hit.getRecipe().getYield();

                dataLabelTextView.setText(hit.getRecipe().getLabel());
                String tUnits = amount + " serving(s)";
                dataUnitsTextView.setText(tUnits);
                dataCalTextView.setText(String.valueOf((int) (hit.getRecipe().getCalories() / servings * amount)));
            }
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class MealAdapter extends RecyclerView.Adapter<MealHolder> {
        private final ArrayList<ResponseInfo> dataArray;

        public MealAdapter(ArrayList<ResponseInfo> dataArray) {
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
            ResponseInfo data = this.dataArray.get(position);
            try {
                holder.bind(data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public int getItemCount() {
            return dataArray.size();
        }
    }
}