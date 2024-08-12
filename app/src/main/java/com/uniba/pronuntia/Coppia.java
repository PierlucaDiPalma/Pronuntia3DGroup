package com.uniba.pronuntia;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

public class Coppia extends AppCompatActivity {

    private Button imageLoad1, imageLoad2, calendario, crea;
    private ImageView immagine1, immagine2;
    private EditText titoloEdit, soluzioneEdit;
    private TextView data;
    private DBHelper db = new DBHelper(this);

    private int day, month, year;

    private static final int REQUEST_CODE_IMAGE1 = 1;
    private static final int REQUEST_CODE_IMAGE2 = 2;

    ActivityResultLauncher<Intent> resultLauncher;
    private ActivityResultLauncher<Intent> loadImage1Launcher;
    private ActivityResultLauncher<Intent> loadImage2Launcher;

    private Esercizio esercizio = new Esercizio(null, null, "Coppia", new byte[0], new byte[0],  null, null, 0, 0,0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_coppia);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.coppia), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageLoad1 = findViewById(R.id.caricaImg1);
        imageLoad2 = findViewById(R.id.caricaImg2);
        immagine1 = findViewById(R.id.image1);
        immagine2 = findViewById(R.id.image2);

        crea = findViewById(R.id.createCoup);
        calendario = findViewById(R.id.calendar);
        data = findViewById(R.id.date);

        titoloEdit = findViewById(R.id.titolo);
        soluzioneEdit = findViewById(R.id.soluzione);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        esercizio.setEmail(email);

        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Coppia.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yearDP, int monthDP, int dayOfMonthDP) {
                        monthDP = monthDP+1;
                        data.setText(dayOfMonthDP + " " + (monthDP) + " " + yearDP );

                    }
                }, day, month, year);

                esercizio.setGiorno(day);
                esercizio.setMese(month);
                esercizio.setAnno(year);

                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis()-1000);
                datePickerDialog.show();
            }
        });
/*
        loadImage1Launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        esercizio.setImmagine1(handleImageResult(result.getData(), immagine1, imageLoad1));
                    }
                });

*/
        imageLoad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage1();
            }
        });
/*
        loadImage2Launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        esercizio.setImmagine2(handleImageResult(result.getData(), immagine2, imageLoad2));
                    }
                });*/

        imageLoad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage2();
            }
        });


        crea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titolo = titoloEdit.getText().toString().trim();
                String soluzione = soluzioneEdit.getText().toString().trim();

                try {
                    titolo = URLEncoder.encode(titolo, "UTF-8");


                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }


                esercizio.setName(titolo);
                esercizio.setAiuto(soluzione);


                if(!titolo.isEmpty() || !soluzione.isEmpty() || immagine1.getDrawable()!=null || immagine2.getDrawable()!=null){

                    if(db.addCoppia(esercizio) && db.addExercises(esercizio)){


                        Toast.makeText(Coppia.this, "Esercizio creato", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Coppia.this, CreazioneEsercizi.class));
                    }else{
                        Toast.makeText(Coppia.this, "Qualcosa è andato storto", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void loadImage1(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery1.launch(intent);
    }

    ActivityResultLauncher<Intent> gallery1 =  registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == AppCompatActivity.RESULT_OK){
                        Uri imageUri = result.getData().getData();
                        immagine1.setVisibility(View.VISIBLE);
                        immagine1.setImageURI(imageUri);
                        imageLoad1.setText("Cambia immagine");

                        InputStream inputStream = null;
                        try {
                            inputStream = getContentResolver().openInputStream(imageUri);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


                        ByteArrayOutputStream outputStreamCorrect = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStreamCorrect);
                        byte[] imageBytes = outputStreamCorrect.toByteArray();


                        esercizio.setImmagine1(imageBytes);

                    }
                }
            }
    );


    private void loadImage2(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery2.launch(intent);
    }

    ActivityResultLauncher<Intent> gallery2 =  registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == AppCompatActivity.RESULT_OK){
                        Uri imageUri = result.getData().getData();
                        immagine2.setVisibility(View.VISIBLE);
                        immagine2.setImageURI(imageUri);
                        imageLoad2.setText("Cambia immagine");

                        InputStream inputStream = null;
                        try {
                            inputStream = getContentResolver().openInputStream(imageUri);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


                        ByteArrayOutputStream outputStreamCorrect = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStreamCorrect);
                        byte[] imageBytes = outputStreamCorrect.toByteArray();


                        esercizio.setImmagine2(imageBytes);

                    }
                }
            }
    );



/*
    private void registerImageResultCorrect(String email){
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try{
                        Uri imageUri = result.getData().getData();

                        try {

                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


                            immagine1.setVisibility(View.VISIBLE);
                            immagine1.setImageURI(imageUri);
                            imageLoad1.setText("Cambia immagine");


                            // Converti Bitmap in byte[]
                            ByteArrayOutputStream outputStreamCorrect = new ByteArrayOutputStream();

                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStreamCorrect);

                            byte[] imageBytes_correct = outputStreamCorrect.toByteArray();


                            esercizio.setImmagine1(imageBytes_correct);



                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(Coppia.this, "Errore nel caricamento dell'immagine", Toast.LENGTH_SHORT).show();
                        }

                        immagine1.setVisibility(View.VISIBLE);
                        immagine1.setImageURI(imageUri);
                        imageLoad1.setText("Cambia immagine");

                    }catch (Exception e){
                        Toast.makeText(Coppia.this, "Nessuna immagine caricata", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        );
    }

    private void registerImageResultIncorrect(String email){
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try{
                            Uri imageUri = result.getData().getData();

                            try {

                                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


                                immagine2.setVisibility(View.VISIBLE);
                                immagine2.setImageURI(imageUri);
                                imageLoad2.setText("Cambia immagine");


                                // Converti Bitmap in byte[]
                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                byte[] imageBytes = outputStream.toByteArray();

                                esercizio.setImmagine2(imageBytes);



                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(Coppia.this, "Errore nel caricamento dell'immagine", Toast.LENGTH_SHORT).show();
                            }

                            immagine2.setVisibility(View.VISIBLE);
                            immagine2.setImageURI(imageUri);
                            imageLoad2.setText("Cambia immagine");

                        }catch (Exception e){
                            Toast.makeText(Coppia.this, "Nessuna immagine caricata", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void loadImage(){

        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }
*/

}