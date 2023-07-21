package com.fitstir.fitstirapp.ui.settings;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.utility.IBasicAlertDialog;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.ResetTheme;

import java.util.Objects;
import java.util.Vector;

public class ChangeThemeDialog extends IBasicAlertDialog {
    private int theme, range, interval, unit;
    private View root;
    private void setRoot(View view) { root = view; }
    private SettingsViewModel settingsViewModel;

    public ChangeThemeDialog() { }

    public static ChangeThemeDialog newInstance(int _layoutID, int _acceptButtonID, int _cancelButtonID, int _themeID, int _rangeID, int _intervalID, int _unitID, View _root) {
        ChangeThemeDialog  frag = new ChangeThemeDialog();

        Bundle args = new Bundle();
        args.putInt("themeID", _themeID);
        args.putInt("rangeID", _rangeID);
        args.putInt("intervalID", _intervalID);
        args.putInt("unitID", _unitID);
        args.putInt("layoutID", _layoutID);
        args.putInt("acceptButtonID", _acceptButtonID);
        args.putInt("cancelButtonID", _cancelButtonID);
        frag.setArguments(args);
        frag.setRoot(_root);

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

        Drawable background = ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.change_dialog_fragment_theme_color_background);
        assert background != null;
        background.setTint(ContextCompat.getColor(getActivity(), colors[0]));
        primaryColor.setImageDrawable(background.getConstantState().newDrawable().mutate());
        background.setTint(ContextCompat.getColor(getActivity(), colors[1]));
        primaryVariantColor.setImageDrawable(background.getConstantState().newDrawable().mutate());
        background.setTint(ContextCompat.getColor(getActivity(), colors[2]));
        secondaryColor.setImageDrawable(background.getConstantState().newDrawable().mutate());
        background.setTint(ContextCompat.getColor(getActivity(), colors[3]));
        secondaryVariantColor.setImageDrawable(background.getConstantState().newDrawable().mutate());
    }

    @Override
    public void onAccept() {
        settingsViewModel.setThemeID(theme);
        settingsViewModel.setRangeID(range);
        settingsViewModel.setIntervalID(interval);
        settingsViewModel.setUnitID(unit);

        ResetTheme.changeToTheme(Objects.requireNonNull(getActivity()), theme);
    }

    @Override
    public void onCancel() {
        ((Spinner) root.findViewById(R.id.themeID_spinner)).setSelection(settingsViewModel.getThemeID().getValue());

        dismiss();
    }
}
