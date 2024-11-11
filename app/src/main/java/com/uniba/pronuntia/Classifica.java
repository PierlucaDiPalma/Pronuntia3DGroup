package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Classifica extends AppCompatActivity {

    private TextView titolo;
    private Button back;
    private ArrayList<Resoconto> resoconti;
    private ArrayList<Giocatore> giocatori;

    private String bambino;
    private String genitore;
    private String logopedista;
    private DBHelper db;
    private int spesa;
    private int punteggio;

    private RecyclerView recyclerView;
    private KidsAdapter customAdapter;

    private static final String TAG = "Classifica";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_classifica);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.classifica), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        db = new DBHelper(this);
        recyclerView = findViewById(R.id.recyclerView);


        bambino = getIntent().getStringExtra("bambino");
        genitore = getIntent().getStringExtra("genitore");
        logopedista = getIntent().getStringExtra("logopedista");
        Utente utente = db.getUtente(logopedista);

        titolo = findViewById(R.id.logopedista);
        titolo.setText("Classifica presso: " + utente.getNome() + " " + utente.getCognome() );

        giocatori = db.getPlayers(logopedista);

        back = findViewById(R.id.indietro);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Classifica.this, HomeBambino.class);
                intent.putExtra("bambino", bambino);
                intent.putExtra("email", genitore);
                intent.putExtra("source", TAG);
                startActivity(intent);
            }
        });

        for(int i=0;i<giocatori.size();i++){

            spesa = db.getSpesa(giocatori.get(i).getBambino(), giocatori.get(i).getGenitore());
            punteggio = 0;
            resoconti = db.getResoconto(giocatori.get(i).getGenitore(), giocatori.get(i).getBambino());

            for(int j = 0; j<resoconti.size();j++){

                punteggio += resoconti.get(j).getPunteggio();

            }

            punteggio -= spesa;
            giocatori.get(i).setPunteggio(punteggio);

            Log.d(TAG, "Persone: " + giocatori.get(i).getBambino() + " " + giocatori.get(i).getGenitore()
                    + " " + giocatori.get(i).getLogopedista() + " " + giocatori.get(i).getPunteggio());
        }

        Collections.sort(giocatori, new Comparator<Giocatore>() {
            @Override
            public int compare(Giocatore player1, Giocatore player2) {
                return Integer.compare(player2.getPunteggio(), player1.getPunteggio());
            }
        });


        customAdapter = new KidsAdapter(Classifica.this, giocatori);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Classifica.this));
    }


}