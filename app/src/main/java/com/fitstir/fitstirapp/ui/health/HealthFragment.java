package com.fitstir.fitstirapp.ui.health;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentHealthBinding;
import com.fitstir.fitstirapp.ui.utility.classes.SectionGridAdapter;
import com.fitstir.fitstirapp.ui.utility.classes.SectionItem;

import java.util.ArrayList;

public class HealthFragment extends Fragment {

    private FragmentHealthBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HealthViewModel healthViewModel =
                new ViewModelProvider(this).get(HealthViewModel.class);

        binding = FragmentHealthBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHealth;
        healthViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Addition Text Here

        SectionGridAdapter sectionGridAdapter = new SectionGridAdapter(getHealthSections(), root);
        GridView grid = root.findViewById(R.id.health_grid_view);
        grid.setAdapter(sectionGridAdapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {
                switch(_position) {
                    case 0:
                        Navigation.findNavController(_view).navigate(R.id.action_navigation_health_to_navigation_calorie_tracker);
                        break;
                    case 1:
                        Navigation.findNavController(_view).navigate(R.id.action_navigation_health_to_navigation_food_guide);
                        break;
                    case 2:
                        Navigation.findNavController(_view).navigate(R.id.action_navigation_health_to_navigation_weight_loss);
                        break;
                    case 3:
                        Navigation.findNavController(_view).navigate(R.id.action_navigation_health_to_navigation_recipes);
                        break;
                    case 4:
                        Navigation.findNavController(_view).navigate(R.id.action_navigation_health_to_navigation_find_dietitian);
                        break;
                    case 5:
                        Navigation.findNavController(_view).navigate(R.id.action_navigation_health_to_navigation_diary);
                        break;
                    default:
                        // TODO: Show error?
                        break;
                }
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

    private ArrayList<SectionItem> getHealthSections() {
        return new ArrayList<SectionItem>() {{
            add(new SectionItem(R.drawable.ic_cal_black_200dp, "Calorie Tracker"));
            add(new SectionItem(R.drawable.ic_guide_black_200dp, "Food Guide"));
            add(new SectionItem(R.drawable.ic_scale_black_200dp, "Weight Loss"));
            add(new SectionItem(R.drawable.ic_recipe_black_200dp, "Recipes"));
            add(new SectionItem(R.drawable.ic_loc_black_200dp, "Find Dietitian"));
            add(new SectionItem(R.drawable.ic_diary_black_200dp, "Diary"));
        }};
    }
}