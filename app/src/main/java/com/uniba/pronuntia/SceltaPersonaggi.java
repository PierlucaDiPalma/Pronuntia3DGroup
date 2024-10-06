package com.uniba.pronuntia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.util.ArrayList;

public class SceltaPersonaggi extends AppCompatActivity implements CharacterInterface{

    private RecyclerView recyclerView;
    private ArrayList<Personaggio> personaggi = new ArrayList<>();
    private CharacterAdapter characterAdapter;
    private TextView punteggioText;
    private int punteggio;
    private Button indietro;
    private String bambino;
    private String genitore;
    private static final String TAG = "SceltaPersonaggi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scelta_personaggi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bitmap profile1 = BitmapFactory.decodeResource(SceltaPersonaggi.this.getResources(), R.drawable.personaggio1);
        Personaggio personaggio1 = new Personaggio("Ricky", 20, profile1);

        Bitmap profile2 = BitmapFactory.decodeResource(SceltaPersonaggi.this.getResources(), R.drawable.personaggio2);
        Personaggio personaggio2 = new Personaggio("Anacleto", 40, profile2);

        personaggi.add(personaggio1);
        personaggi.add(personaggio2);

        indietro = findViewById(R.id.indietro);

        recyclerView = findViewById(R.id.recyclerView);
        Intent intent = getIntent();
        punteggio = intent.getIntExtra("punteggio", 0);
        bambino = intent.getStringExtra("bambino");
        genitore = intent.getStringExtra("email");

        characterAdapter = new CharacterAdapter(SceltaPersonaggi.this, personaggi, punteggio, bambino, genitore, this);
        recyclerView.setAdapter(characterAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SceltaPersonaggi.this));

        punteggioText = findViewById(R.id.punteggio);


        punteggioText.setText("Punti ottenuti: " + punteggio);

        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SceltaPersonaggi.this, HomeBambino.class);
                intent.putExtra("bambino", bambino);
                intent.putExtra("email", genitore);
                intent.putExtra("punteggio", punteggio);
                finish();
            }
        });

    }

    @Override
    public void onItemClick(int value) {
        punteggio = value;
        Log.d(TAG, "onItemClick: Nuovo punteggio: " + punteggio);
        punteggioText.setText("Punti ottenuti: " + punteggio);
    }
}