package com.ianarbuckle.fitnow.walking.walkingtimer.results;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.utils.StringUtils;
import com.ianarbuckle.fitnow.walking.walkingtimer.gallery.GalleryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ian Arbuckle on 06/03/2017.
 *
 */

public class ResultsPresenterImpl implements ResultsPresenter {

  private ResultsView view;
  private final DatabaseHelper databaseHelper;
  private List<GalleryModel> galleryList;
  private AuthenticationHelper authenticationHelper;

  public ResultsPresenterImpl(DatabaseHelper databaseHelper, AuthenticationHelper authenticationHelper) {
    this.databaseHelper = databaseHelper;
    this.authenticationHelper = authenticationHelper;
  }

  public void setView(ResultsView view) {
    this.view = view;
  }

  @Override
  public void sendResultsToNetwork(final String desc, final float rating, final String time, final String distance, final String speed, final String steps, final String calories, final String currentDate) {
    galleryList = new ArrayList<>();
    databaseHelper.receiveUploadsFromFirebase(getListener(desc, rating, time, distance, speed, steps, calories, currentDate));
  }

  @NonNull
  private ValueEventListener getListener(final String desc, final float rating, final String time, final String distance, final String speed, final String steps, final String calories, final String currentDate) {
    return new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
          GalleryModel galleryModel = postSnapshot.getValue(GalleryModel.class);
          galleryList.add(galleryModel);
          sendDataToFirebase(galleryModel);
        }
      }

      private void sendDataToFirebase(GalleryModel galleryModel) {
        if (!StringUtils.isStringEmptyorNull(desc)) {
          Intent intent = view.getActivity().getIntent();
          Bundle bundle = intent.getExtras();
          String username = bundle.getString("username");
          String isDate = bundle.getString("date");
          assert username != null;
          if(username.equals(authenticationHelper.getUserDisplayName()) && galleryModel.getDate().equals(isDate)) {
            databaseHelper.sendWalkingResultsToFirebase(username, desc, rating, time, distance, speed,
                steps, calories, currentDate, galleryList);
          }
        } else {
          view.showErrorMessage();
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    };
  }

}
