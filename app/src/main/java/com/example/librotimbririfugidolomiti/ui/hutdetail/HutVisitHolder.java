package com.example.librotimbririfugidolomiti.ui.hutdetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.VisitaRifugio;


public class HutVisitHolder extends RecyclerView.ViewHolder {
    TextView numeroVisita;
    TextView dataVisita;
    TextView info;
    ImageView showMore;
    RatingBar rating;
    boolean open;

    //TODO: freccia giù bottone
    //TODO: aggiungere i bordi
    //TODO: aggiungere i valori ai campi di testo

    private HutVisitHolder(View itemView, Context context) {
        super(itemView);
        Log.i("POSITION",getAdapterPosition()+"");
        numeroVisita = (TextView) itemView.findViewById(R.id.numeroVisita);
        dataVisita = (TextView) itemView.findViewById(R.id.dataVisita);
        info = (TextView) itemView.findViewById(R.id.info);
        showMore = (ImageView) itemView.findViewById(R.id.showMore);
        rating = (RatingBar) itemView.findViewById(R.id.ratingBar);
        open=false;

        showMore.setOnClickListener(e->
                openAndcloseInfoPanel(context));
    }

    private void openAndcloseInfoPanel(Context context) {
        if(!open) {

            Bitmap icon= BitmapFactory.decodeResource(context.getResources(),R.drawable.reduce_button);
            showMore.setImageBitmap(icon);
            ((LinearLayout) itemView.findViewById(R.id.infos)).setVisibility(View.VISIBLE);
            open=true;
        }else{
            Bitmap icon= BitmapFactory.decodeResource(context.getResources(),R.drawable.expand_button);
            showMore.setImageBitmap(icon);
            ((LinearLayout) itemView.findViewById(R.id.infos)).setVisibility(View.GONE);
            open=false;
        }
    }




    static HutVisitHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visithut_item, parent, false);
        return new HutVisitHolder(view, parent.getContext());
    }

    public void setUpHolder(VisitaRifugio visitaRifugio,int position) {
        //TODO:@String with annotation
        //TODO:Ratink non cancellabile
        //TODO:Stile delle scritte
        numeroVisita.setText((position+1)+"° visita");
        dataVisita.setText(visitaRifugio.getDataVisita());
        info.setText(visitaRifugio.getInfo());
        rating.setRating(visitaRifugio.getRating());
    }
}