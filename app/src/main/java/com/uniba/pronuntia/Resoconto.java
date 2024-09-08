package com.uniba.pronuntia;

public class Resoconto {

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
}
