package com.uniba.pronuntia;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private Context context;
    private ArrayList<Esercizio> exerciseList;
    private static final String TAG = "ExerciseAdapter";
    public ExerciseAdapter(Context context, ArrayList<Esercizio> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_esercizio, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {


            holder.titolo.setText(exerciseList.get(position).getName());
            holder.position.setText(String.valueOf(position + 1));

    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView titolo;
        TextView position;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inizializza i tuoi componenti di vista qui
            titolo = itemView.findViewById(R.id.titleEx);
            position = itemView.findViewById(R.id.positionText);
        }



    }
}

