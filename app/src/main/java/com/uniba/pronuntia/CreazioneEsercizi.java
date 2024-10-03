package com.uniba.pronuntia;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Calendar;

public class CreazioneEsercizi extends AppCompatActivity {

    private Button therapy;
    private TextView nomeText;
    private TextView cognomeText;
    private TextView emailText;
    private TextView motivoText;
    private TextView durataText;

    private TextView dataText;
    private Button calendario;

    private Button addEsercizio;
    private RecyclerView recyclerView;
    private ExerciseAdapter customAdapter;
    private ArrayList<Esercizio> esercizi = new ArrayList<>();
    private DBHelper db;
    private int day, month, year;
    private int durata;
    private String data;
    private String email;
    private String bambino;
    private String motivo;

    private String source;
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
        motivoText = (TextView) findViewById(R.id.motivo);
        durataText = (TextView) findViewById(R.id.durata);
        dataText = (TextView) findViewById(R.id.date);
        calendario = findViewById(R.id.calendar);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        bambino = intent.getStringExtra("bambino");
        motivo = intent.getStringExtra("motivo");
        durata = intent.getIntExtra("durata", 1);

        source = intent.getStringExtra("source");

        //data = intent.getStringExtra("data");


        String nome = intent.getStringExtra("nome");
        String cognome = intent.getStringExtra("cognome");
        String telefono = intent.getStringExtra("telefono");

        nomeText.setText(bambino);
        cognomeText.setText(cognome);
        emailText.setText(email);
        motivoText.setText(motivo);

        if(durata>1) {
            durataText.setText(durata + " settimane");
        }else{
            durataText.setText(durata + " settimana");
        }

        addEsercizio = findViewById(R.id.add);
        recyclerView = findViewById(R.id.exercises);

        db = new DBHelper(this);

        esercizi = db.readExercises(email, bambino);


        customAdapter = new ExerciseAdapter(CreazioneEsercizi.this, esercizi);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CreazioneEsercizi.this));

        Log.d(TAG, "onCreate: " + source);
        if(!source.equals("Logopedista")){
            data = intent.getStringExtra("data");
            dataText.setText(data);
        }else{
        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreazioneEsercizi.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yearDP, int monthDP, int dayOfMonthDP) {

                        data = dayOfMonthDP + " " + (monthDP+1) + " " + yearDP;
                        dataText.setText(data);
                        Log.d(TAG, "onDateSet: " + dayOfMonthDP + " " + (monthDP+1) + " " + yearDP);

                        Calendar forWeeks = Calendar.getInstance();
                        forWeeks.set(yearDP, monthDP, dayOfMonthDP);


                        ArrayList<String> dateList = new ArrayList<>();

                        for (int i = 0; i < (durata*7); i++) {
                            // Ottieni il giorno, mese e anno corrente
                            int currentDay = forWeeks.get(Calendar.DAY_OF_MONTH);
                            int currentMonth = forWeeks.get(Calendar.MONTH) + 1;  // Il mese è 0-based
                            int currentYear = forWeeks.get(Calendar.YEAR);

                            // Aggiungi la data alla lista
                            String currentDate = currentDay + "/" + currentMonth + "/" + currentYear;
                            dateList.add(currentDate);

                            // Aggiungi un giorno al calendario
                            forWeeks.add(Calendar.DAY_OF_YEAR, 1);

                            //Log.d(TAG, i+1 + " " + currentDate);
                        }

                        for(int i = 0;i<dateList.size();i++){
                            Log.d(TAG, i+1 + " " + dateList.get(i));
                        }

                    }
                }, day, month, year);


                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis() - 1000);
                datePickerDialog.show();
            }
        });


        }



        addEsercizio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dataText.getText().toString().equals(data)){
                    showExerciseTypeDialog(email);
                }else{
                    Toast.makeText(CreazioneEsercizi.this, "Data non selezionata", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        
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
                            break;
                        case 1:
                            intent = new Intent(CreazioneEsercizi.this, RipetizioneSequenza.class);
                            break;
                        case 2:
                            intent = new Intent(CreazioneEsercizi.this, Coppia.class);

                            break;
                        default:
                            return;
                    }

                    Log.d(TAG, "showExerciseTypeDialog: " + data);
                    intent.putExtra("email", email);
                    intent.putExtra("bambino", bambino);
                    intent.putExtra("durata", durata);
                    intent.putExtra("data", data);
                    startActivity(intent);
                });
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        customAdapter.notifyDataSetChanged();
    }
}