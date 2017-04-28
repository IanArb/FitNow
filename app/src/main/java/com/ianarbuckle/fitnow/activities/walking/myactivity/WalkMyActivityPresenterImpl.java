package com.ianarbuckle.fitnow.activities.walking.myactivity;

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
 * Created by Ian Arbuckle on 10/04/2017.
 *
 */

public class WalkMyActivityPresenterImpl implements WalkMyActivityPresenter {

  private WalkMyActivityView view;

  private DatabaseReference databaseReference;
  DatabaseReference childRef;

  FirebaseRecyclerAdapter<RunWalkModel, WalkMyActivityCardView> adapter;

  public WalkMyActivityPresenterImpl(WalkMyActivityView view) {
    this.view = view;
    databaseReference = FirebaseDatabase.getInstance().getReference();
  }

  @Override
  public void setMyActivityAdapter() {
    childRef = databaseReference.child(Constants.RESULTS_WALKING_REFERENCE);
    adapter = new WalkMyActivityAdapter(RunWalkModel.class, R.layout.layout_card, WalkMyActivityCardView.class, childRef, view.getContext());
    view.setAdapter(adapter);
  }

  @Override
  public void setEmptyState() {
    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if(!dataSnapshot.child(Constants.RESULTS_WALKING_REFERENCE).exists()) {
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
