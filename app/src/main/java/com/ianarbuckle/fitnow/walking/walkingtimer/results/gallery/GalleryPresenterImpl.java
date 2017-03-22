package com.ianarbuckle.fitnow.walking.walkingtimer.results.gallery;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;

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
        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
          GalleryModel model = postSnapshot.getValue(GalleryModel.class);
          modelList.add(model);
          adapter = new GalleryAdapter(modelList, view.getContext());
          view.setAdapter(adapter);
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        view.hideProgress();
      }
    });

  }

}
