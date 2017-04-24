package com.ianarbuckle.fitnow.walking.gallery;

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

public class WalkGalleryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ivImage)
    ImageView imageView;

    public WalkGalleryViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

}
