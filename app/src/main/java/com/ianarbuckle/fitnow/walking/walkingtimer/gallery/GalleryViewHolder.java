package com.ianarbuckle.fitnow.walking.walkingtimer.gallery;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ianarbuckle.fitnow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 16/03/2017.
 *
 */

public class GalleryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ivImage)
    ImageView imageView;

    public GalleryViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
}
