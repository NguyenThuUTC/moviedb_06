package com.framgia.movie06.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.framgia.movie06.R;
import com.framgia.movie06.adapter.CastAdapter;
import com.framgia.movie06.data.local.DatabaseHelper;
import com.framgia.movie06.data.model.Cast;
import com.framgia.movie06.data.model.CastResponse;
import com.framgia.movie06.data.model.CountriesCompaniesResponse;
import com.framgia.movie06.data.model.Movie;
import com.framgia.movie06.data.model.ProductionCompany;
import com.framgia.movie06.data.model.ProductionCountry;
import com.framgia.movie06.service.CastService;
import com.framgia.movie06.service.Config;
import com.framgia.movie06.service.MovieService;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.framgia.movie06.Constants.Constant.FAVOURITE;
import static com.framgia.movie06.Constants.Constant.HYPHEN;
import static com.framgia.movie06.Constants.Constant.LOAD_ERROR;
import static com.framgia.movie06.Constants.Constant.MAXIMUM_VOTE_POINT;
import static com.framgia.movie06.Constants.Constant.MOVIE;
import static com.framgia.movie06.Constants.Constant.SLASH;
import static com.framgia.movie06.Constants.Constant.TV;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MOVIE = "com.framgia.movie06.EXTRA_MOVIE";
    public static final String EXTRA_GENRE = "com.framgia.movie06.EXTRA_GENRE";
    public static final String EXTRA_FEATURE_MOVIE = "com.framgia.movie06.EXTRA_FEATURE_MOVIE";
    private ImageView mImagePoster;
    private TextView mTextTitle;
    private TextView mTextGenres;
    private TextView mTextReleaseDate;
    private TextView mTextVoteCount;
    private TextView mTextOriginCountry;
    private TextView mTextProductionCompanis;
    private TextView mTextTitleCountry;
    private TextView mTextTitleCompany;
    private TextView mTextVoteAverage;
    private TextView mTextTitleCast;
    private ImageView mImageFavourite;
    private RatingBar mRatingVoteAverage;
    private LinearLayout mLinearTrailer;
    private TextView mTextOverView;
    private RecyclerView mRecyclerViewCast;
    private CastAdapter mCastAdapter;
    private List<Cast> mCastList;
    private Retrofit mRetrofit;
    private MovieService mMovieService;
    private CastService mCastService;
    private int mFeatureMovie;
    private Movie mMovie;
    private String genre;
    private DatabaseHelper mDatabaseHelper;
    private boolean mFavourite;
    private List<ProductionCompany> mCompanies;
    private List<ProductionCountry> mCountries;

    public static Intent getInstance(Context context, Movie movie, String genre, int featureMovie) {
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_MOVIE, movie);
        bundle.putString(EXTRA_GENRE, genre);
        bundle.putInt(EXTRA_FEATURE_MOVIE, featureMovie);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
        getData();
        displayData();
        checkFavourite();
        switch (mFeatureMovie) {
            case MOVIE:
                loadProductCountriesCompaniesMovie();
                loadCastMovie();
                break;
            case TV:
                loadCountriesTV();
                loadProductCompaniesTV();
                loadCastTV();
                break;
            case FAVOURITE:
                getCompanisFromDB();
                getCountries();
                getCastsFromDB();
                break;
            default:
                break;
        }
    }

    private void getCastsFromDB() {
        List<Integer> list = mDatabaseHelper.getIdCastMovie(mMovie.getId());
        mCastList.clear();
        if (list == null) {
            return;
        }
        for (int id : list) {
            mCastList.add(mDatabaseHelper.getCast(id));
        }
        mCastAdapter.notifyDataSetChanged();
    }

    private void getCountries() {
        if (mMovie.getTitle() == null) {
            loadCountriesTV();
            return;
        }
        String textCountry = "";
        List<String> list = mDatabaseHelper.getIdCountryMovie(mMovie.getId());
        if (list == null) {
            return;
        }
        for (String id : list) {
            textCountry += mDatabaseHelper.getNameCountry(id) + HYPHEN;
        }
        textCountry = textCountry.substring(0, textCountry.length() - 1);
        mTextOriginCountry.setText(textCountry);
    }

    private void getCompanisFromDB() {
        String textCompanies = "";
        List<Integer> list = mDatabaseHelper.getIdCompanyMovie(mMovie.getId());
        if (list == null) {
            return;
        }
        for (int id : list) {
            textCompanies += mDatabaseHelper.getNameCompany(id) + HYPHEN;
        }
        textCompanies = textCompanies.substring(0, textCompanies.length() - 1);
        mTextProductionCompanis.setText(textCompanies);
    }

    private void checkFavourite() {
        mDatabaseHelper = new DatabaseHelper(this);
        Movie movie = mDatabaseHelper.getMovie(mMovie.getId());
        mFavourite = movie != null ? true : false;
        mImageFavourite.setImageResource(
                mFavourite ? R.mipmap.ic_favourite : R.mipmap.ic_not_favourite);
    }

    private void loadCountriesTV() {
        String textCountry = "";
        if (mMovie.getOriginCountry().length == 0) {
            mTextTitleCountry.setVisibility(View.GONE);
            mTextOriginCountry.setVisibility(View.GONE);
            return;
        }
        for (String country : mMovie.getOriginCountry()) {
            textCountry += country + HYPHEN;
        }
        if (textCountry.length() > 0) {
            textCountry = textCountry.substring(0, textCountry.length() - 1);
        }
        mTextTitleCountry.setVisibility(View.VISIBLE);
        mTextOriginCountry.setVisibility(View.VISIBLE);
        mTextOriginCountry.setText(textCountry);
    }

    private void loadProductCompaniesTV() {
        mMovieService.getCompanyCountryTV(mMovie.getId(), Config.API_KEY)
                .enqueue(new Callback<CountriesCompaniesResponse>() {

                    @Override
                    public void onResponse(Call<CountriesCompaniesResponse> call,
                            Response<CountriesCompaniesResponse> response) {
                        if (response == null || response.body() == null ||
                                response.body().getCompanyies() == null) {
                            return;
                        }
                        checkCompanies(response.body().getCompanyies());
                    }

                    @Override
                    public void onFailure(Call<CountriesCompaniesResponse> call, Throwable t) {
                        Toast.makeText(DetailActivity.this, LOAD_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkCompanies(List<ProductionCompany> list) {
        String textCompanies = "";
        for (ProductionCompany company : list) {
            textCompanies += company.getName() + HYPHEN;
        }
        if (textCompanies.length() > 0) {
            textCompanies = textCompanies.substring(0, textCompanies.length() - 1);
            mTextProductionCompanis.setText(textCompanies);
            mTextProductionCompanis.setVisibility(View.VISIBLE);
            mTextTitleCompany.setVisibility(View.VISIBLE);
        } else {
            mTextProductionCompanis.setVisibility(View.GONE);
            mTextTitleCompany.setVisibility(View.GONE);
        }
    }

    private void initViews() {
        mRetrofit = new Retrofit.Builder().baseUrl(Config.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mCastService = mRetrofit.create(CastService.class);
        mMovieService = mRetrofit.create(MovieService.class);
        mLinearTrailer = (LinearLayout) findViewById(R.id.linear_trailer);
        mImagePoster = (ImageView) findViewById(R.id.image_poster);
        mTextTitle = (TextView) findViewById(R.id.text_title);
        mTextGenres = (TextView) findViewById(R.id.text_genre);
        mTextOverView = (TextView) findViewById(R.id.text_overview);
        mTextReleaseDate = (TextView) findViewById(R.id.text_release_date);
        mTextVoteAverage = (TextView) findViewById(R.id.text_vote_average);
        mRatingVoteAverage = (RatingBar) findViewById(R.id.rating_vote_average);
        mTextVoteCount = (TextView) findViewById(R.id.text_vote_count);
        mTextOriginCountry = (TextView) findViewById(R.id.text_origin_country);
        mTextProductionCompanis = (TextView) findViewById(R.id.text_production_companis);
        mTextTitleCompany = (TextView) findViewById(R.id.text_title_company);
        mTextTitleCountry = (TextView) findViewById(R.id.text_title_country);
        mTextTitleCast = (TextView) findViewById(R.id.text_title_cast);
        mImageFavourite = (ImageView) findViewById(R.id.image_favourite);
        mCastList = new ArrayList<>();
        mCastAdapter = new CastAdapter(mCastList);
        mRecyclerViewCast = (RecyclerView) findViewById(R.id.recycler_cast);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerViewCast.setLayoutManager(layoutManager);
        mRecyclerViewCast.setAdapter(mCastAdapter);
        mLinearTrailer.setOnClickListener(this);
        mImageFavourite.setOnClickListener(this);
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getExtras();
        mMovie = (Movie) bundle.getSerializable(EXTRA_MOVIE);
        genre = bundle.getString(EXTRA_GENRE, "");
        mFeatureMovie = bundle.getInt(EXTRA_FEATURE_MOVIE);
    }

    private void displayData() {
        if (mMovie == null) {
            return;
        }
        Picasso.with(this)
                .load(Config.POSTER_URL + mMovie.getPosterPath())
                .error(R.drawable.no_image)
                .into(mImagePoster);
        String title = mMovie.getTitle() != null ? mMovie.getTitle()
                : mMovie.getName() != null ? mMovie.getName() : null;
        mTextTitle.setText(title != null ? title : "");
        mTextTitle.setVisibility(title != null ? View.VISIBLE : View.GONE);

        String date = mMovie.getReleaseDate() != null ? mMovie.getReleaseDate()
                : mMovie.getFirstAirDate() != null ? mMovie.getFirstAirDate() : null;
        mTextReleaseDate.setText(date != null ? date : "");
        mTextReleaseDate.setVisibility(date != null ? View.VISIBLE : View.GONE);

        mTextVoteAverage.setText(mMovie.getVoteAverage() + SLASH + MAXIMUM_VOTE_POINT);
        mRatingVoteAverage.setRating(mMovie.getVoteAverage());
        mTextVoteCount.setText(this.getString(R.string.vote_count) + mMovie.getVoteCount());
        mTextGenres.setText(genre);
        mTextOverView.setText(mMovie.getOverview());
    }

    private void loadProductCountriesCompaniesMovie() {
        mMovieService.getCompanyCountryMovie(mMovie.getId(), Config.API_KEY).
                enqueue(new Callback<CountriesCompaniesResponse>() {
                    @Override
                    public void onResponse(Call<CountriesCompaniesResponse> call,
                            Response<CountriesCompaniesResponse> response) {
                        if (response == null || response.body() == null ||
                                (response.body().getCountries() == null
                                        && response.body().getCompanyies() == null)) {
                            return;
                        }
                        if (response.body().getCountries() != null) {
                            mCountries = new ArrayList<ProductionCountry>();
                            mCountries.addAll(response.body().getCountries());
                            String textCountries = "";
                            for (ProductionCountry country : mCountries) {
                                textCountries += country.getName() + HYPHEN;
                            }
                            if (textCountries.length() > 0) {
                                textCountries =
                                        textCountries.substring(0, textCountries.length() - 1);
                            }
                            mTextOriginCountry.setText(textCountries);
                            mTextOriginCountry.setVisibility(View.VISIBLE);
                            mTextTitleCountry.setVisibility(View.VISIBLE);
                        } else {
                            mTextOriginCountry.setVisibility(View.GONE);
                            mTextTitleCountry.setVisibility(View.GONE);
                        }
                        if (response.body().getCompanyies() != null) {
                            mCompanies = new ArrayList<ProductionCompany>();
                            mCompanies.addAll(response.body().getCompanyies());
                            checkCompanies(mCompanies);
                        } else {
                            mTextProductionCompanis.setVisibility(View.GONE);
                            mTextTitleCompany.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<CountriesCompaniesResponse> call, Throwable t) {
                        Toast.makeText(DetailActivity.this, LOAD_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadCastMovie() {
        mCastService.getCastsMovie(mMovie.getId(), Config.API_KEY).
                enqueue(mDataCastCallBack());
    }

    private void loadCastTV() {
        mCastService.getCastsTV(mMovie.getId(), Config.API_KEY).enqueue(mDataCastCallBack());
    }

    private Callback<CastResponse> mDataCastCallBack() {
        Callback<CastResponse> castResponseCallback = new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                if (response == null || response.body() == null ||
                        response.body().getCasts() == null) {
                    mTextTitleCast.setVisibility(View.GONE);
                    return;
                }
                mTextTitleCast.setVisibility(View.VISIBLE);
                mCastList.addAll(response.body().getCasts());
                mCastAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {
                Toast.makeText(DetailActivity.this, LOAD_ERROR, Toast.LENGTH_SHORT).show();
            }
        };
        return castResponseCallback;
    }

    private void removeGenreMovie() {
        if (mMovie.getGenreIds() == null) {
            return;
        }
        for (int genreId : mMovie.getGenreIds()) {
            mDatabaseHelper.removeGenreMovie(genreId, mMovie.getId());
        }
    }

    private void removecompanyMovie() {
        if (mCompanies == null) {
            return;
        }
        for (ProductionCompany company : mCompanies) {
            mDatabaseHelper.removeCompanyMovie(company.getId(), mMovie.getId());
        }
    }

    private void removeCountryMovie() {
        if (mCountries == null) {
            return;
        }
        for (ProductionCountry country : mCountries) {
            mDatabaseHelper.removeCountryMovie(country.getIso(), mMovie.getId());
        }
    }

    private void removeCastMovie() {
        if (mCastList == null) {
            return;
        }
        for (Cast cast : mCastList) {
            mDatabaseHelper.removeCastMovie(cast.getId(), mMovie.getId());
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.linear_trailer) {
            startActivity(TrailerActivity.getInstance(DetailActivity.this, mMovie, mFeatureMovie));
        } else if (id == R.id.image_favourite) {
            if (mFavourite) {
                mFavourite = false;
                mImageFavourite.setImageResource(R.mipmap.ic_not_favourite);
                mDatabaseHelper.removeMovie(mMovie.getId());
                removeGenreMovie();
                removecompanyMovie();
                removeCountryMovie();
                removeCastMovie();
            } else {
                mFavourite = true;
                mImageFavourite.setImageResource(R.mipmap.ic_favourite);
                mDatabaseHelper.insertDataMovie(mMovie);
                insertGenreMovie();
                insertcompanyMovie();
                insertCountryMovie();
                insertCastMovie();
            }
        }
    }

    private void insertCastMovie() {
        if (mCastList == null) {
            return;
        }
        for (Cast cast : mCastList) {
            mDatabaseHelper.insertDataCastMovie(cast.getId(), mMovie.getId());
            if (mDatabaseHelper.getCast(cast.getId()) != null) {
                continue;
            }
            mDatabaseHelper.insertDataCast(cast);
        }
    }

    private void insertCountryMovie() {
        if (mCountries == null) {
            return;
        }
        for (ProductionCountry country : mCountries) {
            mDatabaseHelper.insertDataCountryMovie(country.getIso(), mMovie.getId());
            if (mDatabaseHelper.getNameCountry(country.getIso()) != null) {
                continue;
            }
            mDatabaseHelper.insertDataCountry(country);
        }
    }

    private void insertcompanyMovie() {
        if (mCompanies == null) {
            return;
        }
        for (ProductionCompany company : mCompanies) {
            mDatabaseHelper.insertDataCompanyMovie(company.getId(), mMovie.getId());
            if (mDatabaseHelper.getNameCompany(company.getId()) != null) {
                continue;
            }
            mDatabaseHelper.insertDataCompany(company);
        }
    }

    private void insertGenreMovie() {
        if (mMovie.getGenreIds() == null) {
            return;
        }
        for (int genreId : mMovie.getGenreIds()) {
            mDatabaseHelper.insertDataGenreMovie(genreId, mMovie.getId());
        }
    }
}
