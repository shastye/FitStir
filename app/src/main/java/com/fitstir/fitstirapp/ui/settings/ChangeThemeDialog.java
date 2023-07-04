package com.fitstir.fitstirapp.ui.settings;

import android.app.Activity;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.fitstir.fitstirapp.ui.utility.IBasicDialog;

public class ChangeThemeDialog extends IBasicDialog {
    @Override
    protected void OnAcceptClick() {
        SettingsViewModel.dialogResponse = true;
    }

    @Override
    protected void OnCancelClick() {
        SettingsViewModel.dialogResponse = false;
    }
}
