package com.ianarbuckle.fitnow.activities.bike.gallery;

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
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public class BikeGalleryPresenterImpl implements BikeGalleryPresenter {

  private BikeGalleryView view;

  private DatabaseHelper databaseHelper;

  private Bundle bundle;

  private List<GalleryModel> modelList;

  private BikeGalleryAdapter adapter;

  public BikeGalleryPresenterImpl(BikeGalleryView view, DatabaseHelper databaseHelper) {
    this.view = view;
    this.databaseHelper = databaseHelper;
    bundle = new Bundle();
  }

  @Override
  public void getUploads() {
    modelList = new ArrayList<>();

    view.showProgress();

    databaseHelper.receiveUploadsFromFirebase(getListener(), Constants.FIREBASE_DATABASE_UPLOAD_CYCLING);
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
        adapter = new BikeGalleryAdapter(modelList, view.getContext());
        bundle.putSerializable(Constants.IMAGES_KEY, (Serializable) modelList);
        view.setAdapter(adapter);
      }
    }
  }

  public Bundle getBundle() {
    return bundle;
  }
}
