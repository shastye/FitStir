package com.fitstir.fitstirapp.ui.utility;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.fitstir.fitstirapp.R;

import java.util.Vector;

public class IBasicDialog extends DialogFragment {
    protected int layout, theme;
    protected int acceptID, cancelID;

    public IBasicDialog() { }

    public static IBasicDialog newInstance(int _themeID, int _layoutId, int _acceptID, int _cancelID) {
        IBasicDialog frag = new IBasicDialog();
        Bundle args = new Bundle();
        args.putInt("themeID", _themeID);
        args.putInt("layoutID", _layoutId);
        args.putInt("acceptID", _acceptID);
        args.putInt("cancelID", _cancelID);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        assert getArguments() != null;
        theme = getArguments().getInt("themeID");
        layout = getArguments().getInt("layoutID");
        acceptID = getArguments().getInt("acceptID");
        cancelID = getArguments().getInt("cancelID");

        return inflater.inflate(layout, container, false);
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
                drawables = new int[] {
                        R.drawable.magenta,
                        R.drawable.fuschia,
                        R.drawable.forest,
                        R.drawable.lime
                };
                break;
            case 1:
                drawables = new int[] {
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


        Button cancel = view.findViewById(cancelID);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnCancelClick();
                dismiss();
            }
        });

        Button accept = view.findViewById(acceptID);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnAcceptClick();
                dismiss();
            }
        });
    }

    protected void OnAcceptClick() {
        Toast.makeText(getActivity(), "OnAcceptClick has not been overridden.", Toast.LENGTH_LONG).show();
    }
    protected void OnCancelClick() {
        Toast.makeText(getActivity(), "OnCancelClick has not been overridden.", Toast.LENGTH_LONG).show();
    }
}
