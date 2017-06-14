package com.framgia.movie06.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by admin on 6/11/2017.
 */
public class CastResponse {
    @SerializedName("cast")
    private List<Cast> mCasts;

    public CastResponse(List<Cast> casts) {
        this.mCasts = casts;
    }

    public List<Cast> getCasts() {
        return mCasts;
    }
}
