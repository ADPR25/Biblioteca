package com.example.biblioteca.modelos;

public class prestamo {
    private int id;

    public int getId_prestamo() {
        return id;
    }

    public void setId_prestamo(int id_prestamo) {
        this.id = id;
    }


    private String tipo;

    public String getTipo_equipo() {
        return tipo;
    }

    public void setTipo_equipo(String tipo_equipo) {
        this.tipo = tipo;
    }



    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(String fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public String getHora_prestamo() {
        return hora_prestamo;
    }

    public void setHora_prestamo(String hora_prestamo) {
        this.hora_prestamo = hora_prestamo;
    }

    public String getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(String fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    public String getHora_devolucion() {
        return hora_devolucion;
    }

    public void setHora_devolucion(String hora_devolucion) {
        this.hora_devolucion = hora_devolucion;
    }


    private String cedula;
    private String cantidad;
    private String fecha_prestamo;
    private String hora_prestamo;
    private String fecha_devolucion;
    private String hora_devolucion;


}
