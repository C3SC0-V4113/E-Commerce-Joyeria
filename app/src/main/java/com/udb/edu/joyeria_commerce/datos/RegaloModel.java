package com.udb.edu.joyeria_commerce.datos;

public class RegaloModel {
    private String nombre;
    private String detalle;
    private String imagen;
    private Double precio;
    String key;

    public RegaloModel(){

    }

    public RegaloModel(String nombre, String detalle, String imagen, Double precio){
        this.nombre = nombre;
        this.detalle = detalle;
        this.imagen = imagen;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
}
