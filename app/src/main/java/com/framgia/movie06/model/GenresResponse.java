package com.framgia.movie06.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 6/7/2017.
 */
public class GenresResponse {
    @SerializedName("genres")
    private List<Genre> mGenreList;

    public GenresResponse(List<Genre> genreList) {
        this.mGenreList = genreList;
    }

    public List<Genre> getGenreList() {
        return mGenreList;
    }
}
