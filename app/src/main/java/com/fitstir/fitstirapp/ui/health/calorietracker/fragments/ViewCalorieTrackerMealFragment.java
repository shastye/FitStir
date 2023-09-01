package com.fitstir.fitstirapp.ui.health.calorietracker.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.DialogGenericCalorieTrackerItemBinding;
import com.fitstir.fitstirapp.databinding.FragmentViewCalorieTrackerMealBinding;
import com.fitstir.fitstirapp.ui.health.calorietracker.CalorieTrackerViewModel;
import com.fitstir.fitstirapp.ui.health.calorietracker.ResponseInfo;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.MealType;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Hint;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Nutrients;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Parsed;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Hit;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.TotalNutrients;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

        mealDataRecyclerView = binding.mealSectionItemRv;
        mealDataRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        mealLabelTextView = binding.mealSectionLabel;
        mealNutrTextView = binding.mealSectionBig3;
        mealCalTextView = binding.mealSectionCalories;

        // Hide dropdown button on toolbar and show correct date
        root.findViewById(R.id.calendar_toolbar_dropdown_button).setVisibility(View.GONE);
        ((TextView) root.findViewById(R.id.calendar_date_label)).setText(calorieTrackerViewModel.getDateString().getValue());

        updateUI();

        //END

        return root;
    }

    private void saveData(ResponseInfo data) {
        if (data != null) {
            FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
            assert authUser != null;
            DatabaseReference calReference = FirebaseDatabase.getInstance().
                getReference("CalorieTrackingData").child(authUser.getUid());

            String id = "";
            if (data.getItem() instanceof Parsed) {
                id = ((Parsed) data.getItem()).getFood().getFoodId();
                int index = id.indexOf("food_");
                if (index != -1) {
                    index += 5;
                    id = id.substring(index);
                }
            } else if (data.getItem() instanceof Hint) {
                id = ((Hint) data.getItem()).getFood().getFoodId();
                int index = id.indexOf("food_");
                if (index != -1) {
                    index += 5;
                    id = id.substring(index);
                }
            } else if (data.getItem() instanceof Hit) {
                id = ((Hit) data.getItem()).getRecipe().getUri();
                int index = id.indexOf("recipe_");
                if (index != -1) {
                    index += 7;
                    id = id.substring(index);
                }
            }

            String collID = id + "&&&&&";
            Calendar dataDate = data.getDate();
            collID += dataDate.get(Calendar.YEAR);
            collID += "-";
            collID += dataDate.get(Calendar.MONTH);
            collID += "-";
            collID += dataDate.get(Calendar.DATE);
            collID += "*";
            collID += dataDate.get(Calendar.HOUR_OF_DAY);
            collID += "-";
            collID += dataDate.get(Calendar.MINUTE);
            collID += "-";
            collID += dataDate.get(Calendar.SECOND);
            collID += "-";
            collID += dataDate.get(Calendar.MILLISECOND);

            DatabaseReference dataID = calReference.child(collID);
            dataID.child("resultID").setValue(id);
            dataID.child("date").setValue(data.getDate());
            dataID.child("mealType").setValue(data.getMealType());
            dataID.child("quantity").setValue(data.getQuantity());

            DatabaseReference itemRef = dataID.child("item");
            if (data.getItem() instanceof Parsed) {
                itemRef.child("food").setValue(((Parsed) data.getItem().getItem()).getFood());
                itemRef.child("quantity").setValue(((Parsed) data.getItem().getItem()).getQuantity());
                itemRef.child("measure").setValue(((Parsed) data.getItem().getItem()).getMeasure());
            } else if (data.getItem() instanceof Hint) {
                itemRef.child("food").setValue(((Hint) data.getItem().getItem()).getFood());
                itemRef.child("measures").setValue(((Hint) data.getItem().getItem()).getMeasures());
            } else if (data.getItem() instanceof Hit) {
                itemRef.child("recipe").setValue(((Hit) data.getItem().getItem()).getRecipe());
                itemRef.child("_links").setValue(((Hit) data.getItem().getItem()).get_links());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateUI() {
        ArrayList<ResponseInfo> data = calorieTrackerViewModel.getClickedArray().getValue();

        if (calorieTrackerViewModel.getClickedArray().getValue().size() == 0) {
            calorieTrackerViewModel.setClickedArray(null);
            getParentFragmentManager().popBackStackImmediate();
        } else {
            float calSum = 0.0f;
            float carbSum = 0.0f;
            float fatSum = 0.0f;
            float protSum = 0.0f;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getItem() instanceof Parsed) {
                    Parsed parsed = (Parsed) data.get(i).getItem();
                    int servings = (int) parsed.getFood().getServingsPerContainer();
                    int amount = data.get(i).getQuantity();

                    if (servings == 0) {
                        servings = 1;
                    }
                    if (amount == 0) {
                        amount = 1;
                    }

                    Nutrients nutr = parsed.getFood().getNutrients();
                    calSum += nutr.getENERC_KCAL() / servings;
                    carbSum += nutr.getCHOCDF() / servings * amount;
                    protSum += nutr.getPROCNT() / servings * amount;
                    fatSum += nutr.getFAT() / servings * amount;
                } else if (data.get(i).getItem() instanceof Hint) {
                    Hint hint = (Hint) data.get(i).getItem();
                    int servings = (int) hint.getFood().getServingsPerContainer();
                    int amount = data.get(i).getQuantity();

                    if (servings == 0) {
                        servings = 1;
                    }
                    if (amount == 0) {
                        amount = 1;
                    }

                    Nutrients nutr = hint.getFood().getNutrients();
                    calSum += nutr.getENERC_KCAL() / servings * amount;
                    carbSum += nutr.getCHOCDF() / servings * amount;
                    protSum += nutr.getPROCNT() / servings * amount;
                    fatSum += nutr.getFAT() / servings * amount;
                } else if (data.get(i).getItem() instanceof Hit) {
                    Hit hit = (Hit) data.get(i).getItem();
                    int servings = (int) hit.getRecipe().getYield();
                    int amount = data.get(i).getQuantity();

                    if (servings == 0) {
                        servings = 1;
                    }
                    if (amount == 0) {
                        amount = 1;
                    }

                    TotalNutrients nutr = hit.getRecipe().getTotalNutrients();
                    calSum += nutr.getENERC_KCAL().getQuantity() / servings * amount;
                    carbSum += nutr.getCHOCDF().getQuantity() / servings * amount;
                    protSum += nutr.getPROCNT().getQuantity() / servings * amount;
                    fatSum += nutr.getFAT().getQuantity() / servings * amount;
                }
            }

            String tNutr = "Carbs " + decimalFormat.format(carbSum) + "g \u22C5 " +
                    "Fat " + decimalFormat.format(fatSum) + "g \u22c5 " +
                    "Protein " + decimalFormat.format(protSum) + "g";

            mealLabelTextView.setText(data.get(0).getMealType());
            mealNutrTextView.setText(tNutr);
            mealCalTextView.setText(String.valueOf((int) calSum));

            mealAdapter = new MealAdapter(data);
            mealDataRecyclerView.setAdapter(mealAdapter);
        }
    }

    private class MealHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ResponseInfo data;
        private final TextView dataLabelTextView, dataUnitsTextView, dataCalTextView;
        private final LayoutInflater inflater;
        int calSum = 0;
        float carbSum = 0.0f, protSum = 0.0f, fatSum = 0.0f;

        public MealHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.layout_calorie_tracker_data_section, parent, false));
            itemView.setOnClickListener(this);

            this.inflater = inflater;

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
                int servings = (int) parsed.getFood().getServingsPerContainer();
                int amount = data.getQuantity();

                if (servings == 0) {
                    servings = 1;
                }
                if (amount == 0) {
                    amount = 1;
                }

                Nutrients nutr = parsed.getFood().getNutrients();
                calSum = (int) nutr.getENERC_KCAL() / servings;
                carbSum = nutr.getCHOCDF() / servings * amount;
                protSum = nutr.getPROCNT() / servings * amount;
                fatSum = nutr.getFAT() / servings * amount;

                dataLabelTextView.setText(parsed.getFood().getLabel());
                calSum = (int) (parsed.getFood().getNutrients().getENERC_KCAL() / servings * amount);


                String tUnits = (parsed.getQuantity() / servings * amount) + " " + parsed.getMeasure().getLabel();
                dataUnitsTextView.setText(tUnits);
                dataCalTextView.setText(String.valueOf((int) (parsed.getFood().getNutrients().getENERC_KCAL() / servings * amount)));
            } else if (data.getItem() instanceof Hint) {
                hint = (Hint) data.getItem();
                int servings = (int) hint.getFood().getServingsPerContainer();
                int amount = data.getQuantity();

                if (servings == 0) {
                    servings = 1;
                }
                if (amount == 0) {
                    amount = 1;
                }

                Nutrients nutr = hint.getFood().getNutrients();
                calSum = (int) nutr.getENERC_KCAL() / servings;
                carbSum = nutr.getCHOCDF() / servings * amount;
                protSum = nutr.getPROCNT() / servings * amount;
                fatSum = nutr.getFAT() / servings * amount;

                dataLabelTextView.setText(hint.getFood().getLabel());
                String tUnits = amount + " " + hint.getMeasures().get(0).getLabel();
                dataUnitsTextView.setText(tUnits);
                dataCalTextView.setText(String.valueOf((int) (hint.getFood().getNutrients().getENERC_KCAL() / servings * amount)));
            } else if (data.getItem() instanceof Hit) {
                hit = (Hit) data.getItem();
                int amount = data.getQuantity();
                int servings = (int) hit.getRecipe().getYield();

                if (servings == 0) {
                    servings = 1;
                }
                if (amount == 0) {
                    amount = 1;
                }

                TotalNutrients nutr = hit.getRecipe().getTotalNutrients();
                calSum = (int) nutr.getENERC_KCAL().getQuantity() / servings * amount;
                carbSum = nutr.getCHOCDF().getQuantity() / servings * amount;
                protSum = nutr.getPROCNT().getQuantity() / servings * amount;
                fatSum = nutr.getFAT().getQuantity() / servings * amount;

                dataLabelTextView.setText(hit.getRecipe().getLabel());
                String tUnits = amount + " serving(s)";
                dataUnitsTextView.setText(tUnits);
                dataCalTextView.setText(String.valueOf((int) (hit.getRecipe().getCalories() / servings * amount)));
            }
        }

        @Override
        public void onClick(View v) {
            View popUpView = inflater.inflate(R.layout.dialog_generic_calorie_tracker_item, null);
            PopupWindow popupWindow = new PopupWindow(popUpView, LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

            DialogGenericCalorieTrackerItemBinding binding = DialogGenericCalorieTrackerItemBinding.bind(popUpView);
            TextView label = binding.dialogLabel;
            TextView measure = binding.dialogMeasure;
            TextView calories = binding.dialogCalories;
            TextView nutrients = binding.dialogNutrients;
            EditText resultID = binding.dialogResultID;
            EditText date = binding.dialogDate;
            EditText quantity = binding.dialogQuantity;
            ImageButton delete = binding.dialogDeleteIcon;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v2) {
                    FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseDatabase.getInstance()
                            .getReference("CalorieTrackingData")
                            .child(authUser.getUid())
                            .child(data.getResultID())
                            .removeValue();

                    calorieTrackerViewModel.getClickedArray().getValue().remove(data);
                    popupWindow.dismiss();
                    updateUI();
                }
            });

            MealType[] tMeals = MealType.values();
            String[] spinnerOptions = new String[tMeals.length - 1];
            int currentDayPart = 0;

            Calendar cal = Calendar.getInstance();
            int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
            String dayPart = data.getMealType();

            for (int i = 0; i < tMeals.length - 1; i++) {
                spinnerOptions[i] = tMeals[i + 1].getSpinnerTitle();
                if (dayPart.equals(tMeals[i + 1].getSpinnerTitle())) {
                    currentDayPart = i;
                }
            }
            Spinner mealType = Methods.getSpinnerWithAdapter(requireActivity(), popUpView, R.id.dialog_meal_type_spinner, spinnerOptions);
            mealType.setSelection(currentDayPart);

            int index2 = data.getResultID().indexOf("&");
            String tID = data.getResultID().substring(0, index2);
            resultID.setText(tID);

            date.setText("Today");

            quantity.setText(String.valueOf(data.getQuantity()));

            String tNutr = "Carbs " + decimalFormat.format(carbSum) + "g \u22C5 " +
                    "Fat " + decimalFormat.format(fatSum) + "g \u22c5 " +
                    "Protein " + decimalFormat.format(protSum) + "g";
            String tCal = ((int) calSum) + " calories / serving";

            calories.setText(tCal);
            nutrients.setText(tNutr);

            if (data.getItem() instanceof Parsed) {
                Parsed parsed = (Parsed) data.getItem();
                int servings = (int) parsed.getFood().getServingsPerContainer();
                int amount = data.getQuantity();

                if (servings == 0) {
                    servings = 1;
                }
                if (amount == 0) {
                    amount = 1;
                }

                label.setText(parsed.getFood().getLabel());
                String tUnits = (parsed.getQuantity() / servings * amount) + " " + parsed.getMeasure().getLabel();
                measure.setText(tUnits);
            } else if (data.getItem() instanceof Hint) {
                Hint hint = (Hint) data.getItem();
                int amount = data.getQuantity();

                if (amount == 0) {
                    amount = 1;
                }

                label.setText(hint.getFood().getLabel());
                String tUnits = amount + " " + hint.getMeasures().get(0).getLabel();
                measure.setText(tUnits);
            } else if (data.getItem() instanceof Hit) {
                Hit hit = (Hit) data.getItem();
                int amount = data.getQuantity();

                if (amount == 0) {
                    amount = 1;
                }

                label.setText(hit.getRecipe().getLabel());
                String tUnits = amount + " serving(s)";
                measure.setText(tUnits);
            }

            AppCompatButton acceptButton = binding.dialogGenericAcceptButton;
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ResponseInfo og = data;

                    int index = mealType.getSelectedItemPosition();
                    String tMeal = MealType.values()[index + 1].getSpinnerTitle();
                    data.setMealType(tMeal);

                    int tQuant = Integer.parseInt(quantity.getText().toString());
                    data.setQuantity(tQuant);

                    int i = calorieTrackerViewModel.getClickedArray().getValue().indexOf(og);
                    calorieTrackerViewModel.getClickedArray().getValue().set(i, data);

                    saveData(data);
                    updateUI();

                    popupWindow.dismiss();
                }
            });
            AppCompatButton cancelButton = binding.dialogGenericCancelButton;
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });

            popupWindow.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
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