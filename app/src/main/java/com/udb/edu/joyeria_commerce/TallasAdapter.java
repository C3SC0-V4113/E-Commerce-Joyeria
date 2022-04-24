package com.udb.edu.joyeria_commerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.logging.LogRecord;

public class TallasAdapter extends RecyclerView.Adapter<TallasAdapter.TallasAdapterVh> implements Filterable {
    private List<TallasModel> tallasModelList;
    private Context context;
    private TallaSeleccionada tallaSeleccionada;

    public TallasAdapter(List<TallasModel> tallasModelList,TallaSeleccionada tallaSeleccionada){
        this.tallaSeleccionada=tallaSeleccionada;
        this.tallasModelList=tallasModelList;
        this.tallaSeleccionada=tallaSeleccionada;
    }

    @NonNull
    @Override
    public TallasAdapter.TallasAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        context = parent.getContext();

        return new TallasAdapterVh(LayoutInflater.from(context).inflate(R.layout.fragment_recycler_view_tallas,null));
    }

    @Override
    public void onBindViewHolder(@NonNull TallasAdapter.TallasAdapterVh holder,int position){
        TallasModel tallasModel=tallasModelList.get(position);

        String talla=tallasModel.getTallasModel();

        holder.tvTalla.setText(talla);
    }

    @Override
    public int getItemCount(){
        return tallasModelList.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }


    public interface TallaSeleccionada{
        void tallaSeleccionada(TallasModel tallasModel);
    }

    public class TallasAdapterVh extends RecyclerView.ViewHolder{
        TextView tvTalla;
        public TallasAdapterVh(@NonNull View itemView){
            super(itemView);
            tvTalla=itemView.findViewById(R.id.prefix);
        }
    }
}
