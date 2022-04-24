package com.udb.edu.joyeria_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udb.edu.joyeria_commerce.datos.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductosActivity extends AppCompatActivity {

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference refProductos = database.getReference("productos");

    List<Producto> productos;
    ListView listaProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        inicializar();
    }

    public void inicializar(){
        listaProductos = findViewById(R.id.ListaProductos);

        // Mostrar lista de productos ingresados en la base
        productos = new ArrayList<>();

        refProductos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productos.removeAll(productos);
                for(DataSnapshot dato: snapshot.getChildren())
                {
                    Producto producto = dato.getValue(Producto.class);
                    producto.setKey(dato.getKey());
                    productos.add(producto);
                }

                AdaptadorProducto adapter = new AdaptadorProducto(ProductosActivity.this, productos);
                listaProductos.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}