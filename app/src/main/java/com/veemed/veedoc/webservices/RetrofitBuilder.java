package com.veemed.veedoc.webservices;

import com.veemed.veedoc.utils.Utility;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build();

    private static Retrofit.Builder rb = new Retrofit.Builder()
            .baseUrl(Utility.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = rb.build();
    private static VeeDocAPI veeDocAPI = retrofit.create(VeeDocAPI.class);

    public static VeeDocAPI getVeeDocAPI(){
        return veeDocAPI;
    }

}
