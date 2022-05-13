package com.udb.edu.joyeria_commerce.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udb.edu.joyeria_commerce.AdaptadorProducto;
import com.udb.edu.joyeria_commerce.CarritoFragment;
import com.udb.edu.joyeria_commerce.FiltroFragment;
import com.udb.edu.joyeria_commerce.R;
import com.udb.edu.joyeria_commerce.datos.Producto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductosFragment extends Fragment {

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference refProductos = database.getReference("productos");

    private List<Producto> productos;
    private ListView listaProductos;
    ImageButton ib;


//    String nombre;
//    SharedPreferences settings;

    public ProductosFragment() {
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
         View vista=inflater.inflate(R.layout.fragment_productos, container, false);

         ib = vista.findViewById(R.id.btnBusqueda);
         ib.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 FiltroFragment detalle = new FiltroFragment();
                 getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,detalle).commit();
             }
         });
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        settings = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
//
//        nombre=settings.getString("email","");


        listaProductos = view.findViewById(R.id.ListaProductos);


        listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {

                //Paso de datos del producto seleccionado a fragment_detalle_joya
                Bundle bundle = new Bundle();
                bundle.putString("nombreProducto", productos.get(position).getNombre());
                bundle.putString("precioProducto", productos.get(position).getPrecio().toString());
                bundle.putString("detalleProducto", productos.get(position).getDetalle());
                bundle.putString("imagenProducto", productos.get(position).getImagen());
                getParentFragmentManager().setFragmentResult("key", bundle);

                //Cambio de vista a fragment_detalle_joya
                DetalleJoyaFragment detalle = new DetalleJoyaFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,detalle).commit();

            }
        });


    }
    public void buscar(View v){
        FiltroFragment filtro = new FiltroFragment();
        getFragmentManager().beginTransaction().replace(R.id.filtroFragment,filtro).commit();
    }

    public void cambioPantallaCarrito(View v){
        CarritoFragment carrito = new CarritoFragment();
        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,carrito).commit();
    }

    public void inicializar(){

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

                AdaptadorProducto adapter = new AdaptadorProducto((Activity) getContext(), productos);
                listaProductos.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}