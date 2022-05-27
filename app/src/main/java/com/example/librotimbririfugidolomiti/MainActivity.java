package com.example.librotimbririfugidolomiti;

import android.Manifest;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.Log;

import com.example.librotimbririfugidolomiti.database.RifugiRoomDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.example.librotimbririfugidolomiti.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    String[] PermissionsLocation =
            {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.INTERNET
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkPermissions();
        super.onCreate(savedInstanceState);
        copyAssets("images");
        getSupportActionBar().hide();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_visita, R.id.navigation_map)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


    }

    private void checkPermissions() {
        int iter = 0;
        for (String p : PermissionsLocation) {
            if (ContextCompat.checkSelfPermission(this, p) != getPackageManager().PERMISSION_GRANTED) {
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
