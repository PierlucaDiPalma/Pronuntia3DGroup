package com.uniba.pronuntia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KidsAdapter extends RecyclerView.Adapter<KidsAdapter.KidsViewHolder> {

    private Context context;
    private String bambino;
    private String genitore;
    private String logopedista;
    private ArrayList<Giocatore> giocatori;

    public KidsAdapter(@NonNull Context context, ArrayList<Giocatore> giocatori) {
        this.context = context;
        this.giocatori = giocatori;
    }


    @NonNull
    @Override
    public KidsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_giocatore, parent, false);

        return new KidsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KidsViewHolder holder, int position) {
        holder.position.setText(String.valueOf(position+1));
        holder.nome.setText(giocatori.get(position).getBambino());
        holder.email.setText(giocatori.get(position).getGenitore());
        holder.punteggio.setText(giocatori.get(position).getPunteggio() + " punti");

    }

    @Override
    public int getItemCount() {
        return giocatori.size();
    }

    public class KidsViewHolder extends  RecyclerView.ViewHolder{

        private TextView position;
        private TextView nome;
        private TextView email;
        private TextView punteggio;

        public KidsViewHolder(@NonNull View view) {
            super(view);

            position = view.findViewById(R.id.position);
            nome = view.findViewById(R.id.nome);
            email = view.findViewById(R.id.email);
            punteggio = view.findViewById(R.id.punteggio);

        }
    }

}
