package com.uniba.pronuntia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeGenitore extends AppCompatActivity {
    private DBHelper dbHelper;
    private ImageButton settings;
    private ImageView profile;
    private Button risultati;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_genitore);


        dbHelper = new DBHelper(this);
        Intent intent = getIntent();
        profile = findViewById(R.id.profile_image);

        String emailLogopedista = intent.getStringExtra("logopedista_email");
        email = intent.getStringExtra("genitore");

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        settings = findViewById(R.id.Impostazioni);
        ImageView backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(v -> {

            onBackPressed();
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        GridView gridView = findViewById(R.id.gridView);
        ImageAdapter adapter = new ImageAdapter(this);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Azione per la prima immagine
                        Intent intent = new Intent(HomeGenitore.this, CorrezioneRisultati.class);
                        intent.putExtra("genitore", email);
                        startActivity(intent);
                        // Inserisci qui il codice per la prima azione
                        break;
                    case 1:

                        intent=new Intent(HomeGenitore.this,Impostazioni.class);
                        startActivity(intent);


                        break;
                    case 2:
                        intent=new Intent(HomeGenitore.this,AppuntamentiGenitore.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(HomeGenitore.this, SceltaLogopedista.class);
                        intent.putExtra("nomeActivity","richiediTerapia");
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });


        /*BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem richiediTerapiaItem = menu.findItem(R.id.RichiediTerapia);

        richiediTerapiaItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                Intent intent = new Intent(HomeGenitore.this, SceltaLogopedista.class);
                intent.putExtra("nomeActivity","richiediTerapia");
                startActivity(intent);
                return true;
            }
        });*/

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(HomeGenitore.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_genitore, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {

                    if (item.getItemId() == R.id.logout) {
                        new AlertDialog.Builder(HomeGenitore.this)
                                .setTitle("Vuoi uscire?")
                                .setPositiveButton("Sì", (dialog, which) -> {
                                    startActivity(new Intent(HomeGenitore.this, Login.class));
                                    finish();
                                })
                                .setNegativeButton("No", (dialog, which) -> {
                                    dialog.dismiss();
                                })
                                .show();
                        return true;
                    }
                    return false;
                });
                popupMenu.show();
            }
        });
    }
        /*risultati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeGenitore.this, CorrezioneRisultati.class);
                intent.putExtra("genitore", email);

                startActivity(intent);
            }
        });
*/

        /*MenuItem richiediAppuntamento=menu.findItem(R.id.RichiediAppuntamento);

        richiediAppuntamento.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
             Intent intent=new Intent(HomeGenitore.this,AppuntamentiGenitore.class);


             startActivity(intent);
             return true;
            }
        });



    settings.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(HomeGenitore.this,Impostazioni.class);
            startActivity(intent);
        }
    });
 */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}