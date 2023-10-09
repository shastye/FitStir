package com.fitstir.fitstirapp.ui.settings.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.fitstir.fitstirapp.R;

public class SettingsTopicView extends RelativeLayout {

    private ImageView image;
    private TextView title, description;
    private Context context;

    private String refText;

    public SettingsTopicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SettingsTopicView, 0, 0);

        int imageId = a.getResourceId(R.styleable.SettingsTopicView_image,
                R.drawable.ic_check_circle_solid_200dp);
        String titleText = a.getString(R.styleable.SettingsTopicView_title0);
        String descrText = a.getString(R.styleable.SettingsTopicView_description);
        refText = a.getString(R.styleable.SettingsTopicView_reference);

        a.recycle();

        setGravity(Gravity.CENTER_VERTICAL);

        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixelsHoriz = (int) (20 * scale + 0.5f);
        int pixelsVert = (int) (20 * scale + 0.5f);
        setPadding(pixelsHoriz, pixelsVert, pixelsHoriz, pixelsVert);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_settings_topics, this, true);

        image = (ImageView) getChildAt(0);
        setImage(imageId);
        LayoutParams imageParams = (LayoutParams) image.getLayoutParams();
        imageParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        image.setLayoutParams(imageParams);

        LinearLayout infoRow = (LinearLayout) getChildAt(1);

        title = (TextView) infoRow.getChildAt(0);
        setTitle(titleText);

        description = (TextView) infoRow.getChildAt(1);
        setDescription(descrText);
    }
    public SettingsTopicView(Context context) {
        this(context, null);
        this.context = context;
    }

    public void setImage(int drawableResourceID) {
        image.setImageDrawable(AppCompatResources.getDrawable(context, drawableResourceID));
    }
    public void setTitle(String titleText) {
        title.setText(titleText);
    }
    public void setDescription(String descriptionText) {
        description.setText(descriptionText);
    }

    public String getReference() {
        return refText;
    }
    public String getTitle() {
        return title.getText().toString();
    }
    public String getDescription() {
        return description.getText().toString();
    }
}
