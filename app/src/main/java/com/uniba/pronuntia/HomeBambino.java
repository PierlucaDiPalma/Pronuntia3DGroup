package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeBambino extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExerciseAdapter customAdapter;
    private ArrayList<Esercizio> eserciziList = new ArrayList<>();
    private ArrayList<Esercizio> esercizi = new ArrayList<>();
    private DBHelper db;
    private String email;
    private String bambino;
    private final static String TAG = "HomeBambino";
    private Button avanti;
    private ArrayList<Resoconto> resoconti;
    private int numberOfTrue = 0;
    private TextView nomeBambinoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_bambino);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        int idBambino = intent.getIntExtra("idBambino", -1); // Ottieni l'ID del bambino
        bambino = intent.getStringExtra("bambino"); // Ottieni il nome del bambino
        nomeBambinoTextView = findViewById(R.id.textView);


        if (bambino != null) {
            nomeBambinoTextView.setText(bambino); // Mostra il nome del bambino
        } else {
            nomeBambinoTextView.setText("Nome non disponibile");
        }




        avanti = findViewById(R.id.avanti);
        recyclerView = findViewById(R.id.exercises);
        db = new DBHelper(this);



        email = intent.getStringExtra("email");

        resoconti = db.getResoconto(email);

        eserciziList = db.readExercises(email, bambino);

        Calendar calendar = Calendar.getInstance();

        esercizi = db.getDenominazione(email, bambino, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR));
        esercizi.addAll(db.getSequenza(email, bambino, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR)));
        esercizi.addAll(db.getCoppia(email, bambino, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR)));

        Log.d(TAG, "onCreate: email recuperata " + email);


        customAdapter = new ExerciseAdapter(HomeBambino.this, eserciziList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeBambino.this));

        for(int i = 0; i<resoconti.size();i++){
            for(int j = 0; j<esercizi.size();j++){
                if(resoconti.get(i).getEsercizio().getName().equals( esercizi.get(j).getName()) &&
                        resoconti.get(i).getEsercizio().getGiorno() == esercizi.get(j).getGiorno() &&
                        resoconti.get(i).getEsercizio().getMese() == esercizi.get(j).getMese() &&
                        resoconti.get(i).getEsercizio().getAnno() == esercizi.get(j).getAnno()){

                    numberOfTrue++;
                    Log.d(TAG, "Resoconto: " + resoconti.get(i).getEsercizio().getName() + " " + resoconti.get(i).getEsercizio().getGiorno() + " " + resoconti.get(i).getEsercizio().getMese()
                            + " " + resoconti.get(i).getEsercizio().getAnno()
                            + " esercizio: " + esercizi.get(j).getName() + " " + esercizi.get(j).getGiorno() + " " + esercizi.get(j).getMese() + " " +esercizi.get(j).getAnno());
                }
            }
        }

        avanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(numberOfTrue == esercizi.size() && numberOfTrue>0) {

                    Intent intent = new Intent(HomeBambino.this, RisultatoFinale.class);
                    intent.putExtra("Email", email);
                    intent.putExtra("Bambino", bambino);
                    startActivityForResult(intent, 1);

                }else{
                    Intent intent = new Intent(HomeBambino.this, GamePath.class);
                    intent.putExtra("Email", email);
                    intent.putExtra("Bambino", bambino);
                    startActivityForResult(intent, 1);

                }
            }
        });
    }

    public boolean isEquals(ArrayList<Esercizio> esercizi, ArrayList<Resoconto> resoconti) {
        for (Esercizio esercizio : esercizi) {
            boolean isDone = false; // Variabile esterna per controllare se l'esercizio è completato
            for (Resoconto resoconto : resoconti) {
                // Controlla se tutti i parametri corrispondono tra esercizio e resoconto
                if (esercizio.getName().equals(resoconto.getEsercizio().getName()) &&
                        esercizio.getEmail().equals(resoconto.getGenitore()) &&
                        esercizio.getGiorno() == resoconto.getEsercizio().getGiorno() &&
                        esercizio.getMese() == resoconto.getEsercizio().getMese() &&
                        esercizio.getAnno() == resoconto.getEsercizio().getAnno()) {

                    // Se c'è una corrispondenza, imposta isDone su true
                    isDone = true;
                    return isDone; // Esci dal ciclo, hai trovato un match
                }
            }

        }

        return false;
    }
}