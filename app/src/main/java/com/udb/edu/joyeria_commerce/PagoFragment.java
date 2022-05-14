package com.udb.edu.joyeria_commerce;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udb.edu.joyeria_commerce.ui.ProductosFragment;

import java.util.HashMap;
import java.util.Map;

public class PagoFragment extends Fragment {

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refContador = database.getReference("contadores");
    public DatabaseReference refRegistroCompras = database.getReference("registroCompras");

    String correo, correoU;
    SharedPreferences settings;

    String totalCompra;
    EditText tiDireccionEnvio, tiNumeroTarjeta, tiPinTarjeta;
    Button btnRealizarPedido, btnCancelar;
    int numero = 0;

    public PagoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                totalCompra = bundle.getString("totalCompra");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pago, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnRealizarPedido = view.findViewById(R.id.btnRealizarPedido);
        btnCancelar = view.findViewById(R.id.btnCancelar);
        tiDireccionEnvio = view.findViewById(R.id.tiDireccionEnvio);
        tiNumeroTarjeta = view.findViewById(R.id.tiNumeroTarjeta);
        tiPinTarjeta = view.findViewById(R.id.tiPinTarjeta);



        //Obtención de correo del usuario
        settings = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        correo=settings.getString("email","");
        //Elimnando puntos en el correo
        correoU = correo.replace(".","");

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductosFragment pantallaProductos = new ProductosFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,pantallaProductos).commit();
            }
        });

        btnRealizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refContador.child(""+correoU).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dato) {
                        numero = Integer.parseInt(dato.child("numero").getValue().toString());

                        String direccionEnvio = tiDireccionEnvio.getText().toString();
                        String numeroTarjeta = tiNumeroTarjeta.getText().toString();
                        String pinTarjeta = tiPinTarjeta.getText().toString();

                        Map<String, Object> datoRegistro = new HashMap<>();

                        datoRegistro.put("id",numero);
                        datoRegistro.put("direccion", direccionEnvio);
                        datoRegistro.put("numeroTarjeta", numeroTarjeta);
                        datoRegistro.put("pinTarjeta", pinTarjeta);
                        datoRegistro.put("total", totalCompra);

                        refRegistroCompras.child(""+correoU).child("compra"+numero).setValue(datoRegistro);

                        if(getActivity()!=null){
                            Toast.makeText(getContext(),
                                    "Se ha realizado el pedido correctamente",Toast.LENGTH_SHORT).show();
                        }


                        //Incrementando el contador para una nueva compra
                        numero++;
                        refContador.child(""+correoU).child("numero").setValue(numero);

                        tiDireccionEnvio.setText("");
                        tiNumeroTarjeta.setText("");
                        tiPinTarjeta.setText("");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

            }
        });

    }
}