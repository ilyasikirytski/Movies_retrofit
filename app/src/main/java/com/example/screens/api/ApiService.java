package com.example.screens.api;

import com.example.screens.pojo.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie")
    Observable<Response> getResponse(@Query("api_key") String apiKey,@Query("language") String lang, @Query("sort_by") String methodOfSort, @Query("vote_count.gte") int voteCount, @Query("page") int page);
}

//movie?api_key=3348f966e6084875c231e59a4f14e5bd&language=ru&sort_by=popularity.desc&include_adult=false&include_video=false&page=1
//https://api.themoviedb.org/3/search/movie?api_key=3348f966e6084875c231e59a4f14e5bd&query=Jack+Reacher ДЛЯ ПОИСКА ФИЛЬМОВ