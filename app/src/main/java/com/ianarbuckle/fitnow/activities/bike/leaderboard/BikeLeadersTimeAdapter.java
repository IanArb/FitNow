package com.ianarbuckle.fitnow.activities.bike.leaderboard;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.models.BikeModel;
import com.ianarbuckle.fitnow.utils.Constants;

import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Seconds;

/**
 * Created by Ian Arbuckle on 04/05/2017.
 *
 */

public class BikeLeadersTimeAdapter extends FirebaseRecyclerAdapter<BikeModel, BikeLeadersViewHolder> {

  Context context;

  public BikeLeadersTimeAdapter(Class<BikeModel> modelClass, int modelLayout, Class<BikeLeadersViewHolder> viewHolderClass, Query ref, Context context) {
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
    Seconds convertSeconds = Seconds.seconds(model.getTime());
    Period period = new Period(convertSeconds);
    String formatTime = Constants.FORMAT_HOURS_MINUTES_SECONDS_RESULT.print(period.normalizedStandard(PeriodType.time()));
    tvScore.setText(formatTime);
    tvDate.setText(model.getDate());

    return ivBadge;
  }

  @Override
  public BikeModel getItem(int position) {
    return super.getItem(getItemCount() - (position +1));
  }
}
