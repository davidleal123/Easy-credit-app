package com.example.davidleal.easycreditapp.Vistas.Adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.davidleal.easycreditapp.DataBase.CreditoDB;
import com.example.davidleal.easycreditapp.Modelos.SolicitudAdmin;
import com.example.davidleal.easycreditapp.Modelos.Solicitudes;
import com.example.davidleal.easycreditapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SolicitudesAdapter extends RecyclerView.Adapter<SolicitudesAdapter.miSolicitudAdapterViewHolder>{

    private List<SolicitudAdmin> items;

    public SolicitudesAdapter(List items) {
        this.items = items;
    }

    @NonNull
    @Override
    public miSolicitudAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud_admin, parent, false);

        return new SolicitudesAdapter.miSolicitudAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull miSolicitudAdapterViewHolder holder, int position) {
        final SolicitudAdmin solicitud = items.get(position);
        holder.cantidad.setText(solicitud.getCantidad()+"");
        holder.fechasolicitud.setText(solicitud.getFechaSolicitud());
        holder.usuario.setText(solicitud.getUsuario());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class miSolicitudAdapterViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        @BindView(R.id.itemsolicitudadmin_txv_cantidad)
        TextView cantidad;
        @BindView(R.id.itemsolicitudadmin_txv_fechaSolicitud)
        TextView fechasolicitud;
        @BindView(R.id.itemsolicitudadmin_txv_usuario)
        TextView usuario;
        CreditoDB mydb;


        public miSolicitudAdapterViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            mydb = new CreditoDB(v.getContext());

        }

    }
}
