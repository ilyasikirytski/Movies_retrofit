package com.example.screens.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {
    private static ApiFactory apiFactory;
    private static Retrofit retrofit;
    private static String BASE_URL = "https://api.themoviedb.org/3/discover/";
    private static String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/";
    private static String SMALL_POSTER_SIZE = "w342";
    private static String BIG_POSTER_SIZE = "w780";

    private ApiFactory() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static ApiFactory getInstance() {
        if (apiFactory == null) {
            apiFactory = new ApiFactory();
        }
        return apiFactory;
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }

    public static String moviePosterPathBuilder(boolean smallPoster, String posterPath) {
        if (smallPoster){
            return BASE_URL_IMAGE + SMALL_POSTER_SIZE + posterPath;
        } else {
            return BASE_URL_IMAGE + BIG_POSTER_SIZE + posterPath;
        }
    }
}
