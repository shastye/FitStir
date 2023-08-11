package com.fitstir.fitstirapp.ui.health.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentRecipesBinding;
import com.fitstir.fitstirapp.ui.health.HealthViewModel;
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
    private final int STANDARD_APPBAR = 0;
    private final int SEARCH_APPBAR = 1;
    private boolean isLoading;
    private int appBarState;

    private FragmentRecipesBinding binding;
    private View root;
    private HealthViewModel healthViewModel;
    private RecipeResponse response;
    private ArrayList<Hit> hits;
    private Hit firstHit;

    private RecyclerView hitRecyclerView;
    private HitAdapter hitAdapter;
    private Parcelable recyclerViewState;

    private ConstraintLayout recipeResponse;
    private AppBarLayout viewRecipeBar, searchRecipeBar;
    private TextView labelRecipeBar, centerMessage;
    private EditText searchBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        healthViewModel = new ViewModelProvider(requireActivity()).get(HealthViewModel.class);

        binding = FragmentRecipesBinding.inflate(inflater, container, false);
        root = binding.getRoot();


        // Addition Text Here

        isLoading = false;
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        viewRecipeBar = root.findViewById(R.id.recipe_view_toolbar);
        searchRecipeBar = root.findViewById(R.id.recipe_search_toolbar);
        labelRecipeBar = root.findViewById(R.id.recipe_search_label);
        centerMessage = binding.textRecipes;
        centerMessage.setText("Search for a Recipe.");
        recipeResponse = binding.recipeSearchResponse;
        CardView firstHitBackground = binding.recipeMainBackground;

        if (healthViewModel.getHits().getValue() == null || healthViewModel.getHits().getValue().equals(new ArrayList<>())) {
            labelRecipeBar.setText("");
            centerMessage.setVisibility(View.VISIBLE);
            recipeResponse.setVisibility(View.INVISIBLE);
        } else {
            try {
                search();
            } catch (IOException | ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

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

        firstHitBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthViewModel.setClickedRecipe(firstHit.getRecipe());
                Navigation.findNavController(v).navigate(R.id.action_navigation_recipes_to_navigation_view_recipe);
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
        viewRecipeBar = root.findViewById(R.id.recipe_view_toolbar);
        searchRecipeBar = root.findViewById(R.id.recipe_search_toolbar);
        labelRecipeBar = root.findViewById(R.id.recipe_search_label);
        centerMessage = binding.textRecipes;
        recipeResponse = binding.recipeSearchResponse;

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
        api.execute();

        response = api.getRecipeResponse();

        hits = response.getHits();
        healthViewModel.setHits(hits);

        if (hits.size() != 0) {
            firstHit = hits.get(0);
            healthViewModel.setFirstHit(firstHit);
            hits.remove(firstHit);
            healthViewModel.setHits(hits);

            if (hits.size() != 0) {
                hitRecyclerView = binding.recipeRecyclerView;
                hitRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 2));

                hitRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                        if (!isLoading) {
                            if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == hits.size() - 1) {
                                loadMore();
                                isLoading = true;
                            }
                        }
                    }
                });

                updateUI(hits);
            }

            setAppBarState(STANDARD_APPBAR);
            labelRecipeBar.setText(searchBar.getText().toString());
            centerMessage.setVisibility(View.INVISIBLE);
            recipeResponse.setVisibility(View.VISIBLE);

            binding.recipeMainImage.setImageBitmap(getBitmapFromURL(firstHit.getRecipe().getImage()));
            binding.recipeMainLabel.setText(firstHit.getRecipe().getLabel());
            binding.recipeMainSource.setText(firstHit.getRecipe().getSource());
            float tCalPerServing = firstHit.getRecipe().getCalories() / firstHit.getRecipe().getYield();
            String tCal = (int) tCalPerServing + " calories / serving";
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

    private void loadMore() {
        hits.add(null);
        hitAdapter.notifyItemInserted(hits.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hits.remove(hits.size() - 1);
                int scrollPosition = hits.size();
                hitAdapter.notifyItemRemoved(scrollPosition);

                response.loadMore();

                hitAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }

    private void toggleToolBarState() {
        Log.d("RECIPE FRAGMENT", "toggleToolBarState: toggling AppBarState.");
        if (appBarState == STANDARD_APPBAR) {
            setAppBarState(SEARCH_APPBAR);
        } else {
            setAppBarState(STANDARD_APPBAR);
        }
    }

    private void setAppBarState(int state) {
        appBarState = state;

        if (appBarState == STANDARD_APPBAR) {
            searchRecipeBar.setVisibility(View.GONE);
            viewRecipeBar.setVisibility(View.VISIBLE);

            InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            try {
                im.hideSoftInputFromWindow(root.getWindowToken(), 0); // make keyboard hide
            } catch (NullPointerException e) {
                Log.d("RECIPE FRAGMENT", "setAppBaeState: NullPointerException: " + e);
            }
        } else if (appBarState == SEARCH_APPBAR) {
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
        hitRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        hitAdapter = new HitAdapter(hits);
        hitRecyclerView.setAdapter(hitAdapter);
    }

    private class LoadHolder extends RecyclerView.ViewHolder {
        private ProgressBar bar;

        public LoadHolder(View view) {
            super(view);

            bar = itemView.findViewById(R.id.recipe_more_progress_bar);
            int colorOnPrimary = Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorOnPrimary, requireContext());
            bar.getIndeterminateDrawable().setColorFilter(new BlendModeColorFilter(colorOnPrimary, BlendMode.SRC_ATOP));
        }
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

            if (hit.getRecipe().getCalories() != 0.0f) {
                float tCalPerServing = hit.getRecipe().getCalories() / hit.getRecipe().getYield();
                String calories = (int) tCalPerServing + " calories";
                this.recipeCalories.setText(calories);
            } else {
                this.recipeCalories.setVisibility(View.GONE);
            }

            if (hit.getRecipe().getTotalTime() != 0.0f) {
                String time = String.valueOf((int) hit.getRecipe().getTotalTime()) + " minutes";
                this.recipeTime.setText(time);
            } else {
                this.recipeTime.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            healthViewModel.setClickedRecipe(hit.getRecipe());
            Navigation.findNavController(v).navigate(R.id.action_navigation_recipes_to_navigation_view_recipe);
        }
    }

    private class HitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final ArrayList<Hit> hits;
        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;

        public HitAdapter(ArrayList<Hit> hits) {
            this.hits = hits;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_ITEM) {
                LayoutInflater layoutInflater = LayoutInflater.from(requireActivity());
                return new HitHolder(layoutInflater, parent);
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_recipe_grid, parent, false);
                return new LoadHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof HitHolder) {
                Hit hit = this.hits.get(position);
                ((HitHolder) holder).bind(hit);
            } else if (holder instanceof LoadHolder) {
                holder = ((LoadHolder) holder);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return hits.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }

        @Override
        public int getItemCount() {
            return hits.size();
        }
    }
}