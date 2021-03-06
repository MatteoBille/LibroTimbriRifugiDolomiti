package com.example.librotimbririfugidolomiti.ui.hutdetail;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.librotimbririfugidolomiti.database.Entity.VisitaRifugio;

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
        holder.setUpHolder(huts.get(position), position);
    }

    @Override
    public int getItemCount() {
        return huts.size();
    }

}