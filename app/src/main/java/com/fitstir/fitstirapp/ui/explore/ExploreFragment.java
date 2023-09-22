package com.fitstir.fitstirapp.ui.explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentExploreBinding;

public class ExploreFragment extends Fragment {

    private FragmentExploreBinding binding;
    private WebView youtube;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExploreViewModel exploreViewModel =
                new ViewModelProvider(this).get(ExploreViewModel.class);

        binding = FragmentExploreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        youtube = root.findViewById(R.id.video_View);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}