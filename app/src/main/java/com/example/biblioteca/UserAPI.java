package com.example.biblioteca;

import com.example.biblioteca.modelos.rol;
import com.example.biblioteca.modelos.usuario;
import com.example.biblioteca.modelos.tipo_equipo;
import com.example.biblioteca.modelos.estado_equipo;
import com.example.biblioteca.modelos.estado_prestamo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserAPI {
    String API = "http://10.187.144.113:3000/";

    @GET("usuario")
    Call<List<String>> getusuario();

    @POST(API+"usuario/crear")
    Call<usuario> createusuario(@Body usuario usuario);

    @POST(API+"estado-prestamo/crear")
    Call<estado_prestamo> createestado_p(@Body estado_prestamo estado_prestamo);

    @POST(API+"estado-equipos/crear")
    Call<estado_equipo> createestado(@Body estado_equipo estado_equipo);

    @POST(API+"tipo-equipo/crear")
    Call<tipo_equipo> createtipo(@Body tipo_equipo tipo_equipo);

    @POST(API+"rol/crear")
    Call<rol> createrol(@Body rol rol);

    @GET(API+"rol/obtener")
    Call<List<rol>> rolesobtenidos();

    @POST(API+"estado-prestamo/obtener")
    Call<estado_prestamo> estados_p_obtenidos(@Body estado_prestamo estado_prestamo);

    @POST(API+"estado-equipos/obtener")
    Call<estado_equipo> estado_e_obtenidos (@Body estado_equipo estado_equipo);

    @POST(API+"tipo-equipo/obtener")
    Call<tipo_equipo> tipo_equipo_obtenidos(@Body tipo_equipo tipo_equipo);

}

