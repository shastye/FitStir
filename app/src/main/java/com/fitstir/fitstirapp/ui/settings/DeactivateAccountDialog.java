package com.fitstir.fitstirapp.ui.settings;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.fitstir.fitstirapp.ui.utility.IBasicAlertDialog;
import com.fitstir.fitstirapp.ui.utility.Methods;

public class DeactivateAccountDialog extends IBasicAlertDialog {
    private String message;
    private int messageID;

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

        assert getArguments() != null;
        message = getArguments().getString("message");
        messageID = getArguments().getInt("messageID");

        assert getView() != null;

        TextView messageView = getView().findViewById(messageID);
        messageView.setText(message);
    }

    @Override
    public void onAccept() {
        Methods.deleteFromDatabase();
        Methods.deleteUser();
    }

    @Override
    public void onCancel() {

    }
}