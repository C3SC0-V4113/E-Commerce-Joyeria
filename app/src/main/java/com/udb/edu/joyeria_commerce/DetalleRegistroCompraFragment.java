package com.udb.edu.joyeria_commerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udb.edu.joyeria_commerce.datos.Producto;
import com.udb.edu.joyeria_commerce.ui.ProductosFragment;

import java.util.ArrayList;
import java.util.List;

public class DetalleRegistroCompraFragment extends Fragment {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refProductosCompras = database.getReference("compras");

    private List<Producto> productos;
    private ListView listaProductos;

    double totalCompra = 0; //Cálculo total de la compra
    ImageButton btnBusqueda, btnCarrito, btnRegistroCompras, btnHome;

    String idRegistro, totalRegistro, num;


    String correo, correoU;
    SharedPreferences settings;

    public DetalleRegistroCompraFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                idRegistro = bundle.getString("idRegistro");
                totalRegistro = bundle.getString("totalRegistro");

                num = idRegistro;

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_detalle_registro_compra, container, false);


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

        //Obtención de correo del usuario
        settings = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        correo=settings.getString("email","");
        //Elimnando puntos en el correo
        correoU = correo.replace(".","");

        listaProductos = view.findViewById(R.id.ListaProductos);
        productos = new ArrayList<>();

        refProductosCompras.child(""+correoU).child("compra"+num).addValueEventListener(new ValueEventListener() {
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

                Toast.makeText(getContext(),
                        "Hello " + num + correoU,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}