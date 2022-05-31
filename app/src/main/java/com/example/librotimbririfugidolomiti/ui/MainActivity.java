package com.example.librotimbririfugidolomiti.ui;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.librotimbririfugidolomiti.FileHelper;
import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.databinding.ActivityMainBinding;
import com.example.librotimbririfugidolomiti.ui.login.LoginActivity;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    String[] PermissionsLocation =
            {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.INTERNET
            };
    private SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();

        boolean firstTime = sharedPreferences.getBoolean("firstTime", true);
        Objects.requireNonNull(getSupportActionBar()).hide();

        if (firstTime) {
            firstTimeRoutine();
        } else {
            normalFlow();
        }
    }

    private void normalFlow() {

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_visita, R.id.navigation_map)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void firstTimeRoutine() {
        copyAssets("images");
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
        myEdit.apply();
    }

    private void checkPermissions() {
        int iter = 0;
        for (String p : PermissionsLocation) {
            getPackageManager();
            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, PermissionsLocation, 101 + iter);
            }
            ++iter;
        }
    }

    private void copyAssets(String path) {
        String[] list;
        try {
            list = getAssets().list(path);
            Log.i("RIFUGI", path + " " + list.length);
            if (list.length > 0) {
                // This is a folder
                for (String file : list) {
                    copyAssets(path + "/" + file);
                }
            } else {
                Log.i("RIFUGI", getBaseContext().getFilesDir() + "/" + path);
                AssetFileDescriptor fileDescriptor = getAssets().openFd(path);
                FileInputStream fileInputStream = fileDescriptor.createInputStream();
                FileHelper.copyFile(fileInputStream, getBaseContext().getFilesDir() + "/" + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
