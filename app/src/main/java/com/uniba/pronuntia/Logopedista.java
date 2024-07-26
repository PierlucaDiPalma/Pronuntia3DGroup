package com.uniba.pronuntia;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.Cursor;
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
/*
    void storeArray(){
        Log.d(TAG, "storeArray: chiamato");
        Cursor cursor = db.readData();
        Log.d(TAG, "storeArray: cursore ottenuto");

        while(cursor.moveToNext()) {
            Log.d(TAG, cursor.getString(0));
            Log.d(TAG, cursor.getString(1));
            Log.d(TAG, cursor.getString(2));
        }

        Log.d(TAG, "Store entrato");
        while(cursor.moveToNext()){
            String email = cursor.getString(0);
            String nome = cursor.getString(1);
            String cognome = cursor.getString(2);
            String telefono = cursor.getString(3);
            String password = cursor.getString(4);

            boolean isLogopedista = false;

            if (cursor.getInt(5) == 1) isLogopedista = true;

            users.add(new Utente(email, nome, cognome, telefono, password, isLogopedista));
            Log.d(TAG, "storeArray: ");
        }


    }*/
}