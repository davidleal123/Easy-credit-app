package com.example.davidleal.easycreditapp.Vistas.Actividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.davidleal.easycreditapp.Aplicacion.preferences;
import com.example.davidleal.easycreditapp.DataBase.CreditoDB;
import com.example.davidleal.easycreditapp.Modelos.SolicitudAdmin;
import com.example.davidleal.easycreditapp.Modelos.Solicitudes;
import com.example.davidleal.easycreditapp.R;
import com.example.davidleal.easycreditapp.Utilerias.ItemClickSupport;
import com.example.davidleal.easycreditapp.Vistas.Adaptadores.HistorialAdapter;
import com.example.davidleal.easycreditapp.Vistas.Adaptadores.SolicitudesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Administrador extends BaseActivity {

    public List<SolicitudAdmin> list;
    private RecyclerView.Adapter mAdapter;
    public ProgressDialog mDialog;
    CreditoDB mydb;

    public @BindView(R.id.administrador_rcv_solicitudes)
    RecyclerView solicitudes;
    public @BindView(R.id.administrador_swipe_solicitudes)
    SwipeRefreshLayout mSwipeRefreshLayout;
    public @BindView(R.id.administrador_imgv_nosolicitudes)
    ImageView nosolicitudes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);


        ButterKnife.bind(this);
        mydb = new CreditoDB(this);
        preferences.getInstance().Initialize(this);

        this.list = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        solicitudes.setHasFixedSize(true);
        solicitudes.setLayoutManager(layoutManager);
        cargarSolicitudes();
        // mostrarCargando("Cargando las Solicitudes para el Administrador, espere.....", "Waiting......");
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                cargarSolicitudes();
            }
        });




    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        cargarSolicitudes();
    }

    /**
     * Metodo Para cargar las solicitudes desde base de datos y pasarlas
     * a la lista, aparte genera el evento click para cada item
     *
     */
    public void cargarSolicitudes(){
        mSwipeRefreshLayout.setRefreshing(false);
        list = mydb.SolicitudesAdmin();

        if(list.isEmpty()){
            // No existe historial para este usuario, mostrar boton solicitud
            nosolicitudes.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
        }else {
            nosolicitudes.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mAdapter = new SolicitudesAdapter(list);
            solicitudes.setAdapter(mAdapter);

            ItemClickSupport.addTo(solicitudes).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    SolicitudAdmin solicitud = list.get(position);
                    Intent intent = new Intent(getApplicationContext(), DetalleAdministrador.class);
                    intent.putExtra("idsolicitud",solicitud.getIdsolicitud());
                    intent.putExtra("interes",solicitud.getInteres());
                    intent.putExtra("fechaAclaracion",solicitud.getFechaAclaracion());
                    intent.putExtra("fechaSolicitud",solicitud.getFechaSolicitud());
                    intent.putExtra("plazo",solicitud.getPlazo());
                    intent.putExtra("cantidad",solicitud.getCantidad());
                    intent.putExtra("userid",solicitud.getUserid());
                    intent.putExtra("user",solicitud.getUsuario());
                    startActivity(intent);
                }
            });

        }
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }
}
