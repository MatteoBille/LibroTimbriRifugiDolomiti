package com.example.librotimbririfugidolomiti.ui.book;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.databinding.ActivityMyBookBinding;

public class BookActivity extends AppCompatActivity {
    private ActivityMyBookBinding binding;

    private static final String PERSON_ID_IDENTIFIER = "PersonId";
    private static final String OBTAINED_IDENTIFIER = "Obtained";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();

        boolean obtained = extras.getBoolean(OBTAINED_IDENTIFIER);
        String codicePersona = extras.getString(PERSON_ID_IDENTIFIER);
        BookFragment book = BookFragment.newInstance(codicePersona, obtained);


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.my_book, book).commit();
    }
}