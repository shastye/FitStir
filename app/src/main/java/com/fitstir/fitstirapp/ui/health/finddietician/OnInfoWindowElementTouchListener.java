package com.fitstir.fitstirapp.ui.health.finddietician;

import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.maps.model.Marker;

import android.os.Handler;

public abstract class OnInfoWindowElementTouchListener implements View.OnTouchListener {
    private final View view;
    private final Handler handler;

    private Marker marker;
    private boolean pressed;

    public OnInfoWindowElementTouchListener(View view) {
        this.view = view;

        this.marker = null;
        this.handler = new Handler();
        this.pressed = false;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (0 <= event.getX() && event.getX() <= view.getWidth()
                &&
            0 <= event.getY() && event.getY() <= view.getHeight()) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    startPress();
                    break;
                case MotionEvent.ACTION_UP:
                    handler.postDelayed(confirmClickRunnable, 150);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    endPress();
                    break;
                default: break;
            }
        }
        else {
            endPress();
        }
        return false;
    }

    private void startPress() {
        if (!pressed) {
            pressed = true;
            handler.removeCallbacks(confirmClickRunnable);
            if (marker != null)
                marker.showInfoWindow();
        }
    }

    private boolean endPress() {
        if (pressed) {
            this.pressed = false;
            handler.removeCallbacks(confirmClickRunnable);
            if (marker != null)
                marker.showInfoWindow();
            return true;
        }
        else
            return false;
    }

    private final Runnable confirmClickRunnable = new Runnable() {
        public void run() {
            if (endPress()) {
                onClickConfirmed(view, marker);
            }
        }
    };

    protected abstract void onClickConfirmed(View v, Marker marker);
}
