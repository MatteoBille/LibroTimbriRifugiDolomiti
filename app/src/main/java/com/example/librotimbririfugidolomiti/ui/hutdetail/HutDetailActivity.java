package com.example.librotimbririfugidolomiti.ui.hutdetail;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.databinding.ActivityHutDetailBinding;

public class HutDetailActivity extends AppCompatActivity {
    private ActivityHutDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHutDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        int codiceRifugio=getIntent().getExtras().getInt("codiceRifugio");


        HutDetailFragment hdf = HutDetailFragment.newInstance(codiceRifugio);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.hutDetail, hdf).commit();
    }
}