package com.uniba.pronuntia;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.ims.ImsMmTelManager;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
private  static  final String APPUNTAMENTI_FISSATI="APPUNTAMENTI_FISSATI";
    private static final String TABLE_RESOCONTO = "RISULTATI_ESERCIZI";
    private static final String BAMBINO = "BAMBINO";
    private static final String PUNTEGGIO = "PUNTEGGIO";
    private static final String LOGOPEDISTA = "LOGOPEDISTA";
    private static final String GENITORE = "GENITORE";
    private static final String CORRETTO = "CORRETTO";
    private static final String SBAGLIATO = "SBAGLIATO";
    private static final String AIUTI = "AIUTI";

    private static final String TABLE_PERSONAGGI = "PERSONAGGI";
    private static final String TABLE_ACQUISTI = "PERSONAGGI_SBLOCCATI";
    private static final String PERSONAGGIO = "PERSONAGGIO";
    private static final String VALORE = "VALORE";

    private static final String TABLE_PREMI = "PREMI";
    private static final String CORRETTI = "CORRETTI";
    private static final String NUMERO_PREMI = "NUMERO_PREMI";

    private static final String TABLE_BAMBINI="BAMBINI";
    private static final String NOME_BAMBINO="NOME_BAMBINO";
    private static final String EMAIL_GENITORE="EMAIL_GENITORE";
    private static final String CALENDARIO="CALENDARIO";
    private static final String DATA="DATA";
    private static final String ORA="ORA";
    private static final String ISBOOKED="ISBOOKED";

private  static final String LUOGO_LAVORO_LOGOPEDISTA="LUOGO_LAVORO_LOGOPEDISTA";
private static final String NOME_LUOGO="NOME_LUOGO";
private static final String INDIRIZZO="INDIRIZZO";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 12);
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
                + BAMBINO + " TEXT, "
                + TITOLO +" TEXT, "
                + TIPO +" TEXT )");


        db.execSQL("CREATE TABLE " + TABLE_DENOMINAZIONE
                + " ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EMAIL + " TEXT, "
                + BAMBINO + " TEXT, "
                + TITOLO +" TEXT, "
                + TIPO +" TEXT, "
                + IMMAGINE + " TEXT, "
                + AIUTO + " TEXT, "
                + GIORNO + " INTEGER, "
                + MESE + " INTEGER, "
                + ANNO + " INTEGER )");


        db.execSQL("CREATE TABLE " + TABLE_SEQUENZA
                + " ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EMAIL + " TEXT, "
                + BAMBINO + " TEXT, "
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
                + BAMBINO + " TEXT, "
                + TITOLO +" TEXT, "
                + TIPO +" TEXT, "
                + IMMAGINE_1 + " TEXT, "
                + IMMAGINE_2 + " TEXT, "
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

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ACQUISTI + " ( "
                + BAMBINO + " TEXT, "
                + GENITORE + " TEXT, "
                + PERSONAGGIO + " TEXT, "
                + VALORE + " INTEGER, "
                + IMMAGINE + " BLOB, "
                + "PRIMARY KEY (" + BAMBINO + ", " + GENITORE + ", " + PERSONAGGIO + "))");

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


        db.execSQL("CREATE TABLE " + TABLE_BAMBINI + " ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NOME_BAMBINO + " TEXT, "
                + EMAIL_GENITORE + " TEXT, "
                + "UNIQUE (" + NOME_BAMBINO + ", " + EMAIL_GENITORE + "), "
                + "FOREIGN KEY (" + EMAIL_GENITORE + ") REFERENCES " + TABLE_NAME + " (" + EMAIL + ")"
                + ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PREMI + " ("
                + BAMBINO + " TEXT, "
                + GENITORE + " TEXT, "
                + CORRETTI + " INTEGER, "
                + NUMERO_PREMI + " INTEGER, "
                + "PRIMARY KEY (BAMBINO, GENITORE, NUMERO_PREMI))");


        //db.execSQL("CREATE TABLE " + TABLE_COPPIA + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + EMAIL +" TEXT," + TITOLO + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 2) {


            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ACQUISTI + " ( "
                    + BAMBINO + " TEXT, "
                    + GENITORE + " TEXT, "
                    + PERSONAGGIO + " TEXT, "
                    + VALORE + " INTEGER, "
                    + IMMAGINE + " BLOB, "
                    + "PRIMARY KEY (" + BAMBINO + ", " + GENITORE + ", " + PERSONAGGIO + "))");
        }

        if(oldVersion<5){

            db.execSQL("CREATE TABLE IF NOT EXISTS " + CALENDARIO + " ("
                    + email_logopedista + " TEXT, "
                    + email_genitore + " TEXT, "
                    + DATA + " TEXT, "
                    + ORA + " TEXT, "
                    + ISBOOKED + " BOOLEAN, "
                    + "PRIMARY KEY (" + DATA + ", " + ORA + "), "
                    + "FOREIGN KEY (" + email_logopedista + ") REFERENCES " + TABLE_NAME + "(" + EMAIL + "),"
                    + "FOREIGN KEY (" + email_genitore + ") REFERENCES " + TABLE_NAME + "(" + EMAIL + ")"
                    + ")");

            this.popolaTabellaAppuntamenti();

        }

        if(oldVersion<8){

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ACQUISTI + " ( "
                    + BAMBINO + " TEXT, "
                    + GENITORE + " TEXT, "
                    + PERSONAGGIO + " TEXT, "
                    + VALORE + " INTEGER, "
                    + IMMAGINE + " BLOB, "
                    + "PRIMARY KEY (" + BAMBINO + ", " + GENITORE + ", " + PERSONAGGIO + "))");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PREMI + " ("
                    + BAMBINO + " TEXT, "
                    + GENITORE + " TEXT, "
                    + CORRETTI + " INTEGER, "
                    + NUMERO_PREMI + " INTEGER, "
                    + "PRIMARY KEY (BAMBINO, GENITORE))");
        }
        db.execSQL("CREATE TABLE IF NOT EXISTS " + APPUNTAMENTI_FISSATI + " ("
                + email_logopedista + " TEXT, "
                + email_genitore + " TEXT, "
                + DATA + " TEXT, "
                + ORA + " TEXT, "
                + "PRIMARY KEY (" + DATA + ", " + ORA + "), "
                + "FOREIGN KEY (" + email_logopedista + ") REFERENCES " + TABLE_NAME + "(" + EMAIL + "),"
                + "FOREIGN KEY (" + email_genitore + ") REFERENCES " + TABLE_NAME + "(" + EMAIL + ")"
                + ")");
if(oldVersion<11){


    db.execSQL("CREATE TABLE IF NOT EXISTS " + LUOGO_LAVORO_LOGOPEDISTA + "("
            + EMAIL + " TEXT,"
            + NOME_LUOGO +" TEXT,"
            + INDIRIZZO +" TEXT,"
            + "PRIMARY KEY (" + INDIRIZZO+"),"
            + "FOREIGN KEY ("+ EMAIL + ") REFERENCES " + TABLE_NAME + "(" + EMAIL + ")"
            + ")");








}
if(oldVersion<12){
    db.execSQL("ALTER TABLE " + APPUNTAMENTI_FISSATI + " ADD COLUMN LUOGO_INCONTRO TEXT");


    db.execSQL("ALTER TABLE " + APPUNTAMENTI_FISSATI + " ADD COLUMN INDIRIZZO TEXT");
}


    }


private ArrayList<String> generaDate(){

        ArrayList<String> date=new ArrayList<String>();
Calendar calendar=Calendar.getInstance();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
for(int i=0;i<5*30;i++) {


    Date data=calendar.getTime();
    date.add(sdf.format(data));

    calendar.add(Calendar.DAY_OF_YEAR,1);

}

return date;

}


private ArrayList<String> generaFasceOrarie(){

        return new ArrayList<>( Arrays.asList("09:00-10:00","10:00-11:00","11:00-12:00","12:00-13:00","15:00-16:00","16:00-17:00"));

}


    public void popolaTabellaAppuntamenti() {
        ArrayList<Utente> logopedisti = this.getLogopedisti();
        try(SQLiteDatabase db = this.getWritableDatabase()) {



            ArrayList<String> date = this.generaDate();
            ArrayList<String> fasceOrarie = this.generaFasceOrarie();

            for (Utente logopedista : logopedisti) {
                for (String data : date) {
                    for (String ora : fasceOrarie) {
                        ContentValues cv = new ContentValues();
                        cv.put(email_logopedista, logopedista.getEmail());
                        cv.put(DATA, data);
                        cv.put(ORA, ora);
                        cv.put(email_genitore,"");
                        cv.put(ISBOOKED, false);

                        long result = db.insert(CALENDARIO, null, cv);

                        if (result == -1) {
                            Log.e("Errore nell'inserimento della tupla", cv.toString());
                        } else {
                            Log.i("Inserimento avvenuto con successo", cv.toString());
                        }
                    }
                }
            }

        }  catch (Exception e){
            Log.e("Errore durante l'operazione sul database", e.getMessage()+e.getLocalizedMessage());
        }
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

    public void addBambino(String nomeBambino,String emailGenitore){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(NOME_BAMBINO,nomeBambino);
        values.put(EMAIL_GENITORE,emailGenitore);
        long result=db.insert(TABLE_BAMBINI,null,values);
        if (result == -1) {
            Log.e(TAG, "Errore nell'inserimento della terapia");
        } else {
            Log.d(TAG, "Terapia inserita con successo, ID: " + result);
        }
        db.close();


    }

    public void inserisciAppuntamentifissati(String email_genitore,String email_logopedista,String data,String ora,String luogo,String indirizzo){




        try(SQLiteDatabase db=this.getWritableDatabase()){
            ContentValues cv=new ContentValues();
            cv.put("email_genitore",email_genitore);
            cv.put("email_logopedista",email_logopedista);
            cv.put("DATA",data);
            cv.put("ORA",ora);
            cv.put("LUOGO_INCONTRO",luogo);
            cv.put("INDIRIZZO",indirizzo);
            long result= db.insert(APPUNTAMENTI_FISSATI,null,cv);
            if (result == -1) {
                Log.e(TAG, "Errore nell'inserimento appuntamento");
            } else {
                Log.d(TAG, "appuntamento inserito con successo, ID: " + result);
            }

        }



    }


    public List<itemAppuntamento> getInfoAppuntamentiPassati(String email_genitore){
        ArrayList<itemAppuntamento> infoAppuntamento = new ArrayList<>();
        Calendar calendar=Calendar.getInstance();
        Date dataCompleta=calendar.getTime();
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
SimpleDateFormat sdf2=new SimpleDateFormat("HH:mm",Locale.getDefault());
  String dataAttuale=sdf.format(dataCompleta);
String oraAttuale=sdf2.format(dataCompleta);

        String query = "SELECT " + TABLE_NAME + ".NOME, " + TABLE_NAME + ".COGNOME, " +
                APPUNTAMENTI_FISSATI + ".DATA, " + APPUNTAMENTI_FISSATI + ".ORA " +
                "FROM " + TABLE_NAME +
                " JOIN " + APPUNTAMENTI_FISSATI + " ON " +
                TABLE_NAME + ".EMAIL = " + APPUNTAMENTI_FISSATI + ".email_logopedista " +
                "WHERE "+APPUNTAMENTI_FISSATI + ".email_genitore = ?";

        String[] valori={email_genitore};

try(SQLiteDatabase db=this.getReadableDatabase()){

    Cursor cursor= db.rawQuery(query,valori);
    if(cursor.moveToFirst()){
    do{
     String nome=   cursor.getString(cursor.getColumnIndexOrThrow("NOME"));
    String cognome=    cursor.getString(cursor.getColumnIndexOrThrow("COGNOME"));
     String Data=   cursor.getString(cursor.getColumnIndexOrThrow("DATA"));
      String Ora=  cursor.getString(cursor.getColumnIndexOrThrow("ORA"));

      String[] splitted=Ora.split("-");
        if (splitted.length == 2) {
            String oraFinale = splitted[1].trim();


            if (Data.compareTo(dataAttuale) < 0) {

                itemAppuntamento item = new itemAppuntamento(nome + " " + cognome, Data, Ora);
                infoAppuntamento.add(item);
            } else if (Data.equals(dataAttuale)) {

                if (oraFinale.compareTo(oraAttuale) < 0) {
                    itemAppuntamento item = new itemAppuntamento(nome + " " + cognome, Data, Ora);
                    infoAppuntamento.add(item);
                }
            }
        }
    }while (cursor.moveToNext());

    }



cursor.close();
}

return infoAppuntamento;

    }







    public List<itemAppuntamento> getInfoAppuntamentoFissato(String email_genitore) {
        ArrayList<itemAppuntamento> infoAppuntamento = new ArrayList<>();
        String query = "SELECT " + TABLE_NAME + ".NOME, " + TABLE_NAME + ".COGNOME, " +
                APPUNTAMENTI_FISSATI + ".DATA, " + APPUNTAMENTI_FISSATI + ".ORA, " +
                LUOGO_LAVORO_LOGOPEDISTA + ".INDIRIZZO, " +
                APPUNTAMENTI_FISSATI + ".LUOGO_INCONTRO " +  // Assicurati che LUOGO_INCONTRO sia una colonna nella tabella APPUNTAMENTI_FISSATI
                "FROM " + TABLE_NAME + " " +
                "JOIN " + APPUNTAMENTI_FISSATI + " ON " +
                TABLE_NAME + ".EMAIL = " + APPUNTAMENTI_FISSATI + ".email_logopedista " +
                "JOIN " + LUOGO_LAVORO_LOGOPEDISTA + " ON " +
                LUOGO_LAVORO_LOGOPEDISTA + ".EMAIL = " + APPUNTAMENTI_FISSATI + ".email_logopedista " +
                "WHERE " + APPUNTAMENTI_FISSATI + ".email_genitore = ?";

        String[] valori = {email_genitore};
        try (SQLiteDatabase db = this.getReadableDatabase()) {
            Cursor cursor = db.rawQuery(query, valori);

            if (cursor.moveToFirst()) {

                do {

                    String nome = cursor.getString(cursor.getColumnIndexOrThrow("NOME"));
                    String cognome = cursor.getString(cursor.getColumnIndexOrThrow("COGNOME"));

                    String Data = cursor.getString(cursor.getColumnIndexOrThrow("DATA"));
                    String ora = cursor.getString(cursor.getColumnIndexOrThrow("ORA"));
                    String luogoIncontro = cursor.getString(cursor.getColumnIndexOrThrow("LUOGO_INCONTRO"));
                    String indirizzo = cursor.getString(cursor.getColumnIndexOrThrow("INDIRIZZO"));
                    itemAppuntamento item = new itemAppuntamento(nome + " " + cognome, Data + " ", ora,luogoIncontro,indirizzo);
                    infoAppuntamento.add(item);
                } while (cursor.moveToNext());

            }
            cursor.close();


        }
        return infoAppuntamento;
    }
    public List<String> getLuogoIncontro(String email_logopedista){

        ArrayList<String> info=new ArrayList<>();
        String query = "SELECT * FROM " + LUOGO_LAVORO_LOGOPEDISTA + " WHERE " + EMAIL + " = ?";


        String[] valori={email_logopedista};
        try(SQLiteDatabase db=this.getReadableDatabase()){
            Cursor cursor=db.rawQuery(query,valori);

            if(cursor.moveToFirst()){

                do{

                    String luogo=cursor.getString(cursor.getColumnIndexOrThrow("NOME_LUOGO"));
                    String indirizzo=cursor.getString(cursor.getColumnIndexOrThrow("INDIRIZZO"));
                    info.add(luogo);
                    info.add(indirizzo);
                }while(cursor.moveToNext());

            }
            cursor.close();

        }

        return info;

    }




    public List<itemAppuntamento> getInfoAppuntamentoPendente(String email_logopedista){

        ArrayList<itemAppuntamento> infoAppuntamento=new ArrayList<>();
        String query = "SELECT " + TABLE_NAME + ".NOME, "+TABLE_NAME + ".COGNOME," + CALENDARIO + ".DATA, " + CALENDARIO + ".ORA " +
                "FROM " + TABLE_NAME + " JOIN " + CALENDARIO + " ON " +
                TABLE_NAME + ".EMAIL = " + CALENDARIO + ".email_genitore " +
                "WHERE " + CALENDARIO + ".email_logopedista = ? AND " + CALENDARIO + ".ISBOOKED = 1;";

        String[] valori={email_logopedista};
        try(SQLiteDatabase db=this.getReadableDatabase()){
            Cursor cursor=db.rawQuery(query,valori);

            if(cursor.moveToFirst()){

               do{

                   String nome=cursor.getString(cursor.getColumnIndexOrThrow("NOME"));
                   String cognome=cursor.getString(cursor.getColumnIndexOrThrow("COGNOME"));

                   String Data=cursor.getString(cursor.getColumnIndexOrThrow("DATA"));
                   String ora=cursor.getString(cursor.getColumnIndexOrThrow("ORA"));
                   itemAppuntamento item=new itemAppuntamento(nome+" "+cognome,Data+" ",ora);
                   infoAppuntamento.add(item);
               }while(cursor.moveToNext());

            }
cursor.close();

            }

return infoAppuntamento;

    }

    public String getLogopedista(String bambino, String genitore){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String logopedista = null;

        if(db!=null){
            cursor = db.rawQuery("SELECT " + email_logopedista + " FROM " + TABLE_TERAPIE
                    + " WHERE " + email_genitore + " = ? AND " + nome_bambino + " = ?", new String[]{genitore, bambino});
        }

        while (cursor.moveToNext()){
            logopedista = cursor.getString(0);
        }

        return logopedista;
    }


    public  void SetUnBooked(String data,String ora){

        String query = "UPDATE CALENDARIO SET ISBOOKED = 0, email_genitore = ? " +
                "WHERE DATA = ? " +
                "AND ORA = ?";

        String[] valori={"",data,ora};


        try (SQLiteDatabase db = this.getWritableDatabase()) {

            db.execSQL(query, valori);
            Log.d("Update", "Prenotazione eliminata con successo.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Update", "Errore durante l'aggiornamento: " + e.getMessage());
        }


    }

    public void SetBooked(String email_logopedista,String data,String ora,String email_genitore){

        String query = "UPDATE CALENDARIO SET ISBOOKED = 1, email_genitore = ? " +
                "WHERE email_logopedista = ? " +
                "AND DATA = ? " +
                "AND ORA = ?";

        String[] valori={email_genitore,email_logopedista,data,ora};


        try (SQLiteDatabase db = this.getWritableDatabase()) {

            db.execSQL(query, valori);
            Log.d("Update", "Prenotazione aggiornata con successo.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Update", "Errore durante l'aggiornamento: " + e.getMessage());
        }

    }



    public ArrayList<Bambino> getBambiniByEmail(String emailGenitore) {
        ArrayList<Bambino> bambini = new ArrayList<>();
       try (SQLiteDatabase db = this.getReadableDatabase()) {


           Cursor cursor = db.query(TABLE_BAMBINI, new String[]{"ID", NOME_BAMBINO}, EMAIL_GENITORE + " = ?", new String[]{emailGenitore}, null, null, null);

           if (cursor.moveToFirst()) {
               do {
                   String nome = cursor.getString(1);
                   int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                   Bambino bambino = new Bambino(id, nome);
                   bambini.add(bambino);
               } while (cursor.moveToNext());
           }
           cursor.close();
       }catch (Exception e){
           Log.e("errore nell'apertura del db",e.getMessage());
       }

        return bambini;
    }


    public ArrayList<String> recuperaOrariDisponibili(String email, String data) {
        ArrayList<String> orari = new ArrayList<>();
        String query = "SELECT ORA FROM CALENDARIO WHERE email_logopedista=? AND DATA=? AND ISBOOKED=false ";
        String[] emailslogopedisti = {email, data};

        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery(query, emailslogopedisti)) {


            if (cursor.moveToFirst()) {
                do {

                    String ore = cursor.getString(cursor.getColumnIndexOrThrow("ORA"));
                    orari.add(ore);
                } while (cursor.moveToNext());
            } else {
                Log.d("recuperaOrariDisponibili", "Nessun orario trovato per la data: " + data);
            }

        } catch (Exception e) {
            Log.e("errore nell'apertura del db", e.getMessage());
        }

        return orari;
    }



    public ArrayList<Utente> getLogopedisti() {
        ArrayList<Utente> logopedisti = new ArrayList<>();
        try(SQLiteDatabase db = this.getReadableDatabase()) { // Ottieni un'istanza del database

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
                    Utente logopedista = new Utente(email, nome, cognome, telefono, password, true);

                    // Aggiungi alla lista
                    logopedisti.add(logopedista);

                } while (cursor.moveToNext());
            }

            cursor.close(); // Chiudi il Cursor
        }catch(Exception e){
           Log.e("errore nell'apertura del db",e.getMessage());
            }

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
    public void AddInfoLavoroLogopedista(String nomeLuogo,String Indirizzo,String email){

        try(SQLiteDatabase db=this.getWritableDatabase()){
            ContentValues cv=new ContentValues();
            cv.put("EMAIL",email);
            cv.put("NOME_LUOGO",nomeLuogo);
            cv.put("INDIRIZZO",Indirizzo);
Long result=db.insert(LUOGO_LAVORO_LOGOPEDISTA,null,cv);
if(result!=-1){
    Log.i("Riga inserita con successo","ok");
}

        }catch (SQLiteCantOpenDatabaseException e){
            Log.e(e.getMessage(),e.getLocalizedMessage());
        }



    }


    public ArrayList<Giocatore> getGiocatori(String med){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        ArrayList<Giocatore> giocatori = new ArrayList<>();

        if(db!=null){
            cursor = db.rawQuery("SELECT " + nome_bambino+ ","+ email_genitore+ ","+ email_logopedista + " FROM " + TABLE_TERAPIE + " WHERE " + email_logopedista + " = ?", new String[]{med});
        }

        while (cursor.moveToNext()){
            String bambino = cursor.getString(0);
            String genitore = cursor.getString(1);
            String logopedista = cursor.getString(2);

            Giocatore giocatore = new Giocatore(bambino, genitore, logopedista, 0);
            giocatori.add(giocatore);
        }

        return giocatori;
    }

    public boolean addUser(Utente utente) {
        this.popolaTabellaAppuntamenti();

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

    public Utente getUtente(String userEmail){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        Utente utente = null;

        if(db != null){
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ?", new String[]{userEmail});
        }

        while (cursor.moveToNext()){
            String email = cursor.getString(0);
            String nome = cursor.getString(1);
            String cognome = cursor.getString(2);
            utente = new Utente(email, nome, cognome, null, null, false);
        }

        return utente;

    }

    /*public Bitmap getAllImages(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Bitmap bt = null;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_DENOMINAZIONE +" WHERE ID = ?", new String[]{String.valueOf(id)});

        if(cursor.moveToNext()){
            byte[] image = cursor.getBlob(4);
            bt = BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        return bt;
    }*/

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

            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);


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

        cursor.close();
        return pazienti;

    }

    public ArrayList<Esercizio> readExercises(String user, String child){

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
            cursor = db.rawQuery("SELECT * FROM " + TABLE_ESERCIZI + " WHERE EMAIL = ? AND BAMBINO = ?", new String[]{user, child});

            Log.d(TAG, "readExercises: Esecuzione query");
        }


        while(cursor.moveToNext()){
            String email = cursor.getString(1);
            String bambino = cursor.getString(2);
            String titolo = cursor.getString(3).replace("+", " ");
            String tipo = cursor.getString(4);

            esercizi.add(new Esercizio(email, bambino, titolo, tipo, null, null, null, null, 0, 0, 0));

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
        contentValues.put(BAMBINO, esercizio.getBambino());
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
        contentValues.put(BAMBINO, esercizio.getBambino());
        contentValues.put(TITOLO, esercizio.getName().replace("+", " "));
        contentValues.put(TIPO, esercizio.getTipo());
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
        contentValues.put(BAMBINO, esercizio.getBambino());
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
        contentValues.put(BAMBINO, esercizio.getBambino());
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

    public boolean addPremio(String bambino, String genitore, int corretti, int premio){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BAMBINO, bambino);
        values.put(GENITORE, genitore);
        values.put(CORRETTI, corretti);
        values.put(NUMERO_PREMI, premio);

        long result = db.insert(TABLE_PREMI, null, values);
        return result != -1;
    }

    public int getCorretti(String bambino, String genitore){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(db!=null){
            cursor = db.rawQuery("SELECT CORRETTI FROM " + TABLE_PREMI + " WHERE BAMBINO = ? AND GENITORE = ?", new String[]{bambino, genitore});

        }

        int corretti = 0;

        while(cursor.moveToNext()){
            corretti = cursor.getInt(0);
        }

        return corretti;
    }

    public int getPremi(String bambino, String genitore){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(db!=null){
            cursor = db.rawQuery("SELECT NUMERO_PREMI FROM " + TABLE_PREMI + " WHERE BAMBINO = ? AND GENITORE = ?", new String[]{bambino, genitore});
        }

        int premi = 0;

        while(cursor.moveToNext()){
            premi = cursor.getInt(0);
        }

        return premi;
    }

    public void updateValuesPremi(String bambino, String genitore, int corretti, int premi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BAMBINO, bambino);
        values.put(GENITORE, genitore);
        values.put(CORRETTI, corretti);
        values.put(NUMERO_PREMI, premi);

        db.update(TABLE_PREMI, values, "BAMBINO = ? AND GENITORE = ?", new String[]{bambino, genitore});
    }

    public boolean addResoconto(Resoconto resoconto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(BAMBINO, resoconto.getBambino());
        contentValues.put(GENITORE, resoconto.getGenitore());
        contentValues.put(LOGOPEDISTA, resoconto.getLogopedista());
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



    public boolean addAcquisto(Personaggio personaggio, String bambino, String genitore){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(BAMBINO, bambino);
        contentValues.put(GENITORE, genitore);
        contentValues.put(PERSONAGGIO, personaggio.getNome());
        contentValues.put(VALORE, personaggio.getValore());

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        personaggio.getImmagine().compress(Bitmap.CompressFormat.PNG, 40, stream);
        byte[] picPersonaggio = stream.toByteArray();

        contentValues.put(IMMAGINE, picPersonaggio);

        long result = db.insert(TABLE_ACQUISTI, null, contentValues);
        return  result != -1;
    }

    public int getSpesa(String child, String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        ArrayList<Integer> valori = new ArrayList<>();

        if(db!= null){
            cursor = db.rawQuery("SELECT * FROM " + TABLE_ACQUISTI + " WHERE BAMBINO = ? AND GENITORE = ?", new String[]{child, user});

        }

        int spesa = 0;

        while(cursor.moveToNext()) {

            int valore = cursor.getInt(3);
            valori.add(valore);

        }

        for (int i = 0; i < valori.size(); i++) {
            spesa += valori.get(i);
        }
        return spesa;
    }

    public ArrayList<String> getPersonaggi(String child, String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        ArrayList<String> personaggi = new ArrayList<>();

        if(db!=null){
            cursor = db.rawQuery("SELECT * FROM " + TABLE_ACQUISTI + " WHERE BAMBINO = ? AND GENITORE = ?", new String[]{child, user});
        }

        while(cursor.moveToNext()){

            String personaggio = cursor.getString(2);


            personaggi.add(personaggio);

        }
        return personaggi;
    }


    public ArrayList<Resoconto> getResoconto(String user, String child){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        ArrayList<Resoconto> resoconti  = new ArrayList<>();

        if (user == null) {
            Log.e("DBHelper", "Il valore di genitore è null");
            return resoconti; // Restituisce un array vuoto
        }

        if(db!= null){
            cursor = db.rawQuery("SELECT * FROM " + TABLE_RESOCONTO + " WHERE GENITORE = ? AND BAMBINO = ?", new String[]{user, child});
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

            Esercizio esercizio = new Esercizio(genitore, bambino, titolo, tipo, null, null, null, null, giorno, mese, anno);

            Resoconto resoconto = new Resoconto(bambino, genitore, logopedista, esercizio, punteggio, corretto, sbagliato, aiuti);
            resoconti.add(resoconto);
        }
        return resoconti;
    }



    public ArrayList<Esercizio> getDenominazione(String user, String child, int day, int month, int year){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        ArrayList<Esercizio> esercizi = new ArrayList<>();
        Log.d(TAG, "denominazione: Array creato");

        if (user == null) {
            Log.e(TAG, "User cannot be null");
            return new ArrayList<>(); // Restituisce un array vuoto
        }

        if(db!=null){
            Log.d(TAG, "denominazione: Entrato");
            cursor = db.rawQuery("SELECT * FROM " + TABLE_DENOMINAZIONE + " WHERE EMAIL = ? AND BAMBINO = ? AND GIORNO = ? AND MESE = ? AND ANNO = ?", new String[]{user, child, String.valueOf(day), String.valueOf(month), String.valueOf(year)});

            Log.d(TAG, "denominazione: Esecuzione query");
        }


        while(cursor.moveToNext()){

            Log.d(TAG, "getDenominazione: " + cursor.getString(1));
            Log.d(TAG, "getDenominazione: " + cursor.getString(2));

            String email = cursor.getString(1);
            String bambino = cursor.getString(2);
            String titolo = cursor.getString(3).replace("+", " ");
            String tipo = cursor.getString(4);
            String immagine1 = cursor.getString(5);
            String aiuto = cursor.getString(6);
            int giorno = cursor.getInt(7);
            int mese = cursor.getInt(8);
            int anno = cursor.getInt(9);


            esercizi.add(new Esercizio(email, bambino, titolo, tipo, immagine1, null, aiuto, null, giorno, mese, anno));
            Log.d(TAG, "readExercises: " + cursor.getString(1));
            Log.d(TAG, "readExercises: " + cursor.getString(2));
        }
        return esercizi;
    }

    public ArrayList<Esercizio> getSequenza(String user, String child, int day, int month, int year){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        ArrayList<Esercizio> esercizi = new ArrayList<>();
        Log.d(TAG, "denominazione: Array creato");

        if (user == null) {
            Log.e(TAG, "User cannot be null");
            return new ArrayList<>(); // Restituisce un array vuoto
        }

        if(db!=null){
            Log.d(TAG, "denominazione: Entrato");
            cursor = db.rawQuery("SELECT * FROM " + TABLE_SEQUENZA + " WHERE EMAIL = ? AND BAMBINO = ? AND GIORNO = ? AND MESE = ? AND ANNO = ?", new String[]{user, child, String.valueOf(day), String.valueOf(month), String.valueOf(year)});

            Log.d(TAG, "denominazione: Esecuzione query");
        }


        while(cursor.moveToNext()){


            String email = cursor.getString(1);
            String bambino = cursor.getString(2);
            String titolo = cursor.getString(3).replace("+", " ");
            String tipo = cursor.getString(4);

            String[] sequenza = new String[3];
            sequenza[0] = cursor.getString(5);
            sequenza[1] = cursor.getString(6);
            sequenza[2] = cursor.getString(7);

            Log.d(TAG, "getSequenza: " + sequenza[0]);
            Log.d(TAG, "getSequenza: " + sequenza[1]);
            Log.d(TAG, "getSequenza: " + sequenza[2]);

            int giorno = cursor.getInt(8);
            int mese = cursor.getInt(9);
            int anno = cursor.getInt(10);


            esercizi.add(new Esercizio(email, bambino, titolo, tipo, null, null, null, sequenza, giorno, mese, anno));

        }
        return esercizi;
    }

    public ArrayList<Esercizio> getCoppia(String user, String child, int day, int month, int year){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        ArrayList<Esercizio> esercizi = new ArrayList<>();
        if (user == null) {
            Log.e(TAG, "User cannot be null");
            return new ArrayList<>(); // Restituisce un array vuoto
        }



        if(db!=null){

            cursor = db.rawQuery("SELECT * FROM " + TABLE_COPPIA + " WHERE EMAIL = ? AND BAMBINO = ? AND GIORNO = ? AND MESE = ? AND ANNO = ?", new String[]{user, child, String.valueOf(day), String.valueOf(month), String.valueOf(year)});
      }


        while(cursor.moveToNext()){


            String email = cursor.getString(1);
            String bambino = cursor.getString(2);
            String titolo = cursor.getString(3).replace("+", " ");
            String tipo = cursor.getString(4);

            String immagine1 = cursor.getString(5);
            String immagine2 = cursor.getString(6);
            String aiuto = cursor.getString(7);

            int giorno = cursor.getInt(8);
            int mese = cursor.getInt(9);
            int anno = cursor.getInt(10);


            esercizi.add(new Esercizio(email, bambino, titolo, tipo, immagine1, immagine2, aiuto, null, giorno, mese, anno));

        }
        return esercizi;
    }

}
