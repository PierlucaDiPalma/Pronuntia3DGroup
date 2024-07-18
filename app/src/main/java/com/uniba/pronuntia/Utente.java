package com.uniba.pronuntia;

public class Utente {
    public String email;
    public String nome;
    public String cognome;
    public String telefono;
    public String password;

    public Utente(String email, String nome, String cognome, String telefono, String password) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.password = password;
    }

    public Utente() {
    }
}
