package com.fitstir.fitstirapp.ui.connect;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fitstir.fitstirapp.R;

import com.fitstir.fitstirapp.databinding.FragmentInitialBinding;

public class InitialFragment extends Fragment {

    private FragmentInitialBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel =
                new ViewModelProvider(this).get(ConnectViewModel.class);

        binding = FragmentInitialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textInitial;
        connectViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}