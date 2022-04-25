package com.udb.edu.joyeria_commerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProductoDetalleActivity extends AppCompatActivity implements TallasAdapter.TallaSeleccionada,ColorAdapter.ColorSeleccionado,ProductoAdapter.ProductoSeleccionado{

    RecyclerView rcl_tallas;
    RecyclerView rcl_colores;
    RecyclerView rcl_recomendados;

    List<TallasModel> tallasModelList=new ArrayList<>();
    List<ColorModel> colorModelList=new ArrayList<>();
    List<ProductoModel> productoModelList=new ArrayList<>();

    TallasAdapter tallasAdapter;
    ColorAdapter coloresAdapter;
    ProductoAdapter productoAdapter;

    String[] tallas={"3","3.5","4","4.5","5","5.5","6"};

    String[] colores={"red","cyan","teal","blue","white","gray","fuchsia","navy","#085569"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_detalle);

        rcl_tallas=findViewById(R.id.rclview_tallas);
        rcl_colores=findViewById(R.id.rclview_colores);
        rcl_recomendados=findViewById(R.id.rclview_recomendados);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager linearLayoutManager3=new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);

        for (String s:tallas) {
            TallasModel tallasModel=new TallasModel(s);

            tallasModelList.add(tallasModel);
        }

        for (String s:colores){
            ColorModel colorModel=new ColorModel(s);

            colorModelList.add(colorModel);
        }

        productoModelList.add(new ProductoModel("Anillo corazón esmeralda",183.0));
        productoModelList.add(new ProductoModel("Anillo azul zafiro",228.0));
        productoModelList.add(new ProductoModel("Anillo gota rubí",190.0));
        productoModelList.add(new ProductoModel("Anillo de oro con opalos incrustados",250.0));

        rcl_tallas.setLayoutManager(linearLayoutManager);
        rcl_tallas.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        rcl_colores.setLayoutManager(linearLayoutManager2);
        rcl_colores.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        rcl_recomendados.setLayoutManager(linearLayoutManager3);
        rcl_recomendados.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        tallasAdapter=new TallasAdapter(tallasModelList,this);
        coloresAdapter=new ColorAdapter(colorModelList,this);
        productoAdapter=new ProductoAdapter(productoModelList,this);

        rcl_tallas.setAdapter(tallasAdapter);
        rcl_colores.setAdapter(coloresAdapter);
        rcl_recomendados.setAdapter(productoAdapter);
    }

    @Override
    public void tallaSeleccionada(TallasModel tallasModel){
        Toast.makeText(this, tallasModel.getTallasModel(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void colorSeleccionado(ColorModel colorModel) {
        Toast.makeText(this, colorModel.getColorModel(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void productoSeleccionado(ProductoModel productoModel) {
        Toast.makeText(this, productoModel.getProductoTitulo(), Toast.LENGTH_SHORT).show();
    }
}