package com.framgia.movie06.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 6/11/2017.
 */
public class ProductionCountry {
    @SerializedName("iso_3166_1")
    private String mIso;
    @SerializedName("name")
    private String mName;

    public String getIso() {
        return mIso;
    }

    public String getName() {
        return mName;
    }
}
