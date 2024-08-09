package com.uniba.pronuntia;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageActivity extends AppCompatActivity {

    private TextView emailText;
    private ImageView image;
    private DBHelper db;
    private MediaPlayer mediaPlayer;
    private ProgressBar progressBar;
    private Handler handler = new Handler();
    private Runnable runnable;

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.imageTry), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        emailText = findViewById(R.id.email);
        image = findViewById(R.id.imageView);

        db = new DBHelper(this);



        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        emailText.setText(email);
        image.setImageBitmap(db.getAllImages(2));

        Button playButton = findViewById(R.id.playButton);
        progressBar = findViewById(R.id.progressBar);

        mediaPlayer = new MediaPlayer();

       playButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //Uri audioFileUri = Uri.fromFile(new File("/storage/emulated/0/Recordings/Voice Recorder/Voce 015.m4a"));

               try {
                   mediaPlayer.setDataSource("/storage/emulated/0/Recordings/Voice Recorder/Voce 015.m4a");
                   mediaPlayer.prepare();
                   mediaPlayer.start();
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }

           }
       });

        mediaPlayer.setOnCompletionListener(mp -> {
            progressBar.setProgress(0);
            handler.removeCallbacks(runnable);
        });


    }

    private void updateProgressBar() {
        progressBar.setMax(mediaPlayer.getDuration() / 1000);

        runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer.isPlaying()) {
                    progressBar.setProgress(mediaPlayer.getCurrentPosition() / 1000);
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    // Metodo per ottenere i dati audio dal database


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        handler.removeCallbacks(runnable);
    }
    private void playRecording(Uri audioFileUri) {
        if (audioFileUri != null) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(this, audioFileUri);
                mediaPlayer.prepare();
                mediaPlayer.start();

                progressBar.setVisibility(View.VISIBLE);
                progressBar.setMax(mediaPlayer.getDuration());
                updateProgressBar();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    progressBar.setVisibility(View.GONE);
                    progressBar.setProgress(0);
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    mediaPlayer.setDataSource("/storage/emulated/0/Recordings/Voice Recorder/Voce 015.m4a");
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Toast.makeText(this, "Permesso di lettura negato", Toast.LENGTH_SHORT).show();
            }
        }
    }
}