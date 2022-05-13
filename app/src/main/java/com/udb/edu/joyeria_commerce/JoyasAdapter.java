package com.udb.edu.joyeria_commerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udb.edu.joyeria_commerce.datos.Producto;

import java.util.List;

public class JoyasAdapter extends ArrayAdapter<Producto>
{

    public JoyasAdapter(Context context, int resource, List<Producto> joyasList)
    {
        super(context,resource,joyasList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Producto joyas = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.joyas_cell, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.joyasName);
        ImageView iv = (ImageView) convertView.findViewById(R.id.joyasImage);

        tv.setText(joyas.getNombre());
        String enlaceImagen = joyas.getImagen();
        Picasso.get().load(enlaceImagen).into(iv);



        return convertView;
    }
}
