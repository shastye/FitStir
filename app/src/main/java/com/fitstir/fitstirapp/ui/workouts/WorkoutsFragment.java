package com.fitstir.fitstirapp.ui.workouts;

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
import com.fitstir.fitstirapp.databinding.FragmentWorkoutsBinding;
import com.fitstir.fitstirapp.ui.utility.classes.SectionGridAdapter;
import com.fitstir.fitstirapp.ui.utility.classes.SectionItem;

import java.util.ArrayList;

public class WorkoutsFragment extends Fragment {

    private FragmentWorkoutsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WorkoutsViewModel workoutsViewModel =
                new ViewModelProvider(this).get(WorkoutsViewModel.class);

        binding = FragmentWorkoutsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textWorkouts;
        workoutsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Addition Text Here

        SectionGridAdapter sectionGridAdapter = new SectionGridAdapter(getWorkoutsSections(), root);
        GridView grid = binding.workoutsGridView;
        grid.setAdapter(sectionGridAdapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        Navigation.findNavController(view).navigate(R.id.action_navigation_workouts_to_navigation_run_club);
                        break;
                    case 1:
                        Navigation.findNavController(view).navigate(R.id.action_navigation_workouts_to_navigation_upper_body);
                        break;
                    case 2:
                        Navigation.findNavController(view).navigate(R.id.action_navigation_workouts_to_navigation_lower_body);
                        break;
                    case 3:
                        Navigation.findNavController(view).navigate(R.id.action_navigation_workouts_to_navigation_weight_lifting);
                        break;
                    case 4:
                        Navigation.findNavController(view).navigate(R.id.action_navigation_workouts_to_navigation_circuit_workouts);
                        break;
                    case 5:
                        Navigation.findNavController(view).navigate(R.id.action_navigation_workouts_to_navigation_yoga);
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



    private ArrayList<SectionItem> getWorkoutsSections() {
        return new ArrayList<SectionItem>() {{
            add(new SectionItem(R.drawable.ic_shoe_black_200dp, "Run Club"));
            add(new SectionItem(R.drawable.ic_bicep_black_200dp, "Upper Body"));
            add(new SectionItem(R.drawable.ic_legs_black_200dp, "Lower Body"));
            add(new SectionItem(R.drawable.ic_lifting_black_200dp, "Weight Lifting"));
            add(new SectionItem(R.drawable.ic_cir_black_200dp, "Circuit Workouts"));
            add(new SectionItem(R.drawable.ic_yoga_black_200dp, "Yoga"));
        }};
    }
}