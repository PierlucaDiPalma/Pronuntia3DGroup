package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
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
    private int livello = 1;

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

        esercizi = db.getCoppia(email);
        esercizi.addAll(db.getSequenza(email));
        esercizi.addAll(db.getDenominazione(email));

        shuffleArrayList(esercizi);

        customAdapter = new LevelAdapter(GamePath.this, esercizi, livello);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(GamePath.this));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) { // Verifica il codice della richiesta
            if (resultCode == RESULT_OK) {
                // Ottieni il risultato passato dall'Activity chiusa
                int nuovoLivello = data.getIntExtra("Livello", livello);
                punteggio += data.getIntExtra("Punteggio", 0);
                punteggioText.setText(String.valueOf(punteggio));

                livello = nuovoLivello;
                customAdapter.setLivello(nuovoLivello);  // Metodo da creare nell'Adapter

                // Notifica l'Adapter per aggiornare la RecyclerView
                customAdapter.notifyDataSetChanged();

            }
        }
    }

    public static <T> void shuffleArrayList(ArrayList<T> list) {
        Collections.shuffle(list); // Mescola casualmente l'ArrayList
    }



}