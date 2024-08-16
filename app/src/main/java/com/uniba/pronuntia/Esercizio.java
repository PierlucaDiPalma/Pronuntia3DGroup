package com.uniba.pronuntia;

import android.graphics.Bitmap;

public class Esercizio {

    private String email;
    private String name;
    private String tipo;
    private byte[] immagine1;
    private byte[] immagine2;
    private String aiuto;
    private String[] sequenza = new String[3];
    private int giorno, mese, anno;

    public Esercizio(String email, String name, String tipo, byte[] immagine1, byte[] immagine2, String aiuto, String[] sequenza, int giorno, int mese, int anno) {
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
