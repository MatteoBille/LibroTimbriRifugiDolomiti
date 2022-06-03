package com.example.librotimbririfugidolomiti.ui.book;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.databinding.ActivityMyBookBinding;

public class MyBookActivity extends AppCompatActivity {
    private ActivityMyBookBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        BookFragment book;
        boolean obtained;
        String codicePersona;
        codicePersona = extras.getString("codicePersona");
        obtained = extras.getBoolean("obtained");

        Log.i("MY BOOK ACTIVITY", codicePersona);
        Log.i("MY BOOK ACTIVITY", obtained + "");
        book = BookFragment.newInstance(codicePersona, obtained);


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.my_book, book).commit();
    }
}