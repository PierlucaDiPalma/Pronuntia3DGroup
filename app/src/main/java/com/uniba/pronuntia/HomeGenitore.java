package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeGenitore extends AppCompatActivity {
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_genitore);

        // Inizializza DBHelper
        dbHelper = new DBHelper(this);

        // Recupero del layout che conterrà i pulsanti
        LinearLayout logopedistiLayout = findViewById(R.id.logopedisti_layout);

        // Recupero dei logopedisti dal database
        ArrayList<Utente> logopedisti = dbHelper.getLogopedisti();

        // Verifica se la lista di logopedisti non è vuota
        if (logopedisti != null && !logopedisti.isEmpty()) {
            // Creazione di un pulsante per ogni logopedista
            for (Utente logopedista : logopedisti) {
                Button logopedistaButton = new Button(this);
                logopedistaButton.setText(logopedista.getNome() + " " + logopedista.getCognome());

                // Impostazione di un listener per gestire il click
                logopedistaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Apri la schermata della richiesta terapia passando l'email del logopedista
                        Intent intent = new Intent(HomeGenitore.this, RichiestaTerapia.class);
                        intent.putExtra("EMAIL_LOGOPEDISTA", logopedista.getEmail());
                        startActivity(intent);
                    }
                });

                // Aggiunta del pulsante al layout
                logopedistiLayout.addView(logopedistaButton);
            }
        } else {
            // Gestisci il caso in cui non ci siano logopedisti disponibili
            // Puoi mostrare un messaggio o fare altre operazioni appropriate
        }

        // Configura la Toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // Configura il pulsante "indietro"
        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            // Torna all'Activity precedente quando si clicca sul pulsante "indietro"
            onBackPressed();
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Configura la BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem richiediTerapiaItem = menu.findItem(R.id.RichiediTerapia);

        richiediTerapiaItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                // Qui puoi avviare RichiestaTerapia senza passare l'email, se non necessario
                Intent intent = new Intent(HomeGenitore.this, RichiestaTerapia.class);
                startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Torna all'Activity precedente
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}