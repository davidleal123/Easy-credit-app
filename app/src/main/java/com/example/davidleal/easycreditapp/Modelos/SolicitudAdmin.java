package com.example.davidleal.easycreditapp.Modelos;

public class SolicitudAdmin extends Solicitudes{


    public String Usuario;

    public SolicitudAdmin() {
    }

    public SolicitudAdmin(int idsolicitud, int estatus, int userid, int cantidad, double interes, int plazo, String fechaSolicitud, String fechaAclaracion, String usuario) {
        super(idsolicitud, estatus, userid, cantidad, interes, plazo, fechaSolicitud, fechaAclaracion);
        Usuario = usuario;
    }


    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }


}
