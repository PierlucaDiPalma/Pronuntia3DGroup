package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeGenitore extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_genitore);

        BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem richiediTerapiaItem = menu.findItem(R.id.RichiediTerapia);
richiediTerapiaItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

    @Override
    public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
       Intent intent=new Intent(HomeGenitore.this,RichiestaTerapia.class);
       startActivity(intent);

        return true;
    }
});


    }




    }



