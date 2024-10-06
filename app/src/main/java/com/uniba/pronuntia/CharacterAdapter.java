package com.uniba.pronuntia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>{

    private Context context;
    private ArrayList<Personaggio> personaggi;
    private CharacterInterface listener;
    private int punteggio;
    private DBHelper db;
    private String bambino;
    private String genitore;
    private ArrayList<String> nomiPersonaggi;

    public CharacterAdapter(Context context, ArrayList<Personaggio> personaggi, int punteggio, String bambino, String genitore, CharacterInterface listener) {
        this.context = context;
        this.personaggi = personaggi;
        this.listener = listener;
        this.punteggio = punteggio;
        this.db = new DBHelper(this.context);
        this.bambino = bambino;
        this.genitore = genitore;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.character_card, parent, false);

        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.nome.setText(personaggi.get(position).getNome());
        holder.valore.setText(String.valueOf(personaggi.get(position).getValore()));
        holder.pic.setImageBitmap(personaggi.get(position).getImmagine());

        nomiPersonaggi = db.getPersonaggio(bambino, genitore);

        for(int i=0;i<nomiPersonaggi.size();i++){
            if(nomiPersonaggi.get(i).equals(personaggi.get(position).getNome())){
                holder.acquista.setVisibility(View.GONE);
                holder.usa.setVisibility(View.VISIBLE);
            }
        }


        holder.acquista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(punteggio>=personaggi.get(position).getValore()){
                    punteggio -= personaggi.get(position).getValore();

                    holder.acquista.setVisibility(View.GONE);
                    holder.usa.setVisibility(View.VISIBLE);
                    listener.onItemClick(punteggio);

                    if(db.addAcquisto(personaggi.get(position), bambino, genitore)){
                        Toast.makeText(context, "Acquisto avvenuto con successo", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Non puoi acquistare questo personaggio", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    @Override
    public int getItemCount() {
        return personaggi.size();
    }


    public class CharacterViewHolder extends RecyclerView.ViewHolder{
        TextView nome, valore;
        ImageView pic;
        Button acquista, usa;

        public CharacterViewHolder(@NonNull View view) {
            super(view);
            nome = view.findViewById(R.id.nome);
            valore = view.findViewById(R.id.valore);
            pic = view.findViewById(R.id.imageView);
            acquista = view.findViewById(R.id.acquista);
            usa = view.findViewById(R.id.usa);
        }
    }


}
