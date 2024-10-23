package com.uniba.pronuntia;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RisultatoFinale extends AppCompatActivity {

    private TextView punteggioText;
    private TextView aiutiText;
    private TextView correttiText;
    private TextView sbagliatiText;

    private Button classifica;

    private int punteggio;
    private int numeroAiuti;
    private int corretti;
    private int sbagliati;
    private String email;
    private String bambino;
    private String logopedista;

    private int totaleCorretti = 0;
    private int premio = 1;
    private int totalePremi = 0;

    private RecyclerView recyclerView;
    private ResultAdapter customAdapter;
    private DBHelper db;
    private ArrayList<Resoconto> resoconti;

    private String source;
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
        classifica = findViewById(R.id.classifica);

        email = getIntent().getStringExtra("email");
        bambino = getIntent().getStringExtra("Bambino");
        logopedista = getIntent().getStringExtra("logopedista");

        source = getIntent().getStringExtra("source");

        Log.d(TAG, "LOGOPEDISTA: " + logopedista);

        if(source.equals("GamePath")){

            ArrayList<Resoconto> temp = db.getResoconto(email, bambino);
            resoconti = getIntent().getParcelableArrayListExtra("Resoconti");
            boolean isPresent = false;

            for(int i=0;i<temp.size();i++){
                for(int j=0;j<resoconti.size();j++){
                    if(resoconti.get(j).getEsercizio().getName().equals(temp.get(i).getEsercizio().getName()) &&
                            resoconti.get(j).getEsercizio().getGiorno() == temp.get(i).getEsercizio().getGiorno() &&
                            resoconti.get(j).getEsercizio().getMese() == temp.get(i).getEsercizio().getMese() &&
                            resoconti.get(j).getEsercizio().getAnno() == temp.get(i).getEsercizio().getAnno()){

                        isPresent = true;
                    }
                }

            }

            if(!isPresent){
                for(int i=0;i<resoconti.size();i++){
                    db.addResoconto(resoconti.get(i));
                }
            }

        }else{
            resoconti = db.getResoconto(email, bambino);

        }


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

        classifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RisultatoFinale.this, Classifica.class);
                intent.putExtra("genitore", email);
                intent.putExtra("bambino", bambino);
                intent.putExtra("logopedista", logopedista);
                startActivity(intent);
            }
        });

    }
}