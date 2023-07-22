package com.fitstir.fitstirapp.ui.settings.dialogs;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.utility.classes.IGenericAlertDialog;

public class ResetApplicationDialog extends IGenericAlertDialog {
    private String message;
    private SettingsViewModel settingsViewModel;

    public ResetApplicationDialog() { }

    public static ResetApplicationDialog newInstance(int layoutID, int acceptButtonID, int cancelButtonID, String message) {
        ResetApplicationDialog frag = new ResetApplicationDialog();

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
        boolean success = settingsViewModel.clearApplicationData(requireActivity());

        if (success) {
            // show dialog saying everything deleted
            Toast.makeText(getContext(), "Application Data Deleted", Toast.LENGTH_LONG).show();
        } else {
            // show dialog saying error
            Toast.makeText(getContext(), "Application Data **NOT** Deleted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCancel() {

    }
}
