package com.ianarbuckle.fitnow.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ianarbuckle.fitnow.R;

/**
 * Created by Ian Arbuckle on 10/10/2016.
 *
 */

public class UiUtils {

  public static void customiseToolbar(View view) {
    Toolbar toolbar = (Toolbar) view;
    for(int i = 0; i < toolbar.getChildCount(); i++) {
      View childView = toolbar.getChildAt(i);

      if(childView instanceof TextView) {
        TextView tvToolbar = (TextView) childView;
        tvToolbar.setTextColor(ContextCompat.getColor(childView.getContext(), R.color.colorPrimaryDark));
      }
    }
  }

  public static Drawable colourAndStyleActionBar(View view) {
    final Drawable backArrow;
    backArrow = ContextCompat.getDrawable(view.getContext(), R.drawable.abc_ic_ab_back_material);
    backArrow.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
    return backArrow;
  }

  public static Drawable setPlayDrawable(View view) {
    final Drawable playButton;
    playButton = ContextCompat.getDrawable(view.getContext(), R.drawable.ic_play_arrow);
    return playButton;
  }

  public static void loadImage(String url, ImageView imageView) {
    Context context = imageView.getContext();
    Drawable drawable = ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_insert_photo);
    Glide.with(context)
        .load(url)
        .placeholder(drawable)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .crossFade()
        .thumbnail(0.5f)
        .centerCrop()
        .into(imageView);
  }

}
