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
import java.util.Calendar;

public class DenominazioneImmagini extends AppCompatActivity {

    private EditText titoloEdit, aiutoEdit;
    private TextView data;
    private ImageView immagine;
    private Button crea, calendario, imgLoad;
    private DBHelper db;
    private String titolo, aiuto;
    private int day, month, year;
    private Esercizio esercizio = new Esercizio(null, null, "Denominazione", new byte[0], null, null, null, 0, 0, 0);

    ActivityResultLauncher<Intent> resultLauncher;

    private SeekBar audioBar;
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
        aiutoEdit = findViewById(R.id.aiuto);

        imgLoad = findViewById(R.id.caricaImg);
        immagine = findViewById(R.id.imageEx);


        Intent intent = getIntent();
        String email = intent.getStringExtra("email");


        esercizio.setEmail(email);

        data = findViewById(R.id.date);
        db = new DBHelper(DenominazioneImmagini.this);


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
                        monthDP = monthDP + 1;
                        data.setText(dayOfMonthDP + " " + (monthDP) + " " + yearDP);

                    }
                }, day, month, year);

                esercizio.setGiorno(day);
                esercizio.setMese(month);
                esercizio.setAnno(year);

                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis() - 1000);
                datePickerDialog.show();
            }
        });

        registerImageResult(email);

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
                aiuto = aiutoEdit.getText().toString().trim();
                try {
                    titolo = URLEncoder.encode(titolo, "UTF-8");
                    aiuto = URLEncoder.encode(aiuto, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }


                if (!titolo.isEmpty() || !aiuto.isEmpty() || immagine.getDrawable() != null) {

                    esercizio.setName(titolo);
                    esercizio.setAiuto(aiuto);

                    Log.d(TAG, esercizio.getName());
                    Log.d(TAG, esercizio.getTipo());

                    if (db.addDenominazione(esercizio) && db.addExercises(esercizio)) {
                        Log.d(TAG, "onClick: Scrittura");
                        Toast.makeText(DenominazioneImmagini.this, "Esercizio creato", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DenominazioneImmagini.this, CreazioneEsercizi.class));
                    } else {
                        Toast.makeText(DenominazioneImmagini.this, "Qualcosa è andato storto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DenominazioneImmagini.this, "Inserire tutti gli elementi", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

/*
    private void loadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery.launch(intent);
    }

    ActivityResultLauncher<Intent> gallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                        Uri imageUri = result.getData().getData();

                        imgLoad.setVisibility(View.VISIBLE);
                        imgLoad.setText("Cambia immagine");
                        immagine.setImageURI(imageUri);


                        InputStream inputStream = null;
                        try {
                            inputStream = getContentResolver().openInputStream(imageUri);

                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                        immagine.setImageBitmap(bitmap);

                        ByteArrayOutputStream outputStreamCorrect = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStreamCorrect);
                        byte[] imageBytes = outputStreamCorrect.toByteArray();


                        esercizio.setImmagine1(imageBytes);

                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
    );*/


    private void registerImageResult(String email) {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            Uri imageUri = result.getData().getData();

                            try {

                                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                immagine.setVisibility(View.VISIBLE);
                                immagine.setImageURI(imageUri);
                                imgLoad.setText("Cambia immagine");

                                // Converti Bitmap in byte[]
                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                byte[] imageBytes = outputStream.toByteArray();

                                esercizio.setImmagine1(imageBytes);


                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(DenominazioneImmagini.this, "Errore nel caricamento dell'immagine", Toast.LENGTH_SHORT).show();
                            }
                            immagine.setVisibility(View.VISIBLE);
                            immagine.setImageURI(imageUri);
                            imgLoad.setText("Cambia immagine");
                        } catch (Exception e) {
                            Toast.makeText(DenominazioneImmagini.this, "Nessuna immagine caricata", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void loadImage() {

        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }
}