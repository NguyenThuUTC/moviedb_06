package com.framgia.movie06.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 6/11/2017.
 */
public class ProductionCompany {
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
