package com.example.librotimbririfugidolomiti.ui.visithut;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
import com.example.librotimbririfugidolomiti.database.Rifugio;
import com.example.librotimbririfugidolomiti.database.VisitaRifugio;
import com.example.librotimbririfugidolomiti.databinding.FragmentVisitRifugioBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public class VisitRifugio extends Fragment {

    private FusedLocationProviderClient fusedLocationClient;
    FragmentVisitRifugioBinding binding;
    private RifugiViewModel mRifugiViewModel;
    Map<LatLng, Rifugio> rifugesLocation;
    Map<Double,Rifugio> synchronizedDistanzaRifugio = Collections.synchronizedSortedMap(new TreeMap<>());
    boolean viewOpen;

    private Handler mHandler = new Handler();
    private Runnable checkDistanceThread = new Runnable() {
        @Override
        public void run() {
            getLocation();
            Log.i("Thread","THREAD");
            mHandler.postDelayed(this, 2000);
        }
    };

    public VisitRifugio() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("STATUS","CREATEVIEW");

        binding = FragmentVisitRifugioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mRifugiViewModel = new ViewModelProvider(this).get(RifugiViewModel.class);

        binding.button.setOnClickListener(v -> insertVisit());
        rifugesLocation = new HashMap<>();

        mRifugiViewModel.getAllRifugi().observe(getViewLifecycleOwner(), Huts -> {
            for (Rifugio hut : Huts) {
                LatLng tempCord = new LatLng(hut.getLatitudine(), hut.getLongitudine());
                rifugesLocation.put(tempCord, hut);
            }
            viewOpen = true;

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        });
        return root;
    }


    private void insertVisit() {
        int idRifugio =getSmallDistanceHut().getValue().getCodiceRifugio();
        Log.i("ID",idRifugio+"");
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dataVisita = sdf.format(currentTime);
        VisitaRifugio vis =new VisitaRifugio(1,idRifugio,dataVisita);
        mRifugiViewModel.visitHut(1,idRifugio,dataVisita);
    }

    private void measureDistance(Location location) {
        binding.distanza.setText(location.getLatitude()+"-"+location.getLongitude());
        for(Map.Entry<LatLng, Rifugio> entry : rifugesLocation.entrySet()){
            Double distance=getDistanceInMeter(entry.getKey(),location);
            synchronizedDistanzaRifugio.put(distance, entry.getValue());
        }
        binding.distanza.setText(getSmallDistanceHut().getKey()+"m");
        binding.nomeRifugio.setText(getSmallDistanceHut().getValue().getNomeRifugio());

        if(getSmallDistanceHut().getKey()>100){
            binding.button.setEnabled(false);
            binding.distanza.setTextColor(Color.RED);
        }else{
            binding.button.setEnabled(true);
            binding.distanza.setTextColor(Color.GREEN);
        }

    }

    private Map.Entry<Double,Rifugio> getSmallDistanceHut(){
        Log.i("TYPE",synchronizedDistanzaRifugio.getClass()+"");
        Double key=((SortedMap<Double,Rifugio>)synchronizedDistanzaRifugio).firstKey();
        Rifugio hut=synchronizedDistanzaRifugio.get(key);
        return new AbstractMap.SimpleEntry<>(key,hut);
    }

    public static double getDistanceInMeter(LatLng source, Location destination) {
        double distance = 0d;
        if (source != null && destination != null) {
            double lat1 = source.latitude;
            double lon1 = source.longitude;
            double lat2 = destination.getLatitude();
            double lon2 = destination.getLongitude();

            final int R = 6371;
            // Radius of the earth in km
            double dLat = Math.toRadians(lat2 - lat1);
            // deg2rad below
            double dLon = Math.toRadians(lon2 - lon1);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            // Distance in m
            distance = (R * c) * 1000;
        }
        return distance;
    }


    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("PERMESSI","NOT GRANTED");
            return ;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        Log.i("LOCATION",location+"");
                        if (location != null) {
                            measureDistance(location);
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.post(checkDistanceThread);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("STATUS","PAUSE");
        mHandler.removeCallbacks(checkDistanceThread);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("STATUS","STOP");
    }
}