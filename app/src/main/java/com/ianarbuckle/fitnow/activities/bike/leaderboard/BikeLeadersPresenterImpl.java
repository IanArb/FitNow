package com.ianarbuckle.fitnow.activities.bike.leaderboard;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.models.BikeModel;
import com.ianarbuckle.fitnow.utils.Constants;

/**
 * Created by Ian Arbuckle on 27/04/2017.
 *
 */

public class BikeLeadersPresenterImpl implements BikeLeadersPresenter {

  private BikeLeadersView view;

  DatabaseReference databaseReference;
  private DatabaseReference childRef;

  private FirebaseRecyclerAdapter<BikeModel, BikeLeadersViewHolder> adapter;

  public BikeLeadersPresenterImpl(BikeLeadersView view) {
    this.view = view;
  }

  @Override
  public void setSpeedQuery() {
    getReferences();
    Query query = childRef.orderByChild(Constants.FIREBASE_DATABASE_CHILD_SPEED);
    adapter = new BikeLeadersSpeedAdapter(BikeModel.class, R.layout.layout_leaders, BikeLeadersViewHolder.class, query, view.getContext());
    view.setAdapter(adapter);
  }

  @Override
  public void setDistanceQuery() {
    getReferences();
    Query query = childRef.orderByChild(Constants.FIREBASE_DATABASE_CHILD_DISTANCE);
    adapter = new BikeLeadersDistanceAdapter(BikeModel.class, R.layout.layout_leaders, BikeLeadersViewHolder.class, query, view.getContext());
    view.setAdapter(adapter);
  }

  @Override
  public void setPedalQuery() {
    getReferences();
    Query query = childRef.orderByChild(Constants.FIREBASE_DATABASE_CHILD_PEDAL);
    adapter = new BikeLeadersPedalAdapter(BikeModel.class, R.layout.layout_leaders, BikeLeadersViewHolder.class, query, view.getContext());
    view.setAdapter(adapter);
  }

  private void getReferences() {
    databaseReference = FirebaseDatabase.getInstance().getReference();
    childRef = databaseReference.child(Constants.RESULTS_CYCLING_REFERENCE);
  }


}
