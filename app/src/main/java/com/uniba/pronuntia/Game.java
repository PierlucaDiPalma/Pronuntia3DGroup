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
    private static final String TAG = "MainActivity";
    private TextView punteggioText;
    private String email;
    private int i;
    private int livello;
    private boolean isDone;
    private int numeroAiuti = 0;
    private int corretti = 0;
    private int sbagliati = 0;
    private Esercizio esercizio;
    private Resoconto resoconto;

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

        //email = getIntent().getStringExtra("Email");
        //i = getIntent().getIntExtra("Posizione", 0);
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

                setResult(RESULT_OK, intent); // Imposta il risultato come RESULT_OK
                finish();
            }

        });

    }

    private void loadFragment(int i, Esercizio esercizio, Bundle args){
        if(esercizio.getTipo().equals("Denominazione")){

            args.putString("Titolo", esercizio.getName());
            args.putByteArray("Immagine", esercizio.getImmagine1());
            args.putString("Aiuto", esercizio.getAiuto());
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

            args.putString("Titolo", esercizio.getName());
            args.putByteArray("Immagine1", esercizio.getImmagine1());
            args.putByteArray("Immagine2",esercizio.getImmagine2());
            args.putString("Aiuto", esercizio.getAiuto());
            args.putInt("Punteggio", punteggio);

            LivelloCop fragment = new LivelloCop();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.level, fragment).commit();
        }
    }

    @Override
    public void onDataPass(int points, boolean done, int numeroAiuti, int corretti, int sbagliati) {
        // Ricevi il dato elaborato e fai qualcosa con esso (ad esempio, stampalo)
        punteggio = points;
        isDone = done;
        punteggioText.setText("Punteggio: " + punteggio);
        Log.d("MainActivity", "Dato elaborato dal Fragment: " + punteggio);

        this.numeroAiuti = numeroAiuti;
        this.corretti = corretti;
        this.sbagliati = sbagliati;

        resoconto = new Resoconto("Luigi", "paoloneri@gmail.com", "marcorossi@gmail.com",
                esercizio, punteggio, this.corretti, this.sbagliati, this.numeroAiuti);

        boolean isInserted = db.addResoconto(resoconto);
        if (isInserted) {
            Log.d(TAG, "Resoconto salvato con successo");
        } else {
            Log.d(TAG, "Errore durante il salvataggio del resoconto");
        }

        Log.d(TAG, "Esercizio: " + resoconto.getEsercizio().getName() + " Numero aiuti: " + resoconto.getAiuti()
                + " Numero errori: " + resoconto.getSbagliati() + " Numero corretti " + resoconto.getCorretti());

        if(isDone){
            avanti.setVisibility(View.VISIBLE);
        }
    }

}