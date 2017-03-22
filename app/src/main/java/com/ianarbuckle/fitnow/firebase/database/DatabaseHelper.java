package com.ianarbuckle.fitnow.firebase.database;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;
import com.ianarbuckle.fitnow.walking.walkingtimer.results.gallery.GalleryModel;

import java.util.List;

/**
 * Created by Ian Arbuckle on 27/01/2017.
 *
 */

public interface DatabaseHelper {
  void sendWalkingResultsToFirebase(String desc, String username, float rating, String time, String distance,
                                    String speed, String steps, String calories,
                                    String currentDate, List<GalleryModel> galleryModelList);
  void receiveWalkingResultsFromFirebase(ChildEventListener listener);
  void receiveUploadsFromFirebase(ValueEventListener listener);
}
