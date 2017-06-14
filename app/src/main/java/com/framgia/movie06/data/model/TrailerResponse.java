package com.framgia.movie06.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by admin on 6/12/2017.
 */
public class TrailerResponse {
    @SerializedName("results")
    private List<Trailer> mResults;

    public List<Trailer> getResults() {
        return mResults;
    }
}
