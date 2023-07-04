package com.fitstir.fitstirapp.ui.utility;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public abstract class IBasicDialog extends DialogFragment {
    protected Activity activity;
    protected int layout;
    protected Button accept, cancel;

    public IBasicDialog(@NonNull Activity _activity, int _layoutId, Button _accept, Button _cancel) {
        super();

        activity = _activity;
        layout = _layoutId;
        accept = _accept;
        cancel = _cancel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnCancelClick();

                dismiss();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnAcceptClick();
            }
        });
    }

    protected abstract void OnAcceptClick();
    protected abstract void OnCancelClick();
}
