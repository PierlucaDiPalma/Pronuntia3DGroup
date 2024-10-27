package com.uniba.pronuntia;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class CorrectionAdapter extends RecyclerView.Adapter<CorrectionAdapter.CorrectionViewHolder> {

    private Context context;
    private ArrayList<Resoconto> resoconti;
    private String audioPath = null;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private Handler handler = new Handler();


    public CorrectionAdapter(Context context, ArrayList<Resoconto> resoconti) {
        this.context = context;
        this.resoconti = resoconti;
    }

    @NonNull
    @Override
    public CorrectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_correzione, parent, false);
        return new CorrectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CorrectionViewHolder holder, int position) {

        holder.posizione.setText(String.valueOf(position+1));
        holder.titolo.setText(resoconti.get(position).getEsercizio().getName());
        holder.data.setText(resoconti.get(position).getEsercizio().getTipo());
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
            audioPath = resoconti.get(position).getAudio();
        }

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.play.setImageResource(R.drawable.pause);
                holder.progressBar.setProgress(0);

                MediaPlayer player = new MediaPlayer();
                try {
                    player.setDataSource(audioPath);
                    player.prepare();
                    player.start();
                    Toast.makeText(context, "Riproduzione in corso", Toast.LENGTH_SHORT).show();

                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            holder.play.setImageResource(R.drawable.play);
                            isPlaying = false;

                        }
                    });


                } catch (IOException e) {
                    Log.e("Playback Error", "Errore durante la riproduzione dell'audio", e);
                }
            }
        });


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
