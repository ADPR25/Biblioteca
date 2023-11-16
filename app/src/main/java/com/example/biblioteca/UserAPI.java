package com.example.biblioteca;

import com.example.biblioteca.modelos.rol;
import com.example.biblioteca.modelos.usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserAPI {
    String API = "http://10.187.152.213:3000/";

    @GET("usuario")
    Call<List<String>> getusuario();

    @POST(API+"usuario/crear")
    Call<usuario> createusuario(@Body usuario usuario);

    @GET(API+"rol/obtener")
    Call<List<rol>> rolesobtenidos();
}

