package com.fitstir.fitstirapp.ui.connect;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentMessagBoard3Binding;

import java.util.Objects;

public class MessageBoard3Fragment extends Fragment {

    private FragmentMessagBoard3Binding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel =
                new ViewModelProvider(this).get(ConnectViewModel.class);

        binding = FragmentMessagBoard3Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //additional code here



        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        Button next3 = root.findViewById(R.id.button_next3);
        next3.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_messagBoard3Fragment_to_questions3Fragment);
        });
        return root;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}