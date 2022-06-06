package com.example.librotimbririfugidolomiti.ui.hutdetail;

import android.util.Log;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.librotimbririfugidolomiti.database.VisitaRifugio;

import java.util.List;


public class HutVisitAdapter extends RecyclerView.Adapter<HutVisitHolder> {

    private final List<VisitaRifugio> huts;

    public HutVisitAdapter(List<VisitaRifugio> huts) {
        this.huts = huts;
    }

    @Override
    public HutVisitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return HutVisitHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(HutVisitHolder holder, int position) {
        Log.i("POSITION", huts.get(position).toString());
        holder.setUpHolder(huts.get(position), position);
        VisitaRifugio current = huts.get(position);
    }

    @Override
    public int getItemCount() {
        return huts.size();
    }

}