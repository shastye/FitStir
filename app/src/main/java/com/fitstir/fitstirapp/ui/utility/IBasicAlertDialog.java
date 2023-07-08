package com.fitstir.fitstirapp.ui.utility;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class IBasicAlertDialog extends DialogFragment {

    /*
     *
     *   WHEN OVERRIDING THIS CLASS:
     *           - put layoutID, acceptButtonID and cancelButtonID in args in newInstance
     *           - no need to set layoutID, acceptButtonID and cancelButtonID
     *           - do any functionality in onStart
     *           - must override public void onAccept() to do what you need the accept button to do
     *           - must override public void onCancel() to do what you need the cancel button to do;
     *                   if you don't need it to do anything but dismiss the dialog, you still need
     *                   to override it
     *
     */

    protected int layoutID, acceptButtonID, cancelButtonID;

    public IBasicAlertDialog() { }

    public static IBasicAlertDialog newInstance(int _layoutID, int _acceptButtonID, int _cancelButtonID) {
        IBasicAlertDialog frag = new IBasicAlertDialog();
        Bundle args = new Bundle();
        args.putInt("layoutID", _layoutID);
        args.putInt("acceptButtonID", _acceptButtonID);
        args.putInt("cancelButtonID", _cancelButtonID);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        layoutID = getArguments().getInt("layoutID");
        acceptButtonID = getArguments().getInt("acceptButtonID");
        cancelButtonID = getArguments().getInt("cancelButtonID");

        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutID, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button cancel = view.findViewById(cancelButtonID);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancel();
                dismiss();
            }
        });

        Button accept = view.findViewById(acceptButtonID);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAccept();
                dismiss();
            }
        });
    }

    public void onAccept() {
        Toast.makeText(getContext(), "onAccept Not Overriden.", Toast.LENGTH_LONG).show();
    }
    public void onCancel() {
        Toast.makeText(getContext(), "onCancel Not Overriden.", Toast.LENGTH_LONG).show();
    }
}
