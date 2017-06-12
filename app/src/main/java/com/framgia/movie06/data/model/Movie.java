package com.framgia.movie06.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 6/6/2017.
 */
public class Movie {
    @SerializedName("id")
    private int mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("name")
    private String mName;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("first_air_date")
    private String mFirstAirDate;
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

    public String getName() {
        return mName;
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

    public String getFirstAirDate() {
        return mFirstAirDate;
    }
}
