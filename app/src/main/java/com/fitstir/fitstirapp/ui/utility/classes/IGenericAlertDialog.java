package com.fitstir.fitstirapp.ui.utility.classes;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fitstir.fitstirapp.databinding.DialogGenericAlertBinding;

public class IGenericAlertDialog extends IBasicDialog {
    protected DialogGenericAlertBinding binding = null;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = DialogGenericAlertBinding.bind(requireView());
    }
}
