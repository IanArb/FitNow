package com.ianarbuckle.fitnow.running.leaderboard;

import android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.ianarbuckle.fitnow.models.RunWalkModel;

/**
 * Created by Ian Arbuckle on 27/04/2017.
 *
 */

public interface RunLeadersView {
  Context getContext();
  void setAdapter(FirebaseRecyclerAdapter<RunWalkModel, RunLeadersViewHolder> adapter);
}
