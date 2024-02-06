package com.example.pruebatecnica.rest;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class SingleTon {
    private static Retrofit retrofit = null;

    public static final String BASE_URL = "";


    public static final ArrayList<Interceptor> requestInterceptors = new ArrayList<>();
    public static OkHttpClient okHttpClientTime = null;

    public static void provideOkHttpClient(){
        interceptors();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(requestInterceptors.get(0));
        okHttpClientTime = builder.connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                })
                .build();
    }
    public static ConnectionModel setSingleton() {
        if (retrofit==null){
            if (okHttpClientTime == null){
                provideOkHttpClient();
            }
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClientTime).build();
        }
        return retrofit.create(ConnectionModel.class);
    }

    public static void interceptors(){
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);
        requestInterceptors.add(logger);
    }
}