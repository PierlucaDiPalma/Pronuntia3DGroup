package com.uniba.pronuntia;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class CorrectionAdapter extends RecyclerView.Adapter<CorrectionAdapter.CorrectionViewHolder> {

    private Context context;
    private ArrayList<Resoconto> resoconti;
    private boolean isPlaying = false;

    private DBHelper db;

    private static final String CORRETTO = "CORRETTO";
    private static final String SBAGLIATO = "SBAGLIATO";

    public CorrectionAdapter(Context context, ArrayList<Resoconto> resoconti) {
        this.context = context;
        this.resoconti = resoconti;
        db = new DBHelper(this.context);

    }

    @NonNull
    @Override
    public CorrectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_correzione, parent, false);
        return new CorrectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CorrectionViewHolder holder, int position) {

        int day = resoconti.get(position).getEsercizio().getGiorno();
        int month = resoconti.get(position).getEsercizio().getMese();
        int year = resoconti.get(position).getEsercizio().getAnno();

        holder.posizione.setText(String.valueOf(position+1));
        holder.titolo.setText(resoconti.get(position).getEsercizio().getName());
        holder.data.setText(day +"-"+ month + "-" + year);
        holder.aiuti.setText("Aiuti usati: " + resoconti.get(position).getAiuti());


        if(resoconti.get(position).getCorretti()!=0){
            holder.esito.setImageResource(R.drawable.ok_icon);
        }else{
            holder.esito.setImageResource(R.drawable.no_ok);
        }

        if(resoconti.get(position).getEsercizio().getTipo().equals("Coppia") || resoconti.get(position).getAudio()==null){
            holder.linearLayout.setVisibility(View.GONE);
        }else{
            holder.linearLayout.setVisibility(View.VISIBLE);

        }

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.play.setImageResource(R.drawable.pause);
                holder.progressBar.setProgress(0);

                MediaPlayer player = new MediaPlayer();
                try {
                    player.setDataSource(resoconti.get(holder.getAdapterPosition()).getAudio());
                    player.prepare();
                    player.start();
                    holder.progressBar.setMax(player.getDuration());


                    Handler handler = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (player.isPlaying()) {
                                holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#DB5C00")));
                                holder.progressBar.setProgress(player.getCurrentPosition());
                                handler.postDelayed(this, 100);
                            }
                        }
                    };
                    handler.post(runnable);


                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            holder.play.setImageResource(R.drawable.play);
                            isPlaying = false;
                            holder.progressBar.setProgress(0);
                        }
                    });


                } catch (IOException e) {
                    Log.e("Playback Error", "Errore durante la riproduzione dell'audio", e);
                }
            }
        });

        holder.corretto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("CorrectionAdapter", resoconti.get(holder.getAdapterPosition()).getEsercizio().getName() + " ");
                ActionConfirm(resoconti.get(holder.getAdapterPosition()), 0);

            }
        });

        holder.sbagliato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionConfirm(resoconti.get(holder.getAdapterPosition()), 1);

            }
        });

    }

    public void ActionConfirm(Resoconto resoconto, int code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Conferma");
        builder.setMessage("Vuoi cambiare l'esito dell'esercizio?");

        // Pulsante di conferma
        builder.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(context, "Confermato!", Toast.LENGTH_SHORT).show();
                db.updateResoconto(resoconto, code);
                notifyDataSetChanged();
            }
        });


        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public int getItemCount() {
        return resoconti.size();
    }


    public class CorrectionViewHolder extends RecyclerView.ViewHolder{

        private TextView posizione, titolo, data, aiuti;
        private ImageView play, corretto, sbagliato, esito;
        private LinearLayout linearLayout;
        private ProgressBar progressBar;

        public CorrectionViewHolder(@NonNull View view) {
            super(view);

            posizione = view.findViewById(R.id.position);
            titolo = view.findViewById(R.id.nomeEsercizio);
            data = view.findViewById(R.id.data);
            aiuti = view.findViewById(R.id.aiuti);

            play = view.findViewById(R.id.playButton);
            esito = view.findViewById(R.id.esito);
            corretto = view.findViewById(R.id.corretto);
            sbagliato = view.findViewById(R.id.sbagliato);

            linearLayout = view.findViewById(R.id.linearLayout);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }

}
