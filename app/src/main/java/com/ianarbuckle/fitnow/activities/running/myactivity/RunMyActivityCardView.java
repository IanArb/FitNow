package com.ianarbuckle.fitnow.activities.running.myactivity;

import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ianarbuckle.fitnow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 17/04/2017.
 *
 */

public class RunMyActivityCardView extends RecyclerView.ViewHolder {

  @BindView(R.id.tvDate)
  TextView tvDate;

  @BindView(R.id.tvDisplayName)
  TextView tvDisplayName;

  @BindView(R.id.tvName)
  TextView tvName;

  @BindView(R.id.rbRating)
  AppCompatRatingBar ratingBar;

  public RunMyActivityCardView(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
