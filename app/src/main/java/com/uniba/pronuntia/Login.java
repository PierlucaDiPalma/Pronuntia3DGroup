package com.uniba.pronuntia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";
    private Button defaultUser;
    private TextView userDefault;
    private TextView logopedistaDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        final EditText emailEdit = findViewById(R.id.email);
        final EditText passwordEdit = findViewById(R.id.password);
        final Button logBtn = findViewById(R.id.log);
        final TextView regLink = findViewById(R.id.regButton);

        defaultUser = findViewById(R.id.userDefault);
        userDefault = findViewById(R.id.userDefaultText);
        logopedistaDefault = findViewById(R.id.logopedistaDefaultText);

        DBHelper db = new DBHelper(this);

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailEdit.getText().toString().trim();
                final String password = passwordEdit.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(Login.this, "Inserire l'email o la password", Toast.LENGTH_SHORT).show();
                }else{

                    if(db.checkUser(email, password)){
                        if(db.isLogopedista(email)){
                            SharedPreferences sharedPreferences = getSharedPreferences("LogoPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userEmail", email);
                            editor.apply();

                            Intent intent = new Intent(Login.this, Logopedista.class);
                            intent.putExtra("logopedista_email", email);
                            startActivity(intent);
                            finish();
                        }else{

                            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userEmail", email);
                            editor.apply();

                            Intent intent = new Intent(Login.this, HomeUtente.class);
                            intent.putExtra("email", email);
                            startActivity(intent);

                            finish();
                        }
                    }else{
                        Toast.makeText(Login.this, "Email o password non corrette", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        defaultUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!db.checkUser("luigiverdi@gmail.com", "12345") && !db.checkUser("pietrorossi@gmail.com", "54321")) {
                    db.addUser(new Utente("luigiverdi@gmail.com", "Luigi", "Verdi", "3453456890", "12345", false));
                    db.addUser(new Utente("pietrorossi@gmail.com", "Pietro", "Rossi", "3678980976", "54321", true));
                }
                userDefault.setText("Genitore: luigiverdi@gmail.com, PWD: 12345");
                logopedistaDefault.setText("Logopedista: pietrorossi@gmail.com, PWD: 54321 ");

            }
        });

        regLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);

            }
        });


    }
}