package com.uniba.pronuntia;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RichiestaTerapia extends AppCompatActivity {

    private EditText nomeBambinoEditText;
    private EditText motivoRichiestaEditText;
    private Spinner durataSpinner;
    private String emailLogopedista;
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

        inviaButton = findViewById(R.id.InviaRichiestabtn);
        emailLogopedista = getIntent().getStringExtra("EMAIL_LOGOPEDISTA");
        Log.d("RichiestaTerapia", "Email Logopedista: " + emailLogopedista);


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

        if (emailLogopedista == null || emailLogopedista.isEmpty()) {
            Toast.makeText(this, "Errore: email del logopedista non trovata", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String emailGenitore = sharedPreferences.getString("userEmail", "default@example.com");

        databaseHelper.addTerapia(nomeBambino, motivoRichiesta, durata, emailGenitore, emailLogopedista);

        Toast.makeText(this, "Terapia inserita con successo", Toast.LENGTH_SHORT).show();
    }
}





