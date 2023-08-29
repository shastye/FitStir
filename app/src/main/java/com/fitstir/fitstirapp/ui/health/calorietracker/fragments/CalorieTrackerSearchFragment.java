package com.fitstir.fitstirapp.ui.health.calorietracker.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.DialogGenericCalorieTrackerItemBinding;
import com.fitstir.fitstirapp.databinding.FragmentCalorieTrackerSearchBinding;
import com.fitstir.fitstirapp.ui.health.calorietracker.CalorieTrackerViewModel;
import com.fitstir.fitstirapp.ui.health.calorietracker.ResponseInfo;
import com.fitstir.fitstirapp.ui.health.edamamapi.ISearchResult;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.CategoryOptions;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.CuisineType;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.DietOptions;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.HealthOptions;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.MealType;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.NutritionType;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.SearchOptions;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.EdamamAPI_FoodDatabaseParser;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Food;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.FoodResponse;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Hint;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Nutrients;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.Parsed;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.EdamamAPI_RecipesV2;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Hit;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Recipe;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.RecipeResponse;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.TotalNutrients;
import com.fitstir.fitstirapp.ui.utility.Methods;
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
import java.util.concurrent.ExecutionException;

public class CalorieTrackerSearchFragment extends Fragment {

    private FragmentCalorieTrackerSearchBinding binding;
    private View root;
    private CalorieTrackerViewModel calorieTrackerViewModel;
    private RecipeResponse recipeResponse;
    private FoodResponse foodResponse;
    private ArrayList<ResponseInfo> calorieTrackingData;
    private ArrayList<Recipe> likedRecipeData;
    private ArrayList<Food> likedFoodData;

    private AppCompatSpinner searchOptionsSpinner;
    private AppCompatImageView filterButton;
    private EditText searchBar;
    private ConstraintLayout loadingScreen_CL;

    private RecyclerView searchRecyclerView;
    private Parcelable recyclerViewState;
    private RecipeAdapter recipeAdapter;

    private boolean isRecipeSearch;
    private boolean isInitialLoad;
    private DecimalFormat decimalFormat;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calorieTrackerViewModel = new ViewModelProvider(requireActivity()).get(CalorieTrackerViewModel.class);

        binding = FragmentCalorieTrackerSearchBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        // ADDITIONS HERE

        // Hide back button on searchbar

        root.findViewById(R.id.search_toolbar_back_arrow_icon).setVisibility(View.GONE);
        isRecipeSearch = false;
        loadingScreen_CL = root.findViewById(R.id.generic_loading_screen);
        loadingScreen_CL.setVisibility(View.GONE);
        isInitialLoad = true;
        decimalFormat = new DecimalFormat("####.#");
        calorieTrackingData = new ArrayList<>();
        likedRecipeData = new ArrayList<>();
        likedFoodData = new ArrayList<>();



        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference CT_Reference = FirebaseDatabase.getInstance()
                .getReference("CalorieTrackingData")
                .child(authUser.getUid());
        CT_Reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    ResponseInfo info = new ResponseInfo();
                    ObjectMapper objectMapper = new ObjectMapper();

                    Map<String, Object> kid = (Map<String, Object>) child.getValue();
                    assert kid != null;

                    String resultID = (String) child.getKey();
                    info.setResultID(resultID);

                    Calendar cal = Calendar.getInstance();
                    HashMap<String, Object> calInfo = (HashMap<String, Object>) kid.get("date");
                    HashMap<String, Long> dateInfo = (HashMap<String, Long>) calInfo.get("time");

                    int year = Math.toIntExact((long) calInfo.get("weekYear"));
                    int month = Math.toIntExact(dateInfo.get("month"));
                    int day = Math.toIntExact(dateInfo.get("date"));
                    int hour = Math.toIntExact(dateInfo.get("hours"));
                    int minutes = Math.toIntExact(dateInfo.get("minutes"));
                    int seconds = Math.toIntExact(dateInfo.get("seconds"));
                    int milliseconds = Math.toIntExact(dateInfo.get("seconds"));
                    cal.set(year, month, day, hour, minutes, seconds);
                    cal.set(Calendar.MILLISECOND, milliseconds);
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

                    calorieTrackingData.add(info);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        DatabaseReference LR_Reference = FirebaseDatabase.getInstance()
                .getReference("LikedRecipesData")
                .child(authUser.getUid());
        LR_Reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();

                for (DataSnapshot child : children) {
                    Map<String, Object> kid = (Map<String, Object>) child.getValue();
                    String bitmapString = (String) kid.get("imageBitmapData");
                    String foodIdString = (String) kid.get("foodId");

                    if (bitmapString != null && !bitmapString.equals("")) {
                        Recipe recipe = child.getValue(Recipe.class);
                        likedRecipeData.add(recipe);
                    } else if (foodIdString != null && !foodIdString.equals("")) {
                        Food food = child.getValue(Food.class);
                        likedFoodData.add(food);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw new RuntimeException();
            }
        });



        searchRecyclerView = root.findViewById(R.id.search_response_rv);
        searchRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 2));

        SearchOptions[] searchOptions = SearchOptions.values();
        String[] searchOptionsStrings = new String[searchOptions.length];
        for (int i = 0; i < searchOptions.length; i++) {
            searchOptionsStrings[i] = searchOptions[i].getSpinnerTitle();
        }
        searchOptionsSpinner = (AppCompatSpinner) Methods.getSpinnerWithAdapter(requireActivity(), root, R.id.toolbar_search_spinner, searchOptionsStrings);
        searchOptionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isRecipeSearch = (SearchOptions.RECIPES.getValue() == position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        searchBar = root.findViewById(R.id.toolbar_search_edit_text);
        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if(keyCode == KeyEvent.KEYCODE_ENTER)
                    {
                        return onEnterClick();
                    }

                    return false;
                }
                return false;
            }
        });
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == KeyEvent.KEYCODE_CALL)
                {
                    return onEnterClick();
                }
                return false;
            }
        });

        filterButton = root.findViewById(R.id.toolbar_filter_icon);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecipeSearch) {
                    View popUpView = inflater.inflate(R.layout.popup_recipe_filter, null);
                    PopupWindow popupWindow = new PopupWindow(popUpView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

                    AppCompatEditText min_ingr = popUpView.findViewById(R.id.min_ingr);
                    AppCompatEditText max_ingr = popUpView.findViewById(R.id.max_ingr);
                    AppCompatEditText min_cal = popUpView.findViewById(R.id.min_cal);
                    AppCompatEditText max_cal = popUpView.findViewById(R.id.max_cal);
                    AppCompatEditText min_time = popUpView.findViewById(R.id.min_time);
                    AppCompatEditText max_time = popUpView.findViewById(R.id.max_time);

                    String[] dietOptions = new String[DietOptions.values().length];
                    for (int i = 0; i < DietOptions.values().length; i++) {
                        dietOptions[i] = DietOptions.values()[i].getSpinnerTitle();
                    }
                    Spinner diet = Methods.getSpinnerWithAdapter(requireActivity(), popUpView, R.id.diet_spinner, dietOptions);

                    String[] healthOptions = new String[HealthOptions.values().length];
                    for (int i = 0; i < HealthOptions.values().length; i++) {
                        healthOptions[i] = HealthOptions.values()[i].getSpinnerTitle();
                    }
                    Spinner health = Methods.getSpinnerWithAdapter(requireActivity(), popUpView, R.id.health_spinner, healthOptions);

                    String[] cuisineOptions = new String[CuisineType.values().length];
                    for (int i = 0; i < CuisineType.values().length; i++) {
                        cuisineOptions[i] = CuisineType.values()[i].getSpinnerTitle();
                    }
                    Spinner cuisine = Methods.getSpinnerWithAdapter(requireActivity(), popUpView, R.id.cuisine_spinner, cuisineOptions);

                    String[] mealOptions = new String[MealType.values().length];
                    for (int i = 0; i < MealType.values().length; i++) {
                        mealOptions[i] = MealType.values()[i].getSpinnerTitle();
                    }
                    Spinner meal = Methods.getSpinnerWithAdapter(requireActivity(), popUpView, R.id.meal_spinner, mealOptions);



                    min_ingr.setText(calorieTrackerViewModel.getMinIngr().getValue());
                    max_ingr.setText(calorieTrackerViewModel.getMaxIngr().getValue());
                    min_cal.setText(calorieTrackerViewModel.getMinCal().getValue());
                    max_cal.setText(calorieTrackerViewModel.getMaxCal().getValue());
                    min_time.setText(calorieTrackerViewModel.getMinTime().getValue());
                    max_time.setText(calorieTrackerViewModel.getMaxTime().getValue());

                    diet.setSelection(calorieTrackerViewModel.getDietType().getValue());
                    health.setSelection(calorieTrackerViewModel.getHealthType().getValue());
                    cuisine.setSelection(calorieTrackerViewModel.getCuisineType().getValue());
                    meal.setSelection(calorieTrackerViewModel.getMealType().getValue());

                    popupWindow.showAsDropDown(filterButton, 0,0);



                    AppCompatButton accept = popUpView.findViewById(R.id.recipe_accept_button);
                    accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            calorieTrackerViewModel.setMinIngr(min_ingr.getText().toString().trim());
                            calorieTrackerViewModel.setMaxIngr(max_ingr.getText().toString().trim());
                            calorieTrackerViewModel.setMinCal(min_cal.getText().toString().trim());
                            calorieTrackerViewModel.setMaxCal(max_cal.getText().toString().trim());
                            calorieTrackerViewModel.setMinTime(min_time.getText().toString().trim());
                            calorieTrackerViewModel.setMaxTime(max_time.getText().toString().trim());

                            calorieTrackerViewModel.setDietType(diet.getSelectedItemPosition());
                            calorieTrackerViewModel.setHealthType(health.getSelectedItemPosition());
                            calorieTrackerViewModel.setCuisineType(cuisine.getSelectedItemPosition());
                            calorieTrackerViewModel.setMealType(meal.getSelectedItemPosition());

                            popupWindow.dismiss();

                            onEnterClick();
                        }
                    });
                } else {
                    View popUpView = inflater.inflate(R.layout.popup_food_filter, null);
                    PopupWindow popupWindow = new PopupWindow(popUpView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

                    AppCompatEditText min_cal = popUpView.findViewById(R.id.min_cal);
                    AppCompatEditText max_cal = popUpView.findViewById(R.id.max_cal);

                    String[] healthOptions = new String[HealthOptions.values().length];
                    for (int i = 0; i < HealthOptions.values().length; i++) {
                        healthOptions[i] = HealthOptions.values()[i].getSpinnerTitle();
                    }
                    Spinner health = Methods.getSpinnerWithAdapter(requireActivity(), popUpView, R.id.health_spinner, healthOptions);

                    String[] nutritionOption = new String[NutritionType.values().length];
                    for (int i = 0; i < NutritionType.values().length; i++) {
                        nutritionOption[i] = NutritionType.values()[i].getSpinnerTitle();
                    }
                    Spinner nutrition = Methods.getSpinnerWithAdapter(requireActivity(), popUpView, R.id.nutrition_spinner, nutritionOption);

                    String[] categoryOptions = new String[CategoryOptions.values().length];
                    for (int i = 0; i < CategoryOptions.values().length; i++) {
                        categoryOptions[i] = CategoryOptions.values()[i].getSpinnerTitle();
                    }
                    Spinner category = Methods.getSpinnerWithAdapter(requireActivity(), popUpView, R.id.category_spinner, categoryOptions);



                    min_cal.setText(calorieTrackerViewModel.getMinCal().getValue());
                    max_cal.setText(calorieTrackerViewModel.getMaxCal().getValue());

                    health.setSelection(calorieTrackerViewModel.getHealthType().getValue());
                    nutrition.setSelection(calorieTrackerViewModel.getNutritionType().getValue());
                    category.setSelection(calorieTrackerViewModel.getCategoryType().getValue());

                    popupWindow.showAsDropDown(filterButton, 0,0);




                    AppCompatButton accept = popUpView.findViewById(R.id.food_accept_button);
                    accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            calorieTrackerViewModel.setMinCal(min_cal.getText().toString().trim());
                            calorieTrackerViewModel.setMaxCal(max_cal.getText().toString().trim());

                            calorieTrackerViewModel.setHealthType(health.getSelectedItemPosition());
                            calorieTrackerViewModel.setNutritionType(nutrition.getSelectedItemPosition());
                            calorieTrackerViewModel.setCategoryType(category.getSelectedItemPosition());

                            popupWindow.dismiss();

                            onEnterClick();
                        }
                    });

                }
            }
        });

        // END

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean onEnterClick() {
        if (searchBar != null && !searchBar.getText().toString().equals("")) {
            boolean isFoodReady = true;
            if (!isRecipeSearch) {
                String tSearch = searchBar.getText().toString();
                boolean twoSpaces;
                int indexSpace1 = tSearch.indexOf(" ");
                int indexSpace2 = 0;
                if (indexSpace1 == -1) {
                    twoSpaces = false;
                } else {
                    indexSpace2 = tSearch.indexOf(" ", indexSpace1 + 1);
                    twoSpaces = (indexSpace2 != -1);
                }

                boolean quantityIncluded = true;
                if (twoSpaces) {
                    String tQuant = tSearch.substring(0, indexSpace1);
                    try {
                        int tInt = Integer.parseInt(tQuant);
                        quantityIncluded = true;
                    } catch (NumberFormatException e1) {
                        quantityIncluded = false;
                        tQuant = tSearch.substring(indexSpace1, indexSpace2);

                        try {
                            int tInt = Integer.parseInt(tQuant);
                            quantityIncluded = true;
                        } catch (NumberFormatException e2) {
                            quantityIncluded = false;
                            tQuant = tSearch.substring(indexSpace2);

                            try {
                                int tInt = Integer.parseInt(tQuant);
                                quantityIncluded = true;
                            } catch (NumberFormatException e3) {
                                quantityIncluded = false;
                            }
                        }
                    }
                }

                isFoodReady = (twoSpaces && quantityIncluded);
            }

            if (isFoodReady) {
                calorieTrackerViewModel.setToSearchFor(searchBar.getText().toString());
                try {
                    search();
                    updateUI();
                } catch (IOException | ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

                InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                try {
                    im.hideSoftInputFromWindow(root.getWindowToken(), 0); // make keyboard hide
                } catch (NullPointerException e) {
                    Log.d("CALORIETRACKER SEARCH FRAGMENT", "make keyboard hide: NullPointerException: " + e);
                }
            } else {
                final Drawable warningIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_warning_amber_24);
                assert warningIcon != null;
                warningIcon.setBounds(0, 0, warningIcon.getIntrinsicWidth(), warningIcon.getIntrinsicHeight());

                warningIcon.setTint(Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorSecondaryVariant, requireContext()));

                searchBar.setError(
                    "Please search with the format:\n" +
                        "2 large eggs\n" +
                        "\n" +
                        "[quantity as number]\n" +
                        "[one space]\n" +
                        "[unit]\n" +
                        "[one space]\n" +
                        "[food item]",
                    warningIcon
                );
            }
        } else {
            final Drawable warningIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_warning_amber_24);
            assert warningIcon != null;
            warningIcon.setBounds(0, 0, warningIcon.getIntrinsicWidth(), warningIcon.getIntrinsicHeight());

            warningIcon.setTint(Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorSecondaryVariant, requireContext()));

            searchBar.setError("Please enter a value.", warningIcon);
        }
        return true;
    }

    private void search() throws IOException, ExecutionException, InterruptedException {
        if (isRecipeSearch) {
            EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2(
                    calorieTrackerViewModel.getToSearchFor().getValue(),
                    calorieTrackerViewModel.getMinIngr().getValue(),
                    calorieTrackerViewModel.getMaxIngr().getValue(),
                    DietOptions.values()[calorieTrackerViewModel.getDietType().getValue()].getKey(),
                    HealthOptions.values()[calorieTrackerViewModel.getHealthType().getValue()].getKey(),
                    CuisineType.values()[calorieTrackerViewModel.getCuisineType().getValue()].getKey(),
                    MealType.values()[calorieTrackerViewModel.getMealType().getValue()].getKey(),
                    "",
                    calorieTrackerViewModel.getMinCal().getValue(),
                    calorieTrackerViewModel.getMaxCal().getValue(),
                    calorieTrackerViewModel.getMinTime().getValue(),
                    calorieTrackerViewModel.getMaxTime().getValue()
            );
            api.execute();

            recipeResponse = api.getRecipeResponse();
            calorieTrackerViewModel.setHits(recipeResponse.getHits());
        } else {
            String tSearch = calorieTrackerViewModel.getToSearchFor().getValue();
            int space1 = tSearch.indexOf("", 0);
            int space2 = tSearch.indexOf("", space1);
            String tQuant = tSearch.substring(0, space1);
            String tUnit = tSearch.substring(space1, space2);
            String tIngr = tSearch.substring(space2);

            EdamamAPI_FoodDatabaseParser api = new EdamamAPI_FoodDatabaseParser(
                    tQuant,
                    tUnit,
                    tIngr,
                    NutritionType.values()[calorieTrackerViewModel.getNutritionType().getValue()].getKey(),
                    HealthOptions.values()[calorieTrackerViewModel.getHealthType().getValue()].getKey(),
                    calorieTrackerViewModel.getMinCal().getValue(),
                    calorieTrackerViewModel.getMaxCal().getValue(),
                    CategoryOptions.values()[calorieTrackerViewModel.getCategoryType().getValue()].getKey()
            );

            api.execute();

            foodResponse = api.getFoodResponse();

            calorieTrackerViewModel.setParsed(foodResponse.getParsed());
            calorieTrackerViewModel.setHints(foodResponse.getHints());
        }

        updateUI();
    }

    private void updateUI() {
        searchRecyclerView = root.findViewById(R.id.search_response_rv);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        ArrayList<ResponseInfo> responses = new ArrayList<>(0);
        if (isRecipeSearch) {
            ArrayList<Hit> hits = calorieTrackerViewModel.getHits().getValue();

            for (Hit hit : hits) {
                ResponseInfo info = new ResponseInfo(Calendar.getInstance(), "Breakfast", hit, 1);
                responses.add(info);
            }

            if (responses.size() != 0) {
                searchRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                recipeAdapter = new CalorieTrackerSearchFragment.RecipeAdapter(responses);
                searchRecyclerView.setAdapter(recipeAdapter);
            }
        } else {
            ArrayList<Parsed> parseds = calorieTrackerViewModel.getParsed().getValue();
            ArrayList<Hint> hints = calorieTrackerViewModel.getHints().getValue();

            for (Parsed parsed : parseds) {
                ResponseInfo info = new ResponseInfo(Calendar.getInstance(), "Breakfast", parsed, 1);
                responses.add(info);
            }

            for (Hint hint : hints) {
                ResponseInfo info = new ResponseInfo(Calendar.getInstance(), "Breakfast", hint, 1);
                responses.add(info);
            }

            if (responses.size() != 0) {
                searchRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                recipeAdapter = new CalorieTrackerSearchFragment.RecipeAdapter(responses);
                searchRecyclerView.setAdapter(recipeAdapter);
            }
        }
    }

    public class RecipeHolder extends RecyclerView.ViewHolder {
        private final int SAVE = 0, REMOVE = 1;
        private int saveState;
        int calSum = 0;
        float carbSum = 0.0f, protSum = 0.0f, fatSum = 0.0f;

        private ResponseInfo response;
        private final TextView recipeLabel, recipeCalories, recipeBig3;
        private final AppCompatImageView likeButton;
        private final CheckBox addButton;
        private final int colorPrimaryVariant = Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorPrimaryVariant, requireContext());
        private final int colorOnPrimary = Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorOnPrimary, requireContext());

        public void setSaveState(int state) {
            ISearchResult result = response.getItem();

            String id = "";
            if (result.getItem() instanceof Parsed) {
                id = ((Parsed) result).getFood().getFoodId();
                int index = id.indexOf("food_");
                if (index != -1) {
                    index += 5;
                    id = id.substring(index);
                }
            } else if (result instanceof Hint) {
                id = ((Hint) result).getFood().getFoodId();
                int index = id.indexOf("food_");
                if (index != -1) {
                    index += 5;
                    id = id.substring(index);
                }
            } else if (result instanceof Hit) {
                id = ((Hit) result).getRecipe().getUri();
                int index = id.indexOf("recipe_");
                if (index != -1) {
                    index += 7;
                    id = id.substring(index);
                }
            }

            FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference recRef = FirebaseDatabase.getInstance()
                .getReference("LikedRecipesData")
                .child(authUser.getUid())
                .child(id);

            if (state == REMOVE) {
                saveState = REMOVE;
                recRef.removeValue();
            } else {
                saveState = SAVE;

                if (result instanceof Parsed) {
                    Parsed parsed = (Parsed) result;
                    recRef.setValue(parsed.getFood());
                } else if (result instanceof Hint) {
                    Hint hint = (Hint) result;
                    recRef.setValue(hint.getFood());
                } else if (result instanceof Hit) {
                    Hit hit = (Hit) result;

                    if (hit.getRecipe().getImageBitmapData() == null || hit.getRecipe().getImageBitmapData().equals("")) {
                        hit.getRecipe().setImageBitmapData(
                                Methods.getStringFromBitmap(
                                        Methods.getBitmapFromURL(
                                                hit.getRecipe().getImage()
                                        )
                                )
                        );

                        recRef.setValue(hit.getRecipe());
                    }
                }
            }
        }

        public void setAddState(int state) {
            ISearchResult result = response.getItem();

            FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference dataID = FirebaseDatabase.getInstance()
                    .getReference("CalorieTrackingData")
                    .child(authUser.getUid())
                    .child(response.getResultID());

            if (state == REMOVE) {
                if (calorieTrackingData.remove(response)) {
                    dataID.removeValue();
                }
            } else {
                if (!calorieTrackingData.contains(response)) {
                    calorieTrackingData.add(response);

                    dataID.child("resultID").setValue(response.getResultID());
                    dataID.child("date").setValue(response.getDate());
                    dataID.child("mealType").setValue(response.getMealType());
                    dataID.child("quantity").setValue(response.getQuantity());

                    DatabaseReference itemRef = dataID.child("item");
                    if (result instanceof Parsed) {
                        itemRef.child("food").setValue(((Parsed) result).getFood());
                        itemRef.child("quantity").setValue(((Parsed) result).getQuantity());
                        itemRef.child("measure").setValue(((Parsed) result).getMeasure());
                    } else if (result instanceof Hint) {
                        itemRef.child("food").setValue(((Hint) result).getFood());
                        itemRef.child("measures").setValue(((Hint) result).getMeasures());
                    } else if (result instanceof Hit) {
                        itemRef.child("recipe").setValue(((Hit) result).getRecipe());
                        itemRef.child("_links").setValue(((Hit) result).get_links());
                    }
                }
            }
        }

        public RecipeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.layout_calorie_tracker_search_both_grid, parent, false));

            recipeBig3 = itemView.findViewById(R.id.layout_search_big_3);
            recipeLabel = itemView.findViewById(R.id.layout_search_label);
            recipeCalories = itemView.findViewById(R.id.layout_search_calories);
            likeButton = itemView.findViewById(R.id.liked_search_response);
            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (saveState == REMOVE) {
                        likeButton.setColorFilter(ColorUtils.blendARGB(colorOnPrimary, colorPrimaryVariant, 1.0f));
                        setSaveState(SAVE);
                    } else {
                        likeButton.setColorFilter(ColorUtils.blendARGB(colorPrimaryVariant, colorOnPrimary, 1.0f));
                        setSaveState(REMOVE);
                    }
                }
            });
            addButton = itemView.findViewById(R.id.add_search_response);
            addButton.setChecked(false);
            addButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (response != null) {
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
                            delete.setVisibility(View.GONE);

                            MealType[] tMeals = MealType.values();
                            String[] spinnerOptions = new String[tMeals.length - 1];
                            for (int i = 0; i < tMeals.length - 1; i++) {
                                spinnerOptions[i] = tMeals[i + 1].getSpinnerTitle();
                            }
                            Spinner mealType = Methods.getSpinnerWithAdapter(requireActivity(), popUpView, R.id.dialog_meal_type_spinner, spinnerOptions);

                            int index = response.getResultID().indexOf("&");
                            String tID = response.getResultID().substring(0, index);
                            resultID.setText(tID);
                            date.setText("Today");

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
                                int amount = response.getQuantity();

                                if (amount == 0) {
                                    amount = 1;
                                }

                                label.setText(hint.getFood().getLabel());
                                String tUnits = amount + " " + hint.getMeasures().get(0).getLabel();
                                measure.setText(tUnits);
                            } else if (response.getItem() instanceof Hit) {
                                Hit hit = (Hit) response.getItem();
                                int amount = response.getQuantity();

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
                                    int index = mealType.getSelectedItemPosition();
                                    String tMeal = MealType.values()[index + 1].getSpinnerTitle();
                                    response.setMealType(tMeal);

                                    int tQuant = Integer.parseInt(quantity.getText().toString());
                                    response.setQuantity(tQuant);

                                    setAddState(SAVE);
                                    popupWindow.dismiss();
                                }
                            });
                            AppCompatButton cancelButton = binding.dialogGenericCancelButton;
                            cancelButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    buttonView.setChecked(false);
                                    setAddState(REMOVE);
                                    popupWindow.dismiss();
                                }
                            });

                            popupWindow.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
                        } else {
                            setAddState(REMOVE);
                        }
                    }
                }
            });
        }

        public void bind(ResponseInfo response, boolean isFav, boolean isAdded) throws IOException {
            this.response = response;
            ISearchResult result = response.getItem();

            if (isFav) {
                likeButton.setColorFilter(colorPrimaryVariant);
                saveState = SAVE;
            } else {
                saveState = REMOVE;
            }

            if (isAdded) {
                addButton.setChecked(true);
            } else {
                addButton.setChecked(false);
            }

            String label = "";
            if (result instanceof Parsed) {
                Parsed parsed = (Parsed) result;
                int servings = (int) parsed.getFood().getServingsPerContainer();
                int amount = response.getQuantity();

                if (servings == 0) {
                    servings = 1;
                }
                if (amount == 0) {
                    amount = 1;
                }

                Nutrients nutr = parsed.getFood().getNutrients();
                calSum = (int) (nutr.getENERC_KCAL() / servings * amount);
                carbSum = nutr.getCHOCDF() / servings * amount;
                protSum = nutr.getPROCNT() / servings * amount;
                fatSum = nutr.getFAT() / servings * amount;

                label = parsed.getFood().getLabel();
            } else if (result instanceof Hint) {
                Hint hint = (Hint) result;
                int servings = (int) hint.getFood().getServingsPerContainer();
                int amount = response.getQuantity();

                if (servings == 0) {
                    servings = 1;
                }
                if (amount == 0) {
                    amount = 1;
                }

                Nutrients nutr = hint.getFood().getNutrients();
                calSum = (int) (nutr.getENERC_KCAL() / servings * amount);
                carbSum = nutr.getCHOCDF() / servings * amount;
                protSum = nutr.getPROCNT() / servings * amount;
                fatSum = nutr.getFAT() / servings * amount;

                label = hint.getFood().getLabel();
            } else if (result instanceof Hit) {
                Hit hit = (Hit) result;
                int servings = (int) hit.getRecipe().getYield();
                int amount = response.getQuantity();

                if (servings == 0) {
                    servings = 1;
                }
                if (amount == 0) {
                    amount = 1;
                }

                TotalNutrients nutr = hit.getNutrients();
                calSum = (int) (nutr.getENERC_KCAL().getQuantity() / servings * amount);
                carbSum = nutr.getCHOCDF().getQuantity() / servings * amount;
                protSum = nutr.getPROCNT().getQuantity() / servings * amount;
                fatSum = nutr.getFAT().getQuantity() / servings * amount;

                label = hit.getRecipe().getLabel();
            }

            String tNutr = "Carbs " + decimalFormat.format(carbSum) + "g \u22C5 " +
                    "Fat " + decimalFormat.format(fatSum) + "g \u22c5 " +
                    "Protein " + decimalFormat.format(protSum) + "g";
            this.recipeBig3.setText(tNutr);

            this.recipeLabel.setText(label);

            if (calSum != 0) {
                String calories = calSum + " calories";
                this.recipeCalories.setText(calories);
            } else {
                this.recipeCalories.setVisibility(View.GONE);
            }
        }
    }

    private class RecipeAdapter extends RecyclerView.Adapter<CalorieTrackerSearchFragment.RecipeHolder> {
        private final ArrayList<ResponseInfo> responses;

        public RecipeAdapter(ArrayList<ResponseInfo> responses) {
            this.responses = responses;
        }

        @NonNull
        @Override
        public CalorieTrackerSearchFragment.RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(requireActivity());
            return new CalorieTrackerSearchFragment.RecipeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CalorieTrackerSearchFragment.RecipeHolder holder, int position) {
            ResponseInfo response = this.responses.get(position);
            ISearchResult result = response.getItem();


            boolean isFav = false;
            if (result instanceof Parsed) {
                isFav = likedFoodData.contains(((Parsed) result).getFood());
            } else if (result instanceof Hint) {
                isFav = likedFoodData.contains(((Hint) result).getFood());
            } else if (result instanceof Hit) {
                isFav = likedRecipeData.contains(((Hit) result).getRecipe());
            }

            boolean isAdded = calorieTrackingData.contains(response);


            try {
                holder.bind(response, isFav, isAdded);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public int getItemCount() {
            return this.responses.size();
        }
    }
}