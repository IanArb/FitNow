package com.ianarbuckle.fitnow.activities.bike.myactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.ianarbuckle.fitnow.activities.bike.myactivity.learnmore.LearnMorePagerActivity;
import com.ianarbuckle.fitnow.models.BikeModel;
import com.ianarbuckle.fitnow.utils.Constants;

import java.util.ArrayList;

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
  protected void populateViewHolder(BikeMyActivityCardView viewHolder, final BikeModel model, int position) {
    AppCompatRatingBar ratingBar = viewHolder.ratingBar;
    TextView tvDate = viewHolder.tvDate;
    TextView tvName = viewHolder.tvName;
    TextView tvDisplayName = viewHolder.tvDisplayName;
    Button btnLearnMore = viewHolder.btnLearnMore;
    Button btnShare = viewHolder.btnShare;

    ratingBar.setRating(model.getRating());
    tvDate.setText(model.getDate());
    tvName.setText(model.getDesc());
    tvDisplayName.setText(model.getUsername());
    tvDate.setText(model.getDate());

    if(model.getUsername() == null) {
      tvDisplayName.setText("Anonymous User");
    }

    getLearnMoreBtnListener(model, btnLearnMore);

    getShareBtnListener(model, btnShare);
  }

  private void getLearnMoreBtnListener(final BikeModel model, Button btnLearnMore) {
    btnLearnMore.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = LearnMorePagerActivity.newIntent(context);
        getBundle(intent, model);
        context.startActivity(intent);
      }
    });
  }

  private void getShareBtnListener(final BikeModel model, Button btnShare) {
    btnShare.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        getBundle(intent, model);
        context.startActivity(intent);
      }
    });
  }

  private void getBundle(Intent intent, BikeModel model) {
    Bundle bundle = new Bundle();
    bundle.putInt(Constants.SECONDS_KEY, model.getTime());
    bundle.putInt(Constants.CALORIES_KEY, model.getCalories());
    bundle.putFloat(Constants.DISTANCE_KEY, model.getDistance());
    bundle.putFloat(Constants.PEDAL_KEY, model.getPedalSpeed());
    bundle.putFloat(Constants.SPEED_KEY, model.getSpeed());
    bundle.putString(Constants.NAME_KEY, model.getUsername());
    bundle.putString(Constants.DATE_KEY, model.getDate());
    bundle.putInt(Constants.SECONDS_KEY, model.getTime());
    ArrayList<? extends Parcelable> latLngModels = (ArrayList<? extends Parcelable>) model.getLatLngModels();
    bundle.putParcelableArrayList(Constants.POINTS_KEY, latLngModels);
    intent.putExtras(bundle);
  }

}
