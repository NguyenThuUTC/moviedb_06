package com.framgia.movie06.service;

import com.framgia.movie06.data.model.TrailerResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 6/12/2017.
 */
public interface TrailerService {
    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getTrailerMovie(@Path("movie_id") int movieId,
            @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/videos")
    Call<TrailerResponse> getTrailerTV(@Path("tv_id") int tvId, @Query("api_key") String apiKey);
}
