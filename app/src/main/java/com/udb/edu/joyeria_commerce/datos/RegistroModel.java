package com.udb.edu.joyeria_commerce.datos;

public class RegistroModel {
    private int id;
    private String direccion;
    private String numeroTarjeta;
    private String pinTarjeta;
    private String total;
    String key;

    public RegistroModel(){

    }

    public RegistroModel(int id, String direccion, String numeroTarjeta, String pinTarjeta, String total){
        this.id = id;
        this.direccion = direccion;
        this.numeroTarjeta = numeroTarjeta;
        this.pinTarjeta = pinTarjeta;
        this.total = total;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }

    public String getPinTarjeta() { return pinTarjeta; }
    public void setPinTarjeta(String pinTarjeta) { this.pinTarjeta = pinTarjeta; }

    public String getTotal() { return total; }
    public void setTotal(String total) { this.total = total; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
}
