package com.uniba.pronuntia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppuntamentiLogopedista extends AppCompatActivity {
private ImageButton back;
private  DBHelper db;
private List<itemAppuntamento> infoappuntamenti;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
setContentView(R.layout.appuntamenti_logopedista);

db=new DBHelper(this);
       Intent intent=getIntent();
        String emailLogo=intent.getStringExtra("logopedista_email");
RecyclerView lista=findViewById(R.id.ListaAppuntamentiPendenti);
lista.setLayoutManager(new LinearLayoutManager(this));
 infoappuntamenti=db.getInfoAppuntamentoPendente(emailLogo);
AppuntamentiPendentiRecyclerAdapter adapter=new AppuntamentiPendentiRecyclerAdapter(this,infoappuntamenti);
 lista.setAdapter(adapter);


 back=findViewById(R.id.back_button);

 back.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         onBackPressed();

     }
 });




    }


}
