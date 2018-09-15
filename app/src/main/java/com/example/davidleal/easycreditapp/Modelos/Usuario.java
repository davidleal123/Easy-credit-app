package com.example.davidleal.easycreditapp.Modelos;

public class Usuario {
    public int userid;
    public String nombre;
    public int estatus;

    public Usuario(int userid, String nombre, int estatus) {
        this.userid = userid;
        this.nombre = nombre;
        this.estatus = estatus;
    }

    public Usuario() {
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
}
