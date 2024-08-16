package com.uniba.pronuntia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Pronuntia.db";
    private static final String TABLE_NAME = "UTENTI";
    private static final String EMAIL = "EMAIL";
    private static final String NOME = "NOME";
    private static final String COGNOME = "COGNOME";
    private static final String TELEFONO = "TELEFONO";
    private static final String PASSWORD = "PASSWORD";
    private static final String ISLOGOPEDISTA = "ISLOGOPEDISTA";

    private static final String TABLE_ESERCIZI = "ESERCIZI";
    private static final String TITOLO = "TITOLO";
    private static final String TIPO = "TIPO";

    private static final String TABLE_DENOMINAZIONE = "DENOMINAZIONE";
    private static final String TABLE_SEQUENZA = "SEQUENZA";
    private static final String PAROLA_1 = "PRIMA_PAROLA";
    private static final String PAROLA_2 = "SECONDA_PAROLA";
    private static final String PAROLA_3 = "TERZA_PAROLA";
    private static final String IMMAGINE = "IMMAGINE";
    private static final String AIUTO = "AIUTO";
    private static final String MATCH = "MATCH";
    private static final String GIORNO = "GIORNO";
    private static final String MESE = "MESE";
    private static final String ANNO = "ANNO";


    private static final String TABLE_COPPIA = "COPPIA";
    private static final String IMMAGINE_1 = "IMMAGINE_1";
    private static final String IMMAGINE_2 = "IMMAGINE_2";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    private static final String TAG = "DBHelper";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME
                + " ( " + EMAIL +" TEXT PRIMARY KEY, "
                + NOME + " TEXT, "
                + COGNOME + " TEXT,"
                + TELEFONO + " TEXT, "
                + PASSWORD +" TEXT, "
                + ISLOGOPEDISTA+ " INTEGER )");

        db.execSQL("CREATE TABLE " + TABLE_ESERCIZI
                + " ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EMAIL + " TEXT, "
                + TITOLO +" TEXT, "
                + TIPO +" TEXT )");


        db.execSQL("CREATE TABLE " + TABLE_DENOMINAZIONE
                + " ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EMAIL + " TEXT, "
                + TITOLO +" TEXT, "
                + TIPO +" TEXT, "
                + IMMAGINE + " BLOB, "
                + AIUTO + " TEXT, "
                + GIORNO + " INTEGER, "
                + MESE + " INTEGER, "
                + ANNO + " INTEGER )");


        db.execSQL("CREATE TABLE " + TABLE_SEQUENZA
                + " ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EMAIL + " TEXT, "
                + TITOLO +" TEXT, "
                + TIPO +" TEXT, "
                + PAROLA_1 + " TEXT, "
                + PAROLA_2 + " TEXT, "
                + PAROLA_3 + " TEXT, "
                + GIORNO + " INTEGER, "
                + MESE + " INTEGER, "
                + ANNO + " INTEGER )");

        db.execSQL("CREATE TABLE " + TABLE_COPPIA
                + " ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EMAIL + " TEXT, "
                + TITOLO +" TEXT, "
                + TIPO +" TEXT, "
                + IMMAGINE_1 + " BLOB, "
                + IMMAGINE_2 + " BLOB, "
                + AIUTO + " TEXT, "
                + GIORNO + " INTEGER, "
                + MESE + " INTEGER, "
                + ANNO + " INTEGER )");

        //db.execSQL("CREATE TABLE " + TABLE_COPPIA + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + EMAIL +" TEXT," + TITOLO + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESERCIZI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DENOMINAZIONE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEQUENZA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COPPIA);

        onCreate(db);
    }
    public boolean addData(int giorno, int mese, int anno) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("GIORNO", giorno);
        contentValues.put("MESE", mese);
        contentValues.put("ANNO",anno);
        long result = db.insert("DATA_ES", null, contentValues);
        Log.d(TAG, "addData: " + result);
        return result != -1;
    }

    public boolean addUser(Utente utente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.d(TAG, "addUser: " + utente.getEmail());
        Log.d(TAG, "addUser: " + utente.getNome());
        contentValues.put(EMAIL, utente.getEmail());
        contentValues.put(NOME, utente.getNome());
        contentValues.put(COGNOME, utente.getCognome());
        contentValues.put(TELEFONO, utente.getTelefono());
        contentValues.put(PASSWORD, utente.getPassword());
        contentValues.put(ISLOGOPEDISTA, utente.isLogopedista() ? 1 : 0);
        long result = db.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG, "addUser: " + result);
        return result != -1;
    }

    public boolean addImage(byte[] image, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", email);
        contentValues.put("IMMAGINE", image);
        long result = db.insert("IMAGES", null, contentValues);
        return result != -1;
    }

    public Bitmap getAllImages(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Bitmap bt = null;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_DENOMINAZIONE +" WHERE ID = ?", new String[]{String.valueOf(id)});

        if(cursor.moveToNext()){
            byte[] image = cursor.getBlob(4);
            bt = BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        return bt;
    }

    public Bitmap getCoupleImage1(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Bitmap bt = null;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_COPPIA +" WHERE ID = ?", new String[]{String.valueOf(id)});

        if(cursor.moveToNext()){
            byte[] image = cursor.getBlob(4);
            bt = BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        return bt;
    }

    public boolean isSigned(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ?", new String[]{email});
        return cursor.getCount()>0;
    }

    public boolean checkUser(String email, String password){
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ? AND PASSWORD = ?", new String[]{email, password});
        return cursor.getCount()>0;

    }

    public boolean isLogopedista(String email){
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ISLOGOPEDISTA FROM " + TABLE_NAME + " WHERE EMAIL = ?", new String[]{email});

        boolean isLogopedista = false;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                if(cursor.getInt(cursor.getColumnIndexOrThrow("ISLOGOPEDISTA")) == 1){
                    isLogopedista = true;
                }
            }
        }
        return isLogopedista;

    }

    public ArrayList<Utente> readData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        ArrayList<Utente> pazienti = new ArrayList<>();

        if(db!= null){
            Log.d(TAG, "readData: Lettura dati");
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

            Log.d(TAG, "readData: Lettura dati avvenuta");
        }


        while(cursor.moveToNext()){
            String email = cursor.getString(0);
            String nome = cursor.getString(1);
            String cognome = cursor.getString(2);
            String telefono = cursor.getString(3);
            String password = cursor.getString(4);

            boolean isLogopedista = cursor.getInt(5) == 1;

            if(cursor.getInt(5) == 0)
                pazienti.add(new Utente(email, nome, cognome, telefono, password, isLogopedista));
        }
        Log.d(TAG, "Ritorno array");
        cursor.close();
        return pazienti;

    }

    public ArrayList<Esercizio> readExercises(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        ArrayList<Esercizio> esercizi = new ArrayList<>();
        Log.d(TAG, "readExercises: Array creato");

        if(db!=null){
            Log.d(TAG, "readExercises: Entrato");
            cursor = db.rawQuery("SELECT * FROM " + TABLE_ESERCIZI + " WHERE EMAIL = ?", new String[]{user});

            Log.d(TAG, "readExercises: Esecuzione query");
        }


        while(cursor.moveToNext()){
            String email = cursor.getString(1);
            String titolo = cursor.getString(2).replace("+", " ");
            String tipo = cursor.getString(3);

            esercizi.add(new Esercizio(email, titolo, tipo, null, null, null, null, 0, 0, 0));
            Log.d(TAG, "readExercises: " + cursor.getString(1));
            Log.d(TAG, "readExercises: " + cursor.getString(2));
        }
        return esercizi;
    }

    public boolean addExercises(Esercizio esercizio) {
        Log.d(TAG, "addDenominazione: Entrato");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(EMAIL, esercizio.getEmail());
        contentValues.put(TITOLO, esercizio.getName().replace("+", " "));
        contentValues.put(TIPO, esercizio.getTipo());



        Log.d(TAG, "addDenominazione: Scritto");
        long result = db.insert(TABLE_ESERCIZI, null, contentValues);
        Log.d(TAG, "addDenominazione: Ritorno");
        Log.d(TAG, "addDenominazione: " + result);
        return result != -1;
    }

    public boolean addDenominazione(Esercizio esercizio){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(EMAIL, esercizio.getEmail());
        contentValues.put(TITOLO, esercizio.getName().replace("+", " "));
        contentValues.put(TIPO, esercizio.getTipo());
/*
        Bitmap bitmap = esercizio.getImmagine1();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] immagine = byteArrayOutputStream.toByteArray();
*/
        contentValues.put(IMMAGINE, esercizio.getImmagine1());
        contentValues.put(AIUTO, esercizio.getAiuto().replace("+", " "));
        contentValues.put(GIORNO, esercizio.getGiorno());
        contentValues.put(MESE, esercizio.getMese());
        contentValues.put(ANNO, esercizio.getAnno());

        long result = db.insert(TABLE_DENOMINAZIONE, null, contentValues);

        return result != -1;
    }


    public boolean addSequenza(Esercizio esercizio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(EMAIL, esercizio.getEmail());
        contentValues.put(TITOLO, esercizio.getName().replace("+", " "));
        contentValues.put(TIPO, esercizio.getTipo());
        contentValues.put(PAROLA_1, esercizio.getSequenza()[0]);
        contentValues.put(PAROLA_2, esercizio.getSequenza()[1]);
        contentValues.put(PAROLA_3, esercizio.getSequenza()[2]);
        contentValues.put(GIORNO, esercizio.getGiorno());
        contentValues.put(MESE, esercizio.getMese());
        contentValues.put(ANNO, esercizio.getAnno());

        long result = db.insert(TABLE_SEQUENZA, null, contentValues);
        return result != -1;
    }

    public boolean addCoppia(Esercizio esercizio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(EMAIL, esercizio.getEmail());
        contentValues.put(TITOLO, esercizio.getName().replace("+", " "));
        contentValues.put(TIPO, esercizio.getTipo());

        contentValues.put(IMMAGINE_1, esercizio.getImmagine1());

        contentValues.put(IMMAGINE_2, esercizio.getImmagine2());
        contentValues.put(AIUTO, esercizio.getAiuto());
        contentValues.put(GIORNO, esercizio.getGiorno());
        contentValues.put(MESE, esercizio.getMese());
        contentValues.put(ANNO, esercizio.getAnno());

        long result = db.insert(TABLE_COPPIA, null, contentValues);
        return result != -1;
    }

    public Esercizio getCoppia(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        //Bitmap bt = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_COPPIA + " WHERE ID = ?", new String[]{String.valueOf(id)});

        Esercizio esercizio = null;

        //Log.d(TAG, "getImage1: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2));

        cursor = db.rawQuery("SELECT * FROM " + TABLE_COPPIA + " WHERE ID = ?", new String[]{String.valueOf(id)});

        // Controllo del numero di colonne nel Cursor
        Log.d(TAG, "Numero di colonne nel cursor: " + cursor.getColumnCount());

        // Controlla se il cursore ha dei risultati
        if (cursor != null && cursor.moveToFirst()) {
            Log.d(TAG, "getCoppia: Trovata la prima riga");

            // Verifica che tutte le colonne siano accessibili
            Log.d(TAG, "Colonna 1 (email): " + cursor.getString(1));
            Log.d(TAG, "Colonna 2 (titolo): " + cursor.getString(2));
            Log.d(TAG, "Colonna 4 (immagine1): " + (cursor.getBlob(4) != null));
            Log.d(TAG, "Colonna 5 (immagine2): " + (cursor.getBlob(5) != null));
            Log.d(TAG, "Colonna 6 (aiuto): " + cursor.getString(6));
            Log.d(TAG, "Colonna 7 (sequenza): " + cursor.getString(7));
            Log.d(TAG, "Colonna 8 (giorno): " + cursor.getInt(7));
            Log.d(TAG, "Colonna 9 (mese): " + cursor.getInt(8));
            Log.d(TAG, "Colonna 10 (anno): " + cursor.getInt(9));

            // Estrai i dati dalla riga
            String email = cursor.getString(1);
            String titolo = cursor.getString(2);
            byte[] immagine1 = cursor.getBlob(4);  // Verifica che la colonna esista
            byte[] immagine2 = cursor.getBlob(5);  // Verifica che la colonna esista
            String aiuto = cursor.getString(6);
            String[] sequenza = new String[]{cursor.getString(7)};
            int giorno = cursor.getInt(7);
            int mese = cursor.getInt(8);
            int anno = cursor.getInt(9);

            // Crea l'oggetto Esercizio
            esercizio = new Esercizio(email, titolo, "Coppia", immagine1, immagine2, aiuto, sequenza, giorno, mese, anno);

            Log.d(TAG, "getCoppia: " + esercizio.getEmail() + esercizio.getAiuto());

        } else {
            Log.d(TAG, "getCoppia: Nessun risultato trovato");
        }
        Log.d(TAG, "getCoppia: " + esercizio.getEmail() + esercizio.getAiuto());

        return esercizio;
    }

    public String getImage2(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Bitmap bt = null;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_COPPIA +" WHERE id = ?", new String[]{String.valueOf(id)});
        String image = null;

        if(cursor.moveToNext()){
             image = cursor.getString(2);
            //bt = BitmapFactory.decodeByteArray(image, 0, image.length);
            return image;
        }

        Log.d(TAG, "getImage2: " + bt.toString() );
        return image;
    }

    public void deleteAllDataFromTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName);
        Log.d(TAG, "deleteAllDataFromTable: All data deleted from " + tableName);
    }

}
