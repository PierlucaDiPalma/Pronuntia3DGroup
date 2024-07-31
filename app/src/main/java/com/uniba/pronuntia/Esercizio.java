package com.uniba.pronuntia;

public class Esercizio {

    private String email;
    private String name;
    private String tipo;

    public Esercizio(String email, String name, String tipo) {
        this.email = email;
        this.name = name;
        this.tipo = tipo;

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
}
