package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CorrezioneRisultati extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CorrectionAdapter customAdapter;
    private DBHelper db;
    private ArrayList<Resoconto> resoconti;
    private String bambino, genitore, logopedista;
    private Spinner spinner;

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

        genitore = intent.getStringExtra("genitore");




        recyclerView = findViewById(R.id.recyclerView);
        spinner = findViewById(R.id.spinner);

        ArrayList<Bambino> bambini = db.getBambiniByEmail(genitore);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.Weeks,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayList<String> bambiniString = new ArrayList<>();
        for(Bambino bambino:bambini){
            bambiniString.add(bambino.getNome());
        }
        ArrayAdapter<String> adapterString=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,bambiniString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterString);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bambino = spinner.getSelectedItem().toString();
                logopedista = db.getLogopedista(genitore, bambino);

                resoconti = db.getResoconto(genitore, bambino);
                for(int i = 0;i<resoconti.size();i++){
                    Log.d(TAG, "RESOCONTI: " + resoconti.get(i).getEsercizio().getName() + " " + resoconti.get(i).getBambino() + " " + resoconti.get(i).getAudio());
                }

                customAdapter = new CorrectionAdapter(CorrezioneRisultati.this, resoconti);
                recyclerView.setAdapter(customAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(CorrezioneRisultati.this));

                customAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

}