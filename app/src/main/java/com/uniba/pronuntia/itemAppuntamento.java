package com.uniba.pronuntia;

public class itemAppuntamento {
    private String Ora;
    private String NomeGenitore;
    private String data;


    public itemAppuntamento( String NomeGenitore, String data,String Ora) {
        this.Ora=Ora;
        this.NomeGenitore = NomeGenitore;
        this.data = data;
    }

    public String getOra() {
        return Ora;
    }

    public String getEmailGenitore() {
        return NomeGenitore;
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
