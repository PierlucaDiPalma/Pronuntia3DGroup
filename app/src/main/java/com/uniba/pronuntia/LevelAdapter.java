package com.uniba.pronuntia;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHolder> {

    private Context context;
    private ArrayList<Esercizio> exerciseList;
    private static final String TAG = "LevelAdapter";
    private int punteggio;
    private int livello;

    public LevelAdapter(Context context, ArrayList<Esercizio> exerciseList, int livello) {
        this.context = context;
        this.exerciseList = exerciseList;
        this.livello = livello;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_button, parent, false);
        return new LevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, int position) {
        holder.levelButton.setText(String.valueOf(getItemCount()-position));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.levelButton.getLayoutParams();

        Random random = new Random();
        int i = position;

        int[] positionX = { -500, -300, -100, 0, 100, 300, 500};

        params.rightMargin = positionX[random.nextInt(positionX.length)];



        if (livello != getItemCount() - position) {
            holder.levelButton.setEnabled(false);

        } else {
            holder.levelButton.setEnabled(true);
        }

        holder.levelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                livello++;



                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("Posizione", i);
                intent.putExtra("Punteggio", punteggio);
                intent.putExtra("Livello", livello);
                intent.putExtra("Email", exerciseList.get(i).getEmail());

                ((Activity) context).startActivityForResult(intent, 2);

            }
        });


        Log.d(TAG, "onBindViewHolder: " + params.rightMargin);

    }


    public void setLivello(int nuovoLivello) {
        this.livello = nuovoLivello;
    }
    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class LevelViewHolder extends RecyclerView.ViewHolder {
        Button levelButton;
        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
            levelButton = itemView.findViewById(R.id.levelButton);
        }

    }
}

