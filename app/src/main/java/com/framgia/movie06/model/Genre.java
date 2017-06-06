package com.framgia.movie06.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 6/6/2017.
 */
public class Genre {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
