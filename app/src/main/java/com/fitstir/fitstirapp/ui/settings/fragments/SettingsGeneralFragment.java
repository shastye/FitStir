package com.fitstir.fitstirapp.ui.settings.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentSettingsGeneralBinding;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsGeneralFragment extends Fragment {

    private FragmentSettingsGeneralBinding binding;
    private LayoutInflater inflater;
    private String uid;
    private UserProfileData user;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        binding = FragmentSettingsGeneralBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Add additions here

        settingsViewModel.setStillInSettings(true);
        settingsViewModel.setCameFromFragment(this);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user = settingsViewModel.getThisUser().getValue();

        binding.userId.setText(uid);

        TypedArray ta;
        switch (user.getThemeID()) {
            case 0:
                ta = requireContext().obtainStyledAttributes(R.style.Theme1, R.styleable.ThemeColors);
                break;
            case 1:
                ta = requireContext().obtainStyledAttributes(R.style.Theme2, R.styleable.ThemeColors);
                break;
            default:
                ta = requireContext().obtainStyledAttributes(R.style.Theme1, R.styleable.ThemeColors);
                break;
        }
        binding.themeView.setTitle(ta.getString(R.styleable.ThemeColors_android_name));

        binding.themeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SettingsThemeFragment());
            }
        });

        SwitchCompat chooser = binding.unitView.getSwitch();
        chooser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String reference = binding.unitView.getReference();
                DatabaseReference toSet = FirebaseDatabase.getInstance()
                        .getReference("Users")
                        .child(uid)
                        .child(reference);

                if (isChecked) {
                    toSet.setValue(1);
                } else {
                    toSet.setValue(0);
                }
            }
        });
        switch (user.getUnitID()) {
            case 0:
                chooser.setChecked(false);
                break;
            case 1:
                chooser.setChecked(true);
                break;
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
        getParentFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.settings_container_fragment, fragment, "Settings")
                .commit();
    }
}
