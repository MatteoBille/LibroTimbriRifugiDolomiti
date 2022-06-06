package com.example.librotimbririfugidolomiti.ui.book;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.HutGroup;
import com.example.librotimbririfugidolomiti.database.Persona;
import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
import com.example.librotimbririfugidolomiti.database.Rifugio;
import com.example.librotimbririfugidolomiti.databinding.FragmentBookBinding;
import com.example.librotimbririfugidolomiti.ui.hutdetail.HutDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment {
    private RifugiViewModel databaseSql;
    private FragmentBookBinding binding;
    private int currentPage;
    private SparseArray<ArrayList<Integer>> bookPages;
    private String codicePersona;

    public BookFragment() {
    }


    public static BookFragment newInstance(String codicePersona, boolean obtained) {
        BookFragment myFragment = new BookFragment();
        Bundle args = new Bundle();
        args.putBoolean("obtained", obtained);
        args.putString("codicePersona", codicePersona);
        myFragment.setArguments(args);
        return myFragment;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentBookBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        databaseSql = new ViewModelProvider(this).get(RifugiViewModel.class);

        if (getArguments() != null) {
            codicePersona = getArguments().getString("codicePersona");
        }

        Persona owner = databaseSql.getPersonById(codicePersona);
        binding.nomePersona.setText(owner.getNomeCognome());
        bookPages = setHutsIdForEachPage();


        binding.next.setOnClickListener(v -> toNextPage());
        binding.prev.setOnClickListener(v -> toPrevPage());
        root.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {
                toNextPage();
            }

            @Override
            public void onSwipeRight() {
                toPrevPage();
            }
        });


        currentPage = 0;
        showPage(currentPage);

        return root;
    }


    private SparseArray<ArrayList<Integer>> setHutsIdForEachPage() {
        bookPages = new SparseArray<>();
        List<HutGroup> groups = databaseSql.getNumberOfHutforEachDolomitcGroup();
        for (HutGroup group : groups) {
            List<Rifugio> huts = databaseSql.getListOfHutByDolomiticGroup(group.getGruppoDolomitico());
            for (int i = 0; i < huts.size(); i += 2) {
                ArrayList<Integer> hutsCode = new ArrayList<>();
                hutsCode.add(huts.get(i).getCodiceRifugio());
                if (i + 1 < huts.size()) {
                    hutsCode.add(huts.get(i + 1).getCodiceRifugio());
                }
                bookPages.append(bookPages.size(), hutsCode);
            }
        }
        return bookPages;
    }

    private void showPage(int pageNumber) {
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
        List<Integer> hutsInThisPage = bookPages.get(pageNumber);


        databaseSql.getHutById(hutsInThisPage.get(0)).observe(getViewLifecycleOwner(), hut -> {
            binding.title.setText(hut.getGruppoDolomitico());
            setCardviewElementHut(hut, codicePersona, binding.imageHut1, binding.imageHut1overlay, binding.nameHut1, binding.firstCardView);
        });


        if (hutsInThisPage.size() == 1) {
            binding.secondCardView.setVisibility(View.INVISIBLE);
        } else {
            databaseSql.getHutById(hutsInThisPage.get(1)).observe(getViewLifecycleOwner(), hut ->
                    setCardviewElementHut(hut, codicePersona, binding.imageHut2, binding.imageHut2overlay, binding.nameHut2, binding.secondCardView)
            );
        }

    }

    private void setCardviewElementHut(Rifugio hut, String codicePersona, ImageView
            im1, ImageView imOverlay, TextView text, CardView cardView) {

        Bitmap bit = BitmapFactory.decodeFile(requireContext().getFilesDir() + "/images/" + hut.getNomeImmagine());
        int newHeight = 500;
        float aspect = bit.getWidth() / (float) bit.getHeight();
        int newWidth = (int) (newHeight * aspect);
        bit = Bitmap.createScaledBitmap(bit, newWidth, newHeight, true);
        im1.setImageBitmap(bit);
        text.setText(hut.getNomeRifugio());

        int numberOfVisit1 = databaseSql.numberOfVisitByHutId(codicePersona, hut.getCodiceRifugio());

        if (numberOfVisit1 > 0) {
            imOverlay.setVisibility(View.VISIBLE);
            imOverlay.setImageResource(R.drawable.timbro);
        }

        cardView.setOnClickListener((e) ->
                openHutDetailsActivity(hut.getCodiceRifugio())
        );
    }

    private boolean isFirstPage() {
        return currentPage == 0;
    }

    private boolean isFinalPage() {
        return currentPage == bookPages.size() - 1;
    }

    private void toNextPage() {
        binding.imageHut1overlay.setVisibility(View.INVISIBLE);
        binding.imageHut2overlay.setVisibility(View.INVISIBLE);
        if (currentPage < bookPages.size()) {
            currentPage++;
            showPage(currentPage);
        }
        binding.prev.setEnabled(true);
    }

    private void toPrevPage() {
        binding.imageHut1overlay.setVisibility(View.INVISIBLE);
        binding.imageHut2overlay.setVisibility(View.INVISIBLE);
        if (currentPage >= 1) {
            currentPage--;
            showPage(currentPage);
        }
        binding.next.setEnabled(true);
    }

    private void openHutDetailsActivity(Integer codiceRifugio) {

        Intent intent = new Intent(getActivity(), HutDetailActivity.class);
        if (getArguments() != null) {
            intent.putExtra("codicePersona", codicePersona);
            intent.putExtra("obtained", getArguments().getBoolean("obtained"));
        }
        intent.putExtra("codiceRifugio", codiceRifugio);
        startActivity(intent);
    }

}