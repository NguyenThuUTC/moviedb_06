package com.framgia.movie06.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.framgia.movie06.Constants.Constant;
import com.framgia.movie06.R;
import com.framgia.movie06.adapter.MovieAdapter;
import com.framgia.movie06.data.local.DatabaseHelper;
import com.framgia.movie06.data.model.Movie;
import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity
        implements MovieAdapter.OnItemClickListener {
    private TextView mTextNoData;
    private RecyclerView mRecyclerFavourite;
    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovieList;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        setTitle(this.getString(R.string.my_favourite));
        initViews();
    }

    private void initViews() {
        mDatabaseHelper = new DatabaseHelper(this);
        mTextNoData = (TextView) findViewById(R.id.text_no_data);
        mRecyclerFavourite = (RecyclerView) findViewById(R.id.recycler_favourite);
        mMovieList = new ArrayList<>();
        mMovieList = mDatabaseHelper.getAllMovie();
        mTextNoData.setVisibility(mMovieList != null ? View.GONE : View.VISIBLE);
        mMovieAdapter = new MovieAdapter(mMovieList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerFavourite.setLayoutManager(layoutManager);
        mRecyclerFavourite.setAdapter(mMovieAdapter);
        mMovieAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position, View v, Movie movie, String genres, int featureMovie) {
        startActivity(DetailActivity.getInstance(FavouriteActivity.this, movie, genres,
                Constant.FAVOURITE));
    }
}
