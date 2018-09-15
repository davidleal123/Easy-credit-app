package com.example.davidleal.easycreditapp.Vistas.Actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidleal.easycreditapp.Aplicacion.preferences;
import com.example.davidleal.easycreditapp.DataBase.CreditoDB;
import com.example.davidleal.easycreditapp.Modelos.Solicitudes;
import com.example.davidleal.easycreditapp.R;
import com.example.davidleal.easycreditapp.Utilerias.Utilerias;
import com.example.davidleal.easycreditapp.Utilerias.Validacion;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SolicitudNueva extends BaseActivity {

    @BindView(R.id.nuevas_btn_solicitar)
    Button solicitar;
    @BindView(R.id.nuevas_cbx_Tarjeta)
    CheckBox cbxtarjeta;
    @BindView(R.id.nuevas_input_edad)
    EditText txtEdad;
    @BindView(R.id.nuevas_input_monto)
    EditText monto;
    @BindView(R.id.nuevas_rbtn_tres)
    RadioButton rbtntres;
    @BindView(R.id.nuevas_rbtn_seis)
    RadioButton rbtnseis;
    @BindView(R.id.nuevas_rbtn_nueve)
    RadioButton rbtnnueve;

    @BindView(R.id.nuevas_txt_interes)
    TextView textInteres;

    CreditoDB mydb;
    int plazo = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_nueva);

        ButterKnife.bind(this);
        preferences.getInstance().Initialize(this);
        mydb = new CreditoDB(this);


    }


    @OnClick(R.id.nuevas_cbx_Tarjeta)
    public void cbxTerminos() {
        cbxtarjeta.setChecked(true);
    }

    @OnClick(R.id.nuevas_txt_Tarjeta)
    public void textoTerminos() {
        cbxtarjeta.setChecked(true);
    }

    @OnClick(R.id.nuevas_btn_solicitar)
    void nuevaSolicitud() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (!hasValidation()) {
            mostrarMensajeError(getString(R.string.nuevoIntento));
        } else {
            Solicitudes nuevo = new Solicitudes();
            nuevo.setInteres(Double.parseDouble(textInteres.getText().toString()));
            nuevo.setFechaSolicitud(date);
            nuevo.setFechaAclaracion("''");
            nuevo.setPlazo(plazo);
            nuevo.setCantidad(Integer.parseInt(monto.getText().toString()));
            nuevo.setUserid(Integer.parseInt(preferences.getInstance().getUserid()));
            mydb.crearSolicitud(nuevo);
            mostrarMensaje("Solicitud Generada");
            this.finish();
        }

    }


    @OnClick(R.id.nuevas_rbtn_nueve)
    void btnnueve(){
        double importe = aplicarInteres(12);
        textInteres.setText(importe+"");
        plazo = 9;
    }

    @OnClick(R.id.nuevas_rbtn_seis)
    void btnseis(){
        double importe = aplicarInteres(7);
        textInteres.setText(importe+"");
        plazo = 6;
    }

    @OnClick(R.id.nuevas_rbtn_tres)
    void btntres(){
        double importe = aplicarInteres(5);
        textInteres.setText(importe+"");
        plazo = 3;
    }

    double aplicarInteres(double porcentaje){
        double cantidad;
        if(!Validacion.hasText(monto,"El monto no puede ir vacio")){
            cantidad = 0;
        } else {
            cantidad =  Double.parseDouble(monto.getText().toString());
        }

        double importe = cantidad * (porcentaje/100);
        importe += cantidad;
        return importe;
    }


    boolean hasValidation() {
        if (!Validacion.hasText(txtEdad, getString(R.string.Vacios)))
            return false;
        if (!Validacion.hasText(monto, getString(R.string.Vacios)))
            return false;
        if (!(rbtnnueve.isChecked() || rbtnseis.isChecked() || rbtntres.isChecked()))
            return false;
        int edad = Integer.parseInt(txtEdad.getText().toString());
        if(edad<20){
            Toast mensaje = Toast.makeText(getApplicationContext(), "Debes ser mayor de 20 aós", Toast.LENGTH_SHORT);
            // mensaje.setGravity(Gravity.CENTER, 0,0);
            mensaje.show();
            return  false;
        }
        return cbxtarjeta.isChecked();
    }

}
