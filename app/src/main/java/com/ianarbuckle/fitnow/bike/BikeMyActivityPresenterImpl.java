package com.ianarbuckle.fitnow.bike;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ianarbuckle.fitnow.utils.Constants;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public class BikeMyActivityPresenterImpl implements BikeMyActivityPresenter {

  private BikeMyActivityView view;

  private DatabaseReference databaseReference;

  public BikeMyActivityPresenterImpl(BikeMyActivityView view) {
    this.view = view;
    databaseReference = FirebaseDatabase.getInstance().getReference();
  }

  public void setEmptyState() {
    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if(!dataSnapshot.child(Constants.RESULTS_CYCLING_REFERENCE).exists()) {
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
