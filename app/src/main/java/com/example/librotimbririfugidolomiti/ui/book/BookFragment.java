package com.example.librotimbririfugidolomiti.ui.book;

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
import com.example.librotimbririfugidolomiti.database.Entity.HutGroup;
import com.example.librotimbririfugidolomiti.database.Entity.Persona;
import com.example.librotimbririfugidolomiti.database.Entity.Rifugio;
import com.example.librotimbririfugidolomiti.database.HutsViewModel;
import com.example.librotimbririfugidolomiti.databinding.FragmentBookBinding;
import com.example.librotimbririfugidolomiti.ui.hutdetail.HutDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment {
    private HutsViewModel databaseSql;
    private FragmentBookBinding binding;
    private int currentPage;
    private SparseArray<ArrayList<Integer>> bookPages;
    private String personId;

    private static final String PERSON_ID_IDENTIFIER = "PersonId";
    private static final String OBTAINED_IDENTIFIER = "Obtained";
    private static final String HUT_ID_IDENTIFIER = "HutId";

    public BookFragment() {
    }


    public static BookFragment newInstance(String codicePersona, boolean obtained) {
        BookFragment myFragment = new BookFragment();
        Bundle args = new Bundle();
        args.putBoolean(OBTAINED_IDENTIFIER, obtained);
        args.putString(PERSON_ID_IDENTIFIER, codicePersona);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentBookBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        databaseSql = new ViewModelProvider(this).get(HutsViewModel.class);

        if (getArguments() != null) {
            personId = getArguments().getString(PERSON_ID_IDENTIFIER);
        }

        Persona owner = databaseSql.getPersonById(personId);
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
            List<Rifugio> entityHuts = databaseSql.getListOfHutByDolomiticGroup(group.getGruppoDolomitico());
            for (int i = 0; i < entityHuts.size(); i += 2) {
                ArrayList<Integer> hutsCode = new ArrayList<>();
                hutsCode.add(entityHuts.get(i).getCodiceRifugio());
                if (i + 1 < entityHuts.size()) {
                    hutsCode.add(entityHuts.get(i + 1).getCodiceRifugio());
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
            setCardviewElementHut(hut, personId, binding.imageHut1, binding.imageHut1overlay, binding.nameHut1, binding.firstCardView);
        });


        if (hutsInThisPage.size() == 1) {
            binding.secondCardView.setVisibility(View.INVISIBLE);
        } else {
            databaseSql.getHutById(hutsInThisPage.get(1)).observe(getViewLifecycleOwner(), hut ->
                    setCardviewElementHut(hut, personId, binding.imageHut2, binding.imageHut2overlay, binding.nameHut2, binding.secondCardView)
            );
        }

    }

    private void setCardviewElementHut(Rifugio entityHut, String codicePersona, ImageView
            im1, ImageView imOverlay, TextView text, CardView cardView) {

        Bitmap bit = BitmapFactory.decodeFile(requireContext().getFilesDir() + "/images/" + entityHut.getNomeImmagine());
        int newHeight = 500;
        float aspect = bit.getWidth() / (float) bit.getHeight();
        int newWidth = (int) (newHeight * aspect);
        bit = Bitmap.createScaledBitmap(bit, newWidth, newHeight, true);
        im1.setImageBitmap(bit);
        text.setText(entityHut.getNomeRifugio());

        int numberOfVisit1 = databaseSql.numberOfVisitByHutId(codicePersona, entityHut.getCodiceRifugio());


        if (numberOfVisit1 > 0) {
            imOverlay.setVisibility(View.VISIBLE);
            imOverlay.setImageResource(R.drawable.timbro);

            cardView.setOnClickListener((e) ->
                    openHutDetailsActivity(entityHut.getCodiceRifugio())
            );
        } else {
            cardView.setOnClickListener(null);
        }


    }

    private boolean isFirstPage() {
        return currentPage == 0;
    }

    private boolean isFinalPage() {
        return currentPage == bookPages.size() - 1;
    }

    private void toNextPage() {
        if (currentPage < bookPages.size()) {
            binding.imageHut1overlay.setVisibility(View.INVISIBLE);
            binding.imageHut2overlay.setVisibility(View.INVISIBLE);

            currentPage++;
            showPage(currentPage);
            binding.prev.setEnabled(true);
        }
    }

    private void toPrevPage() {
        if (currentPage >= 1) {
            binding.imageHut1overlay.setVisibility(View.INVISIBLE);
            binding.imageHut2overlay.setVisibility(View.INVISIBLE);

            currentPage--;
            showPage(currentPage);

            binding.next.setEnabled(true);
        }
    }

    private void openHutDetailsActivity(Integer codiceRifugio) {

        Intent intent = new Intent(getActivity(), HutDetailActivity.class);
        if (getArguments() != null) {
            intent.putExtra(PERSON_ID_IDENTIFIER, personId);
            intent.putExtra(OBTAINED_IDENTIFIER, getArguments().getBoolean(OBTAINED_IDENTIFIER));
        }
        intent.putExtra(HUT_ID_IDENTIFIER, codiceRifugio);
        startActivity(intent);
    }

}