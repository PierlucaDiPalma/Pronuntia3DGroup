package com.uniba.pronuntia;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private Context context;
    private ArrayList<Resoconto> resultList;

    public ResultAdapter(Context context, ArrayList<Resoconto> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.risultati_esercizio, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {

        holder.posizione.setText(String.valueOf(position+1));
        holder.titolo.setText(resultList.get(position).getEsercizio().getName());
        holder.tipo.setText(resultList.get(position).getEsercizio().getTipo());
        holder.numeroAiuti.setText("Aiuti usati: " + resultList.get(position).getAiuti());

        if(resultList.get(position).getCorretti() == 1) {
            holder.esito.setImageResource(R.drawable.ok_icon );
        }else{
            holder.esito.setImageResource(R.drawable.no_ok);
        }
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder{

        TextView posizione, titolo, tipo, numeroAiuti;
        ImageView esito;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            posizione = itemView.findViewById(R.id.position);
            titolo = itemView.findViewById(R.id.titolo);
            tipo = itemView.findViewById(R.id.tipo);
            numeroAiuti = itemView.findViewById(R.id.numeroAiuti);
            esito = itemView.findViewById(R.id.esito);
        }
    }
}
