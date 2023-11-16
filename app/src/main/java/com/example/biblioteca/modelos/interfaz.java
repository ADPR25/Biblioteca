package com.example.biblioteca.modelos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class interfaz {
    public interface UserAPI {
        @GET("usuario")
        Call<List<usuario>> getusuario();

        @POST("usuario/crear")
        Call<usuario> createusuario(@Body usuario usuario);


    }


}

