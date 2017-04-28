package com.ianarbuckle.fitnow.activities.running.myactivity;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.models.RunWalkModel;
import com.ianarbuckle.fitnow.utils.Constants;

/**
 * Created by Ian Arbuckle on 17/04/2017.
 *
 */

public class RunMyActivityPresenterImpl implements RunMyActivityPresenter {

  private RunMyActivityView view;

  private DatabaseReference databaseReference;
  DatabaseReference childRef;

  FirebaseRecyclerAdapter<RunWalkModel, RunMyActivityCardView> adapter;

  public RunMyActivityPresenterImpl(RunMyActivityView view) {
    this.view = view;
    databaseReference = FirebaseDatabase.getInstance().getReference();
  }

  @Override
  public void setMyActivityAdapter() {
    childRef = databaseReference.child(Constants.RESULTS_RUNNING_REFERENCE);
    adapter = new RunMyActivityAdapter(RunWalkModel.class, R.layout.layout_card, RunMyActivityCardView.class, childRef, view.getContext());
    view.setAdapter(adapter);
  }

  @Override
  public void setEmptyState() {
    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if(!dataSnapshot.child(Constants.RESULTS_RUNNING_REFERENCE).exists()) {
          view.setEmptyView();
        } else {
          view.setListView();
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        view.showErrorMessage();
      }
    });
  }
}
