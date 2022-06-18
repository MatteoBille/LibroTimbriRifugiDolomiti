package com.example.librotimbririfugidolomiti.ui.hutdetail;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.databinding.ActivityHutDetailBinding;

public class HutDetailActivity extends AppCompatActivity {

    private static final String PERSON_ID_IDENTIFIER = "PersonId";
    private static final String HUT_ID_IDENTIFIER = "HutId";
    private static final String OBTAINED_IDENTIFIER = "Obtained";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHutDetailBinding binding = ActivityHutDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        Bundle extras = getIntent().getExtras();

        int codiceRifugio = extras.getInt(HUT_ID_IDENTIFIER);
        String codicePersona = extras.getString(PERSON_ID_IDENTIFIER);


        boolean obtained = extras.getBoolean(OBTAINED_IDENTIFIER);
        HutDetailFragment hdf = HutDetailFragment.newInstance(codiceRifugio, codicePersona, obtained);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.hutDetail, hdf).commit();
    }
}