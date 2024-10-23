package com.uniba.pronuntia;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;

public class GamePath extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LevelAdapter customAdapter;
    private DBHelper db;
    private ArrayList<Esercizio> esercizi;
    private String email;
    private int punteggio;
    private TextView punteggioText;
    private TextView aiutiText;
    private TextView correttiText;
    private TextView sbagliatiText;
    private int livello = 1;
    private Button risultato;
    private int numeroAiuti = 0;
    private int corretti = 0;
    private int sbagliati = 0;
    private String bambino;
    private Resoconto resoconto;
    private String path;
    private String logopedista;
    private ArrayList<Resoconto> resoconti = new ArrayList<>();
    private String source;
    private final static String TAG = "GamePath";

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

        email = getIntent().getStringExtra("email");
        bambino = getIntent().getStringExtra("Bambino");
        punteggio = 0; //getIntent().getIntExtra("punteggio", 0);
        path = getIntent().getStringExtra("pathPersonaggio");
        logopedista = getIntent().getStringExtra("logopedista");

        punteggioText = findViewById(R.id.punteggio);
        aiutiText = findViewById(R.id.aiuti);
        correttiText = findViewById(R.id.corretti);
        sbagliatiText = findViewById(R.id.sbagliati);
        risultato = findViewById(R.id.fine);




        risultato.setVisibility(View.GONE);

        Calendar calendar = Calendar.getInstance();
        esercizi = db.getDenominazione(email, bambino, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR));

        for(int i=0; i<esercizi.size();i++) {
            Log.d(TAG, "Denominazione: " + esercizi.get(i).getName() + " " + esercizi.get(i).getGiorno() + " " + esercizi.get(i).getMese() + " " + esercizi.get(i).getAnno());
        }

        esercizi.addAll(db.getSequenza(email, bambino, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR)));
        esercizi.addAll(db.getCoppia(email, bambino, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR)));


        shuffleArrayList(esercizi);

        customAdapter = new LevelAdapter(GamePath.this, esercizi, livello, path, logopedista);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(GamePath.this));

        //layoutParams.bottomMargin = 50;

        //setImagePosition(livello);

        risultato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if(source.equals("Game")) {
                    for (int i = 0; i < resoconti.size(); i++) {
                        Log.d(TAG, "STAMPA RESOCONTI: " + resoconti.get(i).getEsercizio().getName() + " " + resoconti.get(i).getBambino()
                                + " " + resoconti.get(i).getEsercizio().getTipo());

                        db.addResoconto(resoconti.get(i));
                    }
                }else{
                    Toast.makeText(GamePath.this, "Non è necessario salvare nel DB", Toast.LENGTH_LONG).show();
                }*/

                Intent intent = new Intent(GamePath.this, RisultatoFinale.class);
                intent.putExtra("Bambino", bambino);
                intent.putExtra("email", email);
                intent.putExtra("logopedista", logopedista);
                intent.putExtra("Punteggio", punteggio);
                intent.putExtra("Aiuti", numeroAiuti);
                intent.putExtra("Corretti", corretti);
                intent.putExtra("Sbagliati", sbagliati);
                intent.putParcelableArrayListExtra("Resoconti", resoconti);
                intent.putExtra("source", TAG);
                startActivity(intent);

            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) { // Verifica il codice della richiesta
            if (resultCode == RESULT_OK) {
                // Ottieni il risultato passato dall'Activity chiusa
                int nuovoLivello = data.getIntExtra("Livello", livello);
                punteggio += data.getIntExtra("Punteggio", 0);
                punteggioText.setText("Punteggio: " + punteggio);

                numeroAiuti += data.getIntExtra("Aiuti", 0);
                aiutiText.setText("Aiuti usati: " + numeroAiuti);

                corretti += data.getIntExtra("Corretti", 0);
                correttiText.setText("Esercisi corretti: " + corretti);

                sbagliati += data.getIntExtra("Sbagliati", 0);
                sbagliatiText.setText("Esercizi sbagliati: " + sbagliati);

                livello = nuovoLivello;
                customAdapter.setLivello(nuovoLivello);  // Metodo da creare nell'Adapter


                //resoconto = new Resoconto(bambino, email, "marcorossi@gmai.com", )

                resoconto = data.getParcelableExtra("Resoconto");
                resoconti.add(resoconto);

                 /*resoconti.add(resoconto);

                Log.d(TAG, "STAMPA RESOCONTI: " + resoconto.getEsercizio().getName() + " " + resoconto.getBambino()
                        + " " + resoconto.getEsercizio().getTipo());
*/
                if(livello > esercizi.size()){
                    risultato.setVisibility(View.VISIBLE);

                }
                // Notifica l'Adapter per aggiornare la RecyclerView
                customAdapter.notifyDataSetChanged();

            }
        }
    }

    public static <T> void shuffleArrayList(ArrayList<T> list) {
        Collections.shuffle(list); // Mescola casualmente l'ArrayList
    }



}