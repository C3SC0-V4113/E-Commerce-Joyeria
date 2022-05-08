package com.udb.edu.joyeria_commerce;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.udb.edu.joyeria_commerce.datos.RegaloModel;

import org.w3c.dom.Text;

import java.util.List;

public class AdaptadorRegalos extends ArrayAdapter<RegaloModel> {
    List<RegaloModel> regalos;
    private Activity context;

    public AdaptadorRegalos(@NonNull Activity context, @NonNull List<RegaloModel> regalos) {
        super(context, R.layout.regalos_layout, regalos);
        this.context = context;
        this.regalos = regalos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowview = null;

        if(view == null)
            rowview = layoutInflater.inflate(R.layout.regalos_layout, null);
        else rowview = view;

        ImageView ivImagenRegalo = rowview.findViewById(R.id.ivImagenRegalo);
        TextView tvNombreRegalo = rowview.findViewById(R.id.tvNombreRegalo);
        TextView tvPrecioRegalo = rowview.findViewById(R.id.tvPrecioRegalo);
        TextView tvDetalleRegalo = rowview.findViewById(R.id.tvDetalleRegalo);

        String enlaceImagen = regalos.get(position).getImagen();

        Picasso.get().load(enlaceImagen).into(ivImagenRegalo);

        tvNombreRegalo.setText("" + regalos.get(position).getNombre());
        tvPrecioRegalo.setText("$" + regalos.get(position).getPrecio());

        return rowview;
    }
}
