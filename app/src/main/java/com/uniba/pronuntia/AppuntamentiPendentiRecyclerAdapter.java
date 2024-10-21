package com.uniba.pronuntia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class AppuntamentiPendentiRecyclerAdapter extends  RecyclerView.Adapter<AppuntamentiPendentiRecyclerAdapter.Viewholder> {
private List<itemAppuntamento> itemAppuntamentoList;
private DBHelper db;
private Context context;
    public AppuntamentiPendentiRecyclerAdapter(Context context , List<itemAppuntamento> item) {
this.context=context;
        this.itemAppuntamentoList=item;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_richiesta_appuntamento,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
itemAppuntamento itemAppuntamento=this.itemAppuntamentoList.get(position);
db=new DBHelper(this.context);


holder.emailGenitore.setText("Nome genitore:"+itemAppuntamento.getEmailGenitore());
        holder.data.setText("Data:"+" "+itemAppuntamento.getData());
        holder.ora.setText("Ora:"+" "+itemAppuntamento.getOra());


        holder.rifuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            db.SetUnBooked(itemAppuntamento.getData().trim(),itemAppuntamento.getOra().trim());
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.itemAppuntamentoList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

TextView ora;
        TextView emailGenitore;
        TextView data;
        ImageButton accetta;
        ImageButton rifuta;

        public Viewholder(@NonNull View itemview){
            super(itemview);


            emailGenitore=itemview.findViewById(R.id.nomeGenitore);
            data=itemview.findViewById(R.id.dataOra);
            ora=itemview.findViewById(R.id.Ora);
            accetta=itemview.findViewById(R.id.AcceptButton);
            rifuta=itemview.findViewById(R.id.RefuseButton);



        }

    }

}