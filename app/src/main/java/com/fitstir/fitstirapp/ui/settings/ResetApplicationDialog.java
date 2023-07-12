package com.fitstir.fitstirapp.ui.settings;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.ui.utility.IBasicAlertDialog;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.ResetTheme;

import org.w3c.dom.Text;

import java.util.Objects;

public class ResetApplicationDialog extends IBasicAlertDialog {
    private String message;
    private int messageID;

    public ResetApplicationDialog() { }

    public static ResetApplicationDialog newInstance(int _layoutID, int _acceptButtonID, int _cancelButtonID, int _messageID, String _message) {
        ResetApplicationDialog frag = new ResetApplicationDialog();

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
        boolean success = Methods.clearApplicationData(getActivity());

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
