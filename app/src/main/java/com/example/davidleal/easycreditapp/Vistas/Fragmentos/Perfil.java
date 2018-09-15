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
import android.widget.TextView;

import com.example.davidleal.easycreditapp.Aplicacion.preferences;
import com.example.davidleal.easycreditapp.DataBase.CreditoDB;
import com.example.davidleal.easycreditapp.Modelos.Solicitudes;
import com.example.davidleal.easycreditapp.R;
import com.example.davidleal.easycreditapp.Utilerias.ItemClickSupport;
import com.example.davidleal.easycreditapp.Vistas.Actividades.DetalleSolicitud;
import com.example.davidleal.easycreditapp.Vistas.Adaptadores.HistorialAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Perfil extends Fragment {

    public List<Solicitudes> list;
    private RecyclerView.Adapter mAdapter;
    public ProgressDialog mDialog;
    CreditoDB mydb;

    public @BindView(R.id.perfil_rcv_solicitudes)
    RecyclerView historial;
    public @BindView(R.id.perfil_swipe_solicitudes)
    SwipeRefreshLayout mSwipeRefreshLayout;
    public @BindView(R.id.perfil_txt_nombre)
    TextView txtnombre;
    public @BindView(R.id.perfil_imgv_nosolicitudes)
    ImageView nosolicitudes;

    private OnFragmentInteractionListener mListener;

    public Perfil() {
        // Required empty public constructor
    }


    public static Perfil newInstance() {
        Perfil fragment = new Perfil();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
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

        this.txtnombre.setText(preferences.getInstance().getUser());

        return  view;
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
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);            mAdapter = new HistorialAdapter(list);
            historial.setAdapter(mAdapter);
        }

        if (mDialog.isShowing())
            mDialog.dismiss();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
