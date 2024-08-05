package com.uniba.pronuntia;

public class Denominazione extends Esercizio{
    private String tipo;
    public Denominazione(String email, String name) {
        super(email, name);
        this.tipo = "Denominazione";
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
