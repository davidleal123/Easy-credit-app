package com.example.davidleal.easycreditapp.Vistas.Fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.davidleal.easycreditapp.Aplicacion.preferences;
import com.example.davidleal.easycreditapp.DataBase.CreditoDB;
import com.example.davidleal.easycreditapp.R;
import com.example.davidleal.easycreditapp.Utilerias.ItemClickSupport;
import com.example.davidleal.easycreditapp.Vistas.Actividades.DetalleSolicitud;
import com.example.davidleal.easycreditapp.Vistas.Actividades.SolicitudNueva;
import com.example.davidleal.easycreditapp.Vistas.Adaptadores.HistorialAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Solicitudes extends Fragment {

    public List<com.example.davidleal.easycreditapp.Modelos.Solicitudes> list;
    private RecyclerView.Adapter mAdapter;
    public ProgressDialog mDialog;
    CreditoDB mydb;

    public @BindView(R.id.solicitiudes_rcv_solicitiudes)
    RecyclerView historial;
    public @BindView(R.id.solicitiudes_swipe_solicitiudes)
    SwipeRefreshLayout mSwipeRefreshLayout;
    public @BindView(R.id.solicitudes_imgv_nosolicitudes)
    ImageView nosolicitudes;

    private OnFragmentInteractionListener mListener;

    public Solicitudes() {
        // Required empty public constructor
    }

    public static Solicitudes newInstance() {
        Solicitudes fragment = new Solicitudes();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        cargarSolicitudes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_solicitudes, container, false);
        ButterKnife.bind(this, view);
        mydb = new CreditoDB(getActivity());
        preferences.getInstance().Initialize(getActivity());

        this.list = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        historial.setHasFixedSize(true);
        historial.setLayoutManager(layoutManager);
        mostrarCargando("Cargando las Solicitudes del Usuario, espere.....", "Waiting......");
        cargarSolicitudes();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                cargarSolicitudes();
            }
        });
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void mostrarCargando(String title, String message) {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
        mDialog = new ProgressDialog(getActivity(), R.style.Theme_MyDialog);
        mDialog.setTitle(title);
        mDialog.setMessage(message);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
    }


    public void cargarSolicitudes(){
        mSwipeRefreshLayout.setRefreshing(false);
        int usr = Integer.parseInt(preferences.getInstance().getUserid());
        list = mydb.SolicitudesPendientes(usr);

        if(list.isEmpty()){
            // No existe historial para este usuario, mostrar boton solicitud
            nosolicitudes.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
        }else {
            nosolicitudes.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mAdapter = new HistorialAdapter(list);
            historial.setAdapter(mAdapter);

            ItemClickSupport.addTo(historial).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    com.example.davidleal.easycreditapp.Modelos.Solicitudes solicitud = list.get(position);
                    Intent intent = new Intent(getActivity(), DetalleSolicitud.class);
                    intent.putExtra("titulo","DetalleSolicitud");
                    intent.putExtra("estatus",solicitud.getEstatus());
                    intent.putExtra("idsolicitud",solicitud.getIdsolicitud());
                    intent.putExtra("interes",solicitud.getInteres());
                    intent.putExtra("fechaAclaracion",solicitud.getFechaAclaracion());
                    intent.putExtra("fechaSolicitud",solicitud.getFechaSolicitud());
                    intent.putExtra("plazo",solicitud.getPlazo());
                    intent.putExtra("cantidad",solicitud.getCantidad());
                    intent.putExtra("iduser",solicitud.getUserid());


                    startActivity(intent);
                }
            });
        }

        if (mDialog.isShowing())
            mDialog.dismiss();
    }

    @OnClick(R.id.solicitudes_fbtn_nuevasolicitud)
    void nuevaSolicitud(){
        Intent intent = new Intent(getActivity(), SolicitudNueva.class);
        startActivity(intent);
    }

    public void Error() {
        if (mDialog.isShowing())
            mDialog.dismiss();

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
