package com.uniba.pronuntia;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Esercizio implements Parcelable {

    private String email;
    private String name;
    private String tipo;
    private byte[] immagine1;
    private byte[] immagine2;
    private String aiuto;
    private String[] sequenza = new String[3];
    private int giorno, mese, anno;
    private String bambino;

    public Esercizio(String email, String bambino, String name, String tipo, byte[] immagine1, byte[] immagine2, String aiuto, String[] sequenza, int giorno, int mese, int anno) {
        this.email = email;
        this.bambino = bambino;
        this.name = name;
        this.tipo = tipo;
        this.immagine1 = immagine1;
        this.immagine2 = immagine2;
        this.sequenza = sequenza;
        this.aiuto = aiuto;
        this.giorno = giorno;
        this.mese = mese;
        this.anno = anno;

    }

    protected Esercizio(Parcel in) {
        email = in.readString();
        bambino = in.readString();
        name = in.readString();
        tipo = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            immagine1 = in.readBlob();
            immagine2 = in.readBlob();
        }

        aiuto = in.readString();
        sequenza = in.createStringArray();
        giorno = in.readInt();
        mese = in.readInt();
        anno = in.readInt();
    }

    public static final Creator<Esercizio> CREATOR = new Creator<Esercizio>() {
        @Override
        public Esercizio createFromParcel(Parcel in) {
            return new Esercizio(in);
        }

        @Override
        public Esercizio[] newArray(int size) {
            return new Esercizio[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBambino() {
       return bambino;
    }

    public void setBambino(String bambino) {
        this.bambino = bambino;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAiuto() {
        return aiuto;
    }

    public byte[] getImmagine1() {
        return immagine1;
    }

    public void setImmagine1(byte[] immagine1) {
        this.immagine1 = immagine1;
    }

    public byte[] getImmagine2() {
        return immagine2;
    }

    public void setImmagine2(byte[] immagine2) {
        this.immagine2 = immagine2;
    }

    public String[] getSequenza() {
        return sequenza;
    }


    public void setSequenza(String[] sequenza) {
        this.sequenza = sequenza;
    }

    public void setAiuto(String aiuto) {
        this.aiuto = aiuto;
    }

    public int getGiorno() {
        return giorno;
    }

    public void setGiorno(int giorno) {
        this.giorno = giorno;
    }

    public int getMese() {
        return mese;
    }

    public void setMese(int mese) {
        this.mese = mese;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(bambino);
        parcel.writeString(name);
        parcel.writeString(tipo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            parcel.writeBlob(immagine1);
            parcel.writeBlob(immagine2);
        }

        parcel.writeString(aiuto);
        parcel.writeStringArray(sequenza);
        parcel.writeInt(giorno);
        parcel.writeInt(mese);
        parcel.writeInt(anno);
    }
}
