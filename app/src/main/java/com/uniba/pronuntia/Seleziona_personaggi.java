package com.uniba.pronuntia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Seleziona_personaggi extends AppCompatActivity {
private boolean isClicked1=false;

    private DBHelper db;
    private boolean isClicked2=false;
    private boolean isClicked3=false;
    private boolean isClicked4=false;
    private boolean isClicked5=false;
    private boolean isClicked6=false;
    private ImageButton personaggio1;
    private ImageButton personaggio2;
    private ImageButton personaggio3;
    private ImageButton personaggio4;
    private ImageButton personaggio5;
    private ImageButton personaggio6;
    private ImageView ok1;
    private ImageView ok2;
    private ImageView ok3;
    private ImageView ok4;
    private ImageView ok5;
    private ImageView ok6;
    private int selectionCounter=0;
    private static final int MAX_Personaggi=3;
    private Button confirmBtn;
    private boolean[] isClicked=new boolean[6];
    private ImageButton[] buttons=new ImageButton[6];
    public void onCreate(Bundle SavedInstanceState){

        super.onCreate(SavedInstanceState);
setContentView(R.layout.seleziona_personaggio);

confirmBtn=findViewById(R.id.ConfirmButton);

Intent intent=getIntent();
        String nomeBambino= intent.getStringExtra("nomeBambino");
        String emailGenitore=intent.getStringExtra("emailGenitore");
personaggio1=findViewById(R.id.personaggio1);
        personaggio2=findViewById(R.id.personaggio2);
        personaggio3=findViewById(R.id.personaggio3);
        personaggio4=findViewById(R.id.personaggio4);
        personaggio5=findViewById(R.id.personaggio5);
        personaggio6=findViewById(R.id.personaggio6);
        buttons[0]=personaggio1;
        buttons[1]=personaggio2;
        buttons[2]=personaggio3;
        buttons[3]=personaggio4;
        buttons[4]=personaggio5;
        buttons[5]=personaggio6;
db=new DBHelper(this);
ok1=findViewById(R.id.personaggio1ok);
        ok2=findViewById(R.id.personaggio2ok);
        ok3=findViewById(R.id.personaggio3ok);
        ok4=findViewById(R.id.personaggio4ok);
        ok5=findViewById(R.id.personaggio5ok);
        ok6=findViewById(R.id.personaggio6ok);

        isClicked[0]=isClicked1;
        isClicked[1]=isClicked2;
        isClicked[2]=isClicked3;
        isClicked[3]=isClicked4;
        isClicked[4]=isClicked5;
        isClicked[5]=isClicked6;

        personaggio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectionCounter<MAX_Personaggi && isClicked[0]==false){
                    ok1.setImageResource(R.drawable.img);
                    selectionCounter++;
                    isClicked[0]=true;
                }else if(isClicked[0]){
                    ok1.setImageResource(0);
                    selectionCounter--;
                    isClicked[0]=false;
                }

                else{
                    Toast.makeText(Seleziona_personaggi.this,"Non è possibile selezionare più di 3 personaggi",Toast.LENGTH_SHORT).show();
                }
            }
        });
        personaggio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectionCounter<MAX_Personaggi &&  isClicked[1]==false){
                    ok2.setImageResource(R.drawable.img);
                    selectionCounter++;
                    isClicked[1]=true;
                }else if( isClicked[1]){
                    ok2.setImageResource(0);
                    selectionCounter--;
                    isClicked[1]=false;
                }

                else{
                    Toast.makeText(Seleziona_personaggi.this,"Non è possibile selezionare più di 3 personaggi",Toast.LENGTH_SHORT).show();
                }
            }
        });
        personaggio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectionCounter<MAX_Personaggi && isClicked[2]==false){
                    ok3.setImageResource(R.drawable.img);
                    selectionCounter++;
                    isClicked[2]=true;
                }else if(isClicked[2]){
                    ok3.setImageResource(0);
                    selectionCounter--;
                    isClicked[2]=false;
                }

                else{
                    Toast.makeText(Seleziona_personaggi.this,"Non è possibile selezionare più di 3 personaggi",Toast.LENGTH_SHORT).show();
                }
            }
        });
        personaggio4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectionCounter<MAX_Personaggi && isClicked[3]==false){
                    ok4.setImageResource(R.drawable.img);
                    selectionCounter++;
                    isClicked[3]=true;
                }else if(isClicked[3]){
                    ok4.setImageResource(0);
                    selectionCounter--;
                    isClicked[3]=false;
                }

                else{
                    Toast.makeText(Seleziona_personaggi.this,"Non è possibile selezionare più di 3 personaggi",Toast.LENGTH_SHORT).show();
                }
            }
        });
        personaggio5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectionCounter<MAX_Personaggi && isClicked[4]==false){
                    ok5.setImageResource(R.drawable.img);
                    selectionCounter++;
                    isClicked[4]=true;
                }else if(isClicked[4]){
                    ok5.setImageResource(0);
                    selectionCounter--;
                    isClicked[4]=false;
                }

                else{
                    Toast.makeText(Seleziona_personaggi.this,"Non è possibile selezionare più di 3 personaggi",Toast.LENGTH_SHORT).show();
                }
            }
        });
        personaggio6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectionCounter<MAX_Personaggi && isClicked[5]==false){
                    ok6.setImageResource(R.drawable.img);
                    selectionCounter++;
                    isClicked[5]=true;
                }else if(isClicked[5]){
                    ok6.setImageResource(0);
                    selectionCounter--;
                    isClicked[5]=false;
                }

                else{
                    Toast.makeText(Seleziona_personaggi.this,"Non è possibile selezionare più di 3 personaggi",Toast.LENGTH_SHORT).show();
                }
            }
        });



        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Drawable> personaggi = new ArrayList<>();
                List<byte[]> personaggiBytes = new ArrayList<>();


                for (int i = 0; i < 6; i++) {
                    if (isClicked[i]) {
                        Drawable drawable = buttons[i].getDrawable();
                        if (drawable != null) {
                            personaggi.add(drawable);
                        }
                    }
                }


                for (Drawable drawable : personaggi) {
                    byte[] drawableBytes = drawableToBlob(drawable);
                    if (drawableBytes != null) {
                        personaggiBytes.add(drawableBytes);
                    }
                }


                if (!personaggiBytes.isEmpty()) {
                    db.modificaPersonaggi(nomeBambino, emailGenitore, personaggiBytes);
                }

                Toast.makeText(Seleziona_personaggi.this,"Personaggi impostati con successo",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Seleziona_personaggi.this, Impostazioni.class);
                startActivity(intent);
            }
        });








    }

    public byte[] drawableToBlob(Drawable drawable){
        Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }

}
