package com.example.librotimbririfugidolomiti.ui.hutdetail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librotimbririfugidolomiti.database.HutsViewModel;
import com.example.librotimbririfugidolomiti.databinding.FragmentHutDetailBinding;

public class HutDetailFragment extends Fragment {
    private FragmentHutDetailBinding binding;
    private HutsViewModel databaseSql;
    private HutVisitAdapter adapter;
    private int HutId;
    private String personId;
    private static final String PERSON_ID_IDENTIFIER = "PersonId";
    private static final String HUT_ID_IDENTIFIER = "HutId";
    private static final String OBTAINED_IDENTIFIER = "Obtained";

    public HutDetailFragment() {
    }


    public static HutDetailFragment newInstance(int codiceRifugio, String codicePersona, boolean obtained) {
        Bundle bundle = new Bundle();
        bundle.putInt(HUT_ID_IDENTIFIER, codiceRifugio);
        bundle.putBoolean(OBTAINED_IDENTIFIER, obtained);
        bundle.putString(PERSON_ID_IDENTIFIER, codicePersona);
        HutDetailFragment categoryResultFragment = new HutDetailFragment();
        categoryResultFragment.setArguments(bundle);
        return categoryResultFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentHutDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        databaseSql = new ViewModelProvider(this).get(HutsViewModel.class);
        personId = getArguments().getString(PERSON_ID_IDENTIFIER);
        HutId = getArguments().getInt(HUT_ID_IDENTIFIER);
        databaseSql.getHutById(HutId).observe(getViewLifecycleOwner(), hut -> {
            Bitmap bit1 = BitmapFactory.decodeFile(requireContext().getFilesDir() + "/images/" + hut.getNomeImmagine());
            binding.hutImage.setImageBitmap(bit1);
            binding.nomeRifugio.setText(hut.getNomeRifugio());
            createRecyclerView();
        });
        return root;
    }


    private void createRecyclerView() {
        databaseSql.getVisitsByHutAndPersonAsync(HutId, personId).observe(getViewLifecycleOwner(), (visits) -> {
            if (visits.size() != 0) {
                RecyclerView recyclerView = binding.recyclerview;
                adapter = new HutVisitAdapter(visits);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);

            }
        });
    }


}