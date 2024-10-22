package com.uniba.pronuntia;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHolder> {

    private Context context;
    private ArrayList<Esercizio> exerciseList;
    private static final String TAG = "LevelAdapter";
    private int punteggio;
    private int livello;
    private String personaggio;
    private String logopedista;

    public LevelAdapter(Context context, ArrayList<Esercizio> exerciseList, int livello, String personaggio, String logopedista) {
        this.context = context;
        this.exerciseList = exerciseList;
        this.livello = livello;
        this.personaggio = personaggio;
        this.logopedista = logopedista;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_button, parent, false);
        return new LevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, int position) {
        holder.levelText.setText(String.valueOf(getItemCount()-position));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.levelButton.getLayoutParams();

        int i = position;

        int[] positionX = { -400, -200, -0, 100, 200, 400, 600};

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
            holder.levelButton.setBackgroundColor(Color.TRANSPARENT);

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.crown);
            bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);

            holder.levelButton.setImageBitmap(bitmap);

        }else if (livello == getItemCount() - position) {
            File file = new File(personaggio);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

            Bitmap resizedBitmap = Bitmap.createScaledBitmap(myBitmap, 200, 200, true);

            holder.levelButton.setBackgroundColor(Color.TRANSPARENT);
            holder.levelButton.setImageBitmap(resizedBitmap);
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
                    intent.putExtra("Bambino", exerciseList.get(i).getBambino());
                    intent.putExtra("Email", exerciseList.get(i).getEmail());
                    intent.putExtra("Logopedista", logopedista);
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
        ImageButton levelButton;
        TextView levelText;

        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
            levelButton = itemView.findViewById(R.id.levelButton);
            levelText = itemView.findViewById(R.id.levelButtonText);
        }

    }
}

