package com.example.librotimbririfugidolomiti.ui.hutdetail;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
import com.example.librotimbririfugidolomiti.databinding.FragmentHutDetailBinding;

public class HutDetailFragment extends Fragment {
    private FragmentHutDetailBinding binding;
    private RifugiViewModel mRifugiViewModel;
    private SharedPreferences sharedPreferences;

    private static final String CODICE_RIF = "codiceRifugio";

    public HutDetailFragment() {
        // Required empty public constructor
    }

    HutVisitAdapter adapter;
    int codiceRifugio;
    String codicePersona;


    public static HutDetailFragment newInstance(int codiceRifugio, String codicePersona, boolean obtained) {
        Bundle bundle = new Bundle();
        bundle.putInt(CODICE_RIF, codiceRifugio);
        bundle.putBoolean("obtained", obtained);
        bundle.putString("codicePersona", codicePersona);
        HutDetailFragment categoryResultFragment = new HutDetailFragment();
        categoryResultFragment.setArguments(bundle);
        return categoryResultFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentHutDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mRifugiViewModel = new ViewModelProvider(this).get(RifugiViewModel.class);
        sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        codicePersona = getArguments().getString("codicePersona");
        codiceRifugio = getArguments().getInt(CODICE_RIF);
        mRifugiViewModel.getHutById(codiceRifugio).observe(getViewLifecycleOwner(), hut -> {
            Bitmap bit1 = BitmapFactory.decodeFile(getContext().getFilesDir() + "/images/" + hut.getNomeImmagine());
            binding.hutImage.setImageBitmap(bit1);
            binding.nomeRifugio.setText(hut.getNomeRifugio());
            createRecyclerView();
        });
        return root;
    }


    private void createRecyclerView() {
        mRifugiViewModel.getVisitsByHutAndPerson(codiceRifugio, codicePersona).observe(getViewLifecycleOwner(), (visits) -> {
            Log.i("RECICLER VIEW", visits.toString());
            if (visits.size() != 0) {
                RecyclerView recyclerView = binding.recyclerview;
                adapter = new HutVisitAdapter(visits);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);

            }
        });
    }


}