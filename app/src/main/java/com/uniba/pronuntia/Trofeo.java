package com.uniba.pronuntia;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Trofeo extends AppCompatActivity {

    private Button continua;
    private TextView nextPointText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trofeo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.premio), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        continua = findViewById(R.id.continua);
        nextPointText = findViewById(R.id.message);

        int numCorretti = getIntent().getIntExtra("soglia", 0);

        nextPointText.setText("Prossimo premio tra " + (numCorretti+3) + " esercizi");

        continua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}