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
import java.util.Calendar;

public class RipetizioneSequenza extends AppCompatActivity {

    private EditText titoloEdit, parola1Edit, parola2Edit, parola3Edit;
    private TextView data;
    private Button crea, calendario;
    private DBHelper db;
    private String titolo;
    private String email;
    private int day, month, year;
    private Esercizio esercizio = new Esercizio(null, null, "Sequenza", null, null,  null, null, 0, 0,0);

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
        calendario = findViewById(R.id.calendar);
        data = findViewById(R.id.date);
        db = new DBHelper(RipetizioneSequenza.this);


        Intent intent = getIntent();
        email = intent.getStringExtra("email");



        Log.d(TAG, "onCreate: " + titolo);










        Log.d(TAG, "onCreate: " + email);

        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker().build();


        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RipetizioneSequenza.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yearDP, int monthDP, int dayOfMonthDP) {
                        monthDP += 1;
                        data.setText(dayOfMonthDP + " " + (monthDP) + " " + yearDP );

                        esercizio.setGiorno(dayOfMonthDP);
                        esercizio.setMese(monthDP);
                        esercizio.setAnno(yearDP);
                    }
                }, day, month, year);




                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis()-1000);
                datePickerDialog.show();
            }

        });


        crea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isNull = false;

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
                    if(db.addExercises(esercizio) && db.addSequenza(esercizio)){
                            Log.d(TAG, "onClick: Scrittura");
                            Toast.makeText(RipetizioneSequenza.this, "Esercizio creato", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RipetizioneSequenza.this, CreazioneEsercizi.class));

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