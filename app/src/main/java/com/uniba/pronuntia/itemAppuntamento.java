package com.uniba.pronuntia;

public class itemAppuntamento {
    private String Ora;
    private String NomeGenitore;
    private String data;
private String Luogoincotro;
private String indirizzo;

    public itemAppuntamento( String NomeGenitore, String data,String Ora,String LuogoIncontro,String indirizzo) {
        this.Ora=Ora;
        this.NomeGenitore = NomeGenitore;
        this.data = data;
        this.Luogoincotro=LuogoIncontro;
        this.indirizzo=indirizzo;
    }
    public itemAppuntamento( String NomeGenitore, String data,String Ora) {
        this.Ora=Ora;
        this.NomeGenitore = NomeGenitore;
        this.data = data;

    }

    public String getOra() {
        return Ora;
    }

    public String getNomeGenitore() {
        return NomeGenitore;
    }

    public String getLuogoincotro() {
        return Luogoincotro;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getData() {
        return data;
    }

    public void setOra(String ora) {
        this.Ora = ora;
    }

    public void setNomeGenitore(String NomeGenitore) {
        this.NomeGenitore = NomeGenitore;
    }

    public void setData(String data) {
        this.data = data;
    }
}
