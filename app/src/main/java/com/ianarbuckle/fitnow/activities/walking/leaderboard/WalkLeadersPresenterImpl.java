package com.ianarbuckle.fitnow.activities.walking.leaderboard;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ianarbuckle.fitnow.models.RunWalkModel;
import com.ianarbuckle.fitnow.utils.Constants;

/**
 * Created by Ian Arbuckle on 25/04/2017.
 *
 */

public class WalkLeadersPresenterImpl implements WalkLeadersPresenter {

  private WalkLeadersView view;

  DatabaseReference databaseReference;
  private DatabaseReference childRef;

  private FirebaseRecyclerAdapter<RunWalkModel, WalkLeadersViewHolder> adapter;

  public WalkLeadersPresenterImpl(WalkLeadersView view) {
    this.view = view;
  }

  @Override
  public void setSpeedQuery(int layout) {
    getReferences();
    Query query = childRef.orderByChild(Constants.FIREBASE_DATABASE_CHILD_SPEED);
    adapter = new WalkLeadersSpeedAdapter(RunWalkModel.class, layout, WalkLeadersViewHolder.class, query, view.getContext());
    view.setAdapter(adapter);
  }

  @Override
  public void setDistanceQuery(int layout) {
    getReferences();
    Query query = childRef.orderByChild(Constants.FIREBASE_DATABASE_CHILD_DISTANCE);
    adapter = new WalkLeadersDistanceAdapter(RunWalkModel.class, layout, WalkLeadersViewHolder.class, query, view.getContext());
    view.setAdapter(adapter);
  }

  @Override
  public void setStepsQuery(int layout) {
    getReferences();
    Query query = childRef.orderByChild(Constants.FIREBASE_DATABASE_CHILD_STEPS);
    adapter = new WalkLeadersStepsAdapter(RunWalkModel.class, layout, WalkLeadersViewHolder.class, query, view.getContext());
    view.setAdapter(adapter);
  }

  private void getReferences() {
    databaseReference = FirebaseDatabase.getInstance().getReference();
    childRef = databaseReference.child(Constants.RESULTS_WALKING_REFERENCE);
  }


}
