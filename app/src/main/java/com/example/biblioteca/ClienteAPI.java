package com.example.biblioteca;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteAPI {

    public static Retrofit getClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http//localhost:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }



}






