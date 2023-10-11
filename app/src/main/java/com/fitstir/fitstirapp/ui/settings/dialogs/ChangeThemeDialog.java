package com.fitstir.fitstirapp.ui.settings.dialogs;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.databinding.DialogChangeThemeBinding;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.utility.classes.IBasicDialog;
import com.fitstir.fitstirapp.ui.utility.classes.ResetTheme;
import com.fitstir.fitstirapp.ui.utility.classes.UserProfileData;
import com.fitstir.fitstirapp.ui.utility.classes.Users;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeThemeDialog extends IBasicDialog {
    private int theme, primaryColor, primaryColorVariant, secondaryColor, secondaryColorVariant;
    private String userID;
    private SettingsViewModel settingsViewModel;

    public ChangeThemeDialog() { }

    public static ChangeThemeDialog newInstance(int layoutID, int acceptButtonID, int cancelButtonID,
                                                int themeID, int primaryColor, int primaryColorVariant,
                                                int secondaryColor, int secondaryColorVariant, String userID) {
        ChangeThemeDialog  frag = new ChangeThemeDialog();

        Bundle args = new Bundle();
        args.putInt("themeID", themeID);
        args.putInt("primaryColor", primaryColor);
        args.putInt("primaryColorVariant", primaryColorVariant);
        args.putInt("secondaryColor", secondaryColor);
        args.putInt("secondaryColorVariant", secondaryColorVariant);
        args.putString("userID", userID);
        args.putInt("layoutID", layoutID);
        args.putInt("acceptButtonID", acceptButtonID);
        args.putInt("cancelButtonID", cancelButtonID);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);
        DialogChangeThemeBinding binding = DialogChangeThemeBinding.bind(requireView());

        assert getArguments() != null;
        theme = getArguments().getInt("themeID");
        primaryColor = getArguments().getInt("primaryColor");
        primaryColorVariant = getArguments().getInt("primaryColorVariant");
        secondaryColor = getArguments().getInt("secondaryColor");
        secondaryColorVariant = getArguments().getInt("secondaryColorVariant");
        userID = getArguments().getString("userID");

        assert getView() != null;

        binding.primaryColorImage.setBackgroundColor(primaryColor);
        binding.primaryVariantImage.setBackgroundColor(primaryColorVariant);
        binding.secondaryColorImage.setBackgroundColor(secondaryColor);
        binding.secondaryVariantImage.setBackgroundColor(secondaryColorVariant);
    }

    @Override
    public void onAccept() {
        settingsViewModel.setThemeID(theme);

        Users user = settingsViewModel.getThisUser().getValue();
        user.setThemeID(theme);
        settingsViewModel.setThisUser(user);

        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(userID)
                .child("themeID")
                .setValue(theme);

        ResetTheme.changeToTheme(requireActivity(), theme);
    }

    @Override
    public void onCancel() {

    }
}
