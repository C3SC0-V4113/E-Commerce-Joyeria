package com.udb.edu.joyeria_commerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.google.firebase.database.ValueEventListener;
import com.udb.edu.joyeria_commerce.datos.RegistroModel;
import com.udb.edu.joyeria_commerce.ui.ProductosFragment;

import java.util.ArrayList;
import java.util.List;

public class RegistroComprasFragment extends Fragment {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference refRegistros = database.getReference("registroCompras");

    List<RegistroModel> registros;
    private ListView listaRegistrosCompras;

    ImageButton btnBusqueda, btnCarrito, btnHome;

    String correo, correoU;
    SharedPreferences settings;

    public RegistroComprasFragment(){

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
        View vista=inflater.inflate(R.layout.fragment_registro_compras, container, false);


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
        btnHome = vista.findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductosFragment home = new ProductosFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,home).commit();
            }
        });

        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listaRegistrosCompras = view.findViewById(R.id.ListaRegistroCompras);
    }

    public void inicializar(){
        //Obtención de correo del usuario
        settings = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        correo=settings.getString("email","");
        //Elimnando puntos en el correo
        correoU = correo.replace(".","");

        registros = new ArrayList<>();

        refRegistros.child(""+correoU).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                registros.removeAll(registros);
                for(DataSnapshot dato: snapshot.getChildren())
                {
                    RegistroModel registro = dato.getValue(RegistroModel.class);
                    registro.setKey(dato.getKey());
                    registros.add(registro);
                }

                AdaptadorRegistros adapter = new AdaptadorRegistros((Activity) getContext(), registros);
                listaRegistrosCompras.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}