package com.udb.edu.joyeria_commerce;

public class ProductoModel {
    private String titulo;
    private String precio;

    public ProductoModel(String titulo,Double precio){
        this.titulo=titulo;
        this.precio= String.valueOf(precio);
    }

    public String getProductoTitulo(){
        return titulo;
    }

    public String getPrecio() {
        return precio;
    }
}
