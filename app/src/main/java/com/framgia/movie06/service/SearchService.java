package com.framgia.movie06.service;

import com.framgia.movie06.data.model.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by admin on 6/9/2017.
 */
public interface SearchService {
    @GET("search/multi")
    Call<MoviesResponse> getSearchMovieFeature(@Query("api_key") String apiKey,
            @Query("query") String query, @Query("page") int page);
}
