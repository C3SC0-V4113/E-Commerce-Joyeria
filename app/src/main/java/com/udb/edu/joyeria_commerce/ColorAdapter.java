package com.udb.edu.joyeria_commerce;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorAdapterVh> implements Filterable {

    private List<ColorModel> colorModelList;
    private Context context;
    private ColorSeleccionado colorSeleccionado;

    public ColorAdapter(List<ColorModel> colorModelList,ColorSeleccionado colorSeleccionado){
        this.colorSeleccionado=colorSeleccionado;
        this.colorModelList=colorModelList;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public ColorAdapter.ColorAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();

        return new ColorAdapterVh(LayoutInflater.from(context).inflate(R.layout.fragment_recycler_view_colores,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ColorAdapter.ColorAdapterVh holder, int position) {
        ColorModel colorModel=colorModelList.get(position);

        int colores=colorModel.getColorModel();

       // colores=Color.valueOf();


        Drawable background=holder.rtlyt.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable)background).getPaint().setColor(colores);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable)background).setColor(colores);
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable)background).setColor(colores);
        }
    }

    @Override
    public int getItemCount() {
        return colorModelList.size();
    }

    public interface ColorSeleccionado{
        void colorSeleccionado(ColorModel colorModel);
    }

    public class ColorAdapterVh extends RecyclerView.ViewHolder{
        RelativeLayout rtlyt;
        public ColorAdapterVh(@NonNull View itemView){
            super(itemView);
            rtlyt=itemView.findViewById(R.id.circulo);
        }
    }
}
