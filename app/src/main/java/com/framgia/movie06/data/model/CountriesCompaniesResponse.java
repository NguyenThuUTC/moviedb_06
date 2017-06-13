package com.framgia.movie06.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by admin on 6/11/2017.
 */
public class CountriesCompaniesResponse {
    @SerializedName("production_countries")
    private List<ProductionCountry> mCountries;
    @SerializedName("production_companies")
    private List<ProductionCompany> mCompanyies;

    public List<ProductionCountry> getCountries() {
        return mCountries;
    }

    public List<ProductionCompany> getCompanyies() {
        return mCompanyies;
    }
}
