package com.example.biblioteca;

import com.example.biblioteca.modelos.rol;
import com.example.biblioteca.modelos.usuario;
import com.example.biblioteca.modelos.tipo_equipo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserAPI {
    String API = "http://10.187.144.134:3000/";

    @GET("usuario")
    Call<List<String>> getusuario();

    @POST(API+"usuario/crear")
    Call<usuario> createusuario(@Body usuario usuario);

    @POST(API+"tipo-equipo/crear")
    Call<tipo_equipo> createtipo(@Body tipo_equipo tipo_equipo);

    @GET(API+"rol/obtener")
    Call<List<rol>> rolesobtenidos();
}

