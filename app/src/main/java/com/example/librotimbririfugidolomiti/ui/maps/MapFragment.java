package com.example.librotimbririfugidolomiti.ui.maps;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.RifugiViewModel;
import com.example.librotimbririfugidolomiti.database.Rifugio;
import com.example.librotimbririfugidolomiti.databinding.FragmentMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;
    private GoogleMap mMap;
    private RifugiViewModel mRifugiViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentMapBinding.inflate(inflater, container, false);
        mRifugiViewModel = new ViewModelProvider(this).get(RifugiViewModel.class);
        View root = binding.getRoot();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("Range")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.i("LTE","CIAOOOOOO");
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }


        mRifugiViewModel.getAllRifugi().observe(getViewLifecycleOwner(), huts -> {
            DecimalFormat df = new DecimalFormat();
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator(' ');
            df.setDecimalFormatSymbols(symbols);

            Map<String, LatLng> rifugiLatLong = new HashMap();

            for (Rifugio hut : huts) {
                Log.i("LTE", hut.getNomeImmagine());
                Log.i("LTE", hut.getLatitudine()+"");
                double latitudine = hut.getLatitudine();
                double longitudine = hut.getLongitudine();
                LatLng tempLatLng = new LatLng(latitudine, longitudine);
                rifugiLatLong.put(hut.getNomeRifugio(), tempLatLng);
                mMap.addMarker(new MarkerOptions().position(tempLatLng).title(hut.getNomeRifugio()));
            }

            LatLngBounds.Builder bc = new LatLngBounds.Builder();

            for (LatLng item : rifugiLatLong.values()) {
                bc.include(item);
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bc.build(), 50));
        });
    }
}