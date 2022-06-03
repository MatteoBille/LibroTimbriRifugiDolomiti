package com.example.librotimbririfugidolomiti.ui.maps;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.HutsWithNumberOfVisit;
import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
import com.example.librotimbririfugidolomiti.databinding.FragmentMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;
    private GoogleMap mMap;
    private RifugiViewModel mRifugiViewModel;
    private SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentMapBinding.inflate(inflater, container, false);
        mRifugiViewModel = new ViewModelProvider(this).get(RifugiViewModel.class);
        View root = binding.getRoot();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("Range")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.style_json));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String codicePersona = sharedPreferences.getString("codicePersona", null);


        mRifugiViewModel.getAllTheHutWithNumberOfVisitByUserId(codicePersona).observe(getViewLifecycleOwner(), huts -> {
            DecimalFormat df = new DecimalFormat();
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator(' ');
            df.setDecimalFormatSymbols(symbols);

            Map<String, LatLng> rifugiLatLong = new HashMap();

            for (HutsWithNumberOfVisit hut : huts) {
                double latitudine = hut.getRifugio().getLatitudine();
                double longitudine = hut.getRifugio().getLongitudine();
                LatLng tempLatLng = new LatLng(latitudine, longitudine);
                rifugiLatLong.put(hut.getRifugio().getNomeRifugio(), tempLatLng);
                BitmapDescriptor bd;

                if (hut.getCount() != null && hut.getCount() > 0) {
                    bd = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                } else {
                    bd = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                }
                mMap.addMarker(new MarkerOptions().position(tempLatLng).icon(bd).title(hut.getRifugio().getNomeRifugio()));
            }

            LatLngBounds.Builder bc = new LatLngBounds.Builder();

            for (LatLng item : rifugiLatLong.values()) {
                bc.include(item);
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bc.build(), 50));
        });
    }
}