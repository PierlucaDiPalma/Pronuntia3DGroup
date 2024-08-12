package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;

public class CreazioneEsercizi extends AppCompatActivity {

    private Button therapy;
    private TextView nomeText;
    private TextView cognomeText;
    private TextView emailText;
    private TextView telefonoText;

    private TextView durata;
    private Button calendario;
    private Button addEsercizio, prova;
    private RecyclerView recyclerView;
    private ExerciseAdapter customAdapter;
    private ArrayList<Esercizio> esercizi = new ArrayList<>();
    private DBHelper db;

    private static final String TAG = "CreazioneEsercizi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_creazione_esercizi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.therapy), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d(TAG, "onCreate: ");

        nomeText = (TextView) findViewById(R.id.nome);
        cognomeText = (TextView) findViewById(R.id.cognome);
        emailText = (TextView) findViewById(R.id.email);
        telefonoText = (TextView) findViewById(R.id.telefono);

        Intent intent = getIntent();
        String nome = intent.getStringExtra("nome");
        String cognome = intent.getStringExtra("cognome");
        String email = intent.getStringExtra("email");
        String telefono = intent.getStringExtra("telefono");

        nomeText.setText(nome);
        cognomeText.setText(cognome);
        emailText.setText(email);
        telefonoText.setText(telefono);


        addEsercizio = findViewById(R.id.add);
        prova = findViewById(R.id.tryImage);
        recyclerView = findViewById(R.id.exercises);



        db = new DBHelper(this);

        esercizi = db.readExercises(email);



        customAdapter = new ExerciseAdapter(CreazioneEsercizi.this, esercizi);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CreazioneEsercizi.this));
        addEsercizio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExerciseTypeDialog(email);
            }
        });

        prova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreazioneEsercizi.this, ImageActivity.class);
                intent.putExtra(email, "email");
                startActivity(intent);
            }
        });
    }

    private void showExerciseTypeDialog(String email) {
        String[] exerciseTypes = {"Denominazione", "Sequenza", "Coppia"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleziona tipo di esercizio")
                .setItems(exerciseTypes, (dialog, which) -> {
                    Intent intent;
                    switch (which) {
                        case 0:
                            intent = new Intent(CreazioneEsercizi.this, DenominazioneImmagini.class);
                            intent.putExtra("email", email);
                            break;
                        case 1:
                            intent = new Intent(CreazioneEsercizi.this, RipetizioneSequenza.class);
                            intent.putExtra("email", email);
                            break;
                        case 2:
                            intent = new Intent(CreazioneEsercizi.this, Coppia.class);
                            intent.putExtra("email", email);
                            break;
                        default:
                            return;
                    }
                    startActivity(intent);
                });
        builder.show();
    }
}