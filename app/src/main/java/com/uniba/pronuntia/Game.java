package com.uniba.pronuntia;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Game extends AppCompatActivity implements OnDataPassListener{

    private DBHelper db;
    private Button avanti;
    private int punteggio = 0;
    private static final String TAG = "Game";
    private TextView punteggioText;
    private String email;
    private String bambino;
    private String logopedista;

    private int i;
    private int livello;
    private boolean isDone;
    private int numeroAiuti = 0;
    private int corretti = 0;
    private int sbagliati = 0;
    private Esercizio esercizio;
    private Resoconto resoconto;
    private String audioFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        avanti = findViewById(R.id.avanti);

        punteggioText = findViewById(R.id.punteggio);
        db = new DBHelper(this);

        email = getIntent().getStringExtra("Email");
        bambino = getIntent().getStringExtra("Bambino");
        logopedista = getIntent().getStringExtra("Logopedista");

        punteggio = getIntent().getIntExtra("Punteggio", 0);
        livello = getIntent().getIntExtra("Livello", 0);
        esercizio = getIntent().getParcelableExtra("Esercizio");


        Log.d(TAG, "Livello: " + livello);
        Log.d(TAG, "Tipo: " + esercizio.getTipo() + " " + esercizio.getName() + " " + esercizio.getGiorno() + " "
                + esercizio.getMese() + " " + esercizio.getAnno());

        Bundle args = new Bundle();

        loadFragment(i, esercizio, args);

        avanti.setVisibility(View.GONE);

        avanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.putExtra("Livello", livello);
                intent.putExtra("Punteggio", punteggio);

                intent.putExtra("Aiuti", numeroAiuti);
                intent.putExtra("Corretti", corretti);
                intent.putExtra("Sbagliati", sbagliati);
                intent.putExtra("Resoconto", resoconto);
                intent.putExtra("source", TAG);

                setResult(RESULT_OK, intent); // Imposta il risultato come RESULT_OK
                finish();
            }

        });

    }

    private void loadFragment(int i, Esercizio esercizio, Bundle args){
        if(esercizio.getTipo().equals("Denominazione")){


            args.putParcelable("esercizio", esercizio);
            args.putInt("Punteggio", punteggio);

            LivelloDen fragment = new LivelloDen();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.level, fragment).commit();

        }else if(esercizio.getTipo().equals("Sequenza")){
            args.putString("Titolo", esercizio.getName());
            args.putString("Parola1", esercizio.getSequenza()[0]);
            args.putString("Parola2", esercizio.getSequenza()[1]);
            args.putString("Parola3", esercizio.getSequenza()[2]);
            args.putInt("Punteggio", punteggio);

            LivelloSeq fragment = new LivelloSeq();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.level, fragment).commit();

        }else if(esercizio.getTipo().equals("Coppia")){


            args.putParcelable("esercizio", esercizio);
            args.putInt("Punteggio", punteggio);
            LivelloCop fragment = new LivelloCop();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.level, fragment).commit();
        }
    }

    @Override
    public void onDataPass(int points, boolean done, int numeroAiuti, int corretti, int sbagliati, String path) {


        Log.d(TAG, "AUDIO FILE PATH: " + path);
        audioFilePath = path;
        punteggio = points;
        isDone = done;
        punteggioText.setText("Punteggio: " + punteggio);
        Log.d(TAG, "Dato elaborato dal Fragment: " + punteggio);

        this.numeroAiuti = numeroAiuti;
        this.corretti = corretti;
        this.sbagliati = sbagliati;
        Log.d(TAG, "Logopedista: " + logopedista);

        resoconto = new Resoconto(bambino, email, logopedista,
                esercizio, audioFilePath, punteggio, this.corretti, this.sbagliati, this.numeroAiuti);



        if(isDone){
            avanti.setVisibility(View.VISIBLE);

        }
    }

}