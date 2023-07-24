package com.fitstir.fitstirapp.ui.settings.dialogs;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.utility.classes.IGenericAlertDialog;

public class HardResetDialog extends IGenericAlertDialog {
    private String message;
    private SettingsViewModel settingsViewModel;

    public HardResetDialog() { }

    public static HardResetDialog newInstance(int layoutID, int acceptButtonID, int cancelButtonID, String message) {
        HardResetDialog frag = new HardResetDialog();

        Bundle args = new Bundle();
        args.putString("message", message);
        args.putInt("layoutID", layoutID);
        args.putInt("acceptButtonID", acceptButtonID);
        args.putInt("cancelButtonID", cancelButtonID);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        assert getArguments() != null;
        message = getArguments().getString("message");

        assert getView() != null;

        TextView messageView = binding.dialogGenericMessage;
        messageView.setText(message);
    }

    @Override
    public void onAccept() {
        settingsViewModel.clearApplicationData(requireActivity());
        settingsViewModel.deleteFromDatabase();
        settingsViewModel.deleteUser();
    }

    @Override
    public void onCancel() {

    }
}
