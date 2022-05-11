package com.udb.edu.joyeria_commerce.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.udb.edu.joyeria_commerce.ColorAdapter;
import com.udb.edu.joyeria_commerce.ProductoAdapter;

import com.udb.edu.joyeria_commerce.R;
import com.udb.edu.joyeria_commerce.TallasAdapter;
import com.udb.edu.joyeria_commerce.datos.ColorModel;
import com.udb.edu.joyeria_commerce.datos.Producto;
import com.udb.edu.joyeria_commerce.datos.ProductoModel;
import com.udb.edu.joyeria_commerce.datos.TallasModel;

import java.util.ArrayList;
import java.util.List;


public class DetalleProductoFragment extends Fragment implements TallasAdapter.TallaSeleccionada, ColorAdapter.ColorSeleccionado, ProductoAdapter.ProductoSeleccionado {

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference refProductos = database.getReference("productos");

    private Producto productoPrinci;

    Query consultaProducto, consultaCategoria = refProductos.orderByChild("categoria").equalTo("Charms");;

    ImageView imagenPrincipal;
    TextView txtPrecio,txtTitulo,txtDescripcion;

    RecyclerView rcl_tallas;
    RecyclerView rcl_colores;
    RecyclerView rcl_recomendados;

    List<TallasModel> tallasModelList=new ArrayList<>();
    List<ColorModel> colorModelList=new ArrayList<>();
    List<Producto> productoModelList=new ArrayList<>();

    TallasAdapter tallasAdapter;
    ColorAdapter coloresAdapter;
    ProductoAdapter productoAdapter;

    String[] tallas={"3","3.5","4","4.5","5","5.5","6"};

    String[] colores={"red","cyan","teal","blue","white","gray","fuchsia","navy","#085569"};


    public DetalleProductoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String strtext = getArguments().getString("nombre");

        View view = inflater.inflate(R.layout.fragment_detalle_producto, container, false);
        consultaProducto=refProductos.orderByChild("nombre").equalTo(strtext);

        Log.e("DetalleProducto",strtext);

        inicializar(view);

        rcl_tallas = view.findViewById(R.id.rclview_tallas);

        rcl_colores=view.findViewById(R.id.rclview_colores);
        rcl_recomendados=view.findViewById(R.id.rclview_recomendados);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager linearLayoutManager3=new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);

        for (String s:tallas) {
            TallasModel tallasModel=new TallasModel(s);

            tallasModelList.add(tallasModel);
        }

        for (String s:colores){
            ColorModel colorModel=new ColorModel(s);

            colorModelList.add(colorModel);
        }

        /*productoModelList.add(new ProductoModel("Anillo corazón esmeralda",183.0));
        productoModelList.add(new ProductoModel("Anillo azul zafiro",228.0));
        productoModelList.add(new ProductoModel("Anillo gota rubí",190.0));
        productoModelList.add(new ProductoModel("Anillo de oro con opalos incrustados",250.0));*/

        rcl_tallas.setLayoutManager(linearLayoutManager);
        rcl_tallas.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        rcl_colores.setLayoutManager(linearLayoutManager2);
        rcl_colores.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        rcl_recomendados.setLayoutManager(linearLayoutManager3);
        rcl_recomendados.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        tallasAdapter=new TallasAdapter(tallasModelList,this);
        coloresAdapter=new ColorAdapter(colorModelList,this);
        Log.e("Productos relacionado", productoModelList.toString() );
        productoAdapter=new ProductoAdapter(productoModelList,this);

        rcl_tallas.setAdapter(tallasAdapter);
        rcl_colores.setAdapter(coloresAdapter);
        rcl_recomendados.setAdapter(productoAdapter);



        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void tallaSeleccionada(TallasModel tallasModel){
        Toast.makeText(getContext(), tallasModel.getTallasModel(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void colorSeleccionado(ColorModel colorModel) {
        Toast.makeText(getContext(), colorModel.getColorModel(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void productoSeleccionado(Producto productoModel) {
        Log.e("productoSeleccionado: ",productoModel.getNombre() );
    }

    public void inicializar(View v){
        txtPrecio=v.findViewById(R.id.precio);
        txtDescripcion=v.findViewById(R.id.descripcion);
        txtTitulo=v.findViewById(R.id.titulo);
        imagenPrincipal=v.findViewById(R.id.img_detalle);

        consultaProducto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dato: snapshot.getChildren()) {

                    Producto producto = dato.getValue(Producto.class);
                    producto.setKey(dato.getKey());
                    productoPrinci=producto;
                }

                txtTitulo.setText(productoPrinci.getNombre());
                txtPrecio.setText("$"+productoPrinci.getPrecio());
                txtDescripcion.setText(productoPrinci.getDetalle());
                String txtEnlaceImg = productoPrinci.getImagen();
                String categoria = productoPrinci.getCategoria();

                Log.e("Categoria", categoria);

                //consultaCategoria = refProductos.orderByChild("categoria").equalTo(categoria);
                Picasso.get().load(txtEnlaceImg).into(imagenPrincipal);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        consultaCategoria.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dato: snapshot.getChildren()) {

                    Producto producto = dato.getValue(Producto.class);
                    producto.setKey(dato.getKey());
                    productoModelList.add(producto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}