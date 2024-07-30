package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DenominazioneImmagini extends AppCompatActivity {

    private EditText titoloEdit;
    private Button crea;
    private DBHelper db;
    private String titolo;
    private static final String TAG = "DenominazioneImmagini";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_denominazione_immagini);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.denominazioneImmagini), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d(TAG, "onCreate: Entrato");
        titoloEdit = findViewById(R.id.titoloEsercizio);
        crea = findViewById(R.id.createDen);
        db = new DBHelper(DenominazioneImmagini.this);


        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        Log.d(TAG, "onCreate: " + email);

        crea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                titolo = titoloEdit.getText().toString().trim();
                Esercizio esercizio = new Esercizio(email, titolo);

                if(db.addDenominazione(esercizio)){
                    Log.d(TAG, "onClick: Scrittura");
                    Toast.makeText(DenominazioneImmagini.this, "Esercizio creato", Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    Toast.makeText(DenominazioneImmagini.this, "Qualcosa è andato storto", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}