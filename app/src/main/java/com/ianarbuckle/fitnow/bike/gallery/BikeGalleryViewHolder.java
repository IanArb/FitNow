package com.ianarbuckle.fitnow.bike.gallery;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ianarbuckle.fitnow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public class BikeGalleryViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.ivImage)
  ImageView imageView;

  public BikeGalleryViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }


}
