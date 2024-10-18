package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;



import java.util.ArrayList;

public class SceltaLogopedista extends AppCompatActivity {

    private DBHelper db;
private LinearLayout logopedisti;
private ArrayList<Utente> listalogopedisti;


    public void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);

setContentView(R.layout.scelta_logopedisti);
       db=new DBHelper(this);

logopedisti=findViewById(R.id.logopedisti_layout);
listalogopedisti=db.getLogopedisti();
Intent intent=getIntent();
String nomeActivity=intent.getStringExtra("nomeActivity");
 String emailLogopedista=intent.getStringExtra("logopedista_email");
        Toolbar toolbar=findViewById(R.id.my_toolbarSceltalogopedista);

    setSupportActionBar(toolbar);

if(listalogopedisti!=null && !listalogopedisti.isEmpty()){
    for(Utente logopedista:listalogopedisti){
        Button pulsanteLogopedista=new Button(this);
pulsanteLogopedista.setText(logopedista.getNome()+" "+logopedista.getCognome());

pulsanteLogopedista.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if(nomeActivity.equals("richiediTerapia")) {
            Intent intent = new Intent(SceltaLogopedista.this, RichiestaTerapia.class);
            intent.putExtra("EMAIL_LOGOPEDISTA", logopedista.getEmail());
            startActivity(intent);
        } else if (nomeActivity.equals("richiestaAppuntamento")) {
            Intent intent=new Intent(SceltaLogopedista.this,RichiestaAppuntamento.class);
            intent.putExtra("EMAIL_LOGOPEDISTA", logopedista.getEmail());
            startActivity(intent);
        }
    }
});

logopedisti.addView(pulsanteLogopedista);




    }
}

ImageView pulsanteback=findViewById(R.id.back_buttonSceltaLogo);

pulsanteback.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        onBackPressed();
    }
});

if(getSupportActionBar()!=null){
    getSupportActionBar().setDisplayShowTitleEnabled(false);
}



    }


}
