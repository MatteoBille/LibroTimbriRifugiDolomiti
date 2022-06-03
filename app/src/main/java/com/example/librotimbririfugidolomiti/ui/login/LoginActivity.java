package com.example.librotimbririfugidolomiti.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.Persona;
import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
import com.example.librotimbririfugidolomiti.databinding.ActivityLoginBinding;
import com.example.librotimbririfugidolomiti.ui.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private RifugiViewModel rifugiViewModel;
    FirebaseFirestore firebaseDatabase;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        rifugiViewModel = new ViewModelProvider(this).get(RifugiViewModel.class);
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

        firebaseDatabase = FirebaseFirestore.getInstance();

        Persona person = new Persona(nome + " " + surname, email);
        Map<String, Object> user = person.toMap();
        user.put("sharingOf", Arrays.asList());
        firebaseDatabase.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("FIREBASE", "DocumentSnapshot added with ID: " + documentReference.getId());

                        Persona person = new Persona(documentReference.getId(), nome + " " + surname, email);
                        rifugiViewModel.insert(person);
                        myEdit.putBoolean("firstTime", false);
                        myEdit.putString("codicePersona", documentReference.getId());
                        myEdit.commit();

                        boolean firstTime = sharedPreferences.getBoolean("firstTime", true);
                        Log.i("FIREBASE", firstTime + "");

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FIREBASE", "Error writing document", e);
                    }
                });


    }
}