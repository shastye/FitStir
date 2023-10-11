package com.fitstir.fitstirapp.ui.settings.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentSettingsBlankBinding;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.utility.classes.IOnBackPressed;

public class SettingsBlankFragment extends Fragment implements IOnBackPressed {

    private FragmentSettingsBlankBinding binding;
    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        binding = FragmentSettingsBlankBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Add additions here

        settingsViewModel.setStillInSettings(false);
        settingsViewModel.setCameFromFragment(this);

        if (settingsViewModel.getCameFromProfile().getValue()) {
            replaceFragment(new SettingsProfileFragment());
        } else {
            replaceFragment(new SettingsMainFragment());
        }

        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void replaceFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container_fragment, fragment)
                .commit();
    }

    @Override
    public boolean onBackPressed() {
        if (settingsViewModel.getStillInSettings().getValue()) {

            if (settingsViewModel.getCameFromProfile().getValue()) {
                settingsViewModel.setCameFromProfile(false);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_settings_to_navigation_profile);
            } else if (settingsViewModel.getCameFromFragment().getValue() instanceof SettingsThemeFragment) {
                replaceFragment(new SettingsGeneralFragment());
            } else {
                replaceFragment(new SettingsMainFragment());
            }
            return true;
        } else {
            return Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).navigateUp();
        }
    }
}
