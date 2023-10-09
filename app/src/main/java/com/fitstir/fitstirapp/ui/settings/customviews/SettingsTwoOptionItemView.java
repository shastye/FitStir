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

import com.fitstir.fitstirapp.R;

import org.w3c.dom.Text;

public class SettingsTwoOptionItemView extends LinearLayout {

    private ImageView image1, image2;
    private TextView option1, option2, title;
    private SwitchCompat chooser;
    private Context context;
    private String refText;

    public SettingsTwoOptionItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SettingsTwoOptionItemView, 0, 0);

        int image1Id = a.getResourceId(R.styleable.SettingsTwoOptionItemView_image1,
                R.drawable.ic_check_circle_solid_200dp);
        int image2Id = a.getResourceId(R.styleable.SettingsTwoOptionItemView_image2,
                R.drawable.ic_check_circle_solid_200dp);
        String titleText = a.getString(R.styleable.SettingsTwoOptionItemView_title1);
        String option1Text = a.getString(R.styleable.SettingsTwoOptionItemView_option1);
        String option2Text = a.getString(R.styleable.SettingsTwoOptionItemView_option2);
        refText = a.getString(R.styleable.SettingsTwoOptionItemView_reference1);

        a.recycle();

        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixelsHoriz = (int) (20 * scale + 0.5f);
        int pixelsVert = (int) (20 * scale + 0.5f);
        setPadding(pixelsHoriz, pixelsVert, pixelsHoriz, pixelsVert);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_settings_two_option_item, this, true);

        LinearLayout infoRow = (LinearLayout) getChildAt(0);
        image1 = (ImageView) infoRow.getChildAt(0);
        option1 = (TextView) infoRow.getChildAt(1);
        chooser = (SwitchCompat) infoRow.getChildAt(2);
        option2 = (TextView) infoRow.getChildAt(3);
        image2 = (ImageView) infoRow.getChildAt(4);

        title = (TextView) getChildAt(1);

        setImage(image1, image1Id);
        setImage(image2, image2Id);
        setText(title, titleText);
        setText(option1, option1Text);
        setText(option2, option2Text);
    }
    public SettingsTwoOptionItemView(Context context) {
        super(context, null);
    }

    public void setImage(ImageView image, int drawableResourceID) {
        image.setImageDrawable(AppCompatResources.getDrawable(context, drawableResourceID));
    }
    public void setText(TextView view, String titleText) {
        view.setText(titleText);
    }

    public String getReference() {
        return refText;
    }
    public SwitchCompat getSwitch() { return chooser; }
}
