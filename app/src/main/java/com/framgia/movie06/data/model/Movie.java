package com.framgia.movie06.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 6/6/2017.
 */
public class Movie implements Serializable {
    @SerializedName("id")
    private int mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("name")
    private String mName;
    @SerializedName("origin_country")
    private String[] originCountry;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("first_air_date")
    private String mFirstAirDate;
    @SerializedName("genre_ids")
    private int[] mGenreIds;
    @SerializedName("vote_count")
    private int mVoteCount;
    @SerializedName("vote_average")
    private float mVoteAverage;
    @SerializedName("overview")
    private String mOverview;

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

    public int getVoteCount() {
        return mVoteCount;
    }

    public float getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(float mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public String getFirstAirDate() {
        return mFirstAirDate;
    }

    public String getOverview() {
        return mOverview;
    }
}
