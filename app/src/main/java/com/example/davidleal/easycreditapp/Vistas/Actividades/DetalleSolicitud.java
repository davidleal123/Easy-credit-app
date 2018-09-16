package com.example.davidleal.easycreditapp.Vistas.Actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.davidleal.easycreditapp.Aplicacion.preferences;
import com.example.davidleal.easycreditapp.DataBase.CreditoDB;
import com.example.davidleal.easycreditapp.Modelos.Solicitudes;
import com.example.davidleal.easycreditapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalleSolicitud extends BaseActivity {

    @BindView(R.id.detalle_txt_titulo)
    TextView titulo;
    @BindView(R.id.detalle_txt_estado)
    TextView estado;
    @BindView(R.id.detalle_txt_fecharesp)
    TextView fecharespuesta;
    @BindView(R.id.detalle_txt_fechasol)
    TextView fechasolicitud;
    @BindView(R.id.detalle_txt_interes)
    TextView interes;
    @BindView(R.id.detalle_txt_monto)
    TextView monto;
    @BindView(R.id.detalle_txt_plazo)
    TextView plazo;
    @BindView(R.id.detalle_txt_user)
    TextView usuario;
    @BindView(R.id.detalle_txt_fecharespmod)
    TextView fecharespuestamod;
    CreditoDB mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_solicitud);
        ButterKnife.bind(this);
        mydb = new CreditoDB(this);
        preferences.getInstance().Initialize(this);
        Bundle extras = getIntent().getExtras();
        Solicitudes solicitud = new Solicitudes();
        usuario.setText(preferences.getInstance().getUser());
        if (extras != null) {
            if (extras.getInt("estatus") == 0) {
                // Cero igual a una solicitud
                fecharespuesta.setVisibility(View.GONE);
                fecharespuestamod.setVisibility(View.GONE);
                titulo.setText("Detalle de Solicitud");
                estado.setText("PENDIENTE");
            } else if(extras.getInt("estatus") == 1){
                //Historial
                fecharespuesta.setVisibility(View.VISIBLE);
                fecharespuestamod.setVisibility(View.VISIBLE);
                titulo.setText("Detalle de Historial");
                fecharespuesta.setText(extras.getString("fechaAclaracion"));
                estado.setText("ACEPTADO");
            }else {
                //Historial
                fecharespuesta.setVisibility(View.VISIBLE);
                fecharespuestamod.setVisibility(View.VISIBLE);
                titulo.setText("Detalle de Historial");
                fecharespuesta.setText(extras.getString("fechaAclaracion"));
                estado.setText("CANCELADO");
            }

            fechasolicitud.setText(extras.getString("fechaSolicitud"));
            interes.setText("$" +extras.getDouble("interes"));
            monto.setText("$" + extras.getInt("cantidad"));
            plazo.setText(extras.getInt("plazo") + " Meses");

        }

    }
}
