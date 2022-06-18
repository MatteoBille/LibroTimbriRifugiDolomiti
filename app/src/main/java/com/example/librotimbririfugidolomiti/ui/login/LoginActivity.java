package com.example.librotimbririfugidolomiti.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.Entity.Persona;
import com.example.librotimbririfugidolomiti.database.HutsViewModel;
import com.example.librotimbririfugidolomiti.databinding.ActivityLoginBinding;
import com.example.librotimbririfugidolomiti.ui.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private HutsViewModel databaseSql;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        databaseSql = new ViewModelProvider(this).get(HutsViewModel.class);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.login.setOnClickListener(e -> createAccount());

    }

    private void createAccount() {
        String nome = binding.usernameName.getText().toString();
        String surname = binding.usernameSurname.getText().toString();
        String email = binding.email.getText().toString();

        FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();

        Persona persona = new Persona(nome + " " + surname, email);
        Map<String, Object> user = persona.toMap();
        user.put("sharingOf", Collections.emptyList());
        firebaseDatabase.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Persona persona = new Persona(documentReference.getId(), nome + " " + surname, email);
                        databaseSql.insert(persona);
                        myEdit.putBoolean("firstTime", false);
                        myEdit.putString("codicePersona", documentReference.getId()).apply();

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(e -> Log.w("FIREBASE", "Error writing document", e));


    }
}