package com.example.librotimbririfugidolomiti.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.librotimbririfugidolomiti.MyBookActivity;
import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
import com.example.librotimbririfugidolomiti.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private RifugiViewModel mRifugiViewModel;
    private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mRifugiViewModel = new ViewModelProvider(this).get(RifugiViewModel.class);
        Integer numberOfHut = mRifugiViewModel.getNumberOfHut();
        Integer numberOfHutVisited = mRifugiViewModel.getNumberOfHutVisited();

        binding.visitedHuts.setText(numberOfHutVisited+"/"+numberOfHut);

        String date = mRifugiViewModel.getLastVisitDay();
        binding.lastVisit.setText(date);

        binding.goToBook.setOnClickListener(e->{
            Intent intent= new Intent(root.getContext(),MyBookActivity.class);
            startActivity(intent);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}