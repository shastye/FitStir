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
import com.fitstir.fitstirapp.databinding.FragmentMessageBoardBinding;

import java.util.Objects;


public class MessageBoardFragment extends Fragment {

    private FragmentMessageBoardBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConnectViewModel connectViewModel =
                new ViewModelProvider(this).get(ConnectViewModel.class);

        binding = FragmentMessageBoardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //additional code here



        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        Button next1 = root.findViewById(R.id.button_next1);
        next1.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_messageBoardFragment_to_questions2Fragment);
        });

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}