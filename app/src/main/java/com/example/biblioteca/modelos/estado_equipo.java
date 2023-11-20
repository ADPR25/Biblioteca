package com.example.biblioteca.modelos;

public class estado_equipo {
    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private Number id;

    private String descripcion;
}
