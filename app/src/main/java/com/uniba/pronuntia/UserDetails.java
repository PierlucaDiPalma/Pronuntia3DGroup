package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.userDetails), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView nomeText = (TextView) findViewById(R.id.nome);
        TextView cognomeText = (TextView) findViewById(R.id.cognome);
        TextView emailText = (TextView) findViewById(R.id.email);
        TextView telefonoText = (TextView) findViewById(R.id.telefono);

        Intent intent = getIntent();

        nomeText.setText(intent.getStringExtra("nome"));
        cognomeText.setText(intent.getStringExtra("cognome"));
        emailText.setText(intent.getStringExtra("email"));
        telefonoText.setText(intent.getStringExtra("telefono"));


    }

}