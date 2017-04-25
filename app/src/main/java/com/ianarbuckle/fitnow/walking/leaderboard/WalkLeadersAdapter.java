package com.ianarbuckle.fitnow.walking.leaderboard;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.models.RunWalkModel;
import com.ianarbuckle.fitnow.utils.StringUtils;

/**
 * Created by Ian Arbuckle on 25/04/2017.
 *
 */

public class WalkLeadersAdapter extends FirebaseRecyclerAdapter<RunWalkModel, WalkLeadersViewHolder> {

  Context context;

  public WalkLeadersAdapter(Class<RunWalkModel> modelClass, int modelLayout, Class<WalkLeadersViewHolder> viewHolderClass, Query ref, Context context) {
    super(modelClass, modelLayout, viewHolderClass, ref);
    this.context = context;
  }

  @Override
  protected void populateViewHolder(WalkLeadersViewHolder viewHolder, RunWalkModel model, int position) {
    TextView tvDisplayName = viewHolder.tvName;
    TextView tvPosition = viewHolder.tvPosition;
    TextView tvScore = viewHolder.tvScore;
    ImageView ivProfileIcon = viewHolder.ivProfileIcon;
    ImageView ivBadge = viewHolder.ivBadge;
    TextView tvDate = viewHolder.tvDate;

    tvDisplayName.setText(model.getUsername());
    ivProfileIcon.setImageResource(R.drawable.ic_person_pin);
    tvPosition.setText(position + 1 + "");
    String formatSpeed = StringUtils.formatSpeed(model.getSpeed());
    tvScore.setText(formatSpeed);
    tvDate.setText(model.getCurrentDate());

    switch (position) {
      case 0:
        ivBadge.setImageResource(R.drawable.ic_star_gold);
        break;
      case 1:
        ivBadge.setImageResource(R.drawable.ic_star_silver);
        break;
      case 2:
        ivBadge.setImageResource(R.drawable.ic_star_brown);
        break;
      default:
        ivBadge.setVisibility(View.GONE);
        break;
    }

  }
}
