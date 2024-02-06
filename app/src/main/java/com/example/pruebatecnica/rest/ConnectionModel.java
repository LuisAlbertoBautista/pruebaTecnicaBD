package com.example.pruebatecnica.rest;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface ConnectionModel {
    @GET
    @Headers({"Content-Type: application/json; charset=UTF-8"})
    Call<ResponseBody> simpleGet(@Url String url);

}