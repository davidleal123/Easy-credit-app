package com.example.davidleal.easycreditapp.Modelos;

public class Solicitudes {

    public int idsolicitud;
    public  int estatus;
    public int userid;
    public  int cantidad;
    public  double Interes;
    public int plazo;
    public String FechaSolicitud;
    public String FechaAclaracion;

    public Solicitudes() {
    }

    public Solicitudes(int idsolicitud, int estatus, int userid, int cantidad, double interes, int plazo, String fechaSolicitud, String fechaAclaracion) {
        this.idsolicitud = idsolicitud;
        this.estatus = estatus;
        this.userid = userid;
        this.cantidad = cantidad;
        Interes = interes;
        this.plazo = plazo;
        FechaSolicitud = fechaSolicitud;
        FechaAclaracion = fechaAclaracion;
    }

    public int getIdsolicitud() {
        return idsolicitud;
    }

    public void setIdsolicitud(int idsolicitud) {
        this.idsolicitud = idsolicitud;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getInteres() {
        return Interes;
    }

    public void setInteres(double interes) {
        Interes = interes;
    }

    public void setInteres(int interes) {
        Interes = interes;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public String getFechaSolicitud() {
        return FechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        FechaSolicitud = fechaSolicitud;
    }

    public String getFechaAclaracion() {
        return FechaAclaracion;
    }

    public void setFechaAclaracion(String fechaAclaracion) {
        FechaAclaracion = fechaAclaracion;
    }
}
