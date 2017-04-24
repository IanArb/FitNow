package com.ianarbuckle.fitnow.running.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.models.GalleryModel;
import com.ianarbuckle.fitnow.utils.UiUtils;

import java.util.List;

/**
 * Created by Ian Arbuckle on 16/03/2017.
 *
 */

public class RunGalleryAdapter extends RecyclerView.Adapter<RunGalleryViewHolder> {

  private List<GalleryModel> galleryModelList;

  Context context;

  public RunGalleryAdapter(List<GalleryModel> galleryModels, Context context) {
    this.galleryModelList = galleryModels;
    this.context = context;
  }

  @Override
  public RunGalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_gallery_list, parent, false);
    return new RunGalleryViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final RunGalleryViewHolder holder, int position) {
    final String imageUrl = galleryModelList.get(position).getImageUrl();
    UiUtils.loadImage(imageUrl, holder.imageView);
  }

  @Override
  public int getItemCount() {
    return galleryModelList.size();
  }


}
