package com.uniba.pronuntia;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Utente> utente;

    CustomAdapter(Context context, ArrayList<Utente> utente){
        this.context = context;
        this.utente = utente;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.email.setText(String.valueOf(utente.get(position).getEmail()));
        holder.nome.setText(String.valueOf(utente.get(position).getNome()));
        holder.cognome.setText(String.valueOf(utente.get(position).getCognome()));
        holder.telefono.setText(String.valueOf(utente.get(position).getTelefono()));

        holder.itemView.findViewById(R.id.cardButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CreazioneEsercizi.class);
                intent.putExtra("email", utente.get(holder.getAdapterPosition()).getEmail());
                intent.putExtra("nome", utente.get(holder.getAdapterPosition()).getNome());
                intent.putExtra("cognome", utente.get(holder.getAdapterPosition()).getCognome());
                intent.putExtra("telefono", utente.get(holder.getAdapterPosition()).getTelefono());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return utente.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView email, nome, cognome, telefono;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.email);
            nome = itemView.findViewById(R.id.nome);
            cognome = itemView.findViewById(R.id.cognome);
            telefono = itemView.findViewById(R.id.telefono);

        }
    }
}
