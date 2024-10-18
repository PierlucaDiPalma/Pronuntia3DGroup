package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RichiestaAppuntamento extends AppCompatActivity {
private CalendarView calendario;
private String emailLogopedista;
private ArrayList<String> orari;
private DBHelper db;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.richiesta_appuntamento);



Intent intent=getIntent();
emailLogopedista=intent.getStringExtra("EMAIL_LOGOPEDISTA");
db=new DBHelper(this);
calendario=findViewById(R.id.calendario);
        LinearLayout layout=findViewById(R.id.linearPulsantiOrari);


calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int anno, int mese, int giorno) {
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd") ;
        Calendar calendar=Calendar.getInstance();
        calendar.set(anno,mese,giorno);
Date data=calendar.getTime();

String dataFormattata=sdf.format(data);
orari=db.recuperaOrariDisponibili(emailLogopedista,dataFormattata);
        layout.removeAllViews();
for(String ora:orari){
    Button button=new Button(RichiestaAppuntamento.this);
    button.setText(ora);

    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Logica da eseguire quando si clicca sul pulsante
            Log.d("Pulsante", "Orario selezionato: " + ora);
        }
    });


layout.addView(button);

}



    }
});





    }





}
