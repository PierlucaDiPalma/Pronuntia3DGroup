package com.uniba.pronuntia;


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

    public LevelAdapter(Context context, ArrayList<Esercizio> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
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

        int[] positions = { -500, -300, -100, 0, 100, 300, 500};
        Random random = new Random();

        int lastPosition = -1;  // Inizialmente non esiste un valore precedente
        int newPosition;

        do {
            newPosition = positions[random.nextInt(positions.length)];
        } while (newPosition == lastPosition);




        params.rightMargin = newPosition;
        lastPosition = newPosition;

        holder.levelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);

            }
        });


        Log.d(TAG, "onBindViewHolder: " + params.rightMargin);
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

