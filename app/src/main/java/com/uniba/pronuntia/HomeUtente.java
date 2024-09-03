package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeUtente extends AppCompatActivity {

    private Button genitoreBTN, bambinoBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeutente);

        genitoreBTN = findViewById(R.id.pulsanteGenitore);
        bambinoBTN = findViewById(R.id.pulsanteBambino);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        genitoreBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HomeUtente.this,HomeGenitore.class);
                startActivity(intent);


            }
        });

        bambinoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeUtente.this, HomeBambino.class);
                intent.putExtra("email", email);
                startActivity(intent);

            }
        });

    }

}



