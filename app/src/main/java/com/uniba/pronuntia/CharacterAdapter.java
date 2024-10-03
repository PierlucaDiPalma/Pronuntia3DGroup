package com.uniba.pronuntia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>{

    private Context context;
    private ArrayList<Personaggio> personaggi;

    public CharacterAdapter(Context context, ArrayList<Personaggio> personaggi) {
        this.context = context;
        this.personaggi = personaggi;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.character_card, parent, false);

        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {

        holder.nome.setText(personaggi.get(position).getNome());
        holder.valore.setText(String.valueOf(personaggi.get(position).getValore()));
        holder.pic.setImageBitmap(personaggi.get(position).getImmagine());

    }

    @Override
    public int getItemCount() {
        return personaggi.size();
    }


    public class CharacterViewHolder extends RecyclerView.ViewHolder{
        TextView nome, valore;
        ImageView pic;
        Button acquista;

        public CharacterViewHolder(@NonNull View view) {
            super(view);
            nome = view.findViewById(R.id.nome);
            valore = view.findViewById(R.id.valore);
            pic = view.findViewById(R.id.imageView);
            acquista = view.findViewById(R.id.acquista);
        }
    }


}
