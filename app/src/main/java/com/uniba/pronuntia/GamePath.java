package com.uniba.pronuntia;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GamePath extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LevelAdapter customAdapter;
    private DBHelper db;
    private ArrayList<Esercizio> esercizi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_path);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.levelView);
        db = new DBHelper(GamePath.this);

        esercizi = db.getCoppia("paoloneri@gmail.com");
        esercizi.addAll(db.getSequenza("paoloneri@gmail.com"));
        esercizi.addAll(db.getDenominazione("paoloneri@gmail.com"));

        customAdapter = new LevelAdapter(GamePath.this, esercizi);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(GamePath.this));


    }




}