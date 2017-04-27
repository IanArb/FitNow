package com.ianarbuckle.fitnow.bike.leaderboard;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.models.BikeModel;
import com.ianarbuckle.fitnow.utils.StringUtils;

/**
 * Created by Ian Arbuckle on 27/04/2017.
 *
 */

public class BikeLeadersDistanceAdapter extends FirebaseRecyclerAdapter<BikeModel, BikeLeadersViewHolder> {

  Context context;

  public BikeLeadersDistanceAdapter(Class<BikeModel> modelClass, int modelLayout, Class<BikeLeadersViewHolder> viewHolderClass, Query ref, Context context) {
    super(modelClass, modelLayout, viewHolderClass, ref);
    this.context = context;
  }

  @Override
  protected void populateViewHolder(BikeLeadersViewHolder viewHolder, BikeModel model, int position) {
    ImageView ivBadge = getViews(viewHolder, model, position);

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

  private ImageView getViews(BikeLeadersViewHolder viewHolder, BikeModel model, int position) {
    TextView tvDisplayName = viewHolder.tvName;
    TextView tvPosition = viewHolder.tvPosition;
    TextView tvScore = viewHolder.tvScore;
    ImageView ivBadge = viewHolder.ivBadge;
    TextView tvDate = viewHolder.tvDate;

    tvDisplayName.setText(model.getUsername());
    tvPosition.setText(position + 1 + "");
    String formatDistance = StringUtils.formatDistance(model.getDistance());
    tvScore.setText(formatDistance);
    tvDate.setText(model.getDate());

    return ivBadge;
  }

  @Override
  public BikeModel getItem(int position) {
    return super.getItem(getItemCount() - (position +1));
  }

}
