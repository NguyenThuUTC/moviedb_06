package com.framgia.movie06.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.framgia.movie06.R;
import com.framgia.movie06.data.model.Cast;
import com.framgia.movie06.service.Config;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by admin on 6/11/2017.
 */
public class CastAdapter extends RecyclerView.Adapter<CastAdapter.RecyclerViewHolder> {
    private List<Cast> mListCast;
    private LayoutInflater mLayoutInflater;

    public CastAdapter(List<Cast> listCast) {
        this.mListCast = listCast;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) mLayoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = mLayoutInflater.inflate(R.layout.item_recycler_cast, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Cast cast = mListCast.get(position);
        if (cast == null) {
            return;
        }

        holder.bindData(cast);
    }

    @Override
    public int getItemCount() {
        return mListCast == null ? 0 : mListCast.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageProfile;
        private TextView mTextCharacter;
        private TextView mTextName;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mImageProfile = (ImageView) itemView.findViewById(R.id.image_profile);
            mTextCharacter = (TextView) itemView.findViewById(R.id.text_character);
            mTextName = (TextView) itemView.findViewById(R.id.text_name);
        }

        private void bindData(Cast cast) {
            Picasso.with(mLayoutInflater.getContext())
                    .load(Config.POSTER_URL + cast.getProfilePath())
                    .error(R.drawable.no_image)
                    .into(mImageProfile);
            mTextCharacter.setText(cast.getCharacter());
            mTextName.setText(cast.getName());
        }
    }
}
