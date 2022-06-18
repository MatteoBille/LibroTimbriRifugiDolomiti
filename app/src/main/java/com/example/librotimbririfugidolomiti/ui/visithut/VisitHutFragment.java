package com.example.librotimbririfugidolomiti.ui.visithut;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.Entity.Rifugio;
import com.example.librotimbririfugidolomiti.database.Entity.VisitaRifugio;
import com.example.librotimbririfugidolomiti.database.HutsViewModel;
import com.example.librotimbririfugidolomiti.databinding.FragmentVisitHutBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public class VisitHutFragment extends Fragment {

    private FusedLocationProviderClient fusedLocationClient;
    FragmentVisitHutBinding binding;
    private HutsViewModel mHutsViewModel;
    HashMap<LatLng, Integer> rifugesLocation;
    SortedMap<Double, Integer> synchronizedDistanzaRifugio = Collections.synchronizedSortedMap(new TreeMap<>());
    boolean viewOpen;
    SharedPreferences sharedPreferences;

    private final Handler mHandler = new Handler();
    private final Runnable checkDistanceThread = new Runnable() {
        @Override
        public void run() {
            getLocation();
            mHandler.postDelayed(this, 2000);
        }
    };

    public VisitHutFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        binding = FragmentVisitHutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mHutsViewModel = new ViewModelProvider(this).get(HutsViewModel.class);

        binding.button.setEnabled(false);
        binding.button.setOnClickListener(v -> openVisitPopup());

        rifugesLocation = new HashMap<>();

        mHutsViewModel.getAllRifugi().observe(getViewLifecycleOwner(), Huts -> {
            for (Rifugio entityHut : Huts) {
                LatLng tempCord = new LatLng(entityHut.getLatitudine(), entityHut.getLongitudine());
                rifugesLocation.put(tempCord, entityHut.getCodiceRifugio());
            }
            viewOpen = true;
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        });
        return root;
    }


    private void openVisitPopup() {
        Double key = synchronizedDistanzaRifugio.firstKey();
        mHutsViewModel.getHutById(synchronizedDistanzaRifugio.get(key)).observe(getViewLifecycleOwner(), hut -> {
            View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_insert_visit_popup, null);
            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.MATCH_PARENT;

            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

            ((TextView) popupView.findViewById(R.id.hutName)).setText(hut.getNomeRifugio());
            popupView.findViewById(R.id.visitButton).setOnClickListener(e -> {
                saveVisitInDb(hut, popupView, popupWindow);
            });
        });
    }


    private void saveVisitInDb(Rifugio entityHut, View popupView, PopupWindow popupWindow) {
        int idRifugio = entityHut.getCodiceRifugio();
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dataVisita = sdf.format(currentTime);


        String info = ((EditText) popupView.findViewById(R.id.visitDescription)).getText().toString();
        Integer star = Math.round(((RatingBar) popupView.findViewById(R.id.ratingBar)).getRating());
        String codicePersona = sharedPreferences.getString("codicePersona", null);

        mHutsViewModel.visitHut(codicePersona, idRifugio, dataVisita, info, star);
        popupWindow.dismiss();
    }

    private void measureDistance(Location location) {
        binding.distanza.setText(location.getLatitude() + "-" + location.getLongitude());
        for (Map.Entry<LatLng, Integer> entry : rifugesLocation.entrySet()) {
            Double distance = getDistanceInMeter(entry.getKey(), location);
            synchronizedDistanzaRifugio.put(distance, entry.getValue());
        }
        Integer nearestHutId = synchronizedDistanzaRifugio.get(synchronizedDistanzaRifugio.firstKey());
        mHutsViewModel.getHutById(nearestHutId).observe(getViewLifecycleOwner(), nearestHut -> {
            String formattedDistance = formatDistance(synchronizedDistanzaRifugio.firstKey());
            binding.distanza.setText(formattedDistance);
            binding.nomeRifugio.setText(nearestHut.getNomeRifugio());

            String codicePersona = sharedPreferences.getString("codicePersona", null);

            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dataVisita = sdf.format(currentTime);
            VisitaRifugio visitRifugio = mHutsViewModel.getVisitsByHutPersonAndDate(nearestHut.getCodiceRifugio(), codicePersona, dataVisita);
            if (visitRifugio != null) {
                binding.button.setEnabled(false);
            } else {
                binding.button.setEnabled(true);


                if (synchronizedDistanzaRifugio.firstKey() > 100) {
                    binding.button.setEnabled(false);
                    binding.distanza.setTextColor(Color.RED);
                } else {
                    binding.button.setEnabled(true);
                    binding.distanza.setTextColor(Color.GREEN);
                }
            }
            int nVisit = mHutsViewModel.getNumberOfVisitByHut(nearestHut.getCodiceRifugio(), codicePersona);

            String numberOfVisitsStringDescriptor = getResources().getText(R.string.numberOfVisits).toString();
            binding.numeroVisite.setText(MessageFormat.format(numberOfVisitsStringDescriptor, nVisit));
        });
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
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        measureDistance(location);
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
        mHandler.post(checkDistanceThread);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(checkDistanceThread);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}