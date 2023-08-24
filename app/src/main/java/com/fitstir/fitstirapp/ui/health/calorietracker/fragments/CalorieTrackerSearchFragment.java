package com.fitstir.fitstirapp.ui.health.calorietracker.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentCalorieTrackerSearchBinding;
import com.fitstir.fitstirapp.ui.health.calorietracker.CalorieTrackerViewModel;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.CategoryOptions;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.CuisineType;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.DietOptions;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.HealthOptions;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.MealType;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.NutritionType;
import com.fitstir.fitstirapp.ui.health.edamamapi.enums.SearchOptions;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.EdamamAPI_FoodDatabaseParser;
import com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser.FoodResponse;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.EdamamAPI_RecipesV2;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Hit;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.RecipeResponse;
import com.fitstir.fitstirapp.ui.utility.Methods;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CalorieTrackerSearchFragment extends Fragment {

    private FragmentCalorieTrackerSearchBinding binding;
    private View root;
    private CalorieTrackerViewModel calorieTrackerViewModel;
    private RecipeResponse recipeResponse;
    private FoodResponse foodResponse;
    private ArrayList<Hit> hits;

    private AppCompatSpinner searchOptionsSpinner;
    private AppCompatImageView filterButton;
    private EditText searchBar;

    private RecyclerView searchRecyclerView;
    private Parcelable recyclerViewState;
    private RecipeAdapter recipeAdapter;

    private boolean isRecipeSearch;
    private boolean isLoading;
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
        isLoading = false;
        decimalFormat = new DecimalFormat("####.#");

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
                    // TODO: Do the update
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

        // TODO: Do the update
        updateUI();
    }

    private void updateUI() {
        if (isRecipeSearch) {
            ArrayList<Hit> hits = calorieTrackerViewModel.getHits().getValue();
            if (hits != null && hits.size() != 0) {
                searchRecyclerView = root.findViewById(R.id.search_response_rv);
                searchRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 2));

                searchRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                recipeAdapter = new CalorieTrackerSearchFragment.RecipeAdapter(hits);
                searchRecyclerView.setAdapter(recipeAdapter);
            }
        } else {

        }
    }

    private class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Hit recipe;
        private final ImageView recipeImage;
        private final TextView recipeLabel, recipeCalories;

        public RecipeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.layout_recipe_calorietracker_grid, parent, false));
            itemView.setOnClickListener(this);

            recipeImage = itemView.findViewById(R.id.layout_recipe_image);
            recipeLabel = itemView.findViewById(R.id.layout_recipe_label);
            recipeCalories = itemView.findViewById(R.id.layout_recipe_calories);
        }

        public void bind(Hit recipe) throws IOException {
            this.recipe = recipe;

            this.recipeImage.setImageBitmap(Methods.getBitmapFromURL(recipe.getRecipe().getImage()));

            this.recipeLabel.setText(recipe.getRecipe().getLabel());

            if (recipe.getRecipe().getCalories() != 0.0f) {
                float tCalPerServing = recipe.getRecipe().getCalories() / recipe.getRecipe().getYield();
                String calories = (int) tCalPerServing + " calories";
                this.recipeCalories.setText(calories);
            } else {
                this.recipeCalories.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class RecipeAdapter extends RecyclerView.Adapter<CalorieTrackerSearchFragment.RecipeHolder> {
        private final ArrayList<Hit> recipes;

        public RecipeAdapter(ArrayList<Hit> recipes) {
            this.recipes = recipes;
        }

        @NonNull
        @Override
        public CalorieTrackerSearchFragment.RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(requireActivity());
            return new CalorieTrackerSearchFragment.RecipeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CalorieTrackerSearchFragment.RecipeHolder holder, int position) {
            Hit recipe = this.recipes.get(position);
            try {
                holder.bind(recipe);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public int getItemCount() {
            return this.recipes.size();
        }
    }
}