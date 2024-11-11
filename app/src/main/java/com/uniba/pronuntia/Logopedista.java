package com.uniba.pronuntia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Logopedista extends AppCompatActivity {

    private static final String TAG = "Logopedista";

    DBHelper db;
    ArrayList<Utente> users;
    ArrayList<RichiestaTerapia> richieste;
    ListView listView;
    ImageView profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logopedista);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.logopedista), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    Intent intent=getIntent();
    String emailLogopedista=intent.getStringExtra("logopedista_email");
    BottomNavigationView bnv =findViewById(R.id.BottomNavigationView);
    Menu menu=bnv.getMenu();
    MenuItem menuItem= menu.findItem(R.id.Appuntamento);

    profile = findViewById(R.id.profile_image);
    profile.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PopupMenu popupMenu = new PopupMenu(Logopedista.this, view);
            popupMenu.getMenuInflater().inflate(R.menu.bottom_nav_bar_logo, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {

                if(item.getItemId() == R.id.Appuntamento){
                    Intent intent=new Intent(Logopedista.this, AppuntamentiLogopedista.class);
                    intent.putExtra("logopedista_email",emailLogopedista);
                    startActivity(intent);

                }else if (item.getItemId() == R.id.logout) {
                    new AlertDialog.Builder(Logopedista.this)
                            .setTitle("Vuoi uscire?")
                            .setPositiveButton("Sì", (dialog, which) -> {
                                startActivity(new Intent(Logopedista.this, Login.class));
                                finish();
                            })
                            .setNegativeButton("No", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();return true;
                }
                return false;
            });
            popupMenu.show();
        }
    });
    /*logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(Logopedista.this)
                    .setTitle("Vuoi uscire?")
                    .setPositiveButton("Sì", (dialog, which) -> {
                        startActivity(new Intent(Logopedista.this, Login.class));
                        finish();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        }
    });*/

    menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

            Intent intent=new Intent(Logopedista.this, AppuntamentiLogopedista.class);
            intent.putExtra("logopedista_email",emailLogopedista);
            startActivity(intent);

            return true;
        }
    });




    db = new DBHelper(Logopedista.this);
    users = new ArrayList<>();
    richieste=db.getTerapie(emailLogopedista);


        users = db.readData();


        listView = findViewById(R.id.requests_list);
        if (listView == null) {

            return;
        }

        if (richieste.isEmpty()) {
            Toast.makeText(this, "Nessuna richiesta di terapia trovata.", Toast.LENGTH_SHORT).show();
        } else {

            ArrayAdapter<RichiestaTerapia> adapter = new ArrayAdapter<RichiestaTerapia>(this, R.layout.item_richiesta, R.id.nomeBambino, richieste) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        LayoutInflater inflater = LayoutInflater.from(getContext());
                        convertView = inflater.inflate(R.layout.item_richiesta, parent, false);
                    }

                    RichiestaTerapia richiesta = getItem(position);

                    TextView nomeBambino = convertView.findViewById(R.id.nomeBambino);
                    TextView motivo = convertView.findViewById(R.id.motivo);
                    TextView durata = convertView.findViewById(R.id.durata);
                    Button pulsanteMkTerapia=convertView.findViewById(R.id.PendenteButton);

                    if (richiesta != null) {
                        nomeBambino.setText(richiesta.getNomeBambino());
                        motivo.setText(richiesta.getMotivoRichiesta());

                        if(richiesta.getDurataTerapia()>1){
                            durata.setText(String.valueOf(richiesta.getDurataTerapia())+"\tsettimane");
                        }else {
                            durata.setText(String.valueOf(richiesta.getDurataTerapia()) + "\tsettimana");
                        }
                       pulsanteMkTerapia.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               Intent intent=new Intent(Logopedista.this, CreazioneEsercizi.class);
                               intent.putExtra("email", richiesta.getEmailGenitore());
                               intent.putExtra("bambino", richiesta.getNomeBambino());
                               intent.putExtra("motivo", richiesta.getMotivoRichiesta());
                               intent.putExtra("durata", richiesta.getDurataTerapia());
                               intent.putExtra("logopedista", emailLogopedista);
                               intent.putExtra("source", TAG);
                               startActivity(intent);


                           }
                       } );



                    }

                    return convertView;
                }
            };

            listView.setAdapter(adapter);
        }
    }

    public void onRestart(){
        super.onRestart();
        recreate();
    }

}