package com.ianarbuckle.fitnow.bike;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.ianarbuckle.fitnow.models.BikeModel;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public class BikeMyActivityAdapter extends FirebaseRecyclerAdapter<BikeModel, BikeMyActivityCardView> {

  Context context;

  public BikeMyActivityAdapter(Class<BikeModel> bikeModel, int modelLayout, Class<BikeMyActivityCardView> viewHolderClass, Query ref, Context context) {
    super(bikeModel, modelLayout, viewHolderClass, ref);
    this.context = context;
  }

  @Override
  protected void populateViewHolder(BikeMyActivityCardView viewHolder, BikeModel model, int position) {
    AppCompatRatingBar ratingBar = viewHolder.ratingBar;
    TextView tvDate = viewHolder.tvDate;
    TextView tvName = viewHolder.tvName;
    TextView tvDisplayName = viewHolder.tvDisplayName;

    ratingBar.setRating(model.getRating());
    tvDate.setText(model.getDate());
    tvName.setText(model.getDesc());
    tvDisplayName.setText(model.getUsername());
  }

}
