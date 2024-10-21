package com.uniba.pronuntia;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
private String emailGenitore;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.richiesta_appuntamento);


        SharedPreferences sharedPreferences=getSharedPreferences("UserPrefs", MODE_PRIVATE);
         emailGenitore=sharedPreferences.getString("userEmail",null);
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
          confermaAlertDialog(ora,dataFormattata);
        }
    });


layout.addView(button);

}



    }
});





    }



    private void confermaAlertDialog(String ora,String data){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Conferma appuntamento");
        builder.setMessage("Sei sicuro di voler confermare l'appuntamento alle "+ ora +"?");


        builder.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                db.SetBooked(emailLogopedista,data,ora,emailGenitore);
                Toast.makeText(getApplicationContext(), "Appuntamento richiesto", Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
builder.show();
    }




}
