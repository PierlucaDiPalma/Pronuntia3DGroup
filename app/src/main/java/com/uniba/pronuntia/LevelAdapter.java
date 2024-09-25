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

        int i = position;

        int[] positionX = { -500, -300, -100, 0, 100, 300, 500};

        if(((getItemCount()-position)/6%2)==0) {
            switch ((getItemCount()-position) % 6) {
                case 0:
                    params.rightMargin = positionX[3];
                    break;
                case 1:
                case 5:
                    params.rightMargin = positionX[2];
                    break;
                case 2:
                case 4:
                    params.rightMargin = positionX[1];
                    break;
                case 3:
                    params.rightMargin = positionX[0];
                    break;
                default: break;
            }
        }else{
            switch (position % 6) {
                case 0:
                    params.rightMargin = positionX[3];
                    break;
                case 1:
                case 5:
                    params.rightMargin = positionX[4];
                    break;
                case 2:
                case 4:
                    params.rightMargin = positionX[5];
                    break;
                case 3:
                    params.rightMargin = positionX[6];
                    break;
                default: break;
            }
        }

        if(getItemCount() - position<livello){

            holder.levelButton.setEnabled(true);
            holder.levelButton.setClickable(false);

        }else if (livello == getItemCount() - position) {

            holder.levelButton.setEnabled(true);
            holder.levelButton.setClickable(true);

            holder.levelButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    livello++;

                    Intent intent = new Intent(context, Game.class);
                    intent.putExtra("Posizione", i);
                    intent.putExtra("Punteggio", punteggio);
                    intent.putExtra("Livello", livello);
                    intent.putExtra("Esercizio", exerciseList.get(i));
                    intent.putExtra("Email", exerciseList.get(i).getEmail());

                    Log.d(TAG, "onClick: " + exerciseList.get(i).getName() + " " + exerciseList.get(i).getTipo() );

                    ((Activity) context).startActivityForResult(intent, 2);

                }
            });

        } else {
            holder.levelButton.setEnabled(false);
            holder.levelButton.setClickable(false);
        }

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

