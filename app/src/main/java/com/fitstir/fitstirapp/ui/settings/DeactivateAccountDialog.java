package com.fitstir.fitstirapp.ui.settings;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.ui.utility.classes.IBasicAlertDialog;

public class DeactivateAccountDialog extends IBasicAlertDialog {
    private String message;
    private int messageID;
    private SettingsViewModel settingsViewModel;

    public DeactivateAccountDialog() { }

    public static DeactivateAccountDialog newInstance(int _layoutID, int _acceptButtonID, int _cancelButtonID, int _messageID, String _message) {
        DeactivateAccountDialog frag = new DeactivateAccountDialog();

        Bundle args = new Bundle();
        args.putString("message", _message);
        args.putInt("messageID", _messageID);
        args.putInt("layoutID", _layoutID);
        args.putInt("acceptButtonID", _acceptButtonID);
        args.putInt("cancelButtonID", _cancelButtonID);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        assert getArguments() != null;
        message = getArguments().getString("message");
        messageID = getArguments().getInt("messageID");

        assert getView() != null;

        TextView messageView = getView().findViewById(messageID);
        messageView.setText(message);
    }

    @Override
    public void onAccept() {
        boolean success = settingsViewModel.deleteFromDatabase();

        if (success) {
            success = settingsViewModel.deleteUser();

            if (!success) {
                Toast.makeText(getContext(), "Account Data **NOT** Deleted", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Database Data **NOT** Deleted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCancel() {

    }
}
