package com.uniba.pronuntia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private static final String IMMAGINE = "IMMAGINE";
    private static final String AUDIO = "AUDIO";
    private static final String MATCH = "MATCH";
    private static final String GIORNO = "GIORNO";


    private static final String TABLE_SEQUENZA = "SEQUENZA";
    private static final String TABLE_COPPIA = "COPPIA";

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
        db.execSQL(" CREATE TABLE DATA_ES (id INTEGER PRIMARY KEY AUTOINCREMENT, GIORNO INTEGER, MESE INTEGER, ANNO INTEGER)");

        //db.execSQL("CREATE TABLE " + TABLE_SEQUENZA + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " + EMAIL + " TEXT, " + TITOLO + " TEXT, " + TIPO + " TEXT )");
        //db.execSQL("CREATE TABLE " + TABLE_COPPIA + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + EMAIL +" TEXT," + TITOLO + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESERCIZI);
        db.execSQL("DROP TABLE IF EXISTS DATA_ES");
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEQUENZA);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_COPPIA);
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

            esercizi.add(new Esercizio(email, titolo, tipo));
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

    public boolean addIndovinello(Esercizio esercizio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(EMAIL, esercizio.getEmail());
        contentValues.put(TITOLO, esercizio.getName());
        contentValues.put(TIPO, esercizio.getTipo());
        long result = db.insert(TABLE_SEQUENZA, null, contentValues);
        return result != -1;
    }

    public boolean addCoppia(String email, String titolo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL, email);
        contentValues.put(TITOLO, titolo);
        long result = db.insert(TABLE_COPPIA, null, contentValues);
        return result != -1;
    }

    public void deleteAllDataFromTable(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName);
        Log.d(TAG, "deleteAllDataFromTable: All data deleted from " + tableName);
    }

}
