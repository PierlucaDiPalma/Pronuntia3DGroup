package com.uniba.pronuntia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pronuntiadb-58c18-default-rtdb.firebaseio.com/");
    //DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private static final String TAG = "Login";


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


        final EditText emailEdit = findViewById(R.id.email);
        final EditText passwordEdit = findViewById(R.id.password);
        final Button logBtn = findViewById(R.id.log);
        final TextView regLink = findViewById(R.id.regButton);

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailEdit.getText().toString().trim();
                final String password = passwordEdit.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(Login.this, "Inserire l'email o la password", Toast.LENGTH_SHORT).show();
                }else{
                    dbRef.child("users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //controlla che l'indirizzo email sia registrato
                            Log.d(TAG, "Chiamato ");

                            Log.d(TAG, snapshot.getValue().toString());

                            for (DataSnapshot userData : snapshot.getChildren()) {
                                Log.d(TAG, userData.getValue().toString());
                                if (userData.child("email").getValue().toString().equals(email) ||
                                        userData.child("password").getValue().toString().equals(password)) {
                                    Log.d(TAG, "Loggato ");
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;
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

        regLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);

            }
        });

    }
}