package com.framgia.movie06.service;

import com.framgia.movie06.data.model.CastResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 6/11/2017.
 */
public interface CastService {
    @GET("movie/{movie_id}/credits")
    Call<CastResponse> getCastsMovie(@Path("movie_id") int movieId,
            @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/credits")
    Call<CastResponse> getCastsTV(@Path("tv_id") int tvId, @Query("api_key") String apiKey);
}
