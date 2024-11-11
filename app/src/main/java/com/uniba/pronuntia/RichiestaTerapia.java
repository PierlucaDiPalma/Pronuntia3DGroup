package com.uniba.pronuntia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.uniba.pronuntia.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class RichiestaTerapia extends AppCompatActivity {
    private int logopedistaId;
    private String nomeBambino;
    private String motivoRichiesta;
    private int durataTerapia;
    private String emailGenitore;
    private String getEmailLogopedista;

    private EditText motivoRichiestaEditText;
    private Spinner durataSpinner;
    private String emailLogopedista;
    private Button inviaButton;
    private ImageView backBtn;
    private BottomNavigationView bottombar;
    private ArrayList<Bambino> bambini;
    private DBHelper databaseHelper;
    private Spinner spinner1;

    public RichiestaTerapia() {
    }
    public RichiestaTerapia(int logopedistaId, String nomeBambino, String motivoRichiesta, int durataTerapia, String emailGenitore, String getEmailLogopedista) {
        this.logopedistaId = logopedistaId;
        this.nomeBambino = nomeBambino;
        this.motivoRichiesta = motivoRichiesta;
        this.durataTerapia = durataTerapia;
        this.emailGenitore = emailGenitore;
        this.getEmailLogopedista = getEmailLogopedista;
    }

    public int getLogopedistaId() {
        return logopedistaId;
    }

    public String getNomeBambino() {
        return nomeBambino;
    }

    public String getMotivoRichiesta() {
        return motivoRichiesta;
    }

    public int getDurataTerapia() {
        return durataTerapia;
    }

    public String getEmailGenitore() {
        return emailGenitore;
    }

    public String getGetEmailLogopedista() {
        return getEmailLogopedista;
    }



    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.richiesta_terapia);

        databaseHelper = new DBHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String emailGenitore = sharedPreferences.getString("userEmail", null);
        this.bambini=databaseHelper.getBambiniByEmail(emailGenitore);


        Spinner spinner=findViewById(R.id.Spinner);
         spinner1=findViewById(R.id.SpinnerBambini);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.Weeks,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        ArrayList<String> bambiniString=new ArrayList<>();
        for(Bambino bambino:bambini){
            bambiniString.add(bambino.getNome());
        }

        ArrayAdapter<String> adapterString=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,bambiniString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapterString);

        motivoRichiestaEditText = findViewById(R.id.CampoMotivoRichiesta);
        durataSpinner = findViewById(R.id.Spinner);

        inviaButton = findViewById(R.id.InviaRichiestabtn);
        emailLogopedista = getIntent().getStringExtra("EMAIL_LOGOPEDISTA");
        Log.d("RichiestaTerapia", "Email Logopedista: " + emailLogopedista);


        inviaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserisciTerapiaNelDB();

               Intent intent=new Intent(RichiestaTerapia.this,HomeGenitore.class);
               startActivity(intent);
            }
        });

backBtn=findViewById(R.id.back_buttonRichiestaTerapia);
backBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        onBackPressed();
    }
});

    }

    private void inserisciTerapiaNelDB() {
        String nomeBambino =spinner1.getSelectedItem().toString();
        String motivoRichiesta = motivoRichiestaEditText.getText().toString();
        String durataString = durataSpinner.getSelectedItem().toString();
        String[] parts = durataString.split(" ");
        int durata = Integer.parseInt(parts[0]);

        if (emailLogopedista == null || emailLogopedista.isEmpty()) {
            Toast.makeText(this, "Errore: email del logopedista non trovata", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences sdf = getSharedPreferences("UserPrefs", MODE_PRIVATE);
    String emailGen=sdf.getString("userEmail", null);

        databaseHelper.addTerapia(nomeBambino, motivoRichiesta, durata, emailGen, emailLogopedista);
        databaseHelper.addGiocatore(new Giocatore(nomeBambino, emailGen, emailLogopedista, 0));
        databaseHelper.addPaziente(emailGen, nomeBambino, emailLogopedista);
        Toast.makeText(this, "Terapia inserita con successo", Toast.LENGTH_SHORT).show();
    }
}





