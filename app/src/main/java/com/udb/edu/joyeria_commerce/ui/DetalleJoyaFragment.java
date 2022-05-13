package com.udb.edu.joyeria_commerce.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.udb.edu.joyeria_commerce.R;
import com.udb.edu.joyeria_commerce.datos.ContadorModel;

import java.util.HashMap;
import java.util.Map;

public class DetalleJoyaFragment extends Fragment {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refContador = database.getReference("contador");
    public DatabaseReference refCompras = database.getReference();


    String nombreProducto, precioProducto, detalleProducto, imagenProducto;

    TextView tvNombreProducto, tvPrecioProducto, tvDetalleProducto;
    ImageView ivImagenProducto;
    Button btnAgregarCarrito;

    String correo;
    SharedPreferences settings;

    public DetalleJoyaFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                nombreProducto = bundle.getString("nombreProducto");
                precioProducto = bundle.getString("precioProducto");
                detalleProducto = bundle.getString("detalleProducto");
                imagenProducto = bundle.getString("imagenProducto");

                tvNombreProducto.setText("" + nombreProducto);
                tvPrecioProducto.setText("$" + precioProducto);
                tvDetalleProducto.setText("" + detalleProducto);
                Picasso.get().load(imagenProducto).into(ivImagenProducto);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_joya, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Obtención de correo del usuario
        settings = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        correo=settings.getString("email","");

        // Relaciones con id del fragment
        tvNombreProducto = view.findViewById(R.id.tvNombreProducto);
        tvPrecioProducto = view.findViewById(R.id.tvPrecioProducto);
        tvDetalleProducto = view.findViewById(R.id.tvDetalleProducto);
        ivImagenProducto = view.findViewById(R.id.ivImagenProducto);

        btnAgregarCarrito = view.findViewById(R.id.btnAgregarCarrito);

        //Al hacer clic el elemento se debe crear la lista de compra
        btnAgregarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Preparamos los datos para subirlo al nodo de compras del usuario
                Map<String, Object> datoCompra = new HashMap<>();

                datoCompra.put("nombre", nombreProducto);
                datoCompra.put("precio", precioProducto);
                datoCompra.put("detalle", detalleProducto);
                datoCompra.put("imagen", imagenProducto);


                refContador.child("numero").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dato) {

                        if(dato.exists())
                        {
                            //Si el nodo ha sido creado se agrega el producto a la lista

                            //Obtenemos el valor del contador de ese usuario (el número de compras que va realizado)
                            int numero = Integer.parseInt(dato.getValue().toString());

                            //Ingresamos los datos a la lista de compra
                            refCompras.child("compras").child("compra" + numero).push().setValue(datoCompra);

                            Toast.makeText(getContext(),
                                    "Se ha agregado el producto al carrito",Toast.LENGTH_SHORT).show();

                        }
                        else {
                            // Si el nodo del contador del usuario no ha sido creado se procede a crearlo (la primera compra del usuario)
                            int contador = 1;
                            refContador.child("numero").setValue(contador);

//                            //y se agrega el producto a la lista de compra
//                            refCompras.child("compras").child("correo1").child("compra" + contador).push().setValue(datoCompra);
//
//                            Toast.makeText(getContext(),
//                                    "Se ha agregado el producto al carrito",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//                Toast.makeText(getContext(),
//                        "Hello! " + imagenProducto,Toast.LENGTH_SHORT).show();

            }
        });

    }
}