package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RisultatoFinale extends AppCompatActivity {

    private TextView punteggioText;
    private TextView aiutiText;
    private TextView correttiText;
    private TextView sbagliatiText;

    private int punteggio;
    private int numeroAiuti;
    private int corretti;
    private int sbagliati;
    private String email;
    private String bambino;

    private int totaleCorretti = 0;
    private int premio = 1;
    private int totalePremi = 0;

    private RecyclerView recyclerView;
    private ResultAdapter customAdapter;
    private DBHelper db;
    private ArrayList<Resoconto> resoconti;

    private static final String TAG = "RisultatoFinale";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_risultato_finale);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DBHelper(this);
        email = getIntent().getStringExtra("email");
        bambino = getIntent().getStringExtra("Bambino");

        resoconti = db.getResoconto(email, bambino);
        //totaleCorretti = db.getCorretti(bambino, email);
        totalePremi = db.getPremi(bambino, email);

        punteggioText = findViewById(R.id.punteggio);
        aiutiText = findViewById(R.id.aiuti);
        correttiText = findViewById(R.id.corretti);
        sbagliatiText = findViewById(R.id.sbagliati);


        //punteggio = getIntent().getIntExtra("Punteggio", 0);
        numeroAiuti = getIntent().getIntExtra("Aiuti", 0);
        corretti = getIntent().getIntExtra("Corretti", 0);
        sbagliati = getIntent().getIntExtra("Sbagliati", 0);

        for(int i = 0; i<resoconti.size();i++){
          totaleCorretti += resoconti.get(i).getCorretti();
        }

        Log.d(TAG, "ESERCIZI CORRETTI: " + totaleCorretti);



        if(totaleCorretti == 3 && totalePremi==0){
            Toast.makeText(RisultatoFinale.this, "Hai completato " + totaleCorretti + "  esercizi", Toast.LENGTH_LONG).show();
            db.addPremio(bambino, email, totaleCorretti, premio);

            Intent intent = new Intent(RisultatoFinale.this, Trofeo.class);
            intent.putExtra("soglia", 3);
            startActivity(intent);

        }else{
            if(totaleCorretti>=(3*totalePremi)+((totalePremi+1)*3)){
                Toast.makeText(RisultatoFinale.this, "Hai completato altri" + ((totalePremi+1)*3) + "  esercizi.\n Prossimo premio tra 3 esercizi corretti", Toast.LENGTH_LONG).show();
                premio += totalePremi;
                db.updateValuesPremi(bambino, email, totaleCorretti, premio);

                Intent intent = new Intent(RisultatoFinale.this, Trofeo.class);
                intent.putExtra("soglia", ((totalePremi+1)*3) );
                startActivity(intent);
            }
        }



        for(int i=0; i<resoconti.size();i++){
            punteggio += resoconti.get(i).getPunteggio();
        }

        punteggioText.setText("Punteggio: " + punteggio);
        aiutiText.setText("Aiuti usati: " + numeroAiuti);
        correttiText.setText("Esercizi corretti: " + corretti);
        sbagliatiText.setText("Esercizi sbagliati: " + sbagliati);

        recyclerView = findViewById(R.id.recyclerView);
        customAdapter = new ResultAdapter(RisultatoFinale.this, resoconti);

        customAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(RisultatoFinale.this));

    }
}