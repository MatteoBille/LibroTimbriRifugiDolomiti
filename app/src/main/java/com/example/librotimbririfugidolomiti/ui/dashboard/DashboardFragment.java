package com.example.librotimbririfugidolomiti.ui.dashboard;

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
import com.example.librotimbririfugidolomiti.databinding.FragmentDashboardBinding;


public class DashboardFragment extends Fragment {

    TableLayout table;
    private FragmentDashboardBinding binding;
    private RifugiViewModel mRifugiViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        mRifugiViewModel = new ViewModelProvider(this).get(RifugiViewModel.class);

        createRecyclerView(inflater);
        return root;
    }

    private void createRecyclerView(LayoutInflater inflater) {


        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.recyclerview);
        final RecyclerCustomAdapter adapter = new RecyclerCustomAdapter(new RecyclerCustomAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
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