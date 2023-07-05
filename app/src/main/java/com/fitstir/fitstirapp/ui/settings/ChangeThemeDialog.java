package com.fitstir.fitstirapp.ui.settings;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.utility.IBasicAlertDialog;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.ResetTheme;

import java.util.Objects;
import java.util.Vector;

public class ChangeThemeDialog extends IBasicAlertDialog {
    private int theme, range, interval, unit;

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
        SettingsViewModel.dialogRoot = _root;
        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

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

        int[] drawables = new int[0];
        switch (theme) {
            case 0:
                drawables = new int[]{
                        R.drawable.magenta,
                        R.drawable.fuschia,
                        R.drawable.forest,
                        R.drawable.lime
                };
                break;
            case 1:
                drawables = new int[]{
                        R.drawable.black,
                        R.drawable.dark_purple,
                        R.drawable.lavender,
                        R.drawable.teal
                };
                break;
        }
        Vector<Bitmap> bitmaps = Methods.convertPNGtoBitmap(getView(), drawables);

        primaryColor.setImageBitmap(bitmaps.get(0));
        primaryVariantColor.setImageBitmap(bitmaps.get(1));
        secondaryColor.setImageBitmap(bitmaps.get(2));
        secondaryVariantColor.setImageBitmap(bitmaps.get(3));
    }

    @Override
    public void onAccept() {
        SettingsViewModel.themeID = theme;
        SettingsViewModel.rangeID = range;
        SettingsViewModel.intervalID = interval;
        SettingsViewModel.unitID = unit;

        ResetTheme.changeToTheme(Objects.requireNonNull(getActivity()), theme);
    }

    @Override
    public void onCancel() {
        ((Spinner) SettingsViewModel.dialogRoot.findViewById(R.id.themeID_spinner)).setSelection(SettingsViewModel.themeID);

        dismiss();
    }
}
