package com.example.biblioteca.modelos;

public class equipos {

    public int tipo;
    public int estado;
    private int id_equipos;
    private String serial;
    private String descripcion;
    private String codigo;
    private String referencia;  // Add this field

    public int getId_equipos() {
        return id_equipos;
    }

    public void setId_equipos(int id_equipos) {
        this.id_equipos = id_equipos;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
