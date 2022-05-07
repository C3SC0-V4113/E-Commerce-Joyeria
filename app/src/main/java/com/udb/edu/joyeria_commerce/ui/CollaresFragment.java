package com.udb.edu.joyeria_commerce.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.udb.edu.joyeria_commerce.AdaptadorProducto;
import com.udb.edu.joyeria_commerce.R;
import com.udb.edu.joyeria_commerce.datos.Producto;

import java.util.ArrayList;
import java.util.List;

public class CollaresFragment extends Fragment {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference refProductos = database.getReference("productos");

    Query consultaCollares = refProductos.orderByChild("categoria").equalTo("Collar");

    private List<Producto> productos;
    private ListView listaCollares;

    public CollaresFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collares, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaCollares = view.findViewById(R.id.ListaCollares);
    }

    public void inicializar(){
        productos = new ArrayList<>();

        consultaCollares.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productos.removeAll(productos);
                for (DataSnapshot dato: snapshot.getChildren())
                {
                    Producto producto = dato.getValue(Producto.class);
                    producto.setKey(dato.getKey());
                    productos.add(producto);
                }

                AdaptadorProducto adapter = new AdaptadorProducto((Activity) getContext(), productos);
                listaCollares.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}