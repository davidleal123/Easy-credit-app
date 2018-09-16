package com.example.davidleal.easycreditapp.Vistas.Actividades;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.davidleal.easycreditapp.Aplicacion.preferences;
import com.example.davidleal.easycreditapp.DataBase.CreditoDB;
import com.example.davidleal.easycreditapp.R;
import com.example.davidleal.easycreditapp.Vistas.Fragmentos.Historial;
import com.example.davidleal.easycreditapp.Vistas.Fragmentos.Perfil;
import com.example.davidleal.easycreditapp.Vistas.Fragmentos.Solicitudes;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, Historial.OnFragmentInteractionListener {

    CreditoDB mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        preferences.getInstance().Initialize(this);
        mydb = new CreditoDB(this);

        /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsuarioName = (TextView) headerView.findViewById(R.id.nav_txv_user);
        navUsuarioName.setText(preferences.getInstance().getUser());

        Historial historialfragment = new Historial();
        iniciarFragmento(historialfragment, "Historial");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            cerrarSesion();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        if (id == R.id.nav_historial) {
            // Handle the camera action
            fragment = new Historial();
            title = "Historial";
            cambiarFragmento(fragment,title);
        } else if (id == R.id.nav_solicitudes) {
            fragment = new Solicitudes();
            title = "Solicitudes";
            cambiarFragmento(fragment,title);
        } else if (id == R.id.nav_perfil) {
            fragment = new Perfil();
            title = "Perfil";
            cambiarFragmento(fragment,title);
        } else if (id == R.id.nav_logout) {
            //CerrarSesion
            cerrarSesion();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void cambiarFragmento(Fragment fragmento, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragmento,tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void iniciarFragmento(Fragment fragmento, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container,fragmento,tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void cerrarSesion(){

        //Cerrar Sesion
        preferences.getInstance().setUSerId("-1");
        preferences.getInstance().setUser("");
        //Eliminar todo de base de datos
        mydb.cerrarSesion();
        //fragmento catalogo
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        this.finish();
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
