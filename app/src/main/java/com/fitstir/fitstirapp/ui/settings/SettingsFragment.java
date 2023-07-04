package com.fitstir.fitstirapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentSettingsBinding;

import org.w3c.dom.Text;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textUserIdSettings;
        settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Add additions here

        TextView theme = root.findViewById(R.id.themeID);
        TextView range = root.findViewById(R.id.rangeID);
        TextView interval = root.findViewById(R.id.intervalID);
        TextView unit = root.findViewById(R.id.unitID);

        theme.setText(root.getResources().getStringArray(R.array.theme_array)[SettingsViewModel.themeID]);
        range.setText(root.getResources().getStringArray(R.array.range_array)[SettingsViewModel.rangeID]);
        interval.setText(root.getResources().getStringArray(R.array.interval_array)[SettingsViewModel.intervalID]);
        unit.setText(root.getResources().getStringArray(R.array.unit_array)[SettingsViewModel.unitID]);

        CardView editButton = root.findViewById(R.id.editbutton_cardView_settings);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_settings_to_navigation_edit_settings);
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