package com.example.davidleal.easycreditapp.Vistas.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.davidleal.easycreditapp.Aplicacion.preferences;
import com.example.davidleal.easycreditapp.DataBase.CreditoDB;
import com.example.davidleal.easycreditapp.Interfaces.ILoginView;
import com.example.davidleal.easycreditapp.Modelos.Usuario;
import com.example.davidleal.easycreditapp.R;
import com.example.davidleal.easycreditapp.Utilerias.Validacion;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends BaseActivity implements ILoginView {

    @BindView(R.id.login_input_usuario)
    public EditText usuariotxt;
    @BindView(R.id.login_btn_login)
    public Button btnlogin;

    CreditoDB mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ButterKnife.bind(this);
        preferences.getInstance().Initialize(this);
        mydb = new CreditoDB(this);


    }

    @OnClick(R.id.login_btn_login)
    void loginClick(){
        if(hasValidation()){
            if(usuariotxt.getText().toString().equals("ADMINISTRADOR")){
                preferences.getInstance().setUSerId("0");
                preferences.getInstance().setUser("ADMINISTRADOR");
                Intent intent = new Intent(this, Administrador.class);
                startActivity(intent);
            }else{
                Usuario user = new Usuario();
                user.setNombre(usuariotxt.getText().toString());
                user= mydb.login(user);

                preferences.getInstance().setUSerId(user.getUserid()+"");
                preferences.getInstance().setUser(user.getNombre());

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                this.finish();
            }


        }else {
            mostrarMensajeError(getString(R.string.IntenteNuevo));
        }

    }

    boolean hasValidation() {
        if (!Validacion.hasText(usuariotxt, getString(R.string.Vacios)))
            return false;
        return true;
    }

    @Override
    public boolean crearSesion(int id, String Nombre) {
        return false;
    }

    @Override
    public void cerrarSesion() {

    }
}
