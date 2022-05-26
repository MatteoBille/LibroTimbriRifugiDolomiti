package com.example.librotimbririfugidolomiti.ui.listofhut;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.librotimbririfugidolomiti.database.Rifugio;

public class RecyclerCustomAdapter extends ListAdapter<Rifugio, RifugiViewHolder> {

    public RecyclerCustomAdapter(@NonNull DiffUtil.ItemCallback<Rifugio> diffCallback) {
        super(diffCallback);
    }

    @Override
    public RifugiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RifugiViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RifugiViewHolder holder, int position) {
        Rifugio current = getItem(position);
        holder.bind(current.getNomeRifugio());
    }

    static class WordDiff extends DiffUtil.ItemCallback<Rifugio> {

        @Override
        public boolean areItemsTheSame(@NonNull Rifugio oldItem, @NonNull Rifugio newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Rifugio oldItem, @NonNull Rifugio newItem) {
            return oldItem.getNomeRifugio().equals(newItem.getNomeRifugio());
        }
    }
}