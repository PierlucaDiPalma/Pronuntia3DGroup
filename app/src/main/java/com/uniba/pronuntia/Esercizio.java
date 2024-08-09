package com.uniba.pronuntia;

public class Esercizio {

    private String email;
    private String name;
    private String tipo;
    private byte[] immagine;
    private String aiuto;
    private int giorno, mese, anno;

    public Esercizio(String email, String name, String tipo, byte[] immagine, String aiuto, int giorno, int mese, int anno) {
        this.email = email;
        this.name = name;
        this.tipo = tipo;
        this.immagine = immagine;
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

    public byte[] getImmagine() {
        return immagine;
    }

    public void setImmagine(byte[] immagine) {
        this.immagine = immagine;
    }

    public String getAiuto() {
        return aiuto;
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
