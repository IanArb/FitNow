package com.ianarbuckle.fitnow.activities.bike.myactivity;

import android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.ianarbuckle.fitnow.models.BikeModel;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public interface BikeMyActivityView {
  Context getContext();
  void setAdapter(FirebaseRecyclerAdapter<BikeModel, BikeMyActivityCardView> adapter);
  void setEmptyView();
  void setListView();
  void showErrorMessage();
}
