package com.uniba.pronuntia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class Impostazioni extends AppCompatActivity {

    private Spinner spinner;
    private List<Bambino> bambini;
    private DBHelper db;
    private Button ambientazionebtn;
    private Button personaggiBtn;
    public void onCreate(Bundle InstanceSavedInstance) {
        super.onCreate(InstanceSavedInstance);


        setContentView(R.layout.impostazioni);
SharedPreferences sharedPreferences=getSharedPreferences("UserPrefs",MODE_PRIVATE);
       String emailGenitore=sharedPreferences.getString("userEmail",null);
        db=new DBHelper(this);
        bambini=db.getBambiniByEmail(emailGenitore);
        List<String> bamb=new ArrayList<>();
       for(Bambino bambino:bambini){
           bamb.add(bambino.getNome());

       }

spinner=findViewById(R.id.spinnerbambini);
       ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,bamb);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ambientazionebtn=findViewById(R.id.selectAmbientazione);
        personaggiBtn=findViewById(R.id.selectPersonaggio);


ambientazionebtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String nomeSelezionato = spinner.getSelectedItem().toString();
        Intent intent=new Intent(Impostazioni.this,Seleziona_ambientazione.class);

        intent.putExtra("nomeBambino",nomeSelezionato);
        intent.putExtra("emailGenitore",emailGenitore);
        startActivity(intent);
    }
});

        personaggiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeSelezionato = spinner.getSelectedItem().toString();
                Intent intent=new Intent(Impostazioni.this,Seleziona_personaggi.class);
                intent.putExtra("nomeBambino",nomeSelezionato);
                intent.putExtra("emailGenitore",emailGenitore);
                startActivity(intent);
            }
        });


    }

}
