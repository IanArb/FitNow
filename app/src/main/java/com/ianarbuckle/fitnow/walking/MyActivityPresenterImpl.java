package com.ianarbuckle.fitnow.walking;

import android.support.annotation.VisibleForTesting;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.walking.walkingtimer.results.ResultsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ian Arbuckle on 26/01/2017.
 *
 */

public class MyActivityPresenterImpl implements MyActivityPresenter {

  private MyActivityWalkView view;

  private DatabaseHelper databaseHelper;

  private List<ResultsModel> walkingResultsList;

  private MyActivityAdapter adapter;

  public MyActivityPresenterImpl(MyActivityWalkView view, DatabaseHelper databaseHelper) {
    this.view = view;
    this.databaseHelper = databaseHelper;
  }

  @Override
  public void retrieveWalkingResults() {
    databaseHelper.receiveWalkingResultsFromFirebase(provideResultsCallback());
  }

  @VisibleForTesting
  private ChildEventListener provideResultsCallback() {
    walkingResultsList = new ArrayList<>();
    return new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String string) {
        ResultsModel resultsModel = dataSnapshot.getValue(ResultsModel.class);
        if(resultsModel != null) {
          walkingResultsList.add(resultsModel);
          adapter = new MyActivityAdapter(walkingResultsList, view.getContext());
          view.setAdapter(adapter);
        }
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String string) {

      }

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {

      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String string) {

      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        view.showErrorMessage();
      }
    };
  }


}
