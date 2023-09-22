package com.fitstir.fitstirapp.ui.yoga.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.SearchView;
import android.widget.TextView;

import com.fitstir.fitstirapp.R;
import com.fitstir.fitstirapp.databinding.FragmentYogaViewBinding;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.utility.RvInterface;
import com.fitstir.fitstirapp.ui.yoga.models.CategoryModel;
import com.fitstir.fitstirapp.ui.yoga.models.PoseModel;
import com.fitstir.fitstirapp.ui.yoga.models.YogaViewModel;
import com.fitstir.fitstirapp.ui.yoga.utilitesYoga.YogaAdapter;

import java.util.ArrayList;
import java.util.List;


public class CategoryViewFragment extends Fragment implements RvInterface {
    private FragmentYogaViewBinding binding;
    private RecyclerView rv;
    private YogaAdapter adapter;
    private RvInterface rvInterface;
    private ArrayList<CategoryModel> categoryList;
    private ArrayList<PoseModel> poseList;
    private SearchView search;
    private TextView title;
    private CategoryModel category;
    private PoseModel poseModel;
    private YogaViewModel yogaViews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Call  yoga view model
        yogaViews = new ViewModelProvider(this.requireActivity()).get(YogaViewModel.class);

        // Inflate the layout for this fragment
        binding = FragmentYogaViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        search = root.findViewById(R.id.searchView_Yoga_Explore);
        title = root.findViewById(R.id.category_Title);
        rv = root.findViewById(R.id.yoga_RV);
        category = new CategoryModel();
        categoryList = new ArrayList<>();
        poseList = new ArrayList<>();
        rvInterface = this;
        adapter = new YogaAdapter(rvInterface,requireActivity(), poseList);
        poseModel = new PoseModel();

        int id = yogaViews.getCat_Id().getValue();
        switch(id){
            case 1:
                title.setText(Constants.YOGA_ID.BEGINNER_POSE);
                yogaViews.getResponsePose().observe(requireActivity(), new Observer<List<PoseModel>>() {
                    @Override
                    public void onChanged(List<PoseModel> poses) {
                        for(PoseModel pose : poses) {

                            poseModel.setPose_benefits(pose.getPose_benefits());
                            poseModel.setUrl_png(pose.getUrl_png());
                            poseModel.setEnglish_name(pose.getEnglish_name());
                            poseList.add(pose);

                            rv.setLayoutManager(new LinearLayoutManager(requireActivity()));
                            rv.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
                            adapter = new YogaAdapter(rvInterface, requireActivity(), poseList);
                            rv.setAdapter(adapter);
                        }
                    }
                });

                yogaViews.fetchApiData(adapter);
                break;

            case 2:
                title.setText(Constants.YOGA_ID.INTERMEDIATE_POSE);
                break;

            case 3:
                title.setText(Constants.YOGA_ID.EXPERT_POSE);
                break;

            case 4:
                title.setText(Constants.YOGA_ID.EXPLORE_POSES);
                break;

            case 5:
                title.setText(Constants.YOGA_ID.QUICK_START_POSES);
                break;
            case 6:
                title.setText(Constants.YOGA_ID.LEARN_POSES);
                break;

            case 7:
                title.setText(Constants.YOGA_ID.STANDING_POSE);
                break;
            case 8:
                title.setText(Constants.YOGA_ID.BALANCE_POSE);
                break;
        }

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int position) {
        YogaViewModel views = new ViewModelProvider(requireActivity()).get(YogaViewModel.class);
        views.getClickedItem(position, poseList);
        Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main).navigate(R.id.action_categoryViewFragment_to_yogaPoseFragment);
    }
}