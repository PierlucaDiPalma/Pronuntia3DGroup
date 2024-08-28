package com.uniba.pronuntia;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RichiestaTerapia extends AppCompatActivity {

    private EditText nomeBambinoEditText;
    private EditText motivoRichiestaEditText;
    private Spinner durataSpinner;
    private CheckBox checkbox1, checkbox2, checkbox3;
    private Button inviaButton;

    private DBHelper databaseHelper;



    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.richiesta_terapia);
        Spinner spinner=findViewById(R.id.Spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.Weeks,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        databaseHelper = new DBHelper(this);
        nomeBambinoEditText = findViewById(R.id.CampoNomeBambino);
        motivoRichiestaEditText = findViewById(R.id.CampoMotivoRichiesta);
        durataSpinner = findViewById(R.id.Spinner);
        checkbox1 = findViewById(R.id.checkboxRiconoscimentoCoppieMinime);
        checkbox2 = findViewById(R.id.checkboxRipetizioneSeqParole);
        checkbox3 = findViewById(R.id.checkboxDenominazImg);
        inviaButton = findViewById(R.id.InviaRichiestabtn);

        inviaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserisciTerapiaNelDB();
            }
        });




    }

    private void inserisciTerapiaNelDB() {
        String nomeBambino = nomeBambinoEditText.getText().toString();
        String motivoRichiesta = motivoRichiestaEditText.getText().toString();
        String durataString = durataSpinner.getSelectedItem().toString();
        String[] parts = durataString.split(" ");
        int durata = Integer.parseInt(parts[0]);



        StringBuilder contenutiTerapia = new StringBuilder();
        if (checkbox1.isChecked()) contenutiTerapia.append("Riconoscimento coppie minime, ");
        if (checkbox2.isChecked()) contenutiTerapia.append("Ripetizione di sequenze di parole, ");
        if (checkbox3.isChecked()) contenutiTerapia.append("Denominazione immagini, ");

        // Rimuovere l'ultima virgola e spazio
        if (contenutiTerapia.length() > 0) {
            contenutiTerapia.setLength(contenutiTerapia.length() - 2);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String emailGenitore = sharedPreferences.getString("userEmail", "default@example.com");


        databaseHelper.addTerapia(nomeBambino, motivoRichiesta, durata, contenutiTerapia.toString(), emailGenitore);

        Toast.makeText(this, "Terapia inserita con successo", Toast.LENGTH_SHORT).show();

        // Pulisci i campi
        nomeBambinoEditText.setText("");
        motivoRichiestaEditText.setText("");
        durataSpinner.setSelection(0);
        checkbox1.setChecked(false);
        checkbox2.setChecked(false);
        checkbox3.setChecked(false);
    }
    }





