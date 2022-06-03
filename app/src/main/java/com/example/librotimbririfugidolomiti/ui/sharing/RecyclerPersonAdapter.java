package com.example.librotimbririfugidolomiti.ui.sharing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.librotimbririfugidolomiti.R;
import com.example.librotimbririfugidolomiti.database.Persona;

import java.util.ArrayList;
import java.util.List;

public class RecyclerPersonAdapter extends RecyclerView.Adapter<RecyclerPersonAdapter.PersonViewHolder> {

    private final List<Persona> obtainedPeople;
    private final OnPersonListener onPersonListener;

    public RecyclerPersonAdapter(List<Persona> obtainedPeople, OnPersonListener onPersonListener) {
        this.obtainedPeople = obtainedPeople;
        this.onPersonListener = onPersonListener;
    }

    public void add(List<Persona> people) {
        for (Persona person : people) {
            add(person);
        }
    }

    public void add(Persona person) {
        ((ArrayList<Persona>) obtainedPeople).remove(person);
        ((ArrayList<Persona>) obtainedPeople).add(person);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.personview_item, parent, false);

        return new PersonViewHolder(view, onPersonListener);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.nome.setText(obtainedPeople.get(position).getNomeCognome());
    }

    @Override
    public int getItemCount() {
        return obtainedPeople.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nome;
        OnPersonListener onPersonListener;

        private PersonViewHolder(View itemView, OnPersonListener onPersonListener) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomePersona);
            itemView.setOnClickListener(this);
            this.onPersonListener = onPersonListener;
        }

        @Override
        public void onClick(View view) {
            onPersonListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnPersonListener {
        void onNoteClick(int position);
    }
}