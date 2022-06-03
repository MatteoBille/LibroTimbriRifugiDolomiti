package com.example.librotimbririfugidolomiti.ui.hutdetail;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.librotimbririfugidolomiti.database.VisitaRifugio;


public class HutVisitRecyclerCustomAdapter extends ListAdapter<VisitaRifugio, HutVisitHolder> {


    public HutVisitRecyclerCustomAdapter(@NonNull DiffUtil.ItemCallback<VisitaRifugio> diffCallback) {
        super(diffCallback);
    }

    @Override
    public HutVisitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("CREATE VIEW",viewType+"");
        return HutVisitHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(HutVisitHolder holder, int position) {
        holder.setUpHolder(getCurrentList().get(position),position);
        VisitaRifugio current = getItem(position);
    }

    static class VisitDiff extends DiffUtil.ItemCallback<VisitaRifugio> {

        @Override
        public boolean areItemsTheSame(@NonNull VisitaRifugio oldItem, @NonNull VisitaRifugio newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull VisitaRifugio oldItem, @NonNull VisitaRifugio newItem) {
            return oldItem.equals(newItem);
        }
    }
}