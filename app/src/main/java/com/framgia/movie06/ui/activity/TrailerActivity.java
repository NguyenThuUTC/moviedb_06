package com.framgia.movie06.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.framgia.movie06.R;
import com.framgia.movie06.adapter.TrailerAdapter;
import com.framgia.movie06.data.model.Movie;
import com.framgia.movie06.data.model.Trailer;
import com.framgia.movie06.data.model.TrailerResponse;
import com.framgia.movie06.service.Config;
import com.framgia.movie06.service.TrailerService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.framgia.movie06.Constants.Constant.LOAD_ERROR;
import static com.framgia.movie06.Constants.Constant.MOVIE;

public class TrailerActivity extends AppCompatActivity
        implements TrailerAdapter.OnItemTrailerClickListener {
    public static final String EXTRA_MOVIE = "com.framgia.movie06.EXTRA_MOVIE";
    public static final String EXTRA_FEATURE_MOVIE = "com.framgia.movie06.EXTRA_FEATURE_MOVIE";
    public static final String TRAILER = "Trailer";
    private TextView mTextTitle;
    private TextView mTextNoResult;
    private RecyclerView mRecylerTrailer;
    private List<Trailer> mTrailerList;
    private TrailerAdapter mTrailerAdapter;
    private Movie mMovie;
    private int mFeatureMovie;
    private Retrofit mRetrofit;
    private TrailerService mTrailerService;

    public static Intent getInstance(Context context, Movie movie, int featureMovie) {
        Intent intent = new Intent(context, TrailerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_MOVIE, movie);
        bundle.putInt(EXTRA_FEATURE_MOVIE, featureMovie);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        initViews();
        getData();
        displayData();
        if (mFeatureMovie == MOVIE) {
            loadTrailerMovie();
        } else {
            loadTrailerTV();
        }
    }

    private void loadTrailerTV() {
        mTrailerService.getTrailerTV(mMovie.getId(), Config.API_KEY)
                .enqueue(mDataTrailerCallBack());
    }

    private void loadTrailerMovie() {
        mTrailerService.getTrailerMovie(mMovie.getId(), Config.API_KEY)
                .enqueue(mDataTrailerCallBack());
    }

    private Callback<TrailerResponse> mDataTrailerCallBack() {
        Callback<TrailerResponse> trailerResponseCallback = new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if (response == null || response.body() == null ||
                        response.body().getResults() == null) {
                    mTextNoResult.setVisibility(View.VISIBLE);
                    return;
                } else {
                    mTextNoResult.setVisibility(View.GONE);
                    mTrailerList.addAll(response.body().getResults());
                    mTrailerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Toast.makeText(TrailerActivity.this, LOAD_ERROR, Toast.LENGTH_SHORT).show();
            }
        };
        return trailerResponseCallback;
    }

    private void initViews() {
        mRetrofit = new Retrofit.Builder().baseUrl(Config.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mTrailerService = mRetrofit.create(TrailerService.class);
        mTextTitle = (TextView) findViewById(R.id.text_title);
        mRecylerTrailer = (RecyclerView) findViewById(R.id.recycler_trailer);
        mTextNoResult = (TextView) findViewById(R.id.text_no_result);
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mMovie = (Movie) bundle.getSerializable(EXTRA_MOVIE);
        mFeatureMovie = bundle.getInt(EXTRA_FEATURE_MOVIE);
    }

    private void displayData() {
        mTextTitle.setText(mMovie.getTitle() != null ? mMovie.getTitle() : mMovie.getName());
        mTrailerList = new ArrayList<>();
        mTrailerAdapter = new TrailerAdapter(mTrailerList, mMovie.getPosterPath());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecylerTrailer.setLayoutManager(layoutManager);
        mRecylerTrailer.setAdapter(mTrailerAdapter);
        mTrailerAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position, View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW).
                setData(Uri.parse(Config.TRAILER_URL + mTrailerList.get(position).getKey()));
        startActivity(intent);
    }
}
