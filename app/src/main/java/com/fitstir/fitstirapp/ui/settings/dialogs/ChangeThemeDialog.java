package com.fitstir.fitstirapp.ui.settings.dialogs;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.utility.classes.IBasicAlertDialog;
import com.fitstir.fitstirapp.ui.utility.classes.ResetTheme;

import java.util.Objects;

public class ChangeThemeDialog extends IBasicAlertDialog {
    private int theme, range, interval, unit;
    private View root;
    private void setRoot(View view) { root = view; }
    private SettingsViewModel settingsViewModel;

    public ChangeThemeDialog() { }

    public static ChangeThemeDialog newInstance(int layoutID, int acceptButtonID, int cancelButtonID, int themeID, int rangeID, int intervalID, int unitID, View root) {
        ChangeThemeDialog  frag = new ChangeThemeDialog();

        Bundle args = new Bundle();
        args.putInt("themeID", themeID);
        args.putInt("rangeID", rangeID);
        args.putInt("intervalID", intervalID);
        args.putInt("unitID", unitID);
        args.putInt("layoutID", layoutID);
        args.putInt("acceptButtonID", acceptButtonID);
        args.putInt("cancelButtonID", cancelButtonID);
        frag.setArguments(args);
        frag.setRoot(root);

        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        assert getArguments() != null;
        theme = getArguments().getInt("themeID");
        range = getArguments().getInt("rangeID");
        interval = getArguments().getInt("intervalID");
        unit = getArguments().getInt("unitID");

        assert getView() != null;
        ImageView primaryColor = getView().findViewById(R.id.primary_color_image);
        ImageView primaryVariantColor = getView().findViewById(R.id.primary_variant_image);
        ImageView secondaryColor = getView().findViewById(R.id.secondary_color_image);
        ImageView secondaryVariantColor = getView().findViewById(R.id.secondary_variant_image);

        int[] colors = new int[0];
        switch (theme) {
            case 0:
                colors = new int[]{
                        R.color.soft_blue,
                        R.color.fuchsia,
                        R.color.forest,
                        R.color.lime
                };
                break;
            case 1:
                colors = new int[]{
                        R.color.black,
                        R.color.purple_700,
                        R.color.purple_200,
                        R.color.teal_200
                };
                break;
        }

        Drawable background = ContextCompat.getDrawable(Objects.requireNonNull(requireActivity()), R.drawable.change_dialog_fragment_theme_color_background);
        assert background != null;
        background.setTint(ContextCompat.getColor(requireActivity(), colors[0]));
        primaryColor.setImageDrawable(background.getConstantState().newDrawable().mutate());
        background.setTint(ContextCompat.getColor(requireActivity(), colors[1]));
        primaryVariantColor.setImageDrawable(background.getConstantState().newDrawable().mutate());
        background.setTint(ContextCompat.getColor(requireActivity(), colors[2]));
        secondaryColor.setImageDrawable(background.getConstantState().newDrawable().mutate());
        background.setTint(ContextCompat.getColor(requireActivity(), colors[3]));
        secondaryVariantColor.setImageDrawable(background.getConstantState().newDrawable().mutate());
    }

    @Override
    public void onAccept() {
        settingsViewModel.setThemeID(theme);
        settingsViewModel.setRangeID(range);
        settingsViewModel.setIntervalID(interval);
        settingsViewModel.setUnitID(unit);

        ResetTheme.changeToTheme(requireActivity(), theme);
    }

    @Override
    public void onCancel() {
        ((Spinner) root.findViewById(R.id.themeID_spinner)).setSelection(settingsViewModel.getThemeID().getValue());

        dismiss();
    }
}
