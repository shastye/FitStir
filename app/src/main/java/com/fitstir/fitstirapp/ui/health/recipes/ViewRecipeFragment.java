package com.fitstir.fitstirapp.ui.health.recipes;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentViewRecipeBinding;
import com.fitstir.fitstirapp.ui.health.HealthViewModel;
import com.fitstir.fitstirapp.ui.health.edamamapi.recipev2.Recipe;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewRecipeFragment extends Fragment {

    private HealthViewModel healthViewModel;
    private FragmentViewRecipeBinding binding;
    private Recipe clickedRecipe;
    private ValueAnimator buttonColorAnimator = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        healthViewModel = new ViewModelProvider(requireActivity()).get(HealthViewModel.class);

        binding = FragmentViewRecipeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textViewRecipe;
        //healthViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Addition Text Here

        clickedRecipe = healthViewModel.getClickedRecipe().getValue();

        TextView recipeName = root.findViewById(R.id.recipe_view_label);
        recipeName.setText(clickedRecipe.getLabel());


        ShapeableImageView recipeImage = binding.viewRecipeImage;

        Bitmap temp = null;
        if (clickedRecipe.getImageBitmapData() != null && clickedRecipe.getImageBitmapData().length() != 0) {
            temp = Methods.getBitmapFromString(clickedRecipe.getImageBitmapData());
        } else {
            temp = Methods.getBitmapFromURL(clickedRecipe.getImage());
            clickedRecipe.setImageBitmapData(Methods.getStringFromBitmap(temp));
        }
        recipeImage.setImageBitmap(temp);

        TextView time = binding.viewRecipeTime;
        TextView calories = binding.viewRecipeCal;
        TextView yield = binding.viewRecipeYield;
        TextView diet = binding.viewRecipeDiet;
        TextView health = binding.viewRecipeHealth;
        TextView cuisine = binding.viewRecipeCuis;
        TextView dish = binding.viewRecipeDish;
        TextView meal = binding.viewRecipeMeal;
        TextView ingredients = binding.viewRecipeIngr;
        TextView instructions = binding.viewRecipeInstr;

        LinearLayoutCompat timeRow = binding.timeRow;
        LinearLayoutCompat calRow = binding.calRow;
        LinearLayoutCompat yieldRow = binding.yieldRow;
        LinearLayoutCompat dietRow = binding.dietRow;
        LinearLayoutCompat healthRow = binding.healthRow;
        LinearLayoutCompat cuisRow = binding.cuisRow;
        LinearLayoutCompat dishRow = binding.dishRow;
        LinearLayoutCompat mealRow = binding.mealRow;

        if (clickedRecipe.getTotalTime() != 0.0f) {
            String tTime = (int) clickedRecipe.getTotalTime() + " minutes";
            time.setText(tTime);
        } else {
            timeRow.setVisibility(View.GONE);
        }

        if (clickedRecipe.getCalories() != 0.0f) {
            String tCal = ((int) clickedRecipe.getCalories() / (int) clickedRecipe.getYield()) + " cal / serv";
            calories.setText(tCal);
        } else {
            calRow.setVisibility(View.GONE);
        }

        if (clickedRecipe.getYield() != 0.0f) {
            String tYield = (int) clickedRecipe.getYield() + " servings";
            yield.setText(tYield);
        } else {
            yieldRow.setVisibility(View.GONE);
        }

        ArrayList<String> dietList = clickedRecipe.getDietLabels();
        StringBuilder printedDiet = new StringBuilder();
        if (dietList == null || dietList.size() == 0) {
            dietRow.setVisibility(View.GONE);
        } else if (dietList.size() == 1) {
            printedDiet.append(dietList.get(0));
        } else {
            for (int i = 0; i < dietList.size(); i++) {
                printedDiet.append((char) (0x2460 + i));
                printedDiet.append("  ");
                printedDiet.append(dietList.get(i));
                if (i != dietList.size() - 1) {
                    printedDiet.append("\n");
                }

                if (i == 19) {
                    break;
                }
            }
        }
        diet.setText(printedDiet.toString());

        ArrayList<String> healthList = clickedRecipe.getHealthLabels();
        StringBuilder printedHealth = new StringBuilder();
        if (healthList == null || healthList.size() == 0) {
            healthRow.setVisibility(View.GONE);
        } else if (healthList.size() == 1) {
            printedHealth.append(healthList.get(0));
        } else {
            for (int i = 0; i < healthList.size(); i++) {
                printedHealth.append((char) (0x2460 + i));
                printedHealth.append("  ");
                printedHealth.append(healthList.get(i));
                if (i != healthList.size() - 1) {
                    printedHealth.append("\n");
                }

                if (i == 19) {
                    break;
                }
            }
        }
        health.setText(printedHealth.toString());

        ArrayList<String> cuisineList = clickedRecipe.getCuisineType();
        StringBuilder printedCuisine = new StringBuilder();
        if (cuisineList == null || cuisineList.size() == 0) {
            cuisRow.setVisibility(View.GONE);
        } else if (cuisineList.size() == 1) {
            printedCuisine.append(cuisineList.get(0));
        } else {
            for (int i = 0; i < cuisineList.size(); i++) {
                printedCuisine.append((char) (0x2460 + i));
                printedCuisine.append("  ");
                printedCuisine.append(cuisineList.get(i));
                if (i != cuisineList.size() - 1) {
                    printedCuisine.append("\n");
                }

                if (i == 19) {
                    break;
                }
            }
        }
        cuisine.setText(printedCuisine.toString());

        ArrayList<String> dishList = clickedRecipe.getDishType();
        StringBuilder printedDish = new StringBuilder();
        if (dishList == null || dishList.size() == 0) {
            dishRow.setVisibility(View.GONE);
        } else if (dishList.size() == 1) {
            printedDish.append(dishList.get(0));
        } else {
            for (int i = 0; i < dishList.size(); i++) {
                printedDish.append((char) (0x2460 + i));
                printedDish.append("  ");
                printedDish.append(dishList.get(i));
                if (i != dishList.size() - 1) {
                    printedDish.append("\n");
                }

                if (i == 19) {
                    break;
                }
            }
        }
        dish.setText(printedDish.toString());

        ArrayList<String> mealList = clickedRecipe.getMealType();
        StringBuilder printedMeal = new StringBuilder();
        if (mealList == null || mealList.size() == 0) {
            mealRow.setVisibility(View.GONE);
        } else if (mealList.size() == 1) {
            printedMeal.append(mealList.get(0));
        } else {
            for (int i = 0; i < mealList.size(); i++) {
                printedMeal.append((char) (0x2460 + i));
                printedMeal.append("  ");
                printedMeal.append(mealList.get(i));
                if (i != mealList.size() - 1) {
                    printedMeal.append("\n");
                }

                if (i == 19) {
                    break;
                }
            }
        }
        meal.setText(printedMeal.toString());



        ArrayList<String> ingrList = clickedRecipe.getIngredientLines();
        StringBuilder printedIngr = new StringBuilder();
        for (int i = 0; i < ingrList.size(); i++) {
            printedIngr.append((char) (0x2460 + i));
            printedIngr.append("  ");
            printedIngr.append(ingrList.get(i));
            if (i != ingrList.size() - 1) {
                printedIngr.append("\n\n");
            }
        }
        ingredients.setText(printedIngr.toString());

        instructions.setText(healthViewModel.getInstructionsList().getValue());

        // For if we buy the feature that allows this
        /*ArrayList<String> instrList = clickedRecipe.getInstructionLines();
        StringBuilder printedInstr = new StringBuilder();
        for (int i = 0; i < ingrList.size(); i++) {
            printedInstr.append((char) (0x2460 + i));
            printedInstr.append("  ");
            printedInstr.append(instrList.get(i));
            printedInstr.append("\n\n");
        }
        ingredients.setText(printedInstr.toString());*/



        int colorPrimaryVariant = Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorPrimaryVariant, requireContext());
        int colorOnPrimary = Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorOnPrimary, requireContext());

        ImageView likeButton = root.findViewById(R.id.recipe_toolbar_heart_icon);

        if (healthViewModel.getLikedRecipes().getValue().contains(clickedRecipe)) {
            likeButton.setColorFilter(colorPrimaryVariant);

            buttonColorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorOnPrimary, colorPrimaryVariant);
            buttonColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                    likeButton.setColorFilter((int) animation.getAnimatedValue());
                }
            });
            buttonColorAnimator.start();
        } else {
            likeButton.setColorFilter(colorOnPrimary);
            buttonColorAnimator = null;
        }

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonColorAnimator != null) {
                    buttonColorAnimator.reverse();
                    buttonColorAnimator = null;

                    healthViewModel.getLikedRecipes().getValue().remove(clickedRecipe);
                } else {
                    buttonColorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorOnPrimary, colorPrimaryVariant);
                    buttonColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                            likeButton.setColorFilter((int) animation.getAnimatedValue());
                        }
                    });
                    buttonColorAnimator.start();

                    if (!healthViewModel.getLikedRecipes().getValue().contains(clickedRecipe)) {
                        healthViewModel.getLikedRecipes().getValue().add(clickedRecipe);
                    }
                }
            }
        });

        // End

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

        UserProfileData user = healthViewModel.getThisUser().getValue();
        user.setLikedRecipes(healthViewModel.getLikedRecipes().getValue());
        healthViewModel.setThisUser(user);

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        assert authUser != null;
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(authUser.getUid());
        userReference.setValue(healthViewModel.getThisUser().getValue());

        binding = null;
    }
}