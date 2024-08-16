package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeUtente extends AppCompatActivity {




@Override
protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_homeutente);

    Button genitoreBTN=findViewById(R.id.pulsanteGenitore);

    genitoreBTN.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent=new Intent(HomeUtente.this,HomeGenitore.class);
            startActivity(intent);


        }
    });


}





}



