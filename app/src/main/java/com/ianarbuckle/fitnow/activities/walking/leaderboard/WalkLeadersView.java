package com.ianarbuckle.fitnow.activities.walking.leaderboard;

import android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.ianarbuckle.fitnow.models.RunWalkModel;

/**
 * Created by Ian Arbuckle on 25/04/2017.
 *
 */

public interface WalkLeadersView {
  Context getContext();
  void setAdapter(FirebaseRecyclerAdapter<RunWalkModel, WalkLeadersViewHolder> adapter);
  void setEmptyView();
  void showErrorMessage();
  void setListView();
}
