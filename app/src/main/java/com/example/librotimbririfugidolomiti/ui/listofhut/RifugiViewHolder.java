package com.example.librotimbririfugidolomiti.ui.listofhut;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.librotimbririfugidolomiti.R;

public class RifugiViewHolder extends RecyclerView.ViewHolder {
    private final TextView wordItemView;

    private RifugiViewHolder(View itemView) {
        super(itemView);
        wordItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(String text) {
        wordItemView.setText(text);
    }

    static RifugiViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new RifugiViewHolder(view);
    }
}
