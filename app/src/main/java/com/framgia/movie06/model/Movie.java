package com.framgia.movie06.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 6/6/2017.
 */
public class Movie {
    @SerializedName("id")
    private int mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("genre_ids")
    private int[] mGenreIds;
    @SerializedName("vote_average")
    private String mVoteAverage;

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public int[] getGenreIds() {
        return mGenreIds;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }
}
