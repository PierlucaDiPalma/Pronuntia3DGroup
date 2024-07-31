package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.Calendar;

public class RipetizioneSequenza extends AppCompatActivity {

    private EditText titoloEdit;
    private TextView data;
    private Button crea, calendario;
    private DBHelper db;
    private String titolo;
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
        crea = findViewById(R.id.createSeq);
        calendario = findViewById(R.id.calendar);
        data = findViewById(R.id.date);
        db = new DBHelper(RipetizioneSequenza.this);


        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        Log.d(TAG, "onCreate: " + email);

        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker().build();



        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        data.setText("Data: " + materialDatePicker.getHeaderText());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis((Long) selection);

                        Log.d(TAG, "onPositiveButtonClick: ");

                    }
                });
            }

        });


        crea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                titolo = titoloEdit.getText().toString().trim();


                try {
                    titolo = URLEncoder.encode(titolo, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Esercizio esercizio = new Esercizio(email, titolo, "Denominazione");

                if(db.addExercises(esercizio)){
                    Log.d(TAG, "onClick: Scrittura");
                    Toast.makeText(RipetizioneSequenza.this, "Esercizio creato", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RipetizioneSequenza.this, CreazioneEsercizi.class));

                }else{
                    Toast.makeText(RipetizioneSequenza.this, "Qualcosa è andato storto", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}