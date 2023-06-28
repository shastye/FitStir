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

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentHealthBinding;
import com.fitstir.fitstirapp.ui.utility.SectionGridAdapter;
import com.fitstir.fitstirapp.ui.utility.Tags;

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

        SectionGridAdapter sectionGridAdapter = new SectionGridAdapter(Tags.HEALTH_SECTION, root);
        GridView grid = root.findViewById(R.id.health_grid_view);
        grid.setAdapter(sectionGridAdapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {
                switch(_position) {
                    case 0:
                        // Navigate to section
                        break;
                    case 1:
                        // Navigate to section
                        break;
                    case 2:
                        // Navigate to section
                        break;
                    case 3:
                        // Navigate to section
                        break;
                    case 4:
                        // Navigate to section
                        break;
                    case 5:
                        // Navigate to section
                        break;
                    default:
                        // Show error?
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
}