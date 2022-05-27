package com.example.librotimbririfugidolomiti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.librotimbririfugidolomiti.databinding.ActivityMyBookBinding;
import com.example.librotimbririfugidolomiti.ui.mybook.BookFragment;

public class MyBookActivity extends AppCompatActivity {
    private ActivityMyBookBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.my_book, new BookFragment()).commit();
    }
}