package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    //DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pronuntiadb-58c18-default-rtdb.firebaseio.com/");
    //DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

    private EditText emailEdit;
    private EditText nomeEdit;
    private EditText cognomeEdit;
    private EditText telefonoEdit;
    private EditText passwordEdit;
    private EditText conPasswordEdit;
    private SwitchCompat isLogopedistaSwitch;
    private boolean isLogopedista = false;

    private Button regBtn;
    private TextView logLink;

    private static final String TAG = "Register";
    private boolean emailIsPresent = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);


        emailEdit = findViewById(R.id.email);
        nomeEdit = findViewById(R.id.nome);
        cognomeEdit = findViewById(R.id.cognome);
        telefonoEdit = findViewById(R.id.telefono);
        passwordEdit = findViewById(R.id.password);
        conPasswordEdit = findViewById(R.id.conPassword);
        isLogopedistaSwitch = findViewById(R.id.isLogopedista);

        regBtn = findViewById(R.id.reg);
        logLink = findViewById(R.id.logButton);

        DBHelper db = new DBHelper(this);



        isLogopedistaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(Register.this, "Sei un logopedista", Toast.LENGTH_SHORT).show();
                    isLogopedista = true;
                }else{
                    Toast.makeText(Register.this, "Non sei un logopedista", Toast.LENGTH_SHORT).show();
                }
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Chiamato");

                String email = emailEdit.getText().toString().trim();
                String nome = nomeEdit.getText().toString().trim();
                String cognome = cognomeEdit.getText().toString().trim();
                String telefono = telefonoEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                String conPassword = conPasswordEdit.getText().toString().trim();


                Utente utente = new Utente(email, nome, cognome, telefono, password, isLogopedista);

                if(email.isEmpty() || nome.isEmpty() || cognome.isEmpty() || telefono.isEmpty() || password.isEmpty()){
                    Toast.makeText(Register.this, "Alcuni campi sono vuoti", Toast.LENGTH_SHORT).show();
                }else if(!password.equals(conPassword)){
                    Toast.makeText(Register.this, "Le password non coincidono", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d(TAG, "Passato");
                    if(!db.isSigned(email)){

                        if(db.addUser(utente)){
                            Toast.makeText(Register.this, "Registrazione avvenuta", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this, Login.class));
                            

                        }else{
                            Toast.makeText(Register.this, "Errore durante la registrazione", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Register.this, "Email già presente", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        logLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



}


