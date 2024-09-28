package com.uniba.pronuntia;

public class Bambino {

        private int id; // ID univoco
        private String nome; // Nome del bambino

        // Costruttore
        public Bambino(int id, String nome) {
            this.id = id;
            this.nome = nome;
        }

        // Getters
        public int getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }
    }

