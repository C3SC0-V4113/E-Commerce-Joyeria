package com.udb.edu.joyeria_commerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.logging.Filter;

public class TallasAdapter extends RecyclerView.Adapter<TallasAdapter.TallasAdapterVh> implements Filterable {
    private List<TallasModel> tallasModelList;
    private Context context;
    private TallaSeleccionada tallaSeleccionada;
    
    public TallasAdapter(List<TallasModel> tallasModelList,TallaSeleccionada tallaSeleccionada){
        this.tallaSeleccionada=tallaSeleccionada;
        this.tallasModelList=tallasModelList;
    }

    @NonNull
    @Override
    public TallasAdapter.TallasAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        context = parent.getContext();

        return new TallasAdapterVh(LayoutInflater.from(context).inflate(R.layout.activity_producto_detalle,null));
    }
}
