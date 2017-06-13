package com.framgia.movie06.service;

import com.framgia.movie06.data.model.CountriesCompaniesResponse;
import com.framgia.movie06.data.model.GenresResponse;
import com.framgia.movie06.data.model.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 6/7/2017.
 */
public interface MovieService {
    @GET("genre/movie/list")
    Call<GenresResponse> getGenres(@Query("api_key") String apiKey);

    @GET("genre/tv/list")
    Call<GenresResponse> getGenresTV(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MoviesResponse> getpopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/upcoming")
    Call<MoviesResponse> getUpcomingMovies(@Query("api_key") String apiKey,
            @Query("page") int page);

    @GET("movie/now_playing")
    Call<MoviesResponse> getNowPlayingMovies(@Query("api_key") String apiKey,
            @Query("page") int page);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey,
            @Query("page") int page);

    @GET("tv/popular")
    Call<MoviesResponse> getPopularTV(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/top_rated")
    Call<MoviesResponse> getTopRatedTV(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("tv/on_the_air")
    Call<MoviesResponse> getTheAirTV(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{movie_id}")
    Call<CountriesCompaniesResponse> getCompanyCountry(@Path("movie_id") int movieId,
            @Query("api_key") String apiKey);
}
