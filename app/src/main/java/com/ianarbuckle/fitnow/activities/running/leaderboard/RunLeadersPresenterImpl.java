package com.ianarbuckle.fitnow.activities.running.leaderboard;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ianarbuckle.fitnow.models.RunWalkModel;
import com.ianarbuckle.fitnow.utils.Constants;

/**
 * Created by Ian Arbuckle on 27/04/2017.
 *
 */

public class RunLeadersPresenterImpl implements RunLeadersPresenter {

  private RunLeadersView view;

  DatabaseReference databaseReference;
  private DatabaseReference childRef;

  private FirebaseRecyclerAdapter<RunWalkModel, RunLeadersViewHolder> adapter;

  public RunLeadersPresenterImpl(RunLeadersView view) {
    this.view = view;
  }

  @Override
  public void setEmptyState() {
    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if(!dataSnapshot.child(Constants.RESULTS_RUNNING_REFERENCE).exists()) {
          view.showEmptyView();
        } else {
          view.showListView();
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        view.showErrorMessage();
      }
    });
  }

  @Override
  public void setSpeedQuery(int layout) {
    getReferences();
    Query query = childRef.orderByChild(Constants.FIREBASE_DATABASE_CHILD_SPEED);
    adapter = new RunLeadersSpeedAdapter(RunWalkModel.class, layout, RunLeadersViewHolder.class, query, view.getContext());
    view.setAdapter(adapter);
  }

  @Override
  public void setDistanceQuery(int layout) {
    getReferences();
    Query query = childRef.orderByChild(Constants.FIREBASE_DATABASE_CHILD_DISTANCE);
    adapter = new RunLeadersDistanceAdapter(RunWalkModel.class, layout, RunLeadersViewHolder.class, query, view.getContext());
    view.setAdapter(adapter);
  }

  @Override
  public void setStepsQuery(int layout) {
    getReferences();
    Query query = childRef.orderByChild(Constants.FIREBASE_DATABASE_CHILD_STEPS);
    adapter = new RunLeadersStepsAdapter(RunWalkModel.class, layout, RunLeadersViewHolder.class, query, view.getContext());
    view.setAdapter(adapter);
  }

  @Override
  public void setTimeQuery(int layout) {
    getReferences();
    Query query = childRef.orderByChild(Constants.FIREBASE_DATABASE_CHILD_TIME);
    adapter = new RunLeadersTimeAdapter(RunWalkModel.class, layout, RunLeadersViewHolder.class, query, view.getContext());
    view.setAdapter(adapter);
  }

  private void getReferences() {
    databaseReference = FirebaseDatabase.getInstance().getReference();
    childRef = databaseReference.child(Constants.RESULTS_RUNNING_REFERENCE);
  }


}
