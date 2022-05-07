package com.udb.edu.joyeria_commerce.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ValueEventListener;
import com.udb.edu.joyeria_commerce.AdaptadorRegalos;
import com.udb.edu.joyeria_commerce.R;
import com.udb.edu.joyeria_commerce.datos.RegaloModel;

import java.util.ArrayList;
import java.util.List;

public class RegalosFragment extends Fragment {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refRegalos = database.getReference("regalos");

    private List<RegaloModel> regalos;
    private ListView listaRegalos;

    public RegalosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_regalos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaRegalos = view.findViewById(R.id.ListaRegalos);
    }

    public void inicializar(){
        regalos = new ArrayList<>();

        refRegalos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                regalos.removeAll(regalos);
                for (DataSnapshot dato: snapshot.getChildren())
                {
                    RegaloModel regalo = dato.getValue(RegaloModel.class);
                    regalo.setKey(dato.getKey());
                    regalos.add(regalo);
                }

                AdaptadorRegalos adapter = new AdaptadorRegalos((Activity) getContext(), regalos);
                listaRegalos.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}