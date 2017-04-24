package com.ianarbuckle.fitnow.walking;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;

import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.ianarbuckle.fitnow.models.RunWalkModel;

/**
 * Created by Ian Arbuckle on 21/03/2017.
 *
 *
 */

public class MyActivityAdapter extends FirebaseRecyclerAdapter<RunWalkModel, MyActivityCardView> {

  Context context;


  public MyActivityAdapter(Class<RunWalkModel> modelClass, int modelLayout, Class<MyActivityCardView> viewHolderClass, DatabaseReference ref, Context context) {
    super(modelClass, modelLayout, viewHolderClass, ref);
    this.context = context;
  }

  @Override
  protected void populateViewHolder(MyActivityCardView viewHolder, RunWalkModel model, int position) {
    AppCompatRatingBar ratingBar = viewHolder.ratingBar;
    TextView tvDate = viewHolder.tvDate;
    TextView tvName = viewHolder.tvName;
    TextView tvDisplayName = viewHolder.tvDisplayName;

    ratingBar.setRating(model.getRating());
    tvDate.setText(model.getCurrentDate());
    tvName.setText(model.getDesc());
    tvDisplayName.setText(model.getUsername());
  }

}
