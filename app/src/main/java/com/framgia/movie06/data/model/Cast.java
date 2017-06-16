package com.framgia.movie06.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 6/11/2017.
 */
public class Cast {
    @SerializedName("id")
    private int mId;
    @SerializedName("character")
    private String mCharacter;
    @SerializedName("name")
    private String mName;
    @SerializedName("profile_path")
    private String mProfilePath;

    public Cast(int id, String character, String name, String profilePath) {
        mId = id;
        mCharacter = character;
        mName = name;
        mProfilePath = profilePath;
    }

    public int getId() {
        return mId;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public String getName() {
        return mName;
    }

    public String getProfilePath() {
        return mProfilePath;
    }
}
