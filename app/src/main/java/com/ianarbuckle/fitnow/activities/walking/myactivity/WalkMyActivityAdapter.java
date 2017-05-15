package com.ianarbuckle.fitnow.activities.walking.myactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatRatingBar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.ianarbuckle.fitnow.activities.walking.myactivity.learnmore.LearnMorePagerActivity;
import com.ianarbuckle.fitnow.models.RunWalkModel;
import com.ianarbuckle.fitnow.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Ian Arbuckle on 21/03/2017.
 *
 *
 */

public class WalkMyActivityAdapter extends FirebaseRecyclerAdapter<RunWalkModel, WalkMyActivityCardView> {

  Context context;


  public WalkMyActivityAdapter(Class<RunWalkModel> modelClass, int modelLayout, Class<WalkMyActivityCardView> viewHolderClass, DatabaseReference ref, Context context) {
    super(modelClass, modelLayout, viewHolderClass, ref);
    this.context = context;
  }

  @Override
  protected void populateViewHolder(WalkMyActivityCardView viewHolder, final RunWalkModel model, int position) {
    AppCompatRatingBar ratingBar = viewHolder.ratingBar;
    TextView tvDate = viewHolder.tvDate;
    TextView tvName = viewHolder.tvName;
    TextView tvDisplayName = viewHolder.tvDisplayName;
    Button btnShare = viewHolder.btnShare;
    Button btnLearnMore = viewHolder.btnLearnMore;

    ratingBar.setRating(model.getRating());
    tvDate.setText(model.getCurrentDate());
    tvName.setText(model.getDesc());
    tvDisplayName.setText(model.getUsername());

    if(model.getUsername() == null) {
      tvDisplayName.setText("Anonymous User");
    }

    btnLearnMore.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = LearnMorePagerActivity.newIntent(context);
        getBundle(intent, model);
        context.startActivity(intent);
      }
    });

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

  private void getBundle(Intent intent, RunWalkModel model) {
    Bundle bundle = new Bundle();
    bundle.putInt(Constants.SECONDS_KEY, model.getTime());
    bundle.putInt(Constants.CALORIES_KEY, model.getCalories());
    bundle.putFloat(Constants.DISTANCE_KEY, model.getDistance());
    bundle.putInt(Constants.STEPS_KEY, model.getSteps());
    bundle.putFloat(Constants.SPEED_KEY, model.getSpeed());
    bundle.putString(Constants.NAME_KEY, model.getUsername());
    bundle.putString(Constants.DATE_KEY, model.getCurrentDate());
    bundle.putInt(Constants.SECONDS_KEY, model.getTime());
    ArrayList<? extends Parcelable> latLngModels = (ArrayList<? extends Parcelable>) model.getLatLngModels();
    bundle.putParcelableArrayList(Constants.POINTS_KEY, latLngModels);
    intent.putExtras(bundle);
  }

}
