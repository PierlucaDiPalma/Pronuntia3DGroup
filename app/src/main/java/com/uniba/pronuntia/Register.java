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

    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pronuntiadb-58c18-default-rtdb.firebaseio.com/").child("users");
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
   /*@Override
    protected void onStart() {
        super.onStart();
        dbRef.child("users").setValue("marco");
    }*/

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

        isLogopedistaSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> isLogopedista = isChecked);

        if(isLogopedistaSwitch.isChecked()){
            isLogopedista = true;
            Toast.makeText(Register.this, "Sei un logopedista", Toast.LENGTH_SHORT).show();
        }else{
            isLogopedista = false;
            Toast.makeText(Register.this, "Non sei un logopedista", Toast.LENGTH_SHORT).show();

        }

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


                Utente utente = new Utente(email, nome, cognome, telefono, password);

                if(email.isEmpty() || nome.isEmpty() || cognome.isEmpty() || telefono.isEmpty() || password.isEmpty()){
                    Toast.makeText(Register.this, "Alcuni campi sono vuoti", Toast.LENGTH_SHORT).show();
                }else if(!password.equals(conPassword)){
                    Toast.makeText(Register.this, "Le password non coincidono", Toast.LENGTH_SHORT).show();
                }else {

                    //1-Accedere al DB e controllare la presenza della email

                    //2-Se l'email è presente -> Errore altrimenti -> procede a scrivere i dati sul DB

                    dbRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            boolean isPresent = false;

                            if (snapshot.exists()) {
                                for (DataSnapshot userData : snapshot.getChildren()) {
                                    Log.d(TAG, userData.getValue().toString());
                                    if (userData.child("email").getValue().toString().equals(email)) {
                                        isPresent = true;
                                    }

                                }
                                if (isPresent) {
                                    Toast.makeText(Register.this, "Email già presente", Toast.LENGTH_SHORT).show();

                                    Log.d(TAG, "Utente già registrato");

                                } else {
                                    Log.d(TAG, "onClick: Validazione superata, scrittura sul database");
                                    dbRef.child(email.replace(".", "")).setValue(utente).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "Scrittura avvenuta con successo");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "Scrittura non riuscita");
                                                }
                                            });
                                    //3-Torna all'activity di login chiudendo quella di registrazione
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });





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


