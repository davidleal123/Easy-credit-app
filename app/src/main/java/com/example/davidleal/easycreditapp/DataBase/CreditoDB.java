package com.example.davidleal.easycreditapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.davidleal.easycreditapp.Modelos.SolicitudAdmin;
import com.example.davidleal.easycreditapp.Modelos.Solicitudes;
import com.example.davidleal.easycreditapp.Modelos.Usuario;

import java.util.ArrayList;
import java.util.List;

public class CreditoDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Credito.db";
    public static final String Usuario_TABLE_NAME = "Usuarios";
    public static final String Solicitud_TABLE_NAME = "SolicitudCredito";


    public CreditoDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table Usuarios " +
                        "(idusuario INTEGER primary key AUTOINCREMENT, Nombre text, Estado int)"
        );
        db.execSQL(
                "create table SolicitudCredito " +
                        "(idsolicitud INTEGER primary key AUTOINCREMENT, Estado int, idUser int,Cantidad int,Interes double, Plazo int, FechaSolicitud text, FechaAclaracion,FOREIGN KEY(idUser) REFERENCES Usuarios(idusuario))"
        );
        db.execSQL(
                "create table Sesion " +
                        "(iduser INTEGER primary key,Nombre text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
// TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Credito");
        onCreate(db);
    }

    public  boolean crearSesion(int id, String Nombre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("iduser", id);
        contentValues.put("Nombre", Nombre);

        db.insert("Sesion", null, contentValues);
        return true;
    }

    public Usuario getSesion(){
        SQLiteDatabase db = this.getReadableDatabase();
        Usuario userq = new Usuario();
        userq.setUserid(-1);
        String query = "select * from Sesion";
        try{
            Cursor res =  db.rawQuery( query, null );
            res.moveToFirst();
            while (!res.isAfterLast()) {
                userq.setUserid(res.getInt(0));
                userq.setNombre(res.getString(1));
                userq.setEstatus(1);
                res.moveToNext();
            }
        }catch(SQLiteException e) { }

        return userq;
    }

    public void cerrarSesion(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Sesion");
    }

    public boolean crearUsuario(Usuario user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", user.getNombre());
        contentValues.put("Estado", 1);

        db.insert("Usuarios", null, contentValues);
        return true;
    }


    public Usuario consultaUsuario(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Usuario userq = new Usuario();
        userq.setUserid(-1);
        String query = "select * from " + Usuario_TABLE_NAME +" where Nombre='"+user+"'";
        try{
            Cursor res =  db.rawQuery( query, null );
            res.moveToFirst();
            while (!res.isAfterLast()) {
                userq.setUserid(res.getInt(0));
                userq.setNombre(res.getString(1));
                userq.setEstatus(res.getInt(2));
                res.moveToNext();
            }
        }catch(SQLiteException e) {
            userq.setUserid(-1);
        }

        return userq;
    }

    public Usuario login(Usuario user){
        Usuario usr = consultaUsuario(user.nombre);
        if(usr.getUserid() == -1){
            //Nuevo usuario
            crearUsuario(user);
        }
        usr = consultaUsuario(user.nombre);
        crearSesion(usr.getUserid(),usr.getNombre());
        return consultaUsuario(user.nombre);
    }

    public boolean updateUsuario (Usuario user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Nombre", user.getNombre());
        contentValues.put("idusuario", user.getUserid());
        contentValues.put("Estado", user.getEstatus());

        db.update("Usuarios", contentValues, "id = ? ", new String[] { user.getUserid()+"" } );
        return true;
    }

    ///////////////////

    public boolean crearSolicitud(Solicitudes solicitud){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Estado", solicitud.getEstatus());
        contentValues.put("idUser", solicitud.getUserid());
        contentValues.put("Cantidad", solicitud.getCantidad());
        contentValues.put("Interes",solicitud.getInteres());
        contentValues.put("Plazo", solicitud.getPlazo());
        contentValues.put("FechaSolicitud",solicitud.getFechaSolicitud());
        contentValues.put("FechaAclaracion", solicitud.getFechaAclaracion());

        db.insert(Solicitud_TABLE_NAME, null, contentValues);
        return true;
    }

    public List<Solicitudes> Historial(int userid) {
        List<Solicitudes> array_list = new ArrayList();
        Solicitudes solicitud = new Solicitudes();


        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + Solicitud_TABLE_NAME +" where idUser=" +userid+ " and Estado <> 0";
        try {
            Cursor res = db.rawQuery(query, null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                solicitud = new Solicitudes();
                solicitud.setIdsolicitud(res.getInt(0));
                solicitud.setEstatus(res.getInt(1));
                solicitud.setUserid(res.getInt(2));
                solicitud.setCantidad(res.getInt(3));
                solicitud.setInteres(res.getInt(4));
                solicitud.setPlazo(res.getInt(5));
                solicitud.setFechaSolicitud(res.getString(6));
                solicitud.setFechaAclaracion(res.getString(7));

                array_list.add(solicitud);
                res.moveToNext();
            }
        }
        catch(SQLiteException e) { }
        return array_list;
    }

    public List<Solicitudes> SolicitudesPendientes(int userid) {
        List<Solicitudes> array_list = new ArrayList();
        Solicitudes solicitud = new Solicitudes();


        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + Solicitud_TABLE_NAME +" where idUser =" +userid + " and Estado = 0";
        try {
            Cursor res = db.rawQuery(query, null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                solicitud = new Solicitudes();
                solicitud.setIdsolicitud(res.getInt(0));
                solicitud.setEstatus(res.getInt(1));
                solicitud.setUserid(res.getInt(2));
                solicitud.setCantidad(res.getInt(3));
                solicitud.setInteres(res.getInt(4));
                solicitud.setPlazo(res.getInt(5));
                solicitud.setFechaSolicitud(res.getString(6));
                solicitud.setFechaAclaracion(res.getString(7));

                array_list.add(solicitud);
                res.moveToNext();
            }
        }
        catch(SQLiteException e) { }
        return array_list;
    }
    public boolean updateSolicitud (Solicitudes solicitud) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idsolicitud", solicitud.getIdsolicitud());
        contentValues.put("Estado", solicitud.getEstatus());
        contentValues.put("idUser", solicitud.getUserid());
        contentValues.put("Cantidad", solicitud.getCantidad());
        contentValues.put("Interes",solicitud.getInteres());
        contentValues.put("Plazo", solicitud.getPlazo());
        contentValues.put("FechaSolicitud",solicitud.getFechaSolicitud());
        contentValues.put("FechaAclaracion", solicitud.getFechaAclaracion());

        db.update(Solicitud_TABLE_NAME, contentValues, "idsolicitud = ? ", new String[] { solicitud.getIdsolicitud()+"" } );
        return true;
    }


    public List<SolicitudAdmin> SolicitudesAdmin() {
        List<SolicitudAdmin> array_list = new ArrayList();
        SolicitudAdmin solicitud = new SolicitudAdmin();


        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + Solicitud_TABLE_NAME + " INNER JOIN "+Usuario_TABLE_NAME +
                " ON SolicitudCredito.idUser = Usuarios.idusuario "+
                " where SolicitudCredito.Estado = 0";
        try {
            Cursor res = db.rawQuery(query, null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                solicitud = new SolicitudAdmin();
                solicitud.setIdsolicitud(res.getInt(0));
                solicitud.setEstatus(res.getInt(1));
                solicitud.setUserid(res.getInt(2));
                solicitud.setCantidad(res.getInt(3));
                solicitud.setInteres(res.getInt(4));
                solicitud.setPlazo(res.getInt(5));
                solicitud.setFechaSolicitud(res.getString(6));
                solicitud.setFechaAclaracion(res.getString(7));
                solicitud.setUserid(res.getInt(8));
                solicitud.setUsuario(res.getString(9));

                array_list.add(solicitud);
                res.moveToNext();
            }
        }
        catch(SQLiteException e) { }
        return array_list;
    }
    public List<SolicitudAdmin> SolicitudesAdmin(int userid) {
        List<SolicitudAdmin> array_list = new ArrayList();
        SolicitudAdmin solicitud = new SolicitudAdmin();


        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + Solicitud_TABLE_NAME + " INNER JOIN "+Usuario_TABLE_NAME +
                " ON SolicitudCredito.idUser = Usuarios.idusuario "+
                " where Estado = 0 and idUser = "+ userid;
        try {
            Cursor res = db.rawQuery(query, null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                solicitud = new SolicitudAdmin();
                solicitud.setIdsolicitud(res.getInt(0));
                solicitud.setEstatus(res.getInt(1));
                solicitud.setUserid(res.getInt(2));
                solicitud.setCantidad(res.getInt(3));
                solicitud.setInteres(res.getInt(4));
                solicitud.setPlazo(res.getInt(5));
                solicitud.setFechaSolicitud(res.getString(6));
                solicitud.setFechaAclaracion(res.getString(7));
                solicitud.setUserid(res.getInt(8));
                solicitud.setUsuario(res.getString(9));

                array_list.add(solicitud);
                res.moveToNext();
            }
        }
        catch(SQLiteException e) { }
        return array_list;
    }
}
