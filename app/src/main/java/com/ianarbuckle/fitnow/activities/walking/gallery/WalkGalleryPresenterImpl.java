package com.ianarbuckle.fitnow.activities.walking.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.models.GalleryModel;
import com.ianarbuckle.fitnow.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ian Arbuckle on 16/03/2017.
 *
 */

public class WalkGalleryPresenterImpl implements WalkGalleryPresenter {

  private WalkGalleryView view;

  private List<GalleryModel> modelList;

  WalkGalleryAdapter adapter;

  private Bundle bundle;

  private DatabaseHelper databaseHelper;

  public WalkGalleryPresenterImpl(WalkGalleryView view, DatabaseHelper databaseHelper) {
    this.view = view;
    this.databaseHelper = databaseHelper;
  }

  @Override
  public void getUploads() {
    modelList = new ArrayList<>();

    view.showProgress();

    databaseHelper.receiveUploadsFromFirebase(getListener(), Constants.FIREBASE_DATABASE_UPLOAD_WALKING);

  }

  @NonNull
  private ValueEventListener getListener() {
    return new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        view.hideProgress();
        getData(dataSnapshot);
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        view.hideProgress();
        view.showErrorMessage();
      }
    };
  }

  @VisibleForTesting
  private void getData(DataSnapshot dataSnapshot) {
    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
      GalleryModel model = postSnapshot.getValue(GalleryModel.class);
      Intent intent = view.getActivity().getIntent();
      assert intent != null;
      Bundle getBundle = intent.getExtras();
      String date = getBundle.getString(Constants.DATE_KEY);
      bundle = new Bundle();
      boolean isDate = model.getDate().equals(date);
      if(isDate) {
        modelList.add(model);
        adapter = new WalkGalleryAdapter(modelList, view.getContext());
        bundle.putSerializable(Constants.IMAGES_KEY, (Serializable) modelList);
        view.setAdapter(adapter);
      }
    }
  }

  public Bundle getBundle() {
    return bundle;
  }

}
