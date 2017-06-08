package com.framgia.movie06.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 6/6/2017.
 */
public class Genre {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;

    public Genre(int id, String name) {
        this.mId = id;
        this.mName = name;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
