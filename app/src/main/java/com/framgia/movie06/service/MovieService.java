package com.framgia.movie06.service;

import com.framgia.movie06.model.GenresResponse;
import com.framgia.movie06.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by admin on 6/7/2017.
 */
public interface MovieService {
    @GET("genre/movie/list")
    Call<GenresResponse> getGenres(@Query("api_key") String apiKey);
    @GET("movie/popular")
    Call<MoviesResponse> getpopularMovies(@Query("api_key") String apiKey);
    @GET("movie/upcoming")
    Call<MoviesResponse> getUpcomingMovies(@Query("api_key") String apiKey);
    @GET("movie/now_playing")
    Call<MoviesResponse> getNowPlayingMovies(@Query("api_key") String apiKey);
    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);
}
