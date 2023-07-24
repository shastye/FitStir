package com.fitstir.fitstirapp.ui.utility.classes;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fitstir.fitstirapp.databinding.DialogGenericGoalBinding;

public class IGenericGoalDialog extends IBasicDialog {
    protected DialogGenericGoalBinding binding = null;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = DialogGenericGoalBinding.bind(requireView());
    }
}
