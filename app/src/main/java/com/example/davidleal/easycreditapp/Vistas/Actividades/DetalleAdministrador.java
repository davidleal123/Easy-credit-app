package com.example.davidleal.easycreditapp.Vistas.Actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.davidleal.easycreditapp.Aplicacion.preferences;
import com.example.davidleal.easycreditapp.DataBase.CreditoDB;
import com.example.davidleal.easycreditapp.Modelos.Solicitudes;
import com.example.davidleal.easycreditapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetalleAdministrador extends BaseActivity {

    @BindView(R.id.admindetalle_txt_titulo)
    TextView titulo;
    @BindView(R.id.admindetalle_txt_estado)
    TextView estado;
    @BindView(R.id.admindetalle_txt_fechasol)
    TextView fechasolicitud;
    @BindView(R.id.admindetalle_txt_interes)
    TextView interes;
    @BindView(R.id.admindetalle_txt_monto)
    TextView monto;
    @BindView(R.id.admindetalle_txt_plazo)
    TextView plazo;
    @BindView(R.id.admindetalle_txt_user)
    TextView usuario;

    CreditoDB mydb;
    Solicitudes solicitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_administrador);
        ButterKnife.bind(this);
        mydb = new CreditoDB(this);
        preferences.getInstance().Initialize(this);
        Bundle extras = getIntent().getExtras();
        solicitud = new Solicitudes();
        if (extras != null) {
            // Cero igual a una solicitud
            titulo.setText("Detalle de Solicitud");
            estado.setText("PENDIENTE");
            fechasolicitud.setText(extras.getString("fechaSolicitud"));
            interes.setText("$" +extras.getDouble("interes"));
            monto.setText("$" + extras.getInt("cantidad"));
            plazo.setText(extras.getInt("plazo") + " Meses");
            usuario.setText(extras.getString("user"));
            solicitud.setUserid(extras.getInt("userid"));
            solicitud.setFechaSolicitud(extras.getString("fechaSolicitud"));
            solicitud.setInteres(extras.getDouble("interes"));
            solicitud.setCantidad(extras.getInt("cantidad"));
            solicitud.setPlazo(extras.getInt("plazo"));
            solicitud.setIdsolicitud(extras.getInt("idsolicitud"));
        }
    }


    @OnClick(R.id.admindetalle_lyt_aceptar)
    void aceptarSolicitud(){
        solicitud.setEstatus(1);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        solicitud.setFechaAclaracion(date);
        mydb.updateSolicitud(solicitud);
        mostrarMensaje("Solicitud Aceptada");
        this.finish();


    }


    @OnClick(R.id.admindetalle_lyt_cancelar)
    void cancelar(){
        solicitud.setEstatus(2);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        solicitud.setFechaAclaracion(date);
        mydb.updateSolicitud(solicitud);
        mostrarMensaje("Solicitud Cancelada");
        this.finish();
    }
}
