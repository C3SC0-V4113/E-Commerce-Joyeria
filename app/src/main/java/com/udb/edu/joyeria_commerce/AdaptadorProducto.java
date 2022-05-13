package com.udb.edu.joyeria_commerce;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.udb.edu.joyeria_commerce.datos.Producto;

import java.util.List;

public class AdaptadorProducto extends ArrayAdapter<Producto> {
    List<Producto> productos;
    private Activity context;

    public AdaptadorProducto(@NonNull Activity context, @NonNull List<Producto> productos) {
        super(context, R.layout.productos_layout, productos);
        this.context = context;
        this.productos = productos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowview = null;

        if(view == null)
            rowview = layoutInflater.inflate(R.layout.productos_layout, null);
        else rowview = view;

        ImageView ivImagenProducto = rowview.findViewById(R.id.ivImagenProducto);
        TextView tvNombre = rowview.findViewById(R.id.tvNombre);
        TextView tvPrecio = rowview.findViewById(R.id.tvPrecio);

        String enlaceImagen = productos.get(position).getImagen();

        Picasso.get().load(enlaceImagen).into(ivImagenProducto);


        tvNombre.setText("" + productos.get(position).getNombre());
        tvPrecio.setText("$" + productos.get(position).getPrecio());

        return rowview;
    }
}
