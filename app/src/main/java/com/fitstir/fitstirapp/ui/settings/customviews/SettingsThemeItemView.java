package com.fitstir.fitstirapp.ui.settings.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import com.fitstir.fitstirapp.R;

public class SettingsThemeItemView extends LinearLayout {

    private CardView primary, primaryVariant, secondary, secondaryVariant;
    private TextView title;
    private Context context;
    private String refText;

    public SettingsThemeItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        final int black = 0xff000000;

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SettingsThemeItemView, 0, 0);

        int primaryColor = a.getColor(R.styleable.SettingsThemeItemView_primarycolor,
                black);
        int primaryVariantColor = a.getColor(R.styleable.SettingsThemeItemView_primaryvariantcolor,
                black);
        int secondaryColor = a.getColor(R.styleable.SettingsThemeItemView_secondarycolor,
                black);
        int secondaryVariantColor = a.getColor(R.styleable.SettingsThemeItemView_secondaryvariantcolor,
                black);
        String titleText = a.getString(R.styleable.SettingsThemeItemView_title2);
        refText = a.getString(R.styleable.SettingsThemeItemView_reference2);

        a.recycle();

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixelsHoriz = (int) (20 * scale + 0.5f);
        int pixelsVert = (int) (20 * scale + 0.5f);
        setPadding(pixelsHoriz, pixelsVert, pixelsHoriz, pixelsVert);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_settings_theme_item, this, true);

        title = (TextView) getChildAt(0);
        LinearLayout infoSection = (LinearLayout) getChildAt(1);
        LinearLayout primaryRow = (LinearLayout) infoSection.getChildAt(0);
        primary = (CardView) primaryRow.getChildAt(1);
        primaryVariant = (CardView) primaryRow.getChildAt(2);
        LinearLayout secondaryRow = (LinearLayout) infoSection.getChildAt(1);
        secondary = (CardView) secondaryRow.getChildAt(1);
        secondaryVariant = (CardView) secondaryRow.getChildAt(2);

        setText(title, titleText);
        setColor(primary, primaryColor);
        setColor(primaryVariant, primaryVariantColor);
        setColor(secondary, secondaryColor);
        setColor(secondaryVariant, secondaryVariantColor);
    }
    public SettingsThemeItemView(Context context) {
        super(context, null);
    }

    public void setColor(CardView card, int color) {
        card.setCardBackgroundColor(color);
    }
    public void setText(TextView view, String titleText) {
        view.setText(titleText);
    }

    public String getReference() {
        return refText;
    }

    public void setPrimaryColor(int color) {
        setColor(this.primary, color);
    }
    public void setPrimaryVariantColor(int color) {
        setColor(this.primaryVariant, color);
    }
    public void setSecondaryColor(int color) {
        setColor(this.secondary, color);
    }
    public void setSecondaryVariant(int color) {
        setColor(this.secondaryVariant, color);
    }
    public void setTitle(String title) {
        setText(this.title, title);
    }
}
