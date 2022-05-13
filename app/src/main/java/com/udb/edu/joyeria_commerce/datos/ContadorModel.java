package com.udb.edu.joyeria_commerce.datos;

public class ContadorModel {
    private int contador;
    String key;

    public ContadorModel(){

    }

    public ContadorModel(int contador)
    {
        this.contador = contador;

    }

    public int getContador() { return contador; }
    public void setContador(int contador) { this.contador = contador; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

}
