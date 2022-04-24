package com.udb.edu.joyeria_commerce.datos;

public class Producto {
    private String nombre;
    private String marca;
    private String material;
    private String detalle;
    private Double precio;
    private String imagen;
    String key;

    public Producto(){

    }

    public Producto(String nombre, String mcarca, String material, String detalle, Double precio, String imagen){
        this.nombre = nombre;
        this.marca = marca;
        this.material = material;
        this.detalle = detalle;
        this.precio = precio;
        this.imagen = imagen;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMaterial() { return material; }
    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getKey() { return key; }
    public void setKey(String key) {
        this.key = key;
    }

}
