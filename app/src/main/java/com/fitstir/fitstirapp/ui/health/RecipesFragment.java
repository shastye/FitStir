package com.fitstir.fitstirapp.ui.health;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentRecipesBinding;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.recipev2.EdamamAPI_RecipesV2;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.recipev2.Hit;
import com.fitstir.fitstirapp.ui.utility.classes.edamamapi.recipev2.RecipeResponse;
import com.fitstir.fitstirapp.ui.utility.enums.CuisineType;
import com.fitstir.fitstirapp.ui.utility.enums.DietOptions;
import com.fitstir.fitstirapp.ui.utility.enums.HealthOptions;
import com.fitstir.fitstirapp.ui.utility.enums.MealType;
import com.google.android.material.appbar.AppBarLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RecipesFragment extends Fragment {

    private FragmentRecipesBinding binding;
    private static final int STANDARD_APPBAR = 0;
    private static final int SEARCH_APPBAR = 1;
    private int mAppBarState;
    private ConstraintLayout recipeResponse;
    private AppBarLayout viewRecipeBar, searchRecipeBar;
    private TextView labelRecipeBar, centerMessage;
    private EditText searchBar;
    private View root;
    private HealthViewModel healthViewModel;
    private RecyclerView hitRecyclerView;
    private HitAdapter hitAdapter;
    private RecipeResponse response;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        healthViewModel = new ViewModelProvider(requireActivity()).get(HealthViewModel.class);

        binding = FragmentRecipesBinding.inflate(inflater, container, false);
        root = binding.getRoot();


        // Addition Text Here

        viewRecipeBar = root.findViewById(R.id.recipe_view_toolbar);
        searchRecipeBar = root.findViewById(R.id.recipe_search_toolbar);
        labelRecipeBar = root.findViewById(R.id.recipe_search_label);
        labelRecipeBar.setText("");
        centerMessage = binding.textRecipes;
        centerMessage.setVisibility(View.VISIBLE);
        centerMessage.setText("Search for a Recipe.");
        recipeResponse = binding.recipeSearchResponse;
        recipeResponse.setVisibility(View.INVISIBLE);

        setAppBarState(STANDARD_APPBAR);

        AppCompatImageView searchRecipe = root.findViewById(R.id.recipe_toolbar_search_icon);
        searchRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleToolBarState();
            }
        });

        AppCompatImageView backArrow = root.findViewById(R.id.recipe_toolbar_back_arrow_icon);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleToolBarState();
            }
        });

        AppCompatImageView filter = root.findViewById(R.id.recipe_toolbar_filter_icon);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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



                min_ingr.setText(healthViewModel.getMinIngr().getValue());
                max_ingr.setText(healthViewModel.getMaxIngr().getValue());
                min_cal.setText(healthViewModel.getMinCal().getValue());
                max_cal.setText(healthViewModel.getMaxCal().getValue());
                min_time.setText(healthViewModel.getMinTime().getValue());
                max_time.setText(healthViewModel.getMaxTime().getValue());

                diet.setSelection(healthViewModel.getDietType().getValue());
                health.setSelection(healthViewModel.getHealthType().getValue());
                cuisine.setSelection(healthViewModel.getCuisineType().getValue());
                meal.setSelection(healthViewModel.getMealType().getValue());

                popupWindow.showAsDropDown(filter, 0,0);



                AppCompatButton accept = popUpView.findViewById(R.id.recipe_accept_button);
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        healthViewModel.setMinIngr(min_ingr.getText().toString().trim());
                        healthViewModel.setMaxIngr(max_ingr.getText().toString().trim());
                        healthViewModel.setMinCal(min_cal.getText().toString().trim());
                        healthViewModel.setMaxCal(max_cal.getText().toString().trim());
                        healthViewModel.setMinTime(min_time.getText().toString().trim());
                        healthViewModel.setMaxTime(max_time.getText().toString().trim());

                        healthViewModel.setDietType(diet.getSelectedItemPosition());
                        healthViewModel.setHealthType(health.getSelectedItemPosition());
                        healthViewModel.setCuisineType(cuisine.getSelectedItemPosition());
                        healthViewModel.setMealType(meal.getSelectedItemPosition());

                        popupWindow.dismiss();

                        if (searchBar != null && !searchBar.getText().toString().equals("")) {
                            healthViewModel.setToSearchFor(searchBar.getText().toString());
                            try {
                                search();
                            } catch (IOException | ExecutionException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        });

        searchBar = root.findViewById(R.id.recipe_toolbar_search_edit_text);
        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if(keyCode == KeyEvent.KEYCODE_ENTER)
                    {
                        if (!searchBar.getText().toString().equals("")) {
                            healthViewModel.setToSearchFor(searchBar.getText().toString());
                            try {
                                search();
                            } catch (IOException | ExecutionException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return true;
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
                    if (!searchBar.getText().toString().equals("")) {
                        healthViewModel.setToSearchFor(searchBar.getText().toString());
                        try {
                            search();
                        } catch (IOException | ExecutionException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return true;
                }
                return false;
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

    private void search() throws IOException, ExecutionException, InterruptedException {
        // TODO: UPDATE ADAPTER AND DISPLAY RECIPES
        healthViewModel.setToSearchFor(searchBar.getText().toString());

        EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2(
                healthViewModel.getToSearchFor().getValue(),
                healthViewModel.getMinIngr().getValue(),
                healthViewModel.getMaxIngr().getValue(),
                DietOptions.values()[healthViewModel.getDietType().getValue()].getKey(),
                HealthOptions.values()[healthViewModel.getHealthType().getValue()].getKey(),
                CuisineType.values()[healthViewModel.getCuisineType().getValue()].getKey(),
                MealType.values()[healthViewModel.getMealType().getValue()].getKey(),
                "",
                healthViewModel.getMinCal().getValue(),
                healthViewModel.getMaxCal().getValue(),
                healthViewModel.getMinTime().getValue(),
                healthViewModel.getMaxTime().getValue()
        );

        /*EdamamAPI_RecipesV2 api = new EdamamAPI_RecipesV2(
                "chicken parm",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "120",
                "150");*/
        api.execute();

        response = api.getRecipeResponse();

        ArrayList<Hit> hits = response.getHits();
        if (hits.size() != 0) {
            Hit firstHit = hits.get(0);
            healthViewModel.setFirstRecipe(firstHit.getRecipe());
            hits.remove(firstHit);

            if (hits.size() != 0) {
                hitRecyclerView = binding.recipeRecyclerView;
                hitRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 2));

                healthViewModel.setHits(hits);
                updateUI(hits);
            }

            setAppBarState(STANDARD_APPBAR);
            labelRecipeBar.setText(searchBar.getText().toString());
            centerMessage.setVisibility(View.INVISIBLE);
            recipeResponse.setVisibility(View.VISIBLE);

            binding.recipeMainImage.setImageBitmap(getBitmapFromURL(firstHit.getRecipe().getImage()));
            binding.recipeMainLabel.setText(firstHit.getRecipe().getLabel());
            binding.recipeMainSource.setText(firstHit.getRecipe().getSource());
            String tCal = (int) firstHit.getRecipe().getCalories() + " calories";
            binding.recipeMainCalories.setText(tCal);
            String tTime = (int) firstHit.getRecipe().getTotalTime() + " minutes";
            binding.recipeMainTotalTime.setText(tTime);
            String tYield = (int) firstHit.getRecipe().getYield() + " servings";
            binding.recipeMainYeild.setText(tYield);
            binding.recipeMainMealType.setText(firstHit.getRecipe().getMealType().get(0));
            binding.recipeMainCuisineType.setText(firstHit.getRecipe().getCuisineType().get(0));

        } else {
            centerMessage.setVisibility(View.VISIBLE);
            centerMessage.setText("No result found.\n\nTry broadening your search.");
        }
    }

    private void toggleToolBarState() {
        Log.d("RECIPE FRAGMENT", "toggleToolBarState: toggling AppBarState.");
        if (mAppBarState == STANDARD_APPBAR) {
            setAppBarState(SEARCH_APPBAR);
        } else {
            setAppBarState(STANDARD_APPBAR);
        }
    }

    private void setAppBarState(int state) {
        mAppBarState = state;

        if (mAppBarState == STANDARD_APPBAR) {
            searchRecipeBar.setVisibility(View.GONE);
            viewRecipeBar.setVisibility(View.VISIBLE);

            InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            try {
                im.hideSoftInputFromWindow(root.getWindowToken(), 0); // make keyboard hide
            } catch (NullPointerException e) {
                Log.d("RECIPE FRAGMENT", "setAppBaeState: NullPointerException: " + e);
            }
        } else if (mAppBarState == SEARCH_APPBAR) {
            viewRecipeBar.setVisibility(View.GONE);
            searchRecipeBar.setVisibility(View.VISIBLE);

            InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            im.showSoftInput(root, InputMethodManager.SHOW_IMPLICIT); // make keyboard popup
        }
    }

    private Bitmap getBitmapFromURL(String url) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Bitmap> bitmapFuture = executor.submit(() -> {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();

                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);

                return myBitmap;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            return bitmapFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateUI(ArrayList<Hit> hits) {
        hitAdapter = new HitAdapter(hits);
        hitRecyclerView.setAdapter(hitAdapter);
    }

    private class HitHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Hit hit;
        private final ImageView recipeImage;
        private final TextView recipeLabel, recipeSource, recipeCalories, recipeTime;

        public HitHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.layout_recipe_grid, parent, false));
            itemView.setOnClickListener(this);

            recipeImage = itemView.findViewById(R.id.layout_recipe_image);
            recipeLabel = itemView.findViewById(R.id.layout_recipe_label);
            recipeSource = itemView.findViewById(R.id.layout_recipe_source);
            recipeCalories = itemView.findViewById(R.id.layout_recipe_calories);
            recipeTime = itemView.findViewById(R.id.layout_recipe_total_time);
        }

        public void bind(Hit hit) {
            this.hit = hit;

            this.recipeImage.setImageBitmap(getBitmapFromURL(hit.getRecipe().getImage()));
            this.recipeLabel.setText(hit.getRecipe().getLabel());
            this.recipeSource.setText(hit.getRecipe().getSource());
            String calories = String.valueOf((int) hit.getRecipe().getCalories()) + " calories";
            this.recipeCalories.setText(calories);
            String time = String.valueOf((int) hit.getRecipe().getTotalTime()) + " minutes";
            this.recipeTime.setText(time);
        }

        @Override
        public void onClick(View v) {
            healthViewModel.setClickedRecipe(hit.getRecipe());
            //Navigation.findNavController(v).navigate(R.id.action_navigation_recipes_to_navigation_view_recipe);
        }
    }

    private class HitAdapter extends RecyclerView.Adapter<HitHolder> {
        private final ArrayList<Hit> hits;

        public HitAdapter(ArrayList<Hit> hits) {
            this.hits = hits;
        }

        @NonNull
        @Override
        public HitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(requireActivity());
            return new HitHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull HitHolder holder, int position) {
            Hit hit = this.hits.get(position);
            holder.bind(hit);
        }

        @Override
        public int getItemCount() {
            return hits.size();
        }
    }
}