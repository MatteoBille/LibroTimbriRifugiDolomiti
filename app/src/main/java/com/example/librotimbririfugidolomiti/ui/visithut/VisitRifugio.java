package com.example.librotimbririfugidolomiti.ui.visithut;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
import com.example.librotimbririfugidolomiti.database.Rifugio;
import com.example.librotimbririfugidolomiti.database.VisitaRifugio;
import com.example.librotimbririfugidolomiti.databinding.FragmentVisitRifugioBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
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
    Map<Double, Rifugio> synchronizedDistanzaRifugio = Collections.synchronizedSortedMap(new TreeMap<>());
    boolean viewOpen;
    SharedPreferences sharedPreferences;
    FirebaseFirestore firebaseDatabase;

    private final Handler mHandler = new Handler();
    private final Runnable checkDistanceThread = new Runnable() {
        @Override
        public void run() {
            getLocation();
            Log.i("Thread", "THREAD");
            mHandler.postDelayed(this, 2000);
        }
    };

    public VisitRifugio() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("STATUS", "CREATEVIEW");
        sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        binding = FragmentVisitRifugioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mRifugiViewModel = new ViewModelProvider(this).get(RifugiViewModel.class);

        binding.button.setEnabled(false);
        binding.button.setOnClickListener(v -> openVisitPopup());

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


    private void openVisitPopup() {
        Map.Entry<Double, Rifugio> hut = getSmallDistanceHut();
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_insert_visit_popup, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);


        ((TextView) popupView.findViewById(R.id.hutName)).setText(hut.getValue().getNomeRifugio());
        popupView.findViewById(R.id.visitButton).setOnClickListener(e -> {
            saveVisitInDb(hut, popupView, popupWindow);
        });

        RatingBar ratingBar = popupView.findViewById(R.id.ratingBar);
        ratingBar.setRating(4.8f);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.i("POPUP", "onRatingChanged: rating : " + rating);
            }
        });


    }

    private void saveVisitInDb(Map.Entry<Double, Rifugio> hut, View popupView, PopupWindow popupWindow) {
        int idRifugio = hut.getValue().getCodiceRifugio();
        Log.i("ID", idRifugio + "");
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dataVisita = sdf.format(currentTime);


        String info = ((EditText) popupView.findViewById(R.id.visitDescription)).getText().toString();
        Integer star = Math.round(((RatingBar) popupView.findViewById(R.id.ratingBar)).getRating());
        String codicePersona = sharedPreferences.getString("codicePersona", null);

        mRifugiViewModel.visitHut(codicePersona, idRifugio, dataVisita, info, star);
        popupWindow.dismiss();
    }

    private void measureDistance(Location location) {
        binding.distanza.setText(location.getLatitude() + "-" + location.getLongitude());
        for (Map.Entry<LatLng, Rifugio> entry : rifugesLocation.entrySet()) {
            Double distance = getDistanceInMeter(entry.getKey(), location);
            synchronizedDistanzaRifugio.put(distance, entry.getValue());
        }
        String formattedDistance = formatDistance(getSmallDistanceHut().getKey());
        binding.distanza.setText(formattedDistance);
        binding.nomeRifugio.setText(getSmallDistanceHut().getValue().getNomeRifugio());

        String codicePersona = sharedPreferences.getString("codicePersona", null);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dataVisita = sdf.format(currentTime);
        VisitaRifugio visitaRifugio = mRifugiViewModel.getVisitsByHutPersonAndDate(getSmallDistanceHut().getValue().getCodiceRifugio(), codicePersona, dataVisita);
        Log.i("VISITA", visitaRifugio + "");
        if (visitaRifugio != null) {
            binding.button.setEnabled(false);
        } else {
            binding.button.setEnabled(true);


            if (getSmallDistanceHut().getKey() > 100) {
                binding.button.setEnabled(false);
                binding.distanza.setTextColor(Color.RED);
            } else {
                binding.button.setEnabled(true);
                binding.distanza.setTextColor(Color.GREEN);
            }
        }
        int nVisit = mRifugiViewModel.getNumberOfVisitByHut(getSmallDistanceHut().getValue().getCodiceRifugio(), codicePersona);

        if (nVisit != 0) {

            binding.numeroVisite.setText("Già visitato " + nVisit + " " + (nVisit == 1 ? "volta" : "volte"));
        } else {
            binding.numeroVisite.setText("non ancora visitato");
        }

    }

    private Map.Entry<Double, Rifugio> getSmallDistanceHut() {
        Log.i("TYPE", synchronizedDistanzaRifugio.getClass() + "");
        Double key = ((SortedMap<Double, Rifugio>) synchronizedDistanzaRifugio).firstKey();
        Rifugio hut = synchronizedDistanzaRifugio.get(key);
        return new AbstractMap.SimpleEntry<>(key, hut);
    }

    public static double getDistanceInMeter(LatLng source, Location destination) {
        double distance = 0d;
        if (source != null && destination != null) {
            double lat1 = source.latitude;
            double lon1 = source.longitude;
            double lat2 = destination.getLatitude();
            double lon2 = destination.getLongitude();

            final int R = 6371;
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            distance = (R * c) * 1000;
        }
        return distance;
    }


    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("PERMESSI", "NOT GRANTED");
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        Log.i("LOCATION", location + "");
                        if (location != null) {
                            measureDistance(location);
                        }
                    }
                });
    }

    private String formatDistance(Double distance) {
        if (Math.floor(distance) >= 1000) {
            DecimalFormat dfi = new DecimalFormat("#.00");
            return dfi.format(distance / 1000) + " km";
        } else {
            return Math.floor(distance) + " m";
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("STATUS", "RESUME");
        mHandler.post(checkDistanceThread);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("STATUS", "PAUSE");
        mHandler.removeCallbacks(checkDistanceThread);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("STATUS", "STOP");
    }
}