package com.example.librotimbririfugidolomiti.ui.hutdetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.VisitaRifugio;


public class HutVisitHolder extends RecyclerView.ViewHolder {
    private final TextView numeroVisita;
    private final TextView dataVisita;
    private final TextView info;
    private final ImageView showMore;
    private final RatingBar rating;
    private boolean open;
    private final Context context;

    //TODO: aggiungere i bordi
    //TODO: aggiungere i valori ai campi di testo

    private HutVisitHolder(View itemView, Context context) {
        super(itemView);
        Log.i("POSITION", getAdapterPosition() + "");
        numeroVisita = itemView.findViewById(R.id.numeroVisita);
        dataVisita = itemView.findViewById(R.id.dataVisita);
        info = itemView.findViewById(R.id.info);
        showMore = itemView.findViewById(R.id.showMore);
        rating = itemView.findViewById(R.id.ratingBar);
        this.context = context;
        open = false;

        showMore.setOnClickListener(e ->
                openAndcloseInfoPanel(context));
    }

    private void openAndcloseInfoPanel(Context context) {
        if(!open) {

            Bitmap icon= BitmapFactory.decodeResource(context.getResources(),R.drawable.reduce_button);
            showMore.setImageBitmap(icon);
            itemView.findViewById(R.id.infos).setVisibility(View.VISIBLE);
            open = true;
        }else{
            Bitmap icon= BitmapFactory.decodeResource(context.getResources(),R.drawable.expand_button);
            showMore.setImageBitmap(icon);
            itemView.findViewById(R.id.infos).setVisibility(View.GONE);
            open = false;
        }
    }




    static HutVisitHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visithut_item, parent, false);
        return new HutVisitHolder(view, parent.getContext());
    }

    public void setUpHolder(VisitaRifugio visitaRifugio,int position) {
        String visits = String.format(context.getResources().getString(R.string.visits), position + 1);
        numeroVisita.setText(visits);
        dataVisita.setText(visitaRifugio.getDataVisita());
        info.setText(visitaRifugio.getInfo());
        rating.setRating(visitaRifugio.getRating());
    }
}