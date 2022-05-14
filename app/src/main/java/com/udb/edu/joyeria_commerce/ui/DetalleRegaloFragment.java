package com.udb.edu.joyeria_commerce.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.udb.edu.joyeria_commerce.AdaptadorRegalos;
import com.udb.edu.joyeria_commerce.R;
import com.udb.edu.joyeria_commerce.datos.RegaloModel;

import java.util.ArrayList;
import java.util.List;

public class DetalleRegaloFragment extends Fragment {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refRegalos = database.getReference("regalos");

    private List<RegaloModel> regalos;
    private ListView listaRegalos;

    TextView tvNombreRegalo, tvPrecioRegalo, tvDetalleRegalo;
    ImageView ivImagenRegalo;

    public DetalleRegaloFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
//            @Override
//            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
//                String nombreRegalo = bundle.getString("nombreRegalo");
//                String precioRegalo = bundle.getString("precioRegalo");
//                String detalleRegalo = bundle.getString("detalleRegalo");
//                String imagenRegalo = bundle.getString("imagenRegalo");
//
//                tvNombreRegalo.setText("" + nombreRegalo);
//                tvPrecioRegalo.setText("$" + precioRegalo);
//                tvDetalleRegalo.setText("" + detalleRegalo);
//                Picasso.get().load(imagenRegalo).into(ivImagenRegalo);
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_regalo, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNombreRegalo = view.findViewById(R.id.tvNombreRegalo);
        tvPrecioRegalo = view.findViewById(R.id.tvPrecioRegalo);
        tvDetalleRegalo = view.findViewById(R.id.tvDetalleRegalo);
        ivImagenRegalo = view.findViewById(R.id.ivImagenRegalo);
    }

}