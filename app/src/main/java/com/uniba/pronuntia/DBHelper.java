package com.uniba.pronuntia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private static final String TABLE_SEQUENZA = "SEQUENZA";
    private static final String PAROLA_1 = "PRIMA_PAROLA";
    private static final String PAROLA_2 = "SECONDA_PAROLA";
    private static final String PAROLA_3 = "TERZA_PAROLA";
    private static final String IMMAGINE = "IMMAGINE";
    private static final String AIUTO = "AIUTO";
    private static final String GIORNO = "GIORNO";
    private static final String MESE = "MESE";
    private static final String ANNO = "ANNO";


    private static final String TABLE_COPPIA = "COPPIA";
    private static final String IMMAGINE_1 = "IMMAGINE_1";
    private static final String IMMAGINE_2 = "IMMAGINE_2";


    private static final String TABLE_TERAPIE = "Terapie";
    private static final String ID = "id";
    private static final String nome_bambino = "nome_bambino";
    private static final String motivo_richiesta = "motivo_richiesta";
    private static final String durata_terapia = "durata_terapia";
    private static final String contenuti_terapia = "contenuti_terapia";
    private static final String email_genitore = "email_genitore";

    private static final String email_logopedista = "email_logopedista";

    private static final String TABLE_RESOCONTO = "RISULTATI_ESERCIZI";
    private static final String BAMBINO = "BAMBINO";
    private static final String PUNTEGGIO = "PUNTEGGIO";
    private static final String LOGOPEDISTA = "LOGOPEDISTA";
    private static final String GENITORE = "GENITORE";
    private static final String CORRETTO = "CORRETTO";
    private static final String SBAGLIATO = "SBAGLIATO";
    private static final String AIUTI = "AIUTI";


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

        db.execSQL("CREATE TABLE " + TABLE_RESOCONTO
                + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + GENITORE + " TEXT, "
                + BAMBINO + " TEXT, "
                + LOGOPEDISTA + " TEXT, "
                + TITOLO + " TEXT, "
                + TIPO + " TEXT, "
                + GIORNO + " INTEGER, "
                + MESE + " INTEGER, "
                + ANNO + " INTEGER, "
                + PUNTEGGIO + " INTEGER, "
                + CORRETTO + " INTEGER, "
                + SBAGLIATO + " INTEGER, "
                + AIUTI + " INTEGER )");


        String CREATE_TABLE_TERAPIE = "CREATE TABLE " + TABLE_TERAPIE + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + nome_bambino + " TEXT,"
                + motivo_richiesta + " TEXT,"
                + durata_terapia + " INTEGER,"
                + email_genitore + " TEXT,"
                + email_logopedista + " TEXT,"
                + "FOREIGN KEY (" + email_logopedista + ") REFERENCES " + TABLE_NAME + "(" + EMAIL + "),"
                + "FOREIGN KEY (" + email_genitore + ") REFERENCES " + TABLE_NAME + "(" + EMAIL + ")"
                + ")";
        db.execSQL(CREATE_TABLE_TERAPIE);

        //db.execSQL("CREATE TABLE " + TABLE_COPPIA + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + EMAIL +" TEXT," + TITOLO + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESERCIZI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DENOMINAZIONE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEQUENZA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COPPIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESOCONTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERAPIE);
        onCreate(db);
    }

    public void addTerapia(String nomeBambino, String motivoRichiesta, int durata, String emailGenitore, String emailLogopedista) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nome_bambino, nomeBambino);
        values.put(motivo_richiesta, motivoRichiesta);
        values.put(durata_terapia, durata);
        values.put(email_genitore, emailGenitore);
        values.put(email_logopedista,emailLogopedista);
        long result = db.insert(TABLE_TERAPIE, null, values);
        if (result == -1) {
            Log.e(TAG, "Errore nell'inserimento della terapia");
        } else {
            Log.d(TAG, "Terapia inserita con successo, ID: " + result);
        }
        db.close();
    }

    public ArrayList<Utente> getLogopedisti() {
        ArrayList<Utente> logopedisti = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase(); // Ottieni un'istanza del database

        // Query per selezionare solo gli utenti che sono logopedisti
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ISLOGOPEDISTA + " = 1";

        // Esegui la query
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Estrai i valori delle colonne
                int columnEmail = cursor.getColumnIndex(EMAIL);
                String email = cursor.getString(columnEmail);

                int columnNome = cursor.getColumnIndex(NOME);
                String nome = cursor.getString(columnNome);

                int columnCognome = cursor.getColumnIndex(COGNOME);
                String cognome = cursor.getString(columnCognome);

                int columnTelefono = cursor.getColumnIndex(TELEFONO);
                String telefono = cursor.getString(columnTelefono);

                int columnPassword = cursor.getColumnIndex(PASSWORD);
                String password = cursor.getString(columnPassword);

                // Crea un oggetto RichiestaTerapia per ogni logopedista
                Utente logopedista = new Utente(email, nome, cognome, telefono, password,true);

                // Aggiungi alla lista
                logopedisti.add(logopedista);

            } while (cursor.moveToNext());
        }

        cursor.close(); // Chiudi il Cursor
        db.close(); // Chiudi il database

        return logopedisti;
    }


    public ArrayList<RichiestaTerapia> getTerapie(String email){

        ArrayList<RichiestaTerapia> logopedisti=new ArrayList<RichiestaTerapia>();
        String [] emails={email};
        SQLiteDatabase db=this.getReadableDatabase(); //ottengo istanza del db
        String query="SELECT * FROM Terapie WHERE email_logopedista=?";
        Cursor cursor=db.rawQuery(query,emails);

        if(cursor.moveToFirst()) {
            do {
                int columnId = cursor.getColumnIndex("id");
                int logopedistaId = cursor.getInt(columnId);

                int columnNomeBambino = cursor.getColumnIndex("nome_bambino");
                String NomeBambino = cursor.getString(columnNomeBambino);

                int columnMotivo = cursor.getColumnIndex("motivo_richiesta");
                String motivo = cursor.getString(columnMotivo);

                int columnDurataTerapia = cursor.getColumnIndex("durata_terapia");
                int durataTerapia = cursor.getInt(columnDurataTerapia);

                int columnEmailGenitore = cursor.getColumnIndex("email_genitore");
                String emailGenitore = cursor.getString(columnEmailGenitore);

                int columnEmailLogopedista = cursor.getColumnIndex("email_logopedista");
                String emailLogopedista = cursor.getString(columnEmailLogopedista);

                RichiestaTerapia terapia = new RichiestaTerapia(logopedistaId, NomeBambino, motivo, durataTerapia, emailGenitore, emailLogopedista);

                logopedisti.add(terapia);


            }while(cursor.moveToNext());



        }
        cursor.close();
        db.close();

        return logopedisti;

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

        if (user == null) {
            Log.e(TAG, "readExercises: User cannot be null");
            return new ArrayList<>();
        }




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

    public boolean addResoconto(Resoconto resoconto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(BAMBINO, resoconto.getBambino());
        contentValues.put(GENITORE, resoconto.getGenitore());
        contentValues.put(LOGOPEDISTA, resoconto.getGenitore());
        contentValues.put(TITOLO, resoconto.getEsercizio().getName());
        contentValues.put(TIPO, resoconto.getEsercizio().getTipo());
        contentValues.put(GIORNO, resoconto.getEsercizio().getGiorno());
        contentValues.put(MESE, resoconto.getEsercizio().getMese());
        contentValues.put(ANNO, resoconto.getEsercizio().getAnno());
        contentValues.put(PUNTEGGIO, resoconto.getPunteggio());
        contentValues.put(CORRETTO, resoconto.getCorretti());
        contentValues.put(SBAGLIATO, resoconto.getSbagliati());
        contentValues.put(AIUTI, resoconto.getAiuti());

        long result = db.insert(TABLE_RESOCONTO, null, contentValues);
        return result != -1;
    }

    public ArrayList<Resoconto> getResoconto(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        ArrayList<Resoconto> resoconti  = new ArrayList<>();

        if(db!= null){
            cursor = db.rawQuery("SELECT * FROM " + TABLE_RESOCONTO + " WHERE GENITORE = ?", new String[]{user});
        }

        while (cursor.moveToNext()){
            String bambino = cursor.getString(1);
            String genitore = cursor.getString(2);
            String logopedista = cursor.getString(3);
            String titolo = cursor.getString(4);
            String tipo = cursor.getString(5);
            int giorno = cursor.getInt(6);
            int mese = cursor.getInt(7);
            int anno = cursor.getInt(8);
            int punteggio = cursor.getInt(9);
            int corretto = cursor.getInt(10);
            int sbagliato = cursor.getInt(11);
            int aiuti = cursor.getInt(12);

            Esercizio esercizio = new Esercizio(null, titolo, tipo, null, null, null, null, giorno, mese, anno);

            Resoconto resoconto = new Resoconto(bambino, genitore, logopedista, esercizio, punteggio, corretto, sbagliato, aiuti);
            resoconti.add(resoconto);
        }
        return resoconti;
    }
/*
    public boolean isEsercizioDone(Esercizio esercizio){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(db!=null){
            cursor = db.rawQuery("SELECT * FROM " + TABLE_RESOCONTO + " WHERE GENITORE = ? AND TITOLO = ? AND GIORNO = ? AND MESE = ? AND ANNO = ?",
                    new String[]{esercizio.getEmail(), esercizio.getName(), String.valueOf(esercizio.getGiorno()), String.valueOf(esercizio.getMese()), String.valueOf(esercizio.getAnno())});
        }
        if(cursor.getCount()!=0)
            return true;
        else return false;
    }
*/
    public ArrayList<Esercizio> getDenominazione(String user){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        ArrayList<Esercizio> esercizi = new ArrayList<>();
        Log.d(TAG, "denominazione: Array creato");

        if(db!=null){
            Log.d(TAG, "denominazione: Entrato");
            cursor = db.rawQuery("SELECT * FROM " + TABLE_DENOMINAZIONE + " WHERE EMAIL = ?", new String[]{user});

            Log.d(TAG, "denominazione: Esecuzione query");
        }


        while(cursor.moveToNext()){

            Log.d(TAG, "getDenominazione: " + cursor.getString(1));
            Log.d(TAG, "getDenominazione: " + cursor.getString(2));

            String email = cursor.getString(1);
            String titolo = cursor.getString(2).replace("+", " ");
            String tipo = cursor.getString(3);
            byte[] immagine1 = cursor.getBlob(4);
            String aiuto = cursor.getString(5);
            int giorno = cursor.getInt(6);
            int mese = cursor.getInt(7);
            int anno = cursor.getInt(8);


            esercizi.add(new Esercizio(email, titolo, tipo, immagine1, null, aiuto, null, giorno, mese, anno));
            Log.d(TAG, "readExercises: " + cursor.getString(1));
            Log.d(TAG, "readExercises: " + cursor.getString(2));
        }
        return esercizi;
    }

    public ArrayList<Esercizio> getSequenza(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        ArrayList<Esercizio> esercizi = new ArrayList<>();
        Log.d(TAG, "denominazione: Array creato");

        if(db!=null){
            Log.d(TAG, "denominazione: Entrato");
            cursor = db.rawQuery("SELECT * FROM " + TABLE_SEQUENZA + " WHERE EMAIL = ?", new String[]{user});

            Log.d(TAG, "denominazione: Esecuzione query");
        }


        while(cursor.moveToNext()){


            String email = cursor.getString(1);
            String titolo = cursor.getString(2).replace("+", " ");
            String tipo = cursor.getString(3);

            String[] sequenza = new String[3];
            sequenza[0] = cursor.getString(4);
            sequenza[1] = cursor.getString(5);
            sequenza[2] = cursor.getString(6);

            Log.d(TAG, "getSequenza: " + sequenza[0]);
            Log.d(TAG, "getSequenza: " + sequenza[1]);
            Log.d(TAG, "getSequenza: " + sequenza[2]);

            int giorno = cursor.getInt(7);
            int mese = cursor.getInt(8);
            int anno = cursor.getInt(9);


            esercizi.add(new Esercizio(email, titolo, tipo, null, null, null, sequenza, giorno, mese, anno));

        }
        return esercizi;
    }

    public ArrayList<Esercizio> getCoppia(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        ArrayList<Esercizio> esercizi = new ArrayList<>();

        if(db!=null){

            cursor = db.rawQuery("SELECT * FROM " + TABLE_COPPIA + " WHERE EMAIL = ?", new String[]{user});
      }


        while(cursor.moveToNext()){


            String email = cursor.getString(1);
            String titolo = cursor.getString(2).replace("+", " ");
            String tipo = cursor.getString(3);

            byte[] immagine1 = cursor.getBlob(4);
            byte[] immagine2 = cursor.getBlob(5);
            String aiuto = cursor.getString(6);

            int giorno = cursor.getInt(7);
            int mese = cursor.getInt(8);
            int anno = cursor.getInt(9);


            esercizi.add(new Esercizio(email, titolo, tipo, immagine1, immagine2, aiuto, null, giorno, mese, anno));

        }
        return esercizi;
    }

}
