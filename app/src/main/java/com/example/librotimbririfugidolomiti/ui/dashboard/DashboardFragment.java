package com.example.librotimbririfugidolomiti.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
        table = (TableLayout) root.findViewById(R.id.table);


        mRifugiViewModel = new ViewModelProvider(this).get(RifugiViewModel.class);

        createTableRow(inflater);
        return root;
    }

    private void createTableRow(LayoutInflater inflater) {


        mRifugiViewModel.getAllRifugi().observe(getViewLifecycleOwner(), Rifugi -> {
            // Update the cached copy of the words in the adapter.
            for(int i=0;i<Rifugi.size();i++){
                View custom = inflater.inflate(R.layout.table_rows, null);
                TextView tv = (TextView) custom.findViewById(R.id.nomeRifugio);
                tv.setText(Rifugi.get(i).getNomeRifugio());
                table.addView(custom);
            }
        });



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}