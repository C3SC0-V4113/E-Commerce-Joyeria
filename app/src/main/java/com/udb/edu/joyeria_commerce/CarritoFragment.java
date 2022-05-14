package com.udb.edu.joyeria_commerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udb.edu.joyeria_commerce.datos.Producto;
import com.udb.edu.joyeria_commerce.ui.ProductosFragment;

import java.util.ArrayList;
import java.util.List;

public class CarritoFragment extends Fragment {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refContador = database.getReference("contadores");
    public DatabaseReference refProductosCompras = database.getReference("compras");

    private List<Producto> productos;
    private ListView listaProductos;

    double totalCompra = 0; //Cálculo total de la compra
    TextView tvTotalCompra;

    ImageButton btnBusqueda, btnHome, btnRegistroCompras;
    Button btnEliminarCompra, btnConfirmarPedido;

    String correo, correoU;
    SharedPreferences settings;

    public CarritoFragment() {

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
        View vista=inflater.inflate(R.layout.fragment_carrito, container, false);


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
        btnHome = vista.findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductosFragment home = new ProductosFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,home).commit();
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

        listaProductos = view.findViewById(R.id.ListaProductos);

        tvTotalCompra = view.findViewById(R.id.tvTotalCompra);
        btnEliminarCompra = view.findViewById(R.id.btnEliminarCompra);
        btnConfirmarPedido = view.findViewById(R.id.btnConfirmarPedido);



        //Proceso para eliminar la compra
        btnEliminarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refContador.child(""+correoU).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dato) {
                        int numero = Integer.parseInt(dato.child("numero").getValue().toString());

                        refProductosCompras.child(""+correoU).child("compra"+numero).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(),
                                        "Se ha eliminado el registro de la compra",Toast.LENGTH_SHORT).show();

                                ProductosFragment home = new ProductosFragment();
                                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,home).commit();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(),
                                        "No se ha podido eliminar el registro de la compra",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        //Proceso para empezar el pedido
        btnConfirmarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Enviando el total al formulario
                Bundle bundle = new Bundle();
                bundle.putString("totalCompra", ""+totalCompra);
                getParentFragmentManager().setFragmentResult("key",bundle);

                PagoFragment pago = new PagoFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,pago).commit();
            }
        });

    }

    public void inicializar(){
        //Obtención de correo del usuario
        settings = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        correo=settings.getString("email","");
        //Elimnando puntos en el correo
        correoU = correo.replace(".","");

        //Mestra de productos en el carrito
        productos = new ArrayList<>();


        refContador.child(""+correoU).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dato) {
                if(dato.exists())
                {

                    int numero = Integer.parseInt(dato.child("numero").getValue().toString());

                    refProductosCompras.child(""+correoU).child("compra"+numero).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            productos.removeAll(productos);
                            for(DataSnapshot dato: snapshot.getChildren())
                            {
                                Producto producto = dato.getValue(Producto.class);
                                producto.setKey(dato.getKey());
                                totalCompra += producto.getPrecio();
                                productos.add(producto);
                            }

                            if(getActivity()!=null){
                                AdaptadorProducto adapter = new AdaptadorProducto((Activity) getContext(), productos);
                                listaProductos.setAdapter(adapter);

                                tvTotalCompra.setText("Total: $" + totalCompra);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}