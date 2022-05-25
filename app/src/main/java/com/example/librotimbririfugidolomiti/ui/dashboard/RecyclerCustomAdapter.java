package com.example.librotimbririfugidolomiti.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.Rifugio;

import java.util.List;

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