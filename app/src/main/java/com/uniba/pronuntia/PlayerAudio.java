package com.uniba.pronuntia;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class PlayerAudio extends AppCompatActivity {

    private Button playButton;
    private ProgressBar progressBar;
    private MediaPlayer mediaPlayer;
    private DBHelper db;
    private EditText idText;
    private String filePath;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player_audio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DBHelper(this);
        playButton = findViewById(R.id.playButton);
        progressBar = findViewById(R.id.progressBar);
        idText = findViewById(R.id.idText);
        progressBar.setVisibility(View.INVISIBLE);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = Integer.valueOf(idText.getText().toString());
                filePath = db.getAudio(id);

                if(filePath!=null){
                    playAudio(filePath);
                }else{
                    Toast.makeText(PlayerAudio.this, "Audio inesistente", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void playAudio(String filePath) {
        if (filePath != null) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(this, Uri.parse(filePath));
                mediaPlayer.prepare();
                //progressBar.setVisibility(View.VISIBLE);
                mediaPlayer.start();
                //mediaPlayer.setOnCompletionListener(mp -> progressBar.setVisibility(View.INVISIBLE));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Nessun audio trovato", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}