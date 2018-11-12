package com.eclairios.signedqrcodeapp;


import java.util.Date;

public class Turno_Model {

    //it is Model class for data
    //setterGetter Class
    String pva;
    String vendedor;
    String dia;
    String turno;


    public String getPva() {
        return pva;
    }

    public void setPva(String pva) {
        this.pva = pva;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
