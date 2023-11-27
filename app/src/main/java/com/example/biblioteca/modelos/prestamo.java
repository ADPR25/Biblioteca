package com.example.biblioteca.modelos;

import java.util.Date;

public class prestamo {
    public int tipo;
    private int id;
    private int cantidad;
    private Date fechaInicio; // Cambiado a Date
    private Date fechaFinal; // Cambiado a Date

    public int getId_prestamo() {
        return id;
    }

    public void setId_prestamo(int id_prestamo) {
        this.id = id_prestamo;
    }

    public int getTipo_equipo() {
        return tipo;
    }

    public void setTipo_equipo(int tipo_equipo) {
        this.tipo = tipo_equipo;
    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) { // Modificado para aceptar Date
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) { // Modificado para aceptar Date
        this.fechaFinal = fechaFinal;
    }
}
