package com.uniba.pronuntia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppuntamentiFissatiAdapter extends RecyclerView.Adapter<AppuntamentiFissatiAdapter.ViewHolder> {

    List<itemAppuntamento> appuntamentiFissati;
    Context context;
    DBHelper db;

    public AppuntamentiFissatiAdapter(Context context, List<itemAppuntamento> appuntamentiFissati){
        this.appuntamentiFissati=appuntamentiFissati;
        this.context=context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      Context context= parent.getContext();
      View view= LayoutInflater.from(context).inflate(R.layout.item_appuntamenti_fissati,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
db=new DBHelper(this.context);
itemAppuntamento itemAppuntamento=this.appuntamentiFissati.get(position);
        holder.nomeLogopedista.setText("Nome logopedista:"+itemAppuntamento.getNomeGenitore());
        holder.data.setText("Data:"+" "+itemAppuntamento.getData());
        holder.ora.setText("Ora:"+" "+itemAppuntamento.getOra());
holder.LuogoIncontro.setText("Luogo Incotro"+" "+itemAppuntamento.getLuogoincotro());
holder.Indirizzo.setText("Indirizzo"+" "+itemAppuntamento.getIndirizzo());

    }

    @Override
    public int getItemCount() {
        return this.appuntamentiFissati.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nomeLogopedista;
        TextView data;
        TextView ora;
TextView LuogoIncontro;
TextView Indirizzo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

LuogoIncontro=itemView.findViewById(R.id.LuogoIncotro);
Indirizzo=itemView.findViewById(R.id.Indirizzo);
         nomeLogopedista=itemView.findViewById(R.id.nomeLogopedista);
         data=itemView.findViewById(R.id.data);
         ora=itemView.findViewById(R.id.Ora);


        }
    }

}
