package com.ianarbuckle.fitnow.walking.walkingtimer.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ian Arbuckle on 16/03/2017.
 *
 */

public class GalleryPresenterImpl implements GalleryPresenter {

  private GalleryView view;

  private List<GalleryModel> modelList;

  GalleryAdapter adapter;

  Bundle bundle;

  private DatabaseHelper databaseHelper;

  public GalleryPresenterImpl(GalleryView view, DatabaseHelper databaseHelper) {
    this.view = view;
    this.databaseHelper = databaseHelper;
  }

  @Override
  public void getUploads() {
    modelList = new ArrayList<>();

    view.showProgress();

    databaseHelper.receiveUploadsFromFirebase(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        view.hideProgress();
        getData(dataSnapshot);
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        view.hideProgress();
      }
    });

  }

  @VisibleForTesting
  private void getData(DataSnapshot dataSnapshot) {
    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
      GalleryModel model = postSnapshot.getValue(GalleryModel.class);
      Intent intent = view.getActivity().getIntent();
      Bundle getBundle = intent.getExtras();
      String date = getBundle.getString("date");
      String username = getBundle.getString("username");
      bundle = new Bundle();
      boolean isDisplayName = model.getUserName().equals(username);
      boolean isDate = model.getDate().equals(date);
      if(isDisplayName && isDate) {
        modelList.add(model);
        adapter = new GalleryAdapter(modelList, view.getContext());
        bundle.putSerializable("images", (Serializable) modelList);
        view.setAdapter(adapter);
      }
    }
  }

  public Bundle getBundle() {
    return bundle;
  }

}
