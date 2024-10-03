package com.uniba.pronuntia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

public class SceltaPersonaggi extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Personaggio> personaggi = new ArrayList<>();
    private CharacterAdapter characterAdapter;
    private TextView punteggioText;
    private int punteggio;

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
        Personaggio personaggio1 = new Personaggio("Ricky", 10, profile1);

        Bitmap profile2 = BitmapFactory.decodeResource(SceltaPersonaggi.this.getResources(), R.drawable.personaggio2);
        Personaggio personaggio2 = new Personaggio("Anacleto", 20, profile2);

        personaggi.add(personaggio1);
        personaggi.add(personaggio2);

        recyclerView = findViewById(R.id.recyclerView);
        characterAdapter = new CharacterAdapter(SceltaPersonaggi.this, personaggi);
        recyclerView.setAdapter(characterAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SceltaPersonaggi.this));

        punteggioText = findViewById(R.id.punteggio);
        punteggio = getIntent().getIntExtra("punteggio", 0);

        punteggioText.setText("Punti ottenuti: " + punteggio);



    }

}