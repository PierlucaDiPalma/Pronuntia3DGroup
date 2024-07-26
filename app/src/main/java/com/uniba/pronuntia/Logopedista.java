package com.uniba.pronuntia;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Logopedista extends AppCompatActivity {

    private static final String TAG = "Logopedista";

    DBHelper db;
    ArrayList<Utente> users;
    CustomAdapter customAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logopedista);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.logopedista), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d(TAG, "Entrato");
        recyclerView = findViewById(R.id.recyclerView);
        db = new DBHelper(Logopedista.this);
        users = new ArrayList<>();

        users = db.readData();
        Log.d(TAG, "Connesso");

        Log.d(TAG, "Raccolto");
        customAdapter = new CustomAdapter(Logopedista.this, users);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Logopedista.this));

    }
}