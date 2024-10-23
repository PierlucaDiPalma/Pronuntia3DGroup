package com.uniba.pronuntia;

public class Giocatore {

    private String bambino;
    private String genitore;
    private String logopedista;
    private int punteggio;

    public Giocatore(String bambino, String genitore, String logopedista, int punteggio) {
        this.bambino = bambino;
        this.genitore = genitore;
        this.logopedista = logopedista;
        this.punteggio = punteggio;
    }

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

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }
}
