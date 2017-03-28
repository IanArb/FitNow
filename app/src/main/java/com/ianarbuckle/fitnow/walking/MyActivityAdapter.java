package com.ianarbuckle.fitnow.walking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.walking.walkingtimer.results.ResultsModel;

import java.util.List;

/**
 * Created by Ian Arbuckle on 21/03/2017.
 *
 *
 */

public class MyActivityAdapter extends RecyclerView.Adapter<MyActivityCardView> {

  private List<ResultsModel> resultsModelsList;
  Context context;

  public MyActivityAdapter(List<ResultsModel> resultsModelsList, Context context) {
    this.resultsModelsList = resultsModelsList;
    this.context = context;
  }

  @Override
  public MyActivityCardView onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card, parent, false);
    return new MyActivityCardView(view);
  }

  @Override
  public void onBindViewHolder(MyActivityCardView holder, int position) {
    float rating = resultsModelsList.get(position).getRating();
    holder.ratingBar.setRating(rating);
    String currentDate = resultsModelsList.get(position).getCurrentDate();
    holder.tvDate.setText(currentDate);
    String desc = resultsModelsList.get(position).getDesc();
    holder.tvName.setText(desc);
  }

  @Override
  public int getItemCount() {
    return resultsModelsList.size();
  }
}
