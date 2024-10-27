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


        dbHelper = new DBHelper(this);
        Intent intent=getIntent();
        String emailLogopedista=intent.getStringExtra("logopedista_email");


        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {

            onBackPressed();
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavigationView);
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
        });


        MenuItem richiediAppuntamento=menu.findItem(R.id.RichiediAppuntamento);

        richiediAppuntamento.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
             Intent intent=new Intent(HomeGenitore.this,AppuntamentiGenitore.class);


             startActivity(intent);
             return true;
            }
        });






    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}