package com.fitstir.fitstirapp.ui.settings.fragments;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentSettingsThemeBinding;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.settings.dialogs.ChangeThemeDialog;
import com.fitstir.fitstirapp.ui.settings.customviews.SettingsThemeItemView;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class SettingsThemeFragment extends Fragment {

    private FragmentSettingsThemeBinding binding;
    private SettingsViewModel settingsViewModel;
    private String uid;
    private UserProfileData user;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        binding = FragmentSettingsThemeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Add additions here

        settingsViewModel.setStillInSettings(true);
        settingsViewModel.setCameFromFragment(this);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user = settingsViewModel.getThisUser().getValue();

        binding.userId.setText(uid);

        ArrayList<SettingsThemeItemView> themeViews = new ArrayList<SettingsThemeItemView>() {{
            add(binding.theme1View);
            add(binding.theme2View);
        }};

        for (int i = 0; i < themeViews.size(); i++) {
            setThemeView(i, themeViews.get(i));
        }


        // End

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setThemeView(int themeID, SettingsThemeItemView view) {
        TypedArray ta;
        switch (themeID) {
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

        int p = ta.getColor(R.styleable.ThemeColors_colorPrimary, Color.BLACK);
        int pv = ta.getColor(R.styleable.ThemeColors_colorPrimaryVariant, Color.BLACK);
        int s = ta.getColor(R.styleable.ThemeColors_colorSecondary, Color.BLACK);
        int sv = ta.getColor(R.styleable.ThemeColors_colorSecondaryVariant, Color.BLACK);
        String t = ta.getString(R.styleable.ThemeColors_android_name);

        view.setPrimaryColor(p);
        view.setPrimaryVariantColor(pv);
        view.setSecondaryColor(s);
        view.setSecondaryVariant(sv);
        view.setTitle(t);

        if (user.getThemeID() == themeID) {
            view.setBackgroundResource(R.drawable.rounded_a80_section_background);
            view.setClickable(false);
        } else {
            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            view.setBackgroundResource(outValue.resourceId);
            view.setClickable(true);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChangeThemeDialog.newInstance(
                            R.layout.dialog_change_theme,
                            R.id.dialog_accept_button,
                            R.id.dialog_cancel_button,
                            themeID,
                            p,
                            pv,
                            s,
                            sv,
                            uid
                    ).show(getParentFragmentManager(), "Change Theme");
                }
            });
        }
    }

    public void replaceFragment(Fragment fragment) {
        getParentFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.settings_container_fragment, fragment, "Settings")
                .commit();
    }
}
