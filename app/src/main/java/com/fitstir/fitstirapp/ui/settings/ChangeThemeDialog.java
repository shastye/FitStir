package com.fitstir.fitstirapp.ui.settings;

import android.app.Activity;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.fitstir.fitstirapp.ui.utility.IBasicDialog;

public class ChangeThemeDialog extends IBasicDialog {
    public ChangeThemeDialog(@NonNull Activity _activity, int _layoutId, Button _accept, Button _cancel) {
        super(_activity, _layoutId, _accept, _cancel);
    }

    @Override
    protected void OnAcceptClick() {
        SettingsViewModel.dialogResponse = true;
    }

    @Override
    protected void OnCancelClick() {
        SettingsViewModel.dialogResponse = false;
    }
}
