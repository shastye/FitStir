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
import com.fitstir.fitstirapp.databinding.FragmentMessageBoard2Binding;
import com.fitstir.fitstirapp.databinding.FragmentMessageBoardBinding;

import java.util.Objects;

public class MessageBoard2Fragment extends Fragment {

    private FragmentMessageBoard2Binding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel =
                new ViewModelProvider(this).get(ConnectViewModel.class);

        binding = FragmentMessageBoard2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //additional code here



        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        Button next2 = root.findViewById(R.id.button_next2);
        next2.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_messageBoard2Fragment_to_questions3Fragment);
        });
        return root;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}