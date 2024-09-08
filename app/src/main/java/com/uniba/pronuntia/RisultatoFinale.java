package com.uniba.pronuntia;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class RisultatoFinale extends AppCompatActivity {

    private TextView punteggioText;
    private TextView aiutiText;
    private TextView correttiText;
    private TextView sbagliatiText;

    private int punteggio;
    private int numeroAiuti;
    private int corretti;
    private int sbagliati;


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

        punteggioText = findViewById(R.id.punteggio);
        aiutiText = findViewById(R.id.aiuti);
        correttiText = findViewById(R.id.corretti);
        sbagliatiText = findViewById(R.id.sbagliati);

        punteggio = getIntent().getIntExtra("Punteggio", 0);
        numeroAiuti = getIntent().getIntExtra("Aiuti", 0);
        corretti = getIntent().getIntExtra("Corretti", 0);
        sbagliati = getIntent().getIntExtra("Sbagliati", 0);

        punteggioText.setText("Punteggio: " + punteggio);
        aiutiText.setText("Aiuti usati: " + numeroAiuti);
        correttiText.setText("Esercizi corretti: " + corretti);
        sbagliatiText.setText("Esercizi sbagliati: " + sbagliati);


    }
}