package com.uniba.pronuntia;

public class Personaggio {

    private String nome;
    private int valore;
    private byte[] immagine;

    public Personaggio(String nome, int valore, byte[] immagine) {
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

    public byte[] getImmagine() {
        return immagine;
    }

    public void setImmagine(byte[] immagine) {
        this.immagine = immagine;
    }
}
