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
    private Button addEsercizio;
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


        durata = findViewById(R.id.date);
        calendario = findViewById(R.id.calendar);
        addEsercizio = findViewById(R.id.add);
        recyclerView = findViewById(R.id.exercises);

        db = new DBHelper(this);

        //esercizi = db.readExercises();


        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().
                setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds())).build();

        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "Tag_picker");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        durata.setText("Durata: " + materialDatePicker.getHeaderText());
                    }
                });
            }
        });

        addEsercizio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExerciseTypeDialog(email);
            }
        });
    }

    private void showExerciseTypeDialog(String email) {
        String[] exerciseTypes = {"Denominazione", "Indovinello", "Coppia"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleziona Tipo di Esercizio")
                .setItems(exerciseTypes, (dialog, which) -> {
                    Intent intent;
                    switch (which) {
                        case 0:
                            intent = new Intent(CreazioneEsercizi.this, DenominazioneImmagini.class);
                            intent.putExtra("email", email);
                            break;
                        case 1:
                            intent = new Intent(CreazioneEsercizi.this, MainActivity.class);
                            break;
                        case 2:
                            intent = new Intent(CreazioneEsercizi.this, MainActivity.class);
                            break;
                        default:
                            return;
                    }
                    startActivity(intent);
                });
        builder.show();
    }
}