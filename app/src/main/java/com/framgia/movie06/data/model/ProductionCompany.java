package com.framgia.movie06.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 6/11/2017.
 */
public class ProductionCompany {
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
