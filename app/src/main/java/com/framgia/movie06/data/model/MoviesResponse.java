package com.framgia.movie06.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 6/7/2017.
 */
public class MoviesResponse {
    @SerializedName("results")
    private List<Movie> mResults;
    @SerializedName("total_pages")
    private int mTotalPages;

    public MoviesResponse(List<Movie> results) {
        this.mResults = results;
    }

    public List<Movie> getResults() {
        return mResults;
    }

    public int getTotalPages() {
        return mTotalPages;
    }
}
