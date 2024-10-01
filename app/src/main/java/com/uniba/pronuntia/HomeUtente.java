package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HomeUtente extends AppCompatActivity {


    private DBHelper db;
    private Button genitoreBTN ;
    private LinearLayout linearLayout;
    private Button pulsanteAggiungiBambino;
    private String email;
    private ArrayList<Bambino> bambini;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeutente);

        genitoreBTN = findViewById(R.id.pulsanteGenitore);
        linearLayout = findViewById(R.id.containerBambini);


        pulsanteAggiungiBambino = findViewById(R.id.pulsanteAggiungiBambino);

        pulsanteAggiungiBambino.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent=getIntent();
              email=intent.getStringExtra("email");


               Intent intentBambino=new Intent(HomeUtente.this, CreazioneBambino.class);
               intentBambino.putExtra("email", email);

               startActivity(intentBambino);

           }
        });


        db=new DBHelper(this);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        Log.d("Login", "Email passata: " + email); // Log per debugging
        if (email != null) {
            bambini = db.getBambiniByEmail(email);

        for (Bambino bambino : bambini) {
            createButtonForBambino(bambino);
        }
        } else {
            // Gestisci il caso in cui l'email è null (mostrare un messaggio di errore, ecc.)
            Toast.makeText(this, "Email non valida!", Toast.LENGTH_SHORT).show();
        }

        genitoreBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HomeUtente.this,HomeGenitore.class);
                startActivity(intent);


            }
        });

    }

    private void createButtonForBambino(Bambino bambino) {
        Button button = new Button(this);
        button.setText(bambino.getNome());
        button.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        button.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_blue_light));
        button.setTextSize(16);
        button.setPadding(16, 16, 16, 16);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeUtente.this, HomeBambino.class); // Assicurati che questa activity esista
                intent.putExtra("idBambino", bambino.getId()); // Passa l'ID del bambino
                intent.putExtra("bambino", bambino.getNome()); // Passa il nome del bambino
                intent.putExtra("email", getIntent().getStringExtra("email"));

                startActivity(intent);
            }
        });

        // Aggiungi il pulsante al LinearLayout
        linearLayout.addView(button);

    }

}



