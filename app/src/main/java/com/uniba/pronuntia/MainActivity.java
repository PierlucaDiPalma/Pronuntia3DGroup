package com.uniba.pronuntia;

import android.app.Fragment;
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

public class MainActivity extends AppCompatActivity implements OnDataPassListener{

    private DBHelper db;
    private Button avanti;
    private int punteggio = 0;
    private static final String TAG = "MainActivity";
    private TextView punteggioText;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        avanti = findViewById(R.id.avanti);
        punteggioText = findViewById(R.id.punteggio);
        db = new DBHelper(this);

        String email = "paoloneri@gmail.com";

        ArrayList<Esercizio> esercizi = db.getDenominazione(email);

        Log.d(TAG, "onCreate: " + esercizi.get(0).getTipo());
        Log.d(TAG, "onCreate: " + esercizi.get(0).getName());

        esercizi.addAll(db.getSequenza(email));
        esercizi.addAll(db.getCoppia(email));

        shuffleArrayList(esercizi);

        Bundle args = new Bundle();

        loadFragment(i, esercizi, args);



        avanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;

                if(i<esercizi.size()) {
                    loadFragment(i, esercizi, args);
                }else{
                    Toast.makeText(MainActivity.this, "Esercizi finiti", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void loadFragment(int i, ArrayList<Esercizio> esercizi, Bundle args){
        if(esercizi.get(i).getTipo().equals("Denominazione")){

            args.putString("Titolo", esercizi.get(i).getName());
            args.putByteArray("Immagine", esercizi.get(i).getImmagine1());
            args.putString("Aiuto", esercizi.get(i).getAiuto());
            args.putInt("Punteggio", punteggio);

            LivelloDen fragment = new LivelloDen();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.level, fragment).commit();

        }else if(esercizi.get(i).getTipo().equals("Sequenza")){
            args.putString("Titolo", esercizi.get(i).getName());
            args.putString("Parola1", esercizi.get(i).getSequenza()[0]);
            args.putString("Parola2", esercizi.get(i).getSequenza()[1]);
            args.putString("Parola3", esercizi.get(i).getSequenza()[2]);
            args.putInt("Punteggio", punteggio);

            LivelloSeq fragment = new LivelloSeq();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.level, fragment).commit();

        }else if(esercizi.get(i).getTipo().equals("Coppia")){

            args.putString("Titolo", esercizi.get(i).getName());
            args.putByteArray("Immagine1", esercizi.get(i).getImmagine1());
            args.putByteArray("Immagine2", esercizi.get(i).getImmagine2());
            args.putString("Aiuto", esercizi.get(i).getAiuto());
            args.putInt("Punteggio", punteggio);

            LivelloCop fragment = new LivelloCop();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.level, fragment).commit();
        }
    }

    public static <T> void shuffleArrayList(ArrayList<T> list) {
        Collections.shuffle(list); // Mescola casualmente l'ArrayList
    }

    @Override
    public void onDataPass(int points) {
        // Ricevi il dato elaborato e fai qualcosa con esso (ad esempio, stampalo)
        punteggio = points;
        punteggioText.setText(String.valueOf(punteggio));
        Log.d("MainActivity", "Dato elaborato dal Fragment: " + punteggio);
    }

}