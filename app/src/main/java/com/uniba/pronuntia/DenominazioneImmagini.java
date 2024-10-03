package com.uniba.pronuntia;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

public class DenominazioneImmagini extends AppCompatActivity {

    private EditText titoloEdit, aiutoEdit;
    private TextView  dataText;
    private ImageView immagine;
    private Button crea, calendario, imgLoad;
    private DBHelper db;
    private String titolo, aiuto;
    private String email;
    private String data;
    private String bambino;
    private int day, month, year;
    private int durata;
    private Esercizio esercizio = new Esercizio(null, null, null, "Denominazione", null, null, null, null, 0, 0, 0);


    ActivityResultLauncher<Intent> resultLauncher;

    private SeekBar audioBar;

    private Uri imagePath;
    private Bitmap image;

    private static final int PICK_IMAGE_REQUEST = 99;
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
        //calendario = findViewById(R.id.calendar);
        aiutoEdit = findViewById(R.id.aiuto);

        imgLoad = findViewById(R.id.caricaImg);
        immagine = findViewById(R.id.imageEx);


        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        durata = intent.getIntExtra("durata", 1);
        data = intent.getStringExtra("data");
        bambino = intent.getStringExtra("bambino");

        dataText = findViewById(R.id.date);
        db = new DBHelper(DenominazioneImmagini.this);

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

        Log.d(TAG, "onCreate: " + email);


        immagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseImage();
            }
        });
        //registerImageResult(email);
        Log.d(TAG, "onPositiveButtonClick: " + day + " " + month + " " + year);


        crea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int added = 0;

                titolo = titoloEdit.getText().toString().trim();
                aiuto = aiutoEdit.getText().toString().trim();
                try {
                    titolo = URLEncoder.encode(titolo, "UTF-8");
                    aiuto = URLEncoder.encode(aiuto, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }


                if (!titolo.isEmpty() || !aiuto.isEmpty() || titolo!= null || aiuto != null || immagine.getDrawable() != null) {

                    for(int i = 0; i<dateList.size();i++){

                        esercizio.setEmail(email);
                        esercizio.setBambino(bambino);
                        esercizio.setName(titolo);
                        esercizio.setAiuto(aiuto);

                        String[] dateContent = dateList.get(i).split("/");
                        Log.d(TAG, "da settare: " + dateContent[0] + " " + dateContent[1] + " " + dateContent[2]);

                        Log.d(TAG, "data esercizio: " + esercizio.getName()+ " "+esercizio.getGiorno() + " " + esercizio.getMese() + " " + esercizio.getAnno());

                        esercizio.setGiorno(Integer.valueOf(dateContent[0]));
                        esercizio.setMese(Integer.valueOf(dateContent[1]));
                        esercizio.setAnno(Integer.valueOf(dateContent[2]));

                        Log.d(TAG, "data esercizio settata: " + esercizio.getName()+ " "+esercizio.getGiorno() + " " + esercizio.getMese() + " " + esercizio.getAnno());

                        if (db.addDenominazione(esercizio)) {

                            Log.d(TAG, "onClick: Scrittura");
                            added++;
                        }
                    }

                    if(added==dateList.size() && db.addExercises(esercizio)){
                        Toast.makeText(DenominazioneImmagini.this, "Esercizio creato", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DenominazioneImmagini.this, CreazioneEsercizi.class);
                        intent.putExtra("email", email);
                        intent.putExtra("bambino", bambino);
                        intent.putExtra("data", data);
                        intent.putExtra("source", TAG);
                        startActivity(intent);

                    }else {

                        Toast.makeText(DenominazioneImmagini.this, "Qualcosa è andato storto", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(DenominazioneImmagini.this, "Inserire tutti gli elementi", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void choseImage(){
        try{

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{

            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null ){

                imagePath = data.getData();
                image = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                immagine.setImageBitmap(image);
                imgLoad.setText("Cambia immagine");

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                esercizio.setImmagine1(imageBytes);
            }

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}