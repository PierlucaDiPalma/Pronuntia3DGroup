package com.uniba.pronuntia;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Resoconto implements Parcelable {

    private String bambino;
    private String genitore;
    private String logopedista;
    private Esercizio esercizio;
    private int punteggio;
    private int corretti;
    private int sbagliati;
    private int aiuti;

    public Resoconto(String bambino, String genitore, String logopedista, Esercizio esercizio, int punteggio, int corretti, int sbagliati, int aiuti) {
        this.bambino = bambino;
        this.genitore = genitore;
        this.logopedista = logopedista;
        this.esercizio = esercizio;
        this.punteggio = punteggio;
        this.corretti = corretti;
        this.sbagliati = sbagliati;
        this.aiuti = aiuti;
    }

    protected Resoconto(Parcel in) {
        bambino = in.readString();
        genitore = in.readString();
        logopedista = in.readString();
        esercizio = in.readParcelable(Esercizio.class.getClassLoader());
        punteggio = in.readInt();
        corretti = in.readInt();
        sbagliati = in.readInt();
        aiuti = in.readInt();
    }

    public static final Creator<Resoconto> CREATOR = new Creator<Resoconto>() {
        @Override
        public Resoconto createFromParcel(Parcel in) {
            return new Resoconto(in);
        }

        @Override
        public Resoconto[] newArray(int size) {
            return new Resoconto[size];
        }
    };

    public String getBambino() {
        return bambino;
    }

    public void setBambino(String bambino) {
        this.bambino = bambino;
    }

    public String getGenitore() {
        return genitore;
    }

    public void setGenitore(String genitore) {
        this.genitore = genitore;
    }

    public String getLogopedista() {
        return logopedista;
    }

    public void setLogopedista(String logopedista) {
        this.logopedista = logopedista;
    }

    public Esercizio getEsercizio() {
        return esercizio;
    }

    public void setEsercizio(Esercizio esercizio) {
        this.esercizio = esercizio;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

    public int getCorretti() {
        return corretti;
    }

    public void setCorretti(int corretti) {
        this.corretti = corretti;
    }

    public int getSbagliati() {
        return sbagliati;
    }

    public void setSbagliati(int sbagliati) {
        this.sbagliati = sbagliati;
    }

    public int getAiuti() {
        return aiuti;
    }

    public void setAiuti(int aiuti) {
        this.aiuti = aiuti;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(bambino);
        parcel.writeString(genitore);
        parcel.writeString(logopedista);
        parcel.writeParcelable(esercizio, i);
        parcel.writeInt(punteggio);
        parcel.writeInt(corretti);
        parcel.writeInt(sbagliati);
        parcel.writeInt(aiuti);
    }
}
