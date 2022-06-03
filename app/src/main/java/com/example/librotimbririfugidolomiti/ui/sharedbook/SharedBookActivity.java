package com.example.librotimbririfugidolomiti.ui.sharedbook;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.databinding.ActivitySharedBookBinding;
import com.example.librotimbririfugidolomiti.ui.home.HomeFragment;

public class SharedBookActivity extends AppCompatActivity {

    ActivitySharedBookBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySharedBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        Bundle extras = getIntent().getExtras();
        String codicePersona = extras.getString("codicePersona");

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        HomeFragment home = HomeFragment.newInstance(codicePersona, true);
        transaction.add(R.id.fragmentContainerView, home).commit();
    }

}