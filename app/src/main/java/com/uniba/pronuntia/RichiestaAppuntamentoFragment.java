package com.uniba.pronuntia;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.getIntent;

import static androidx.core.app.ActivityCompat.recreate;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RichiestaAppuntamentoFragment extends Fragment {
    private CalendarView calendario;
    private String emailLogopedista;
    private ArrayList<String> orari;
    private DBHelper db;
    private String emailGenitore;

    public static RichiestaAppuntamentoFragment newInstance(String email) {
        RichiestaAppuntamentoFragment fragment = new RichiestaAppuntamentoFragment();
        Bundle args = new Bundle();
        args.putString("EMAIL_LOGOPEDISTA", email);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.tab_richiesta_appuntamento,container,false);

if(getArguments()!=null){
    emailLogopedista=getArguments().getString("EMAIL_LOGOPEDISTA");
}



        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        emailGenitore = sharedPreferences.getString("userEmail", null);

        db = new DBHelper(getActivity());
        calendario = view.findViewById(R.id.calendario);
        LinearLayout layout = view.findViewById(R.id.linearPulsantiOrari);


        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int anno, int mese, int giorno) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                calendar.set(anno, mese, giorno);
                Date data = calendar.getTime();

                String dataFormattata = sdf.format(data);
                orari = db.recuperaOrariDisponibili(emailLogopedista, dataFormattata);
                layout.removeAllViews();
                for (String ora : orari) {
                    Button button = new Button(getActivity());
                    button.setText(ora);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confermaAlertDialog(ora, dataFormattata);
                        }
                    });


                    layout.addView(button);

                }


            }
        });

return view;
    }



    private void confermaAlertDialog(String ora,String data){

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Conferma appuntamento");
        builder.setMessage("Sei sicuro di voler confermare l'appuntamento alle "+ ora +"?");


        builder.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                db.SetBooked(emailLogopedista,data,ora,emailGenitore);

                Toast.makeText(getActivity().getApplicationContext(), "Appuntamento richiesto", Toast.LENGTH_SHORT).show();

                RichiestaAppuntamentoFragment richiestaAppuntamento = RichiestaAppuntamentoFragment.newInstance(emailLogopedista);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.FragmentContainer, richiestaAppuntamento);
                transaction.commit();

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
