package com.example.librotimbririfugidolomiti.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.Persona;
import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
import com.example.librotimbririfugidolomiti.databinding.ActivityLoginBinding;
import com.example.librotimbririfugidolomiti.ui.MainActivity;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private RifugiViewModel mRifugiViewModel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        mRifugiViewModel = new ViewModelProvider(this).get(RifugiViewModel.class);
        Log.i("INSERT", "SONO NEL LOGIN");
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.login.setOnClickListener(e -> {
            Log.i("INSERT", "BOTTONE PREMUTO");
            createAccount();
        });

    }

    private void createAccount() {
        Log.i("INSERT", "STO CREANDO");
        String nome = binding.usernameName.getText().toString();
        String surname = binding.usernameSurname.getText().toString();
        String email = binding.email.getText().toString();
        Persona person = new Persona(nome + " " + surname, email);
        Log.i("INSERT", person + "");
        Long res = mRifugiViewModel.insert(person);
        myEdit.putBoolean("firstTime", false);
        myEdit.putInt("codicePersona", res.intValue());
        myEdit.commit();

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}