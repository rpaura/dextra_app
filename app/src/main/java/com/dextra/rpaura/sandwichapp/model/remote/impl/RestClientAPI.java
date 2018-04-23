package com.dextra.rpaura.sandwichapp.model.remote.impl;

import com.dextra.rpaura.sandwichapp.model.remote.IRestClientAPI;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestClientAPI {
    private static RestClientAPI sInstance;
    private IRestClientAPI mIRestClientAPI;
    private static final String BASE_URL = "http://172.20.159.175:8080/api/";


    private RestClientAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setFieldNamingStrategy(
                                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.mIRestClientAPI = retrofit.create(IRestClientAPI.class);
    }

    public static RestClientAPI getInstance() {
        if (sInstance == null) {
            synchronized (RestClientAPI.class) {
                if (sInstance == null) sInstance = new RestClientAPI();
            }
        }
        return sInstance;
    }

    public IRestClientAPI getAPI() {
        return mIRestClientAPI;
    }
}
