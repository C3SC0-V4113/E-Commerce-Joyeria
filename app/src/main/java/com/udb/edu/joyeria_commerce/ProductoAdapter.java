package com.udb.edu.joyeria_commerce;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udb.edu.joyeria_commerce.datos.Producto;
import com.udb.edu.joyeria_commerce.datos.ProductoModel;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoAdapterVh> implements Filterable {

    private List<Producto> productoModelList;
    private Context context;
    private ProductoSeleccionado productoSeleccionado;

    public ProductoAdapter(List<Producto> productoModelList, ProductoSeleccionado productoSeleccionado){
        this.productoModelList=productoModelList;
        this.productoSeleccionado=productoSeleccionado;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public ProductoAdapter.ProductoAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();

        return new ProductoAdapterVh(LayoutInflater.from(context).inflate(R.layout.fragment_recycler_view_productos,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdapter.ProductoAdapterVh holder, int position) {
        Producto productoModel=productoModelList.get(position);

        String title=productoModel.getNombre();
        Double price=productoModel.getPrecio();
        String urlimg=productoModel.getImagen();
        Log.e("onBindViewHolder: ",urlimg );

        holder.tvTitle.setText(title);
        holder.tvPrecio.setText("$"+price);
        Picasso.get().load(urlimg).into(holder.imgVista);
    }

    @Override
    public int getItemCount() {
        return productoModelList.size();
    }

    public interface ProductoSeleccionado{
        void productoSeleccionado(Producto productoModel);
    }

    public class ProductoAdapterVh extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvPrecio;
        ImageView imgVista;
        public  ProductoAdapterVh(@NonNull View itemView){
            super(itemView);
            tvTitle=itemView.findViewById(R.id.titulo_Card);
            tvPrecio=itemView.findViewById(R.id.precio_Card);
            imgVista=itemView.findViewById(R.id.imgVista);
        }
    }
}
