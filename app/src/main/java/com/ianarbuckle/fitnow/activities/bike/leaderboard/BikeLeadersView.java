package com.ianarbuckle.fitnow.activities.bike.leaderboard;

import android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.ianarbuckle.fitnow.models.BikeModel;

/**
 * Created by Ian Arbuckle on 27/04/2017.
 *
 */

public interface BikeLeadersView {
  Context getContext();
  void setAdapter(FirebaseRecyclerAdapter<BikeModel, BikeLeadersViewHolder> adapter);
  void showEmptyView();
  void showErrorMessage();
  void showListView();
}
