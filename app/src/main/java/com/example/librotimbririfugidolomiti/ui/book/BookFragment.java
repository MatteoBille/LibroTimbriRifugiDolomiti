package com.example.librotimbririfugidolomiti.ui.book;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.HutGroup;
import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
import com.example.librotimbririfugidolomiti.database.Rifugio;
import com.example.librotimbririfugidolomiti.databinding.FragmentBookBinding;
import com.example.librotimbririfugidolomiti.ui.hutdetail.HutDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookFragment extends Fragment {
    private RifugiViewModel mRifugiViewModel;
    private FragmentBookBinding binding;
    private int currentPage;

    Map<Integer, List<Integer>> bookPages;
    private SharedPreferences sharedPreferences;

    public BookFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        mRifugiViewModel = new ViewModelProvider(this).get(RifugiViewModel.class);

        sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        if (sharedPreferences == null) {
            //TODO:throw exception
        }
        bookPages = setPagesMap();

        binding.next.setOnClickListener(v -> toNextPage());
        binding.prev.setOnClickListener(v -> toPrevPage());

        currentPage = 0;
        setPage(currentPage);
        return root;
    }

    private Map<Integer, List<Integer>> setPagesMap() {
        bookPages = new HashMap<>();
        List<HutGroup> groups = mRifugiViewModel.getNumberOfHutforEachDolomitcGroup();
        for (HutGroup group : groups) {
            List<Rifugio> huts = mRifugiViewModel.getListOfHutByDolomiticGroup(group.getGruppoDolomitico());
            for (int i = 0; i < huts.size(); i += 2) {
                List<Integer> hutsCode = new ArrayList<>();
                hutsCode.add(huts.get(i).getCodiceRifugio());
                if (i + 1 < huts.size()) {
                    hutsCode.add(huts.get(i + 1).getCodiceRifugio());
                }
                bookPages.put(bookPages.size(), hutsCode);
            }
        }
        return bookPages;
    }

    private void setPage(int pageNumber) {
        if (isFinalPage()) {
            binding.next.setEnabled(false);
        }
        if (isFirstPage()) {
            binding.prev.setEnabled(false);
        }

        Resources res = getResources();
        String formatted = String.format(res.getString(R.string.page_index), (pageNumber + 1), bookPages.size());
        binding.pageNumber.setText(formatted);

        binding.secondCardView.setVisibility(View.VISIBLE);
        List<Integer> hutsId = bookPages.get(pageNumber);


        Rifugio hut1 = mRifugiViewModel.getHutById(hutsId.get(0));
        int codicePersona = sharedPreferences.getInt("codicePersona", -1);
        if (codicePersona == -1) {
            //TODO:exception
        }
        Bitmap bit1 = BitmapFactory.decodeFile(requireContext().getFilesDir() + "/images/" + hut1.getNomeImmagine());
        binding.imageHut1.setImageBitmap(bit1);
        binding.nameHut1.setText(hut1.getNomeRifugio());
        binding.title.setText(hut1.getGruppoDolomitico());
        sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        int numberOfVisit1 = mRifugiViewModel.numberOfVisitByHutId(codicePersona, hut1.getCodiceRifugio());

        Log.i("VISITATO1", numberOfVisit1 + "");
        if (numberOfVisit1 > 0) {
            binding.imageHut1overlay.setVisibility(View.VISIBLE);
            binding.imageHut1overlay.setImageResource(R.drawable.timbro);
        }

        binding.firstCardView.setOnClickListener((e) -> {
            Log.i("CODID", hut1.getCodiceRifugio() + "");
            openHutDetailsActivity(hut1.getCodiceRifugio());
        });

        if (hutsId.size() == 1) {
            binding.secondCardView.setVisibility(View.INVISIBLE);
        } else {

            Rifugio hut2 = mRifugiViewModel.getHutById(hutsId.get(1));
            int numberOfVisit2 = mRifugiViewModel.numberOfVisitByHutId(codicePersona, hut2.getCodiceRifugio());
            Bitmap bit2 = BitmapFactory.decodeFile(getContext().getFilesDir() + "/images/" + hut2.getNomeImmagine());
            binding.imageHut2.setImageBitmap(bit2);
            binding.nameHut2.setText(hut2.getNomeRifugio());

            Log.i("VISITATO2", numberOfVisit2 + "");
            if (numberOfVisit2 > 0) {
                binding.imageHut2overlay.setVisibility(View.VISIBLE);
                binding.imageHut2overlay.setImageResource(R.drawable.timbro);
            }
            binding.secondCardView.setOnClickListener((e) -> {
                openHutDetailsActivity(hut2.getCodiceRifugio());
            });
        }


    }

    private boolean isFirstPage() {
        return currentPage == 0;
    }

    private boolean isFinalPage() {
        return currentPage == bookPages.size() - 1;
    }

    private void openHutDetailsActivity(Integer codiceRifugio) {

        Intent intent = new Intent(getActivity(), HutDetailActivity.class);
        intent.putExtra("codiceRifugio", codiceRifugio);
        startActivity(intent);
    }

    private void toNextPage() {
        binding.imageHut1overlay.setVisibility(View.INVISIBLE);
        binding.imageHut2overlay.setVisibility(View.INVISIBLE);
        if (currentPage < bookPages.size()) {
            currentPage++;
            setPage(currentPage);
        }
        binding.prev.setEnabled(true);
    }

    private void toPrevPage() {
        binding.imageHut1overlay.setVisibility(View.INVISIBLE);
        binding.imageHut2overlay.setVisibility(View.INVISIBLE);
        if (currentPage >= 1) {
            currentPage--;
            setPage(currentPage);
        }
        binding.next.setEnabled(true);
    }

}