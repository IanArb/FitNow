package com.ianarbuckle.fitnow.activities.walking.myactivity;

import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ianarbuckle.fitnow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 21/03/2017.
 *
 */

public class WalkMyActivityCardView extends RecyclerView.ViewHolder {

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

  public WalkMyActivityCardView(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
