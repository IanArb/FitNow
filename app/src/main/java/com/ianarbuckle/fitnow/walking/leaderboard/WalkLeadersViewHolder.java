package com.ianarbuckle.fitnow.walking.leaderboard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ianarbuckle.fitnow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 25/04/2017.
 *
 */

public class WalkLeadersViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.tvName)
  TextView tvName;

  @BindView(R.id.tvScore)
  TextView tvScore;

  @BindView(R.id.tvPosition)
  TextView tvPosition;

  @BindView(R.id.ivBadge)
  ImageView ivBadge;

  @BindView(R.id.tvDate)
  TextView tvDate;

  public WalkLeadersViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

}
