package com.uniba.pronuntia;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
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
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

public class Coppia extends AppCompatActivity {

    private Button imageLoad1, imageLoad2, calendario, crea;
    private ImageView immagine1, immagine2;
    private EditText titoloEdit, soluzioneEdit;
    private TextView dataText;
    private DBHelper db = new DBHelper(this);

    private int day, month, year;

    private static final int PICK_IMAGE_REQUEST_1 = 1;
    private static final int PICK_IMAGE_REQUEST_2 = 2;

    private Uri imagePath;
    private Bitmap image;



    ActivityResultLauncher<Intent> resultLauncher;
    private ActivityResultLauncher<Intent> loadImage1Launcher;
    private ActivityResultLauncher<Intent> loadImage2Launcher;

    private String titolo;
    private String soluzione;
    private String email;
    private String data;
    private String bambino;
    private int durata;
    private Esercizio esercizio = new Esercizio(null, null, null, "Coppia", null, null,  null, null, 0, 0,0);

    private final static String TAG = "Coppia";

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
        dataText = findViewById(R.id.date);

        titoloEdit = findViewById(R.id.titolo);
        soluzioneEdit = findViewById(R.id.soluzione);

        Intent intent = getIntent();
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


            int currentDay = forWeeks.get(Calendar.DAY_OF_MONTH);
            int currentMonth = forWeeks.get(Calendar.MONTH) + 1;
            int currentYear = forWeeks.get(Calendar.YEAR);


            String currentDate = currentDay + "/" + currentMonth + "/" + currentYear;
            dateList.add(currentDate);


            forWeeks.add(Calendar.DAY_OF_YEAR, 1);

        }

        for(int i = 0;i<dateList.size();i++){
            Log.d(TAG, i+1 + " " + dateList.get(i));
        }

        immagine1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseImage(PICK_IMAGE_REQUEST_1);
            }
        });

        imageLoad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseImage(PICK_IMAGE_REQUEST_1);
            }
        });


        immagine2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseImage(PICK_IMAGE_REQUEST_2);
            }
        });

        imageLoad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseImage(PICK_IMAGE_REQUEST_2);
            }
        });


        crea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int added = 0;
                titolo = titoloEdit.getText().toString().trim();
                soluzione = soluzioneEdit.getText().toString().trim();


                if(!titolo.isEmpty() || !soluzione.isEmpty() || immagine1.getDrawable()!=null || immagine2.getDrawable()!=null){
                    for(int i = 0; i<dateList.size();i++){

                        esercizio.setEmail(email);
                        esercizio.setBambino(bambino);
                        esercizio.setName(titolo);
                        esercizio.setAiuto(soluzione);

                        String[] dateContent = dateList.get(i).split("/");
                  }
                    Intent intent = new Intent();
                    intent.putExtra("Esercizio", esercizio);
                    intent.putExtra("source", TAG);
                    setResult(1, intent);
                    finish();


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
                        String path = getRealPathFromURI(imageUri);

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
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStreamCorrect);
                        byte[] imageBytes = outputStreamCorrect.toByteArray();


                        esercizio.setImmagine1(path);

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
                        String path = getRealPathFromURI(imageUri);

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
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, outputStreamCorrect);
                        byte[] imageBytes = outputStreamCorrect.toByteArray();


                        esercizio.setImmagine2(path);

                    }
                }
            }
    );

    private void choseImage(int PICK_IMAGE_REQUEST){
        try{

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{

            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_OK && data != null && data.getData() != null ) {
                if (requestCode == PICK_IMAGE_REQUEST_1) {

                    imagePath = data.getData();
                    String path = getRealPathFromURI(imagePath);

                    final int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    getContentResolver().takePersistableUriPermission(imagePath, takeFlags);

                    image = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                    immagine1.setImageBitmap(image);
                    imageLoad1.setText("Cambia immagine");
                    immagine1.setBackground(null);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();

                    esercizio.setImmagine1(path);

                }else if(requestCode == PICK_IMAGE_REQUEST_2){
                    imagePath = data.getData();
                    String path = getRealPathFromURI(imagePath);

                    final int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    getContentResolver().takePersistableUriPermission(imagePath, takeFlags);

                    image = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                    immagine2.setImageBitmap(image);
                    imageLoad2.setText("Cambia immagine");
                    immagine2.setBackground(null);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();

                    esercizio.setImmagine2(path);
                }
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }
}