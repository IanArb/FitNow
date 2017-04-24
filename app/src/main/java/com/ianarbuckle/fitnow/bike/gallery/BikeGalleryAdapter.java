package com.ianarbuckle.fitnow.bike.gallery;

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
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public class BikeGalleryAdapter extends RecyclerView.Adapter<BikeGalleryViewHolder> {

  private List<GalleryModel> galleryModelList;

  Context context;

  public BikeGalleryAdapter(List<GalleryModel> galleryModelList, Context context) {
    this.galleryModelList = galleryModelList;
    this.context = context;
  }

  @Override
  public void onBindViewHolder(BikeGalleryViewHolder holder, int position) {
    final String imageUrl = galleryModelList.get(position).getImageUrl();
    UiUtils.loadImage(imageUrl, holder.imageView);
  }

  @Override
  public BikeGalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_gallery_list, parent, false);
    return new BikeGalleryViewHolder(view);
  }

  @Override
  public int getItemCount() {
    return galleryModelList.size();
  }
}
