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

public class Seleziona_ambientazione extends AppCompatActivity {

    private boolean ambSelected=false;
    private ImageButton amb1;
    private ImageButton amb2;
    private ImageButton amb3;
    private ImageButton amb4;
    private ImageButton amb5;
    private ImageButton amb6;
private ImageView ok1;
    private ImageView ok2;
    private ImageView ok3;
    private ImageView ok4;
    private ImageView ok5;
    private ImageView ok6;
private boolean isClickedbt1;
    private boolean isClickedbt2;
    private boolean isClickedbt3;
    private boolean isClickedbt4;
    private boolean isClickedbt5;
    private boolean isClickedbt6;
    private boolean[] isClicked=new boolean[6];
    private ImageButton[] buttons=new ImageButton[6];
private Button confirmButton;

    public void onCreate(Bundle SavedInstanceState){

        super.onCreate(SavedInstanceState);
        setContentView(R.layout.seleziona_ambientazione);


        DBHelper db=new DBHelper(this);
Intent intent=getIntent();
String nomeBambino= intent.getStringExtra("nomeBambino");
String emailGenitore=intent.getStringExtra("emailGenitore");
        confirmButton=findViewById(R.id.ConfirmButtonAmbientazione);
        amb1=findViewById(R.id.ambientazione1);
        amb2=findViewById(R.id.ambientazione2);
        amb3=findViewById(R.id.ambientazione3);
        amb4=findViewById(R.id.ambientazione4);
        amb5=findViewById(R.id.ambientazione5);
        amb6=findViewById(R.id.ambientazione6);
        ok1=findViewById(R.id.ambientazione1ok);
        ok2=findViewById(R.id.ambientazione2ok);
        ok3=findViewById(R.id.ambientazione3ok);
        ok4=findViewById(R.id.ambientazione4ok);
        ok5=findViewById(R.id.ambientazione5ok);
        ok6=findViewById(R.id.ambientazione6ok);
        isClicked[0]=isClickedbt1;
        isClicked[1]=isClickedbt2;
        isClicked[2]=isClickedbt3;
        isClicked[3]=isClickedbt4;
        isClicked[4]=isClickedbt5;
        isClicked[5]=isClickedbt6;

        buttons[0]=amb1;
        buttons[1]=amb2;
        buttons[2]=amb3;
        buttons[3]=amb4;
        buttons[4]=amb5;
        buttons[5]=amb6;

        amb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ambSelected && !isClicked[0]){
ok1.setImageResource(R.drawable.img);
ambSelected=true;
                    isClicked[0]=true;
                }else if(ambSelected && isClicked[0]){
                    ok1.setImageResource(0);
ambSelected=false;
                    isClicked[0]=false;
                }else

                {
                    Toast.makeText(Seleziona_ambientazione.this, "Non è possibile selezionare più di un'ambientazione", Toast.LENGTH_SHORT).show();
                }

            }
        });
        amb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ambSelected && !isClicked[1]){
                    ok2.setImageResource(R.drawable.img);
                    ambSelected=true;
                    isClicked[1]=true;
                }else if(ambSelected && isClicked[1]){
                    ok2.setImageResource(0);
                    ambSelected=false;
                    isClicked[1]=false;
                }else

                {
                    Toast.makeText(Seleziona_ambientazione.this, "Non è possibile selezionare più di un'ambientazione", Toast.LENGTH_SHORT).show();
                }

            }
        });
        amb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ambSelected && !isClicked[2]){
                    ok3.setImageResource(R.drawable.img);
                    ambSelected=true;
                    isClicked[2]=true;
                }else if(ambSelected && isClicked[2]){
                    ok3.setImageResource(0);
                    ambSelected=false;
                    isClicked[2]=false;
                }else

                {
                    Toast.makeText(Seleziona_ambientazione.this, "Non è possibile selezionare più di un'ambientazione", Toast.LENGTH_SHORT).show();
                }

            }
        });
        amb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ambSelected && !isClicked[3]){
                    ok4.setImageResource(R.drawable.img);
                    ambSelected=true;
                    isClicked[3]=true;
                }else if(ambSelected && isClicked[3]){
                    ok4.setImageResource(0);
                    ambSelected=false;
                    isClicked[3]=false;
                }else

                {
                    Toast.makeText(Seleziona_ambientazione.this, "Non è possibile selezionare più di un'ambientazione", Toast.LENGTH_SHORT).show();
                }

            }
        });
        amb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ambSelected && !isClicked[4]){
                    ok5.setImageResource(R.drawable.img);
                    ambSelected=true;
                    isClicked[4]=true;
                }else if(ambSelected && isClicked[4]){
                    ok5.setImageResource(0);
                    ambSelected=false;
                    isClicked[4]=false;
                }else

                {
                    Toast.makeText(Seleziona_ambientazione.this, "Non è possibile selezionare più di un'ambientazione", Toast.LENGTH_SHORT).show();
                }

            }
        });
        amb6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ambSelected && !isClicked[5]){
                    ok6.setImageResource(R.drawable.img);
                    ambSelected=true;
                    isClicked[5]=true;
                }else if(ambSelected && isClicked[5]){
                    ok6.setImageResource(0);
                    ambSelected=false;
                    isClicked[5]=false;
                }else

                {
                    Toast.makeText(Seleziona_ambientazione.this, "Non è possibile selezionare più di un'ambientazione", Toast.LENGTH_SHORT).show();
                }

            }
        });


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 6; i++) {
                    if (isClicked[i]) {
                        Drawable drawable = buttons[i].getDrawable();
                        if (drawable != null) {

                            byte[] drawablebytes=drawableToBlob(drawable);
                            db.modificaAmbientazione(nomeBambino,emailGenitore,drawablebytes);


                        } else {
                            Log.e("Seleziona_ambientazione", "Drawable is null for button: " + i);
                        }
                    }
                }
                Intent intent = new Intent(Seleziona_ambientazione.this, Impostazioni.class);
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
