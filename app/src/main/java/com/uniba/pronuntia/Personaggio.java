package com.uniba.pronuntia;

import android.graphics.Bitmap;

public class Personaggio {

    private String nome;
    private int valore;
    private Bitmap immagine;

    public Personaggio(String nome, int valore, Bitmap immagine) {
        this.nome = nome;
        this.valore = valore;
        this.immagine = immagine;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getValore() {
        return valore;
    }

    public void setValore(int valore) {
        this.valore = valore;
    }

    public Bitmap getImmagine() {
        return immagine;
    }

    public void setImmagine(Bitmap immagine) {
        this.immagine = immagine;
    }
}
