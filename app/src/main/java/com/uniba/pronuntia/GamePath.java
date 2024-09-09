package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GamePath extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LevelAdapter customAdapter;
    private DBHelper db;
    private ArrayList<Esercizio> esercizi;
    private String email;
    private int punteggio = 0;
    private TextView punteggioText;
    private TextView aiutiText;
    private TextView correttiText;
    private TextView sbagliatiText;
    private int livello = 1;
    private Button risultato;
    private int numeroAiuti = 0;
    private int corretti = 0;
    private int sbagliati = 0;
    private Resoconto resoconto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_path);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.levelView);
        db = new DBHelper(GamePath.this);
        email = getIntent().getStringExtra("Email");
        punteggioText = findViewById(R.id.punteggio);
        aiutiText = findViewById(R.id.aiuti);
        correttiText = findViewById(R.id.corretti);
        sbagliatiText = findViewById(R.id.sbagliati);
        risultato = findViewById(R.id.fine);

        risultato.setVisibility(View.GONE);
        esercizi = db.getCoppia(email);
        esercizi.addAll(db.getSequenza(email));
        esercizi.addAll(db.getDenominazione(email));

        shuffleArrayList(esercizi);

        customAdapter = new LevelAdapter(GamePath.this, esercizi, livello);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(GamePath.this));

        risultato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GamePath.this, RisultatoFinale.class);
                intent.putExtra("Punteggio", punteggio);
                intent.putExtra("Aiuti", numeroAiuti);
                intent.putExtra("Corretti", corretti);
                intent.putExtra("Sbagliati", sbagliati);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) { // Verifica il codice della richiesta
            if (resultCode == RESULT_OK) {
                // Ottieni il risultato passato dall'Activity chiusa
                int nuovoLivello = data.getIntExtra("Livello", livello);
                punteggio += data.getIntExtra("Punteggio", 0);
                punteggioText.setText("Punteggio: " + punteggio);

                numeroAiuti += data.getIntExtra("Aiuti", 0);
                aiutiText.setText("Aiuti usati: " + numeroAiuti);

                corretti += data.getIntExtra("Corretti", 0);
                correttiText.setText("Esercisi corretti: " + corretti);

                sbagliati += data.getIntExtra("Sbagliati", 0);
                sbagliatiText.setText("Esercizi sbagliati: " + sbagliati);

                livello = nuovoLivello;
                customAdapter.setLivello(nuovoLivello);  // Metodo da creare nell'Adapter

                //resoconto = data.getParcelableExtra("Resoconto");

                if(livello == esercizi.size()){
                    risultato.setVisibility(View.VISIBLE);
                }
                // Notifica l'Adapter per aggiornare la RecyclerView
                customAdapter.notifyDataSetChanged();

            }
        }
    }

    public static <T> void shuffleArrayList(ArrayList<T> list) {
        Collections.shuffle(list); // Mescola casualmente l'ArrayList
    }



}