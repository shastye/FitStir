package com.fitstir.fitstirapp.ui.settings.customviews;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SettingsRadioGroupView extends LinearLayout {

    private TextView title, description;
    private RadioGroup radioGroup;
    private Context context;
    private String refText;

    public SettingsRadioGroupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SettingsRadioGroupView, 0, 0);

        String titleText = a.getString(R.styleable.SettingsRadioGroupView_title3);
        String descrText = a.getString(R.styleable.SettingsRadioGroupView_description3);
        refText = a.getString(R.styleable.SettingsRadioGroupView_reference3);

        a.recycle();

        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.START);

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixelsHoriz = (int) (20 * scale + 0.5f);
        int pixelsVert = (int) (20 * scale + 0.5f);
        setPadding(pixelsHoriz, pixelsVert, pixelsHoriz, pixelsVert);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_settings_radio_group, this, true);

        title = (TextView) getChildAt(0);
        setText(title, titleText);

        description = (TextView) getChildAt(1);
        setText(description, descrText);

        radioGroup = (RadioGroup) getChildAt(2);
        radioGroup.setOrientation(LinearLayout.VERTICAL);
    }
    public SettingsRadioGroupView(Context context) {
        super(context);
        this.context = context;
    }


    public void setText(TextView view, String titleText) {
        view.setText(titleText);
    }
    public void addRadio(String text, int radioID, int currentValue) {
        RadioButton radio = new RadioButton(context);

        ColorStateList colorStateList = new ColorStateList(
                new int[][] {
                        new int[] { -android.R.attr.state_checked },
                        new int[] {  android.R.attr.state_checked }
                },
                new int[] {
                        Color.BLACK,
                        Methods.getThemeAttributeColor(com.google.android.material.R.attr.colorPrimaryVariant, context)
                }
        );

        radio.setButtonTintList(colorStateList);
        radio.setId(radioID);
        radio.setText(text);
        radio.setTextColor(Color.BLACK);
        radio.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                FirebaseDatabase.getInstance()
                        .getReference("Users")
                        .child(uid)
                        .child(refText)
                        .setValue(radioID);
            }
        });

        if (currentValue == radioID) {
            radio.setChecked(true);
        } else {
            radio.setChecked(false);
        }

        radioGroup.addView(radio);
    }
    public String getReference() {
        return refText;
    }
}
