package com.ianarbuckle.fitnow.activities.running.myactivity;

import android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.ianarbuckle.fitnow.models.RunWalkModel;

/**
 * Created by Ian Arbuckle on 17/04/2017.
 *
 */

public interface RunMyActivityView {
  Context getContext();
  void setAdapter(FirebaseRecyclerAdapter<RunWalkModel, RunMyActivityCardView> adapter);
  void setEmptyView();
  void setListView();
  void showErrorMessage();
}
