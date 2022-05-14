package com.udb.edu.joyeria_commerce;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CuentaFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    TextView correo;
    String nombre;
    SharedPreferences settings;
    public CuentaFragment() {
        // Required empty public constructor
    }

    public static CuentaFragment newInstance(String param1, String param2) {
        CuentaFragment fragment = new CuentaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        settings = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        nombre=settings.getString("email","");
        View v = inflater.inflate(R.layout.fragment_cuenta, container, false);
        correo=v.findViewById(R.id.txtadd);
        correo.setText(nombre);
        return v;
    }

//    public String getUsuario(String correo){
//        String[] textoSeparado = correo.split("@");
//        return textoSeparado[0];
//    }
}