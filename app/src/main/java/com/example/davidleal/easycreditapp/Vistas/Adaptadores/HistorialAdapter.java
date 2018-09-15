package com.example.davidleal.easycreditapp.Vistas.Adaptadores;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.davidleal.easycreditapp.DataBase.CreditoDB;
import com.example.davidleal.easycreditapp.Modelos.Solicitudes;
import com.example.davidleal.easycreditapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.miHistorialAdapterViewHolder>{

    private List<Solicitudes> items;

    public HistorialAdapter(List items) {
        this.items = items;
    }

    @NonNull
    @Override
    public miHistorialAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud, parent, false);

        return new HistorialAdapter.miHistorialAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull miHistorialAdapterViewHolder holder, int position) {
        final Solicitudes solicitud = items.get(position);
        holder.cantidad.setText(solicitud.getCantidad()+"");
        holder.fechasolicitud.setText(solicitud.getFechaSolicitud());
        if(solicitud.getEstatus() == 0){
            //Estado 0 igual a pendiente
            holder.estatus.setText(R.string.pendiente);
            holder.colorestatus.setBackgroundResource(R.color.bannerPendiente);
        }else if(solicitud.getEstatus() == 1){
            //Estado 1, igual a aprobado
            holder.estatus.setText(R.string.Aprobado);
            holder.colorestatus.setBackgroundResource(R.color.bannerAceptado);
        }else if(solicitud.getEstatus() == 2){
            //Estado 2, igual a no aprobado
            holder.estatus.setText(R.string.NoAprobado);
            holder.colorestatus.setBackgroundResource(R.color.bannerCancelado);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class miHistorialAdapterViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        @BindView(R.id.itemsolicitud_txv_cantidad)
        TextView cantidad;
        @BindView(R.id.itemsolicitud_txv_estatus)
        TextView estatus;
        @BindView(R.id.itemsolicitud_layout_estatus)
        LinearLayout colorestatus;
        @BindView(R.id.itemsolicitud_txv_fechaSolicitud)
        TextView fechasolicitud;

        CreditoDB mydb;


        public miHistorialAdapterViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            mydb = new CreditoDB(v.getContext());

        }

    }
}
