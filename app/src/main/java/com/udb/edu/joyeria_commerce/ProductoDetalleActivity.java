package com.udb.edu.joyeria_commerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProductoDetalleActivity extends AppCompatActivity implements TallasAdapter.TallaSeleccionada{

    RecyclerView rcl_tallas;
    RecyclerView rcl_colores;

    List<TallasModel> tallasModelList=new ArrayList<>();

    TallasAdapter tallasAdapter;

    String[] tallas={"3","3.5","4","4.5","5","5.5","6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_detalle);

        rcl_tallas=findViewById(R.id.rclview_tallas);
        rcl_colores=findViewById(R.id.rclview_colores);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        for (String s:tallas) {
            TallasModel tallasModel=new TallasModel(s);

            tallasModelList.add(tallasModel);
        }

        rcl_tallas.setLayoutManager(linearLayoutManager);
        rcl_tallas.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        /*rcl_colores.setLayoutManager(linearLayoutManager);
        rcl_colores.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));*/

        tallasAdapter=new TallasAdapter(tallasModelList,this);

        rcl_tallas.setAdapter(tallasAdapter);
        rcl_colores.setAdapter(tallasAdapter);
    }

    @Override
    public void tallaSeleccionada(TallasModel tallasModel){
        Toast.makeText(this, tallasModel.getTallasModel(), Toast.LENGTH_SHORT).show();
    }
}