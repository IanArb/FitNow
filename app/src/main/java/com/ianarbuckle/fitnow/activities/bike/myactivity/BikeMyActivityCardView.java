package com.ianarbuckle.fitnow.activities.bike.myactivity;

import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ianarbuckle.fitnow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public class BikeMyActivityCardView extends RecyclerView.ViewHolder {

  @BindView(R.id.tvDate)
  TextView tvDate;

  @BindView(R.id.tvDisplayName)
  TextView tvDisplayName;

  @BindView(R.id.tvName)
  TextView tvName;

  @BindView(R.id.rbRating)
  AppCompatRatingBar ratingBar;

  @BindView(R.id.btnLearnMore)
  Button btnLearnMore;

  @BindView(R.id.btnShare)
  Button btnShare;

  public BikeMyActivityCardView(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
