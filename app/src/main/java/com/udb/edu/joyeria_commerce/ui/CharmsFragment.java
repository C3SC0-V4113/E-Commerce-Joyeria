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

public class CharmsFragment extends Fragment {

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference refProductos = database.getReference("productos");

    Query consultaCharms = refProductos.orderByChild("categoria").equalTo("Charms");

    private List<Producto> productos;
    private ListView listaCharms;

    public CharmsFragment() {
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
        return inflater.inflate(R.layout.fragment_charms, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaCharms = view.findViewById(R.id.ListaProductos);
        listaCharms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Intent intent = new Intent(getContext(), FormMovie.class);

                /*intent.putExtra("titulo",peliculas.get(i).getTitulo());
                intent.putExtra("descripcion",peliculas.get(i).getDescripcion());
                //intent.putExtra("foto",peliculas.get(i).getFoto());
                selectedPhoto=peliculas.get(i).getFoto();
                intent.putExtra("estreno",peliculas.get(i).getEsteno());
                intent.putExtra("rate",peliculas.get(i).getRate());*/
                Bundle bundle = new Bundle();
                bundle.putString("nombre", productos.get(i).getNombre());
                bundle.putString("categoria",productos.get(i).getCategoria());
                DetalleProductoFragment detalle = new DetalleProductoFragment();
                detalle.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,detalle).commit();

            }
        });
    }

    public void inicializar(){
        //listaProductos = findViewById(R.id.ListaProductos);

        // Mostrar lista de productos ingresados en la base
        productos = new ArrayList<>();

        consultaCharms.addValueEventListener(new ValueEventListener() {
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
                listaCharms.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}