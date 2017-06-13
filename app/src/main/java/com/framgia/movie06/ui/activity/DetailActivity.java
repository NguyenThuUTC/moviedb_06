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
import com.framgia.movie06.data.model.CountriesCompaniesResponse;
import com.framgia.movie06.data.model.Movie;
import com.framgia.movie06.data.model.ProductionCompany;
import com.framgia.movie06.data.model.ProductionCountry;
import com.framgia.movie06.service.Config;
import com.framgia.movie06.service.MovieService;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.framgia.movie06.Constants.Constant.HYPHEN;
import static com.framgia.movie06.Constants.Constant.LOAD_ERROR;
import static com.framgia.movie06.Constants.Constant.MAXIMUM_VOTE_POINT;
import static com.framgia.movie06.Constants.Constant.SLASH;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "com.framgia.movie06.EXTRA_MOVIE";
    public static final String EXTRA_GENRE = "com.framgia.movie06.EXTRA_GENRE";
    public static final String BUNDLE_MOVIE = "BUNDLE_MOVIE";
    public static final String BUNDLE_GENRE = "BUNDLE_GENRE";
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
    private RatingBar mRatingVoteAverage;
    private LinearLayout mLinearTrailer;
    private TextView mTextOverView;
    private RecyclerView mRecyclerViewCast;
    private Retrofit mRetrofit;
    private MovieService mMovieService;
    private Movie movie;
    private String genre;

    public static Intent getInstance(Context context, Movie movie, String genre) {
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_MOVIE, movie);
        bundle.putString(EXTRA_GENRE, genre);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
        getData();
        loadProductCountries();
        displayData();
    }

    private void initViews() {
        mRetrofit = new Retrofit.Builder().baseUrl(Config.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mImagePoster = (ImageView) findViewById(R.id.image_poster);
        mTextTitle = (TextView) findViewById(R.id.text_title);
        mTextGenres = (TextView) findViewById(R.id.text_genre);
        mTextOverView = (TextView) findViewById(R.id.text_overview);
        mTextReleaseDate = (TextView) findViewById(R.id.text_release_date);
        mTextVoteAverage = (TextView) findViewById(R.id.text_vote_average);
        mRatingVoteAverage = (RatingBar) findViewById(R.id.rating_vote_average);
        mLinearTrailer = (LinearLayout) findViewById(R.id.linear_trailer);
        mTextVoteCount = (TextView) findViewById(R.id.text_vote_count);
        mTextOriginCountry = (TextView) findViewById(R.id.text_origin_country);
        mTextProductionCompanis = (TextView) findViewById(R.id.text_production_companis);
        mTextTitleCompany = (TextView) findViewById(R.id.text_title_company);
        mTextTitleCountry = (TextView) findViewById(R.id.text_title_country);
        mRecyclerViewCast = (RecyclerView) findViewById(R.id.recycler_cast);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerViewCast.setLayoutManager(layoutManager);
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getExtras();
        movie = (Movie) bundle.getSerializable(EXTRA_MOVIE);
        genre = bundle.getString(EXTRA_GENRE, "");
    }

    private void displayData() {
        if (movie == null) {
            return;
        }
        Picasso.with(this)
                .load(Config.POSTER_URL + movie.getPosterPath())
                .error(R.drawable.no_image)
                .into(mImagePoster);
        String title = movie.getTitle() != null ? movie.getTitle()
                : movie.getName() != null ? movie.getName() : null;
        mTextTitle.setText(title != null ? title : "");
        mTextTitle.setVisibility(title != null ? View.VISIBLE : View.GONE);

        String date = movie.getReleaseDate() != null ? movie.getReleaseDate()
                : movie.getFirstAirDate() != null ? movie.getFirstAirDate() : null;
        mTextReleaseDate.setText(date != null ? date : "");
        mTextReleaseDate.setVisibility(date != null ? View.VISIBLE : View.GONE);

        mTextVoteAverage.setText(movie.getVoteAverage() + SLASH + MAXIMUM_VOTE_POINT);
        mRatingVoteAverage.setRating(movie.getVoteAverage());
        mTextVoteCount.setText(this.getString(R.string.vote_count) + movie.getVoteCount());
        mTextGenres.setText(genre);
        mTextOverView.setText(movie.getOverview());
    }

    private void loadProductCountries() {
        mMovieService = mRetrofit.create(MovieService.class);
        mMovieService.getCompanyCountry(movie.getId(), Config.API_KEY).
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
                            String textCountries = "";
                            for (ProductionCountry country : response.body().getCountries()) {
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
                            String textCompanies = "";
                            for (ProductionCompany company : response.body().getCompanyies()) {
                                textCompanies += company.getName() + HYPHEN;
                            }
                            if (textCompanies.length() > 0) {
                                textCompanies =
                                        textCompanies.substring(0, textCompanies.length() - 1);
                            }
                            mTextProductionCompanis.setText(textCompanies);
                            mTextProductionCompanis.setVisibility(View.VISIBLE);
                            mTextTitleCompany.setVisibility(View.VISIBLE);
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
}
