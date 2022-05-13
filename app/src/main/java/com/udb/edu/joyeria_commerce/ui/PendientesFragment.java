package com.udb.edu.joyeria_commerce.ui;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.udb.edu.joyeria_commerce.AdaptadorProducto;
import com.udb.edu.joyeria_commerce.CarritoFragment;
import com.udb.edu.joyeria_commerce.FiltroFragment;
import com.udb.edu.joyeria_commerce.R;
import com.udb.edu.joyeria_commerce.RegistroComprasFragment;
import com.udb.edu.joyeria_commerce.datos.Producto;

import com.google.firebase.database.ValueEventListener;
import com.udb.edu.joyeria_commerce.AdaptadorRegalos;
import com.udb.edu.joyeria_commerce.R;
import com.udb.edu.joyeria_commerce.datos.RegaloModel;

import java.util.ArrayList;
import java.util.List;


import java.util.ArrayList;
import java.util.List;

public class PendientesFragment extends Fragment {


    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference refProductos = database.getReference("productos");

    Query consultaPendientes = refProductos.orderByChild("categoria").equalTo("Pendientes");

    private List<Producto> productos;
    private ListView listaPendientes;

    ImageButton btnBusqueda, btnCarrito, btnRegistroCompras;


    public PendientesFragment() {
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
        View vista=inflater.inflate(R.layout.fragment_pendientes, container, false);


        // Cambio a la vista de búsqueda (filtros)
        btnBusqueda = vista.findViewById(R.id.btnBusqueda);
        btnBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FiltroFragment detalle = new FiltroFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,detalle).commit();
            }
        });

        // Cambio a la vista de la compra
        btnCarrito = vista.findViewById(R.id.btnCarrito);
        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CarritoFragment carrito = new CarritoFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,carrito).commit();
            }
        });

        //Cambio a la vista de registro de compras
        btnRegistroCompras = vista.findViewById(R.id.btnRegistroCompras);
        btnRegistroCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistroComprasFragment registro = new RegistroComprasFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,registro).commit();
            }
        });

        return vista;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaPendientes = view.findViewById(R.id.ListaProductos);
        listaPendientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    public void inicializar(){
        //listaProductos = findViewById(R.id.ListaProductos);

        // Mostrar lista de productos ingresados en la base
        productos = new ArrayList<>();

        consultaPendientes.addValueEventListener(new ValueEventListener() {
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
                listaPendientes.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}