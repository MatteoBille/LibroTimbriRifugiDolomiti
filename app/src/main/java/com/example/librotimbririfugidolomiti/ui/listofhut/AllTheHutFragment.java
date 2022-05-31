package com.example.librotimbririfugidolomiti.ui.listofhut;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
import com.example.librotimbririfugidolomiti.databinding.FragmentListofallhutsBinding;


public class AllTheHutFragment extends Fragment {

    TableLayout table;
    private FragmentListofallhutsBinding binding;
    private RifugiViewModel mRifugiViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentListofallhutsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        mRifugiViewModel = new ViewModelProvider(this).get(RifugiViewModel.class);

        createRecyclerView(inflater);
        return root;
    }

    private void createRecyclerView(LayoutInflater inflater) {


        RecyclerView recyclerView = binding.recyclerview;
        final RecyclerCustomAdapter adapter = new RecyclerCustomAdapter(new RecyclerCustomAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mRifugiViewModel.getAllRifugi().observe(getViewLifecycleOwner(), rifugi -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(rifugi);
            Log.i("DATA",rifugi.get(0).getNomeRifugio()+"");
        });





    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}