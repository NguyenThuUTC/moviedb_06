package com.framgia.movie06.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.framgia.movie06.R;
import com.framgia.movie06.adapter.MovieAdapter;
import com.framgia.movie06.data.local.DatabaseHelper;
import com.framgia.movie06.data.model.Genre;
import com.framgia.movie06.data.model.GenresResponse;
import com.framgia.movie06.data.model.Movie;
import com.framgia.movie06.data.model.MoviesResponse;
import com.framgia.movie06.service.Config;
import com.framgia.movie06.service.MovieService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.framgia.movie06.Constants.Constant.LOAD_ERROR;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MovieAdapter.OnItemClickListener {
    public static final int POPULAR_MOVIE = 1;
    public static final int UPCOMING_MOVIE = 2;
    public static final int NOW_PLAYING_MOVIE = 3;
    public static final int TOP_RATED_MOVIE = 4;
    public static final int POPULAR_TV = 5;
    public static final int TOP_RATED_TV = 6;
    public static final int ON_THE_AIR_TV = 7;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerMovies;
    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovieList;
    private Retrofit mRetrofit;
    private MovieService mMovieService;
    private int mFeatureMovie;
    private int mPage = 1;
    private int mTotalPage;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initViews();
    }

    private void initViews() {
        mDatabaseHelper = new DatabaseHelper(this);
        mRecyclerMovies = (RecyclerView) findViewById(R.id.recyclerview_detail);
        mRetrofit = new Retrofit.Builder().baseUrl(Config.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mMovieService = mRetrofit.create(MovieService.class);
        mMovieList = new ArrayList<>();
        List<Genre> l = mDatabaseHelper.getAllGenre();
        if (mDatabaseHelper.getAllGenre() == null) {
            insertDataForGenreTable();
        }
        loadPopular(mPage);
        mMovieAdapter = new MovieAdapter(mMovieList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerMovies.setLayoutManager(layoutManager);
        mRecyclerMovies.setAdapter(mMovieAdapter);
        mRecyclerMovies.setOnScrollListener(mScrollRecyclerMovie);
        mMovieAdapter.setOnItemClickListener(this);
    }

    private void insertDataForGenreTable() {
        mMovieService.getGenres(Config.API_KEY).enqueue(new Callback<GenresResponse>() {
            @Override
            public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                if (response == null || response.body() == null ||
                        response.body().getGenreList() == null) {
                    return;
                }
                for (Genre genre : response.body().getGenreList()) {
                    if (mDatabaseHelper.getNameGenre(genre.getId()) != null) {
                        continue;
                    }
                    mDatabaseHelper.insertData(genre);
                }
            }

            @Override
            public void onFailure(Call<GenresResponse> call, Throwable throwable) {
                Toast.makeText(HomeActivity.this, LOAD_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
        mMovieService.getGenresTV(Config.API_KEY).enqueue(new Callback<GenresResponse>() {
            @Override
            public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                if (response == null || response.body() == null ||
                        response.body().getGenreList() == null) {
                    return;
                }
                for (Genre genre : response.body().getGenreList()) {
                    if (mDatabaseHelper.getNameGenre(genre.getId()) != null) {
                        continue;
                    }
                    mDatabaseHelper.insertData(genre);
                }
            }

            @Override
            public void onFailure(Call<GenresResponse> call, Throwable throwable) {
                Toast.makeText(HomeActivity.this, LOAD_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private RecyclerView.OnScrollListener mScrollRecyclerMovie = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            final LinearLayoutManager linearLayoutManager =
                    (LinearLayoutManager) mRecyclerMovies.getLayoutManager();
            int visibleItemCount = linearLayoutManager.getChildCount();
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            if (mPage <= mTotalPage
                    && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                mPage++;
                switch (mFeatureMovie) {
                    case POPULAR_MOVIE:
                        loadPopular(mPage);
                        break;
                    case UPCOMING_MOVIE:
                        loadUpComing(mPage);
                        break;
                    case NOW_PLAYING_MOVIE:
                        loadNowPlaying(mPage);
                        break;
                    case TOP_RATED_MOVIE:
                        loadTopRated(mPage);
                        break;
                    case POPULAR_TV:
                        loadPopularTV(mPage);
                        break;
                    case TOP_RATED_TV:
                        loadTopRatedTV(mPage);
                        break;
                    case ON_THE_AIR_TV:
                        loadTheAirTV(mPage);
                        break;
                    default:
                        break;
                }
            }
        }
    };

    private void loadPopular(int page) {
        mFeatureMovie = POPULAR_MOVIE;
        mMovieService.getpopularMovies(Config.API_KEY, page).enqueue(mDataCallBack());
    }

    private void loadNowPlaying(int page) {
        mMovieService.getNowPlayingMovies(Config.API_KEY, page).enqueue(mDataCallBack());
    }

    private void loadUpComing(int page) {
        mMovieService.getUpcomingMovies(Config.API_KEY, page).enqueue(mDataCallBack());
    }

    private Callback<MoviesResponse> mDataCallBack() {
        Callback<MoviesResponse> moviesResponseCallback = new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response == null || response.body() == null ||
                        response.body().getResults() == null) {
                    return;
                }
                mMovieList.addAll(response.body().getResults());
                mTotalPage = response.body().getTotalPages();
                mMovieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable throwable) {
                Toast.makeText(HomeActivity.this, LOAD_ERROR, Toast.LENGTH_SHORT).show();
            }
        };
        return moviesResponseCallback;
    }

    private void loadTopRated(int page) {
        mMovieService.getTopRatedMovies(Config.API_KEY, page).enqueue(mDataCallBack());
    }

    private void loadPopularTV(int page) {
        mMovieService.getPopularTV(Config.API_KEY, page).enqueue(mDataCallBack());
    }

    private void loadTopRatedTV(int page) {
        mMovieService.getTopRatedTV(Config.API_KEY, page).enqueue(mDataCallBack());
    }

    private void loadTheAirTV(int page) {
        mMovieService.getTheAirTV(Config.API_KEY, page).enqueue(mDataCallBack());
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_search) {
            Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        // TODO: 6/6/2017
        switch (id) {
            case R.id.menu_favourite_movie:
                break;
            case R.id.menu_popular_movie:
                mFeatureMovie = POPULAR_MOVIE;
                mPage = 1;
                mMovieList.clear();
                mMovieAdapter.notifyDataSetChanged();
                loadPopular(mPage);
                break;
            case R.id.menu_now_playing_movie:
                mFeatureMovie = NOW_PLAYING_MOVIE;
                mPage = 1;
                mMovieList.clear();
                mMovieAdapter.notifyDataSetChanged();
                loadNowPlaying(mPage);
                break;
            case R.id.menu_upcoming_movie:
                mFeatureMovie = UPCOMING_MOVIE;
                mPage = 1;
                mMovieList.clear();
                mMovieAdapter.notifyDataSetChanged();
                loadNowPlaying(mPage);
                break;
            case R.id.menu_top_rated_movie:
                mFeatureMovie = TOP_RATED_MOVIE;
                mPage = 1;
                mMovieList.clear();
                mMovieAdapter.notifyDataSetChanged();
                loadTopRated(mPage);
                break;
            case R.id.menu_popular_tv:
                mFeatureMovie = POPULAR_TV;
                mPage = 1;
                mMovieList.clear();
                mMovieAdapter.notifyDataSetChanged();
                loadPopularTV(mPage);
                break;
            case R.id.menu_top_rated_tv:
                mFeatureMovie = TOP_RATED_TV;
                mPage = 1;
                mMovieList.clear();
                mMovieAdapter.notifyDataSetChanged();
                loadTopRatedTV(mPage);
                break;
            case R.id.menu_on_the_air_tv:
                mFeatureMovie = ON_THE_AIR_TV;
                mPage = 1;
                mMovieList.clear();
                mMovieAdapter.notifyDataSetChanged();
                loadTheAirTV(mPage);
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(int position, View v, Movie movie, String genres) {
        startActivity(DetailActivity.getInstance(HomeActivity.this, movie, genres));
    }
}
