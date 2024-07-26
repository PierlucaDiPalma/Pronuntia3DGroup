package com.uniba.pronuntia;

public class Utente {
    private String email;
    private String nome;
    private String cognome;
    private String telefono;
    private String password;
    private boolean isLogopedista;


    public Utente(String email, String nome, String cognome, String telefono, String password, boolean isLogopedista) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.password = password;
        this.isLogopedista = isLogopedista;
    }

    public Utente() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogopedista(boolean logopedista) {
        isLogopedista = logopedista;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLogopedista() {
        return isLogopedista;
    }
}
