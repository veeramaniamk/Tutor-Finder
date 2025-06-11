package com.saveetha.tutorfinder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static Retrofit getRetrofit()
    {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppConstants.BASE_URL)  //Change server URL
                .client(okHttpClient).build();
    }

    public static API makeAPI(){
        API api = getRetrofit().create(API.class);
        return api;
    }
}
