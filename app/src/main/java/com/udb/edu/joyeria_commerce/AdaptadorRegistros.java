package com.udb.edu.joyeria_commerce;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.udb.edu.joyeria_commerce.datos.RegistroModel;

import java.util.List;

public class AdaptadorRegistros extends ArrayAdapter<RegistroModel> {
    List<RegistroModel> registros;
    private Activity context;


    public AdaptadorRegistros(@NonNull Activity context, @NonNull List<RegistroModel> registros) {
        super(context, R.layout.registro_layout, registros);
        this.context = context;
        this.registros = registros;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowview = null;

        if(view == null)
            rowview = layoutInflater.inflate(R.layout.registro_layout, null);
        else rowview = view;

        TextView tvNombreRegistro = rowview.findViewById(R.id.tvNombreRegistro);
        TextView tvTotalRegistro = rowview.findViewById(R.id.tvTotalRegistro);

        tvNombreRegistro.setText("Mi compra " + registros.get(position).getId());
        tvTotalRegistro.setText("$" + registros.get(position).getTotal());

        return rowview;
    }

}
