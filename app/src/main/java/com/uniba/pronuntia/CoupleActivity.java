package com.uniba.pronuntia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CoupleActivity extends AppCompatActivity {

    private ImageView immagine1, immagine2;
    private TextView contenuto, emailText;
    private DBHelper db;
    private String id;

    private static final String TAG = "CoupleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_couple);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.couple), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        immagine1 = findViewById(R.id.img1);
        immagine2 = findViewById(R.id.img2);
        contenuto = findViewById(R.id.contenuto);
        emailText = findViewById(R.id.emailText);

        db = new DBHelper(CoupleActivity.this);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        emailText.setText(email);


        //id = idEdit.getText().toString().trim();
        Esercizio esercizio = db.getCoppia(1);

        byte[] image = esercizio.getImmagine1();
        Bitmap imageCorrect = BitmapFactory.decodeByteArray(image, 0, image.length);
        immagine1.setImageBitmap(imageCorrect);

        image = esercizio.getImmagine2();
        Bitmap imageIncorrect = BitmapFactory.decodeByteArray(image, 0, image.length);
        immagine2.setImageBitmap(imageIncorrect);


        Log.d(TAG, "onCreate: "+ String.valueOf(esercizio.getImmagine1() != null));
        Log.d(TAG, "onCreate: "+ String.valueOf(esercizio.getImmagine1()));


        contenuto.setText(esercizio.getName());
    }
}