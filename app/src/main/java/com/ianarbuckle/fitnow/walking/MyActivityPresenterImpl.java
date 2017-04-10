package com.ianarbuckle.fitnow.walking;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ianarbuckle.fitnow.utils.Constants;

/**
 * Created by Ian Arbuckle on 10/04/2017.
 *
 */

public class MyActivityPresenterImpl implements MyActivityPresenter {

  private MyActivityView view;

  DatabaseReference databaseReference;

  public MyActivityPresenterImpl(MyActivityView view) {
    this.view = view;
    databaseReference = FirebaseDatabase.getInstance().getReference();
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
