package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreazioneBambino extends AppCompatActivity {
    private DBHelper db;
    private Button AnnullaBTN;
    private Button ContiunaBTN;
    private String email;

   @Override
    public void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);
       setContentView(R.layout.creazione_profilo_bambino);

       AnnullaBTN=findViewById(R.id.AnnullaAddProfilo);

       AnnullaBTN.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent=new Intent(CreazioneBambino.this,HomeUtente.class);
               intent.putExtra("email", email); // Passa l'email qui
               startActivity(intent);

           }
       });



        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        db=new DBHelper(CreazioneBambino.this);

        ContiunaBTN=findViewById(R.id.ContinuaAddProfilo);


        ContiunaBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText=findViewById(R.id.FieldNomeBambino);
                String nomeBambino=editText.getText().toString();
                if (nomeBambino.isEmpty()) {
                    Toast.makeText(CreazioneBambino.this, "Il nome del bambino non può essere vuoto", Toast.LENGTH_SHORT).show();
                    return;
                }
        db.addBambino(nomeBambino,email);
        Intent intent=new Intent(CreazioneBambino.this,HomeUtente.class);
                intent.putExtra("email", email);
        startActivity(intent);


            }
        });

   }
}
