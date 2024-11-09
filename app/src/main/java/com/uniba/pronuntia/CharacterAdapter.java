package com.uniba.pronuntia;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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


        nomiPersonaggi = db.getPersonaggi(bambino, genitore);

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
                        Toast.makeText(context, "Acquisto avvenuto con successo", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(context, "Non puoi acquistare questo personaggio", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.usa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCharacterSelected(personaggi.get(position));

                Log.d("SceltaPersonaggi", "PERSONAGGIO ARRAY: " + personaggi.get(position).getNome());
                Intent intent = new Intent(context, HomeBambino.class);
                intent.putExtra("nomePersonaggio", personaggi.get(position).getNome());

                intent.putExtra("bambino", bambino);
                intent.putExtra("email", genitore);
                intent.putExtra("source", "SceltaPersonaggi");

                String path = saveImageToInternalStorage(personaggi.get(position).getImmagine());
                intent.putExtra("pathPersonaggio", path);

                ((Activity)context).startActivity(intent);
            }
        });


    }

    public String saveImageToInternalStorage(Bitmap bitmap) {
        ContextWrapper cw = new ContextWrapper(this.context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, "image.jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
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
