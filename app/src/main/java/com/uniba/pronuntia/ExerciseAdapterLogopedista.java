package com.uniba.pronuntia;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExerciseAdapterLogopedista extends RecyclerView.Adapter<ExerciseAdapterLogopedista.ExerciseViewHolder> {

    private Context context;
    private ArrayList<Esercizio> exerciseList;
    private ArrayList<Esercizio> list;
    private int durata;
    private RecyclerView recyclerView;
    private static final String TAG = "ExerciseAdapter";

    public ExerciseAdapterLogopedista(Context context, RecyclerView recyclerView, ArrayList<Esercizio> exerciseList, ArrayList<Esercizio> list, int durata) {
        this.context = context;
        this.exerciseList = exerciseList;
        this.list = list;
        this.durata = durata;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_esercizio_logopedista, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.titolo.setText(exerciseList.get(position).getName());
        holder.position.setText(String.valueOf(position + 1));
        holder.tipo.setText(exerciseList.get(position).getTipo());
/*
        for(int i=0;i<list.size();i++) {
            Log.d(TAG, "PRIMA DELLA RIMOZIONE: " + list.get(i).getName() + " " + list.get(i).getGiorno() + "/" + list.get(i).getMese() + "/" + list.get(i).getAnno());
        }
*/
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateAndRemoveItem(position);
                notifyDataSetChanged();
            }
        });

    }


    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    private void animateAndRemoveItem(int position) {

        View view = recyclerView.findViewHolderForAdapterPosition(position).itemView;
        view.animate()
                .translationX(-view.getWidth())
                .alpha(0)
                .setDuration(300)
                .withEndAction(() -> {


                    /*for(int i = 0;i<exerciseList.size();i++){
                        Log.d(TAG, "1) PRIMA DELLA RIMOZIONE " + exerciseList.get(i).getName() + " " + exerciseList.get(i).getGiorno() + "/" + exerciseList.get(i).getMese() + "/" + exerciseList.get(i).getAnno());
                    }*/

                    for(int i = 0;i<list.size();i++){
                        Log.d(TAG, "2) PRIMA DELLA RIMOZIONE " + list.get(i).getName() + " " + list.get(i).getGiorno() + "/" + list.get(i).getMese() + "/" + list.get(i).getAnno());
                    }

                    exerciseList.remove(position);
                    for(int i = 0;i<durata*7;i++){
                        list.remove(position*7);
                    }
/*                    for(int i = 0;i<durata*7;i++){
                        list.remove(position*7);
                    }
*/


                   /* for(int i = 0;i<exerciseList.size();i++){
                        Log.d(TAG, i + " 1) DOPO LA RIMOZIONE " + exerciseList.get(i).getName() + " " + exerciseList.get(i).getGiorno() + "/" + exerciseList.get(i).getMese() + "/" + exerciseList.get(i).getAnno());
                    }*/

                    for(int i = 0;i<list.size();i++){
                        Log.d(TAG, i + " 2) DOPO LA RIMOZIONE " + list.get(i).getName() + " " + list.get(i).getGiorno() + "/" + list.get(i).getMese() + "/" + list.get(i).getAnno());
                    }

                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, exerciseList.size());
                    notifyItemRangeChanged(position, list.size());
                })
                .start();
    }
    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView titolo, position, tipo, data;
        Button delete;
        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inizializza i tuoi componenti di vista qui
            titolo = itemView.findViewById(R.id.titleEx);
            position = itemView.findViewById(R.id.positionText);
            tipo = itemView.findViewById(R.id.tipo);
            delete = itemView.findViewById(R.id.delete);
        }


    }
}

