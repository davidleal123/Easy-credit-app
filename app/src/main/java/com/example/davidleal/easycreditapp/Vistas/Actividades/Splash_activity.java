package com.example.davidleal.easycreditapp.Vistas.Actividades;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.davidleal.easycreditapp.Aplicacion.preferences;
import com.example.davidleal.easycreditapp.DataBase.CreditoDB;
import com.example.davidleal.easycreditapp.Modelos.Usuario;
import com.example.davidleal.easycreditapp.R;
import com.example.davidleal.easycreditapp.Vistas.Fragmentos.Historial;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Splash_activity extends BaseActivity implements Historial.OnFragmentInteractionListener {

    @BindView(R.id.splash_imgv_logo)
    public ImageView logo;
    CreditoDB mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ButterKnife.bind(this);
        preferences.getInstance().Initialize(this);
        mydb = new CreditoDB(this);


        final Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        logo.setAnimation(bounce);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                getSesion();
                //Si la sesion se encuentra activa, entonces iniciar la app en mainactivity
                //Caso contrario, solicitar al usuario inicar sesion
            }
        }, 2000);


    }


    public void getSesion() {
        Usuario user = mydb.getSesion();
        if (user.getUserid() == -1) {
            //Sesion ==-1 significa que aun no hay ningun usuario, por lo tanto se regresa falso para ell inicio de sesión

            Intent intent = new Intent(Splash_activity.this, Login.class);
            startActivity(intent);
            /* Finish splash activity so user cant go back to it. */
            Splash_activity.this.finish();
        } else {
            //Caso contrario, hay una sesion activa y se procede como verdadero
            //Se agregar variables globales como preferencias

            preferences.getInstance().setUser(user.getNombre());
            preferences.getInstance().setUSerId(user.getUserid() + "");


            Intent intent = new Intent(Splash_activity.this, MainActivity.class);
            startActivity(intent);
            /* Finish splash activity so user cant go back to it. */
            Splash_activity.this.finish();

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
