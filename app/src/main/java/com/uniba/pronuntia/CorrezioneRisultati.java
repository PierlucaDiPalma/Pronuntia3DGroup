package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CorrezioneRisultati extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CorrectionAdapter customAdapter;
    private DBHelper db;
    private ArrayList<Resoconto> resoconti;
    private String bambino, genitore, logopedista;

    private static final String TAG = "CorrezioneRisultati";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_correzione_risultati);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.correzione), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DBHelper(this);

        Intent intent = getIntent();
        bambino = "Luigi";
        genitore = intent.getStringExtra("genitore");

        logopedista = db.getLogopedista(bambino, genitore);
        resoconti = db.getResoconto(genitore, bambino);

        recyclerView = findViewById(R.id.recyclerView);

        for(int i = 0;i<resoconti.size();i++){
            Log.d(TAG, "RESOCONTI: " + resoconti.get(i).getEsercizio().getName() + " " + resoconti.get(i).getBambino() + " " + resoconti.get(i).getAudio());
        }


        customAdapter = new CorrectionAdapter(CorrezioneRisultati.this, resoconti);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CorrezioneRisultati.this));


    }
}