package com.uniba.pronuntia;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DenominazioneImmagini extends AppCompatActivity {

    private EditText titoloEdit;
    private TextView data;
    private ImageView immagine;
    private Button crea, calendario, imgLoad;
    private DBHelper db;
    private String titolo;
    private int day, month, year;

    ActivityResultLauncher<Intent> resultLauncher;

    private static final String TAG = "DenominazioneImmagini";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_denominazione_immagini);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.denominazioneImmagini), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d(TAG, "onCreate: Entrato");
        titoloEdit = findViewById(R.id.titoloEsercizio);
        crea = findViewById(R.id.createDen);
        calendario = findViewById(R.id.calendar);

        imgLoad = findViewById(R.id.caricaImg);
        immagine = findViewById(R.id.imageEx);
        registerResult();

        data = findViewById(R.id.date);
        db = new DBHelper(DenominazioneImmagini.this);


        Intent intent = getIntent();
        String email = intent.getStringExtra("email");


        Log.d(TAG, "onCreate: " + email);

        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DenominazioneImmagini.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yearDP, int monthDP, int dayOfMonthDP) {
                        data.setText(dayOfMonthDP + " " + (monthDP+1) + " " + yearDP );
                        //data temporanea, sostituire con un altro oggetto
                        if(db.addData(dayOfMonthDP, monthDP+1, yearDP)){
                            Log.d(TAG, "onCreate: " + day + " " + month+1 + " " + year);
                            Toast.makeText(DenominazioneImmagini.this, "Data aggiunta", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(DenominazioneImmagini.this, "Data non aggiunta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, day, month, year);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis()-1000);
                datePickerDialog.show();
            }
        });

        imgLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage();
            }
        });

        Log.d(TAG, "onPositiveButtonClick: " + day + " " + month + " " + year);

        crea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                titolo = titoloEdit.getText().toString().trim();
                if(!titolo.isEmpty()){
                    try {
                        titolo = URLEncoder.encode(titolo, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    if(db.addExercises(new Esercizio(email, titolo, "Denominazione"))){
                        Log.d(TAG, "onClick: Scrittura");
                        Toast.makeText(DenominazioneImmagini.this, "Esercizio creato", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DenominazioneImmagini.this, CreazioneEsercizi.class));

                    }else{
                        Toast.makeText(DenominazioneImmagini.this, "Qualcosa è andato storto", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(DenominazioneImmagini.this, "Inserire il titolo", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try{
                            Uri imageUri = result.getData().getData();
                            immagine.setImageURI(imageUri);
                        }catch (Exception e){
                            Toast.makeText(DenominazioneImmagini.this, "Nessuna immagine caricata", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void loadImage(){

        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }
}