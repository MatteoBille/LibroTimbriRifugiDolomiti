package com.example.librotimbririfugidolomiti.ui.hutdetail;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
import com.example.librotimbririfugidolomiti.database.Rifugio;
import com.example.librotimbririfugidolomiti.database.VisitaRifugio;
import com.example.librotimbririfugidolomiti.databinding.FragmentBookBinding;
import com.example.librotimbririfugidolomiti.databinding.FragmentHutDetailBinding;
import com.example.librotimbririfugidolomiti.ui.listofhut.RecyclerCustomAdapter;

import java.util.List;

public class HutDetailFragment extends Fragment {
    private FragmentHutDetailBinding binding;
    private RifugiViewModel mRifugiViewModel;
    private SharedPreferences sharedPreferences;

    private static final String CODICE_RIF = "codiceRifugio";

    public HutDetailFragment() {
        // Required empty public constructor
    }

    HutVisitRecyclerCustomAdapter adapter;
    int codiceRifugio;
    int codicePersona;


    public static HutDetailFragment newInstance(int codiceRifugio) {
        Bundle bundle = new Bundle();
        bundle.putInt(CODICE_RIF, codiceRifugio);
        HutDetailFragment categoryResultFragment = new HutDetailFragment();
        categoryResultFragment.setArguments(bundle);
        return categoryResultFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        binding = FragmentHutDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mRifugiViewModel = new ViewModelProvider(this).get(RifugiViewModel.class);
        sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        codicePersona = sharedPreferences.getInt("codicePersona", -1);

        if (getArguments().containsKey(CODICE_RIF)) {
            codiceRifugio = getArguments().getInt(CODICE_RIF);
            Rifugio hut =mRifugiViewModel.getHutById(codiceRifugio);
            Bitmap bit1 = BitmapFactory.decodeFile(getContext().getFilesDir() + "/images/" + hut.getNomeImmagine());
            binding.hutImage.setImageBitmap(bit1);
            binding.nomeRifugio.setText(hut.getNomeRifugio());
        } else {
            //TODO:add exception
        }

        createRecyclerView(inflater);
        return root;
    }


    private void createRecyclerView(LayoutInflater inflater) {
        List<VisitaRifugio> visits = mRifugiViewModel.getVisitsByHutAndPerson(codiceRifugio, codicePersona);
        if (visits.size() != 0) {
            RecyclerView recyclerView = binding.recyclerview;
            adapter = new HutVisitRecyclerCustomAdapter(new HutVisitRecyclerCustomAdapter.VisitDiff());

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            adapter.submitList(visits);
        }
    }


}