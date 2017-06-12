package com.framgia.movie06.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.framgia.movie06.R;
import com.framgia.movie06.data.local.DatabaseHelper;
import com.framgia.movie06.data.model.Movie;
import com.framgia.movie06.service.Config;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 6/7/2017.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.RecyclerViewHolder> {
    private List<Movie> mListMovie;
    private LayoutInflater mLayoutInflater;
    private DatabaseHelper mDatabaseHelper;
    public static final String HYPHEN = "-";

    public MovieAdapter(List<Movie> listMovie) {
        this.mListMovie = listMovie;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) mLayoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = mLayoutInflater.inflate(R.layout.item_recyclervew_detail, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int position) {
        Movie movie = mListMovie.get(position);
        if (movie != null) {
            recyclerViewHolder.bindData(movie);
        }
    }

    @Override
    public int getItemCount() {
        return mListMovie == null ? 0 : mListMovie.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImagePoster;
        private TextView mTextTitle;
        private TextView mTextGenres;
        private TextView mTextReleaseDate;
        private TextView mTextVoteAverage;
        private RatingBar mRatingVoteAverage;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mImagePoster = (ImageView) itemView.findViewById(R.id.image_poster);
            mTextTitle = (TextView) itemView.findViewById(R.id.text_title);
            mTextGenres = (TextView) itemView.findViewById(R.id.text_genre);
            mTextReleaseDate = (TextView) itemView.findViewById(R.id.text_release_date);
            mTextVoteAverage = (TextView) itemView.findViewById(R.id.text_vote_average);
            mRatingVoteAverage = (RatingBar) itemView.findViewById(R.id.rating_vote_average);
        }

        private void bindData(Movie movie) {
            Picasso.with(mLayoutInflater.getContext())
                .load(Config.POSTER_URL + movie.getPosterPath())
                .into(mImagePoster);
            if (movie.getTitle() != null) {
                mTextTitle.setText(movie.getTitle());
            } else {
                mTextTitle.setText(movie.getName());
            }
            if (movie.getReleaseDate() != null) {
                mTextReleaseDate.setText(movie.getReleaseDate());
            } else {
                mTextReleaseDate.setText(movie.getFirstAirDate());
            }
            mTextVoteAverage.setText(movie.getVoteAverage() + mLayoutInflater
                .getContext().getString(R.string.value_rating));
            mRatingVoteAverage
                .setRating(Float.parseFloat(movie.getVoteAverage()) / 2);
            mDatabaseHelper = new DatabaseHelper(mLayoutInflater.getContext());
            String textGenre = "";
            if (movie.getGenreIds() != null && movie.getGenreIds().length > 0) {
                for (int id : movie.getGenreIds()) {
                    textGenre += mDatabaseHelper.getNameGenre(id) + HYPHEN;
                }
                textGenre = textGenre.substring(0, textGenre.length() - 1);
            }
            if (textGenre.equals("")) {
                mTextGenres.setVisibility(View.GONE);
            } else {
                mTextGenres.setVisibility(View.VISIBLE);
                mTextGenres.setText(textGenre);
            }
        }
    }
}
