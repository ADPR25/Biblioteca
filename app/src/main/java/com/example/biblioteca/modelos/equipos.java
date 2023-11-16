package com.example.biblioteca.modelos;

public class equipos {

    private int id_equipos;

    public String getTipo_equipo() {
        return tipo_equipo;
    }

    public void setTipo_equipo(String tipo_equipo) {
        this.tipo_equipo = tipo_equipo;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    private String tipo_equipo;
    private String serial;
    private String descripcion;
    private String codigo;
    private String estado;

    public int getId_equipos() {
        return id_equipos;
    }

    public void setId_equipos(int id_equipos) {
        this.id_equipos = id_equipos;
    }
}
