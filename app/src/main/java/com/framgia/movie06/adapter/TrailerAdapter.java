package com.framgia.movie06.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.framgia.movie06.R;
import com.framgia.movie06.data.model.Trailer;
import com.framgia.movie06.service.Config;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by admin on 6/12/2017.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.RecyclerViewHolder> {
    private OnItemTrailerClickListener mOnItemClickListener;
    private List<Trailer> mListTrailer;
    private LayoutInflater mLayoutInflater;
    private String mProfilePath;

    public TrailerAdapter(List<Trailer> listTrailer, String profilePath) {
        this.mListTrailer = listTrailer;
        this.mProfilePath = profilePath;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) mLayoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = mLayoutInflater.inflate(R.layout.item_recycler_trailer, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Trailer trailer = mListTrailer.get(position);
        if (trailer == null) {
            return;
        }
        holder.bindData(trailer);
    }

    @Override
    public int getItemCount() {
        return mListTrailer == null ? 0 : mListTrailer.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private ImageView mImagePoster;
        private TextView mTextName;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mImagePoster = (ImageView) itemView.findViewById(R.id.image_poster);
            mTextName = (TextView) itemView.findViewById(R.id.text_name);
            itemView.setOnClickListener(this);
        }

        private void bindData(Trailer trailer) {
            Picasso.with(mLayoutInflater.getContext())
                    .load(Config.POSTER_URL + mProfilePath)
                    .error(R.drawable.no_image)
                    .into(mImagePoster);
            mTextName.setText(trailer.getName());
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition(), v);
            }
        }
    }

    public void setOnItemClickListener(TrailerAdapter.OnItemTrailerClickListener clickListener) {
        mOnItemClickListener = clickListener;
    }

    public interface OnItemTrailerClickListener {
        void onItemClick(int position, View v);
    }
}
