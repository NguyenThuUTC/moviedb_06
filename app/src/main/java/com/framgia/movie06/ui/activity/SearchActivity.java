package com.framgia.movie06.ui.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.framgia.movie06.R;
import com.framgia.movie06.adapter.MovieAdapter;
import com.framgia.movie06.data.model.Movie;
import com.framgia.movie06.data.model.MoviesResponse;
import com.framgia.movie06.service.Config;
import com.framgia.movie06.service.SearchService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.framgia.movie06.Constants.Constant.LOAD_ERROR;

public class SearchActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener {
    private Retrofit mRetrofit;
    private SearchService mSearchService;
    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovieList;
    private int mPage = 1;
    private int mTotalPage;
    private String mQuerySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle(R.string.title_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        RecyclerView recyclerSearch = (RecyclerView) findViewById(R.id.recycler_search);
        mRetrofit = new Retrofit.Builder().baseUrl(Config.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mSearchService = mRetrofit.create(SearchService.class);
        mMovieList = new ArrayList<>();
        mMovieAdapter = new MovieAdapter(mMovieList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerSearch.setLayoutManager(layoutManager);
        recyclerSearch.setAdapter(mMovieAdapter);
        recyclerSearch.setOnScrollListener(mScrollRecyclerSearch);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(mTextChanged);
        return super.onCreateOptionsMenu(menu);
    }

    private SearchView.OnQueryTextListener mTextChanged = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            loadSearchFeatures(mPage, query);
            mQuerySearch = query;
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    };

    private RecyclerView.OnScrollListener mScrollRecyclerSearch =
            new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    final LinearLayoutManager linearLayoutManager =
                            (LinearLayoutManager) recyclerView.getLayoutManager();
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int firstVisibleItemPosition =
                            linearLayoutManager.findFirstVisibleItemPosition();
                    if (mPage <= mTotalPage
                            && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        mPage++;
                        loadSearchFeatures(mPage, mQuerySearch);
                    }
                }
            };

    private void loadSearchFeatures(int page, String query) {
        mMovieList.clear();
        mSearchService.getSearchMovieFeature(Config.API_KEY, query, page)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call,
                            Response<MoviesResponse> response) {
                        if (response == null || response.body() == null ||
                                response.body().getResults() == null) {
                            return;
                        }
                        mMovieList.addAll(response.body().getResults());
                        mTotalPage = response.body().getTotalPages();
                        mMovieAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Toast.makeText(SearchActivity.this, LOAD_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemClick(int position, View v, Movie movie, String genres, int featureMovie) {
        startActivity(DetailActivity.getInstance(SearchActivity.this, movie, genres, featureMovie));
    }
}
