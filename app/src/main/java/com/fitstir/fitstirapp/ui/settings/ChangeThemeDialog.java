package com.fitstir.fitstirapp.ui.settings;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.ResetTheme;

import java.util.Objects;
import java.util.Vector;

public class ChangeThemeDialog extends DialogFragment {
    protected int theme, range, interval, unit;

    public ChangeThemeDialog() { }

    public static ChangeThemeDialog newInstance(int _themeID, int _rangeID, int _intervalID, int _unitID, View _root) {
        ChangeThemeDialog frag = new ChangeThemeDialog();
        Bundle args = new Bundle();
        args.putInt("themeID", _themeID);
        args.putInt("rangeID", _rangeID);
        args.putInt("intervalID", _intervalID);
        args.putInt("unitID", _unitID);
        frag.setArguments(args);
        SettingsViewModel.dialogRoot = _root;
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        theme = getArguments().getInt("themeID");
        range = getArguments().getInt("rangeID");
        interval = getArguments().getInt("intervalID");
        unit = getArguments().getInt("unitID");

        return inflater.inflate(R.layout.dialog_change_theme, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView primaryColor = view.findViewById(R.id.primary_color_image);
        ImageView primaryVariantColor = view.findViewById(R.id.primary_variant_image);
        ImageView secondaryColor = view.findViewById(R.id.secondary_color_image);
        ImageView secondaryVariantColor = view.findViewById(R.id.secondary_variant_image);


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
        Vector<Bitmap> bitmaps = Methods.convertPNGtoBitmap(view, drawables);

        primaryColor.setImageBitmap(bitmaps.get(0));
        primaryVariantColor.setImageBitmap(bitmaps.get(1));
        secondaryColor.setImageBitmap(bitmaps.get(2));
        secondaryVariantColor.setImageBitmap(bitmaps.get(3));


        Button cancel = view.findViewById(R.id.dialog_cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Spinner) SettingsViewModel.dialogRoot.findViewById(R.id.themeID_spinner)).setSelection(SettingsViewModel.themeID);

                dismiss();
            }
        });

        Button accept = view.findViewById(R.id.dialog_accept_button);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsViewModel.themeID = theme;
                SettingsViewModel.rangeID = range;
                SettingsViewModel.intervalID = interval;
                SettingsViewModel.unitID = unit;

                ResetTheme.changeToTheme(Objects.requireNonNull(getActivity()), theme);

                dismiss();
            }
        });
    }
}
