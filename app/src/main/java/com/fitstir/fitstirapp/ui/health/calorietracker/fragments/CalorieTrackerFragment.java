package com.fitstir.fitstirapp.ui.health.calorietracker.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentCalorieTrackerBinding;
import com.fitstir.fitstirapp.ui.health.calorietracker.CalorieTrackerViewModel;
import com.fitstir.fitstirapp.ui.health.calorietracker.ResponseInfo;
import com.fitstir.fitstirapp.ui.health.calorietracker.dialogs.ChangeCalorieGoalDialog;
import com.fitstir.fitstirapp.ui.health.edamamapi.ISearchResult;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.MealType;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Hint;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Nutrients;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Parsed;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Hit;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.TotalNutrients;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CalorieTrackerFragment extends Fragment {

    private FragmentCalorieTrackerBinding binding;
    private CalorieTrackerViewModel calorieTrackerViewModel;

    private ConstraintLayout loadingPopupView;
    private RecyclerView dataRecyclerView;
    private DataAdapter dataAdapter;
    private TextView dateName, goalCalTextView, usedCalTextView, remainingCalTextView;
    private ShapeableImageView goalBackgroundView;
    private LinearLayoutCompat goalInfoView;
    private AppBarLayout dateToolbar;
    private ImageButton moreButton;

    private boolean isLoading;
    private DecimalFormat decimalFormat;
    private String stringUserID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calorieTrackerViewModel = new ViewModelProvider(requireActivity()).get(CalorieTrackerViewModel.class);

        binding = FragmentCalorieTrackerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Addition Text Here

        decimalFormat = new DecimalFormat("####.#");
        dateName = root.findViewById(R.id.calendar_date_label);

        dataRecyclerView = binding.dataRecyclerView;
        dataRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        goalCalTextView = binding.ctgoalGoalAmount;
        usedCalTextView = binding.ctgoalUsedAmount;
        remainingCalTextView = binding.ctgoalRemainingAmount;

        loadingPopupView = root.findViewById(R.id.generic_loading_screen);
        goalBackgroundView = root.findViewById(R.id.ctgoal_background);
        goalInfoView = root.findViewById(R.id.ctgoal_info);
        dateToolbar = root.findViewById(R.id.calendar_toolbar);
        isLoading = false;



        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        assert authUser != null;
        stringUserID = authUser.getUid();
        DatabaseReference thisUser = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(stringUserID);

        toggleLoadingScreen();
        thisUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfileData value = snapshot.getValue(UserProfileData.class);
                calorieTrackerViewModel.setThisUser(value);
                calorieTrackerViewModel.setCalorieTrackerGoal(value.getCalorieTrackerGoal());



                float weight = (float) value.get_Weight();
                weight /= 2.2f;
                String sex = value.getSex();
                float sexMod = 0.0f;
                if (sex.equals("female")) {
                    sexMod = 0.9f;
                } else {
                    sexMod = 1.0f;
                }
                float leanMod = 1.00f;
                float actMod = 1.3f;
                float suggestedCal = weight * sexMod * 24 * leanMod * actMod;
                calorieTrackerViewModel.setSuggestedGoal((int) suggestedCal);



                DatabaseReference listRef = FirebaseDatabase.getInstance()
                        .getReference("CalorieTrackingData").child(stringUserID);
                listRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> children = snapshot.getChildren();
                        ArrayList<ResponseInfo> data = new ArrayList<>();
                        ObjectMapper objectMapper = new ObjectMapper();

                        for (DataSnapshot child : children) {
                            ResponseInfo info = new ResponseInfo();

                            Map<String, Object> kid = (Map<String, Object>) child.getValue();
                            assert kid != null;

                            String resultID = (String) child.getKey();
                            info.setResultID(resultID);

                            Calendar cal = Calendar.getInstance();
                            HashMap<String, Object> calInfo = (HashMap<String, Object>) kid.get("date");

                            long timeInMillis = (long) calInfo.get("timeInMillis");
                            cal.setTimeInMillis(timeInMillis);
                            info.setDate(cal);

                            String mealType = (String) kid.get("mealType");
                            info.setMealType(mealType);

                            long quantityLong = (long) kid.get("quantity");
                            int quantity = (int) quantityLong;
                            info.setQuantity(quantity);

                            ISearchResult item;
                            Map<String, Object> tItem = (Map<String, Object>) kid.get("item");
                            try {
                                Parsed parsed = objectMapper.convertValue(tItem, Parsed.class);
                                item = parsed;
                            } catch (IllegalArgumentException e1) {
                                try {
                                    Hint hint = objectMapper.convertValue(tItem, Hint.class);
                                    item = hint;
                                } catch (IllegalArgumentException e2) {
                                    try {
                                        Hit hit = objectMapper.convertValue(tItem, Hit.class);
                                        item = hit;
                                    } catch (IllegalArgumentException e3) {
                                        throw new RuntimeException();
                                    }
                                }
                            }
                            info.setItem(item);

                            data.add(info);
                        }

                        calorieTrackerViewModel.setCalorieTrackerData(data);
                        updateUI();

                        toggleLoadingScreen();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });



        moreButton = binding.calgoalMoreButton;
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCalorieGoalDialog dialog = ChangeCalorieGoalDialog.newInstance(
                        R.layout.dialog_change_calorie_goal,
                        R.id.dialog_calgoal_accept_button,
                        R.id.dialog_calgoal_cancel_button,
                        calorieTrackerViewModel.getCalorieTrackerGoal().getValue()
                );
                dialog.showNow(getChildFragmentManager(), "Change Calorie Goal");
            }
        });

        AppCompatImageView dateSelectorButton = root.findViewById(R.id.calendar_toolbar_dropdown_button);
        dateSelectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popUpView = inflater.inflate(R.layout.popup_date_picker, null);
                PopupWindow popupWindow = new PopupWindow(popUpView, LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                Calendar selectedDate = Calendar.getInstance();
                final String[] dateString = {"Today"};

                CalendarView calendarView = popUpView.findViewById(R.id.popup_calender_view);
                calendarView.setDate(calorieTrackerViewModel.getSelectedDate().getValue().getTime().getTime());
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        Calendar today = Calendar.getInstance();

                        if (!(today.get(Calendar.YEAR) == year && today.get(Calendar.MONTH) == month && today.get(Calendar.DAY_OF_MONTH) == dayOfMonth)) {
                            dateString[0] = "";
                            switch (month) {
                                case Calendar.JANUARY:
                                    dateString[0] += "Jan.";
                                    break;
                                case Calendar.FEBRUARY:
                                    dateString[0] += "Feb.";
                                    break;
                                case Calendar.MARCH:
                                    dateString[0] += "Mar.";
                                    break;
                                case Calendar.APRIL:
                                    dateString[0] += "Apr.";
                                    break;
                                case Calendar.MAY:
                                    dateString[0] += "May";
                                    break;
                                case Calendar.JUNE:
                                    dateString[0] += "June";
                                    break;
                                case Calendar.JULY:
                                    dateString[0] += "July";
                                    break;
                                case Calendar.AUGUST:
                                    dateString[0] += "Aug.";
                                    break;
                                case Calendar.SEPTEMBER:
                                    dateString[0] += "Sept.";
                                    break;
                                case Calendar.OCTOBER:
                                    dateString[0] += "Oct.";
                                    break;
                                case Calendar.NOVEMBER:
                                    dateString[0] += "Nov.";
                                    break;
                                case Calendar.DECEMBER:
                                    dateString[0] += "Dec.";
                                    break;
                            }
                            dateString[0] += " " + dayOfMonth + ", " + year;
                        }

                        selectedDate.set(year, month, dayOfMonth);
                    }
                });

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        dateName.setText(dateString[0]);
                        calorieTrackerViewModel.setSelectedDate(selectedDate);
                        calorieTrackerViewModel.setDateString(dateString[0]);
                        updateUI();
                    }
                });
                popupWindow.showAtLocation(dateSelectorButton, Gravity.TOP, 0,425);
            }
        });

        FloatingActionButton searchButton = binding.calorieTrackerSearchButton;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_calorie_tracker_to_navigation_calorie_tracker_search);
            }
        });


        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onDismiss(DialogInterface dialog) {
        updateUI();
    }

    private void toggleLoadingScreen() {
        if (isLoading) {
            loadingPopupView.setVisibility(View.GONE);
            goalBackgroundView.setVisibility(View.VISIBLE);
            goalInfoView.setVisibility(View.VISIBLE);
            dateToolbar.setVisibility(View.VISIBLE);

            isLoading = false;
        } else {
            loadingPopupView.setVisibility(View.VISIBLE);
            goalBackgroundView.setVisibility(View.GONE);
            goalInfoView.setVisibility(View.GONE);
            dateToolbar.setVisibility(View.GONE);

            isLoading = true;
        }
    }

    private void updateUI() {
        if (calorieTrackerViewModel.getDateString().getValue().equals("Today")) {
            moreButton.setVisibility(View.VISIBLE);
            moreButton.setClickable(true);
        } else {
            moreButton.setVisibility(View.INVISIBLE);
            moreButton.setClickable(false);
        }

        dateName.setText(calorieTrackerViewModel.getDateString().getValue());

        ArrayList<ResponseInfo> dataArray = calorieTrackerViewModel.getCalorieTrackerData().getValue();
        ArrayList<ResponseInfo> used = new ArrayList<>();
        int dayCalSum = 0;

        for (int i = 0; i < dataArray.size(); i++) {
            Calendar cal = calorieTrackerViewModel.getSelectedDate().getValue();

            if (dataArray.get(i).isDate(cal)) {
                used.add(dataArray.get(i));
                if (dataArray.get(i).getItem() instanceof Parsed) {
                    dayCalSum += ((Parsed) dataArray.get(i).getItem()).getFood().getNutrients().getENERC_KCAL();
                } else if (dataArray.get(i).getItem() instanceof Hint) {
                    dayCalSum += ((Hint) dataArray.get(i).getItem()).getFood().getNutrients().getENERC_KCAL();
                } else if (dataArray.get(i).getItem() instanceof Hit) {
                    int yield = (int) ((Hit) dataArray.get(i).getItem()).getRecipe().getYield();
                    dayCalSum += ((Hit) dataArray.get(i).getItem()).getRecipe().getCalories() / yield;
                }
            }
        }

        dataAdapter = new DataAdapter(used);
        dataRecyclerView.setAdapter(dataAdapter);

        int goal = calorieTrackerViewModel.getCalorieTrackerGoal().getValue();
        goalCalTextView.setText(String.valueOf(goal));
        usedCalTextView.setText(String.valueOf(dayCalSum));
        int remaining = goal - dayCalSum;
        remainingCalTextView.setText(String.valueOf(remaining));
    }

    private class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ArrayList<ResponseInfo> data;
        private final TextView labelTextView, nutrientsTextView, caloriesTextView;
        private LinearLayoutCompat dataLinearLayout;
        private float calSum = 0, carbSum = 0, fatSum = 0, protSum = 0;
        private CalorieTrackerViewModel calorieTrackerViewModel;

        public DataHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.layout_calorie_tracker_meal_section, parent, false));
            itemView.setOnClickListener(this);

            calorieTrackerViewModel = new ViewModelProvider(requireActivity()).get(CalorieTrackerViewModel.class);

            labelTextView = itemView.findViewById(R.id.meal_section_label);
            nutrientsTextView = itemView.findViewById(R.id.meal_section_big_3);
            caloriesTextView = itemView.findViewById(R.id.meal_section_calories);
            dataLinearLayout = itemView.findViewById(R.id.meal_section_item_rv);
            dataLinearLayout.removeAllViews();
        }

        public void bind(ArrayList<ResponseInfo> data, int position) throws IOException {
            this.data = data;

            switch (position) {
                case 0:
                    this.labelTextView.setText("Breakfast");
                    break;
                case 1:
                    this.labelTextView.setText("Tea Time");
                    break;
                case 2:
                    this.labelTextView.setText("Brunch");
                    break;
                case 3:
                    this.labelTextView.setText("Lunch");
                    break;
                case 4:
                    this.labelTextView.setText("Snack");
                    break;
                case 5:
                    this.labelTextView.setText("Dinner");
                    break;
            }

            calSum = 0;
            carbSum = 0;
            fatSum = 0;
            protSum = 0;
            for (int i = 0; i < data.size(); i++) {
                Parsed parsed = new Parsed();
                Hit hit = new Hit();
                Hint hint = new Hint();

                View view = LayoutInflater.from(requireActivity()).inflate(R.layout.layout_calorie_tracker_data_section, null);
                TextView dataLabelTextView = view.findViewById(R.id.data_section_label);
                TextView dataUnitsTextView = view.findViewById(R.id.data_section_units);
                TextView dataCaloriesTextView = view.findViewById(R.id.data_section_calories);

                if (data.get(i).getItem() instanceof Parsed) {
                    parsed = (Parsed) data.get(i).getItem();
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

                    dataLabelTextView.setText(parsed.getFood().getLabel());
                    String tUnits = (parsed.getQuantity() * amount) + " " + parsed.getMeasure().getLabel();
                    dataUnitsTextView.setText(tUnits);
                    dataCaloriesTextView.setText(String.valueOf((int) (parsed.getFood().getNutrients().getENERC_KCAL() * amount)));
                } else if (data.get(i).getItem() instanceof Hint) {
                    hint = (Hint) data.get(i).getItem();
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

                    dataLabelTextView.setText(hint.getFood().getLabel());
                    String tUnits = data.get(i).getQuantity() + " " + hint.getMeasures().get(0).getLabel();
                    dataUnitsTextView.setText(tUnits);
                    dataCaloriesTextView.setText(String.valueOf((int) (hint.getFood().getNutrients().getENERC_KCAL() / servings * amount)));
                } else if (data.get(i).getItem() instanceof Hit) {
                    hit = (Hit) data.get(i).getItem();
                    int servings = (int) hit.getRecipe().getYield();
                    int amount = data.get(i).getQuantity();

                    if (servings == 0) {
                        servings = 1;
                    }
                    if (amount == 0) {
                        amount = 1;
                    }

                    TotalNutrients nutr = hit.getNutrients();
                    calSum += nutr.getENERC_KCAL().getQuantity() / servings * amount;
                    carbSum += nutr.getCHOCDF().getQuantity() / servings * amount;
                    protSum += nutr.getPROCNT().getQuantity() / servings * amount;
                    fatSum += nutr.getFAT().getQuantity() / servings * amount;

                    dataLabelTextView.setText(hit.getRecipe().getLabel());
                    String tUnits = amount + " serving(s)";
                    dataUnitsTextView.setText(tUnits);
                    dataCaloriesTextView.setText(String.valueOf((int) (hit.getRecipe().getCalories() / servings * amount)));
                }

                this.dataLinearLayout.addView(view);
            }

            String tNutr = "Carbs " + decimalFormat.format(carbSum) + "g \u22C5 " +
                    "Fat " + decimalFormat.format(fatSum) + "g \u22c5 " +
                    "Protein " + decimalFormat.format(protSum) + "g";
            this.nutrientsTextView.setText(tNutr);
            this.caloriesTextView.setText(String.valueOf((int) calSum));
        }

        @Override
        public void onClick(View v) {
            calorieTrackerViewModel.setClickedArray(data);
            calorieTrackerViewModel.setCalorieSum(calSum);
            calorieTrackerViewModel.setCarbSum(carbSum);
            calorieTrackerViewModel.setFatSum(fatSum);
            calorieTrackerViewModel.setProteinSum(protSum);

            Navigation.findNavController(v).navigate(R.id.action_navigation_calorie_tracker_to_navigation_view_calorie_tracker_meal);
        }
    }

    private class DataAdapter extends RecyclerView.Adapter<DataHolder> {
        private ArrayList<Pair<Integer, ArrayList<ResponseInfo>>> usedDataArray;
        private CalorieTrackerViewModel calorieTrackerViewModel;

        public DataAdapter(ArrayList<ResponseInfo> dataArray) {

            calorieTrackerViewModel = new ViewModelProvider(requireActivity()).get(CalorieTrackerViewModel.class);

            ArrayList<ArrayList<ResponseInfo>> sectionedData = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                sectionedData.add(new ArrayList<>());
            }

            for (int i = 0; i < dataArray.size(); i++) {
                for (int j = 0; j < MealType.values().length; j++) {
                    if (dataArray.get(i).getMealType().equals(MealType.values()[j].getSpinnerTitle())) {
                        sectionedData.get(j - 1).add(dataArray.get(i));
                    }
                }
            }

            ArrayList<Pair<Integer, ArrayList<ResponseInfo>>> toShow = new ArrayList<>();
            for (int i = 0; i < sectionedData.size(); i++) {
                if (sectionedData.get(i).size() != 0) {
                    toShow.add(new Pair<>(i, sectionedData.get(i)));
                }
            }
            usedDataArray = toShow;
        }

        @NonNull
        @Override
        public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(requireActivity());
            return new DataHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DataHolder holder, int position) {
            Pair<Integer, ArrayList<ResponseInfo>> data = this.usedDataArray.get(position);
            try {
                holder.bind(data.second, data.first);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public int getItemCount() {
            return usedDataArray.size();
        }
    }
}