package com.ianarbuckle.fitnow.activities.walking.myactivity;

import android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.ianarbuckle.fitnow.models.RunWalkModel;

/**
 * Created by Ian Arbuckle on 10/04/2017.
 *
 */

public interface WalkMyActivityView {
  Context getContext();
  void setAdapter(FirebaseRecyclerAdapter<RunWalkModel, WalkMyActivityCardView> adapter);
  void setEmptyView();
  void setListView();
  void showErrorMessage();
}
