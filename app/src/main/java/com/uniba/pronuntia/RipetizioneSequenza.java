package com.uniba.pronuntia;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

public class RipetizioneSequenza extends AppCompatActivity {

    private EditText titoloEdit, parola1Edit, parola2Edit, parola3Edit;
    private TextView dataText;
    private Button crea, calendario;
    private DBHelper db;
    private String titolo;
    private String email;
    private String data;
    private String bambino;
    private int durata;
    private int day, month, year;
    private Esercizio esercizio = new Esercizio(null, null, null, "Sequenza", null, null,  null, null, 0, 0,0);

    private static final String TAG = "RipetzioneSequenza";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ripetizione_sequenza);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ripetzioneSequenza), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d(TAG, "onCreate: Entrato");
        titoloEdit = findViewById(R.id.titoloEsercizio2);
        parola1Edit = findViewById(R.id.parola1);
        parola2Edit = findViewById(R.id.parola2);
        parola3Edit = findViewById(R.id.parola3);

        crea = findViewById(R.id.createSeq);
        dataText = findViewById(R.id.date);
        db = new DBHelper(RipetizioneSequenza.this);


        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        email = intent.getStringExtra("email");
        durata = intent.getIntExtra("durata", 1);
        data = intent.getStringExtra("data");
        bambino = intent.getStringExtra("bambino");

        String[] dataSplitted = data.split(" ");
        day = Integer.valueOf(dataSplitted[0]);
        month = Integer.valueOf(dataSplitted[1])-1;
        year = Integer.valueOf(dataSplitted[2]);

        Calendar forWeeks = Calendar.getInstance();
        forWeeks.set(year, month, day);

        dataText.setText(day + " " + (month+1) + " " + year);

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

        }

        for(int i = 0;i<dateList.size();i++){
            Log.d(TAG, i+1 + " " + dateList.get(i));
        }

        Log.d(TAG, "onCreate: " + titolo);


        Log.d(TAG, "onCreate: " + email);

        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker().build();

        crea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isNull = false;

                int added = 0;

                titolo = titoloEdit.getText().toString().trim();
                String[] sequenza = new String[3];

                sequenza[0] = parola1Edit.getText().toString().trim();

                sequenza[1] = parola2Edit.getText().toString().trim();

                sequenza[2] = parola3Edit.getText().toString().trim();

                for(int i = 0;i<sequenza.length;i++){
                    if(sequenza[i].isEmpty() || sequenza[i].equals(null)){
                        isNull = true;
                        break;
                    }
                    Log.d(TAG, "onClick: " + sequenza[i]);
                }

                esercizio.setName(titolo);
                esercizio.setEmail(email);
                esercizio.setSequenza(sequenza);

                if(!titolo.isEmpty() || !isNull){
                    try {
                        titolo = URLEncoder.encode(titolo, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }

                    for(int i = 0; i<dateList.size();i++) {

                        esercizio.setEmail(email);
                        esercizio.setName(titolo);
                        esercizio.setBambino(bambino);


                        String[] dateContent = dateList.get(i).split("/");

                        esercizio.setGiorno(Integer.valueOf(dateContent[0]));
                        esercizio.setMese(Integer.valueOf(dateContent[1]));
                        esercizio.setAnno(Integer.valueOf(dateContent[2]));

                        if (db.addSequenza(esercizio)) {
                            added++;

                        }
                    }

                    if(added == dateList.size() && db.addExercises(esercizio)){
                            Log.d(TAG, "onClick: Scrittura");
                            Toast.makeText(RipetizioneSequenza.this, "Esercizio creato", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RipetizioneSequenza.this, CreazioneEsercizi.class);
                            intent.putExtra("email", email);
                            finish();


                    }else{
                        Toast.makeText(RipetizioneSequenza.this, "Qualcosa è andato storto", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(RipetizioneSequenza.this, "Inserire tutti gli elementi", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}