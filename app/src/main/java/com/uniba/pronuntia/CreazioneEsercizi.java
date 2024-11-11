package com.uniba.pronuntia;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

public class CreazioneEsercizi extends AppCompatActivity {

    private Button therapy;
    private TextView nomeText;
    private TextView cognomeText;
    private TextView emailText;
    private TextView motivoText;
    private TextView durataText;
private ImageButton back;

    private TextView dataText;
    private Button calendario;

    private Button addEsercizio;
    private RecyclerView recyclerView;
    private ExerciseAdapter customAdapter;
    private ArrayList<Esercizio> esercizi = new ArrayList<>();
    private DBHelper db;
    private int day, month, year;
    private int durata = 0;
    private String data;
    private ArrayList<String> dateList = new ArrayList<>();

    private String email;
    private String bambino;
    private String logopedista;
    private String motivo;

    private Button sendTerapia;

    private String source;
    private ArrayList<Esercizio> eserciziList = new ArrayList<>();
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
        Spinner spinnerDurata = findViewById(R.id.spinner_durata);


        String[] settimane = {"1 settimana", "2 settimane", "3 settimane", "4 settimane"};


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, settimane);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDurata.setAdapter(adapter);


        spinnerDurata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                durata = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        nomeText = (TextView) findViewById(R.id.nome);
        cognomeText = (TextView) findViewById(R.id.cognome);
        emailText = (TextView) findViewById(R.id.email);
        motivoText = (TextView) findViewById(R.id.motivo);
        dataText = (TextView) findViewById(R.id.date);
        calendario = findViewById(R.id.calendar);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        bambino = intent.getStringExtra("bambino");
        motivo = intent.getStringExtra("motivo");
        //durata = intent.getIntExtra("durata", 1);
        logopedista = intent.getStringExtra("logopedista");

        source = intent.getStringExtra("source");


        String nome = intent.getStringExtra("nome");
        String cognome = intent.getStringExtra("cognome");
        String telefono = intent.getStringExtra("telefono");

        nomeText.setText(bambino);
        cognomeText.setText(cognome);
        emailText.setText(email);
        motivoText.setText(motivo);


        addEsercizio = findViewById(R.id.add);
        sendTerapia = findViewById(R.id.send);
        recyclerView = findViewById(R.id.exercises);

        db = new DBHelper(this);

back=findViewById(R.id.back_button);
        customAdapter = new ExerciseAdapter(CreazioneEsercizi.this, eserciziList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CreazioneEsercizi.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        sendTerapia.setVisibility(View.INVISIBLE);

        Log.d(TAG, "onCreate: " + source);
        if(!source.equals("Logopedista")){
            /*data = intent.getStringExtra("data");
            dataText.setText(data);*/
        }else{

        /*if(db.getLogopedista(bambino, email)==null){
            db.addPazienti(bambino, email, logopedista);
        }*/

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


                            for (int i = 0; i < (durata*7); i++) {

                                int currentDay = forWeeks.get(Calendar.DAY_OF_MONTH);
                                int currentMonth = forWeeks.get(Calendar.MONTH) + 1;  // Il mese è 0-based
                                int currentYear = forWeeks.get(Calendar.YEAR);


                                String currentDate = currentDay + "/" + currentMonth + "/" + currentYear;
                                dateList.add(currentDate);


                                forWeeks.add(Calendar.DAY_OF_YEAR, 1);


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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addEsercizio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(durata != 0) {
                    if (dataText.getText().toString().equals(data)) {
                        showExerciseTypeDialog(email);
                        for (int i = 0; i < dateList.size(); i++) {
                            Log.d(TAG, i + 1 + " " + dateList.get(i));
                        }

                    } else {
                        Toast.makeText(CreazioneEsercizi.this, "Data non selezionata", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CreazioneEsercizi.this, "Durata non selezionata", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sendTerapia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i<esercizi.size();i++){

                    if(esercizi.get(i).getTipo().equals("Denominazione")){
                        db.addDenominazione(esercizi.get(i));
                    }else if(esercizi.get(i).getTipo().equals("Sequenza")){
                        db.addSequenza(esercizi.get(i));
                    }else{
                        db.addCoppia(esercizi.get(i));
                    }
                }

                for(int i = 0; i<eserciziList.size();i++){
                    db.addExercises(eserciziList.get(i));
                }

                db.deleteTerapia(email,logopedista,bambino);

                finish();
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
                    startActivityForResult(intent, 1);
                });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==1){
            Esercizio esercizio = data.getParcelableExtra("Esercizio");
            eserciziList.add(esercizio);

            for(int i = 0 ;i<eserciziList.size();i++){
                db.addExercises(eserciziList.get(i));
            }

            for(int i = 0; i<dateList.size();i++){


                String[] dateContent = dateList.get(i).split("/");

                Log.d(TAG, "DATA: " + Integer.valueOf(dateContent[0])+"/"+Integer.valueOf(dateContent[1])+"/"+Integer.valueOf(dateContent[2]));
                esercizi.add(new Esercizio(esercizio.getEmail(), esercizio.getBambino(), esercizio.getName(), esercizio.getTipo(), esercizio.getImmagine1(), esercizio.getImmagine2(),
                        esercizio.getAiuto(), esercizio.getSequenza(), Integer.valueOf(dateContent[0]), Integer.valueOf(dateContent[1]), Integer.valueOf(dateContent[2])));
            }

            for(int i = 0;i<esercizi.size();i++){
                Log.d(TAG, "ARRAY ESERCIZI: " + esercizi.get(i).getName() + " " + esercizi.get(i).getGiorno()+"/"+esercizi.get(i).getMese()+"/"+esercizi.get(i).getAnno());

            }
            if(esercizi.size()!=0 && eserciziList.size()!=0){
                sendTerapia.setVisibility(View.VISIBLE);
            }
            customAdapter.notifyDataSetChanged();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        customAdapter.notifyDataSetChanged();
    }
}