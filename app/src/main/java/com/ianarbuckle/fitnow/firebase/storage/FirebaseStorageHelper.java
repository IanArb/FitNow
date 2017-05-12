package com.ianarbuckle.fitnow.firebase.storage;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ian Arbuckle on 01/02/2017.
 *
 */

public interface FirebaseStorageHelper {
  File createImageFile() throws IOException;
  void uploadToStorage(OnSuccessListener<UploadTask.TaskSnapshot> successListener, OnFailureListener failureListener, OnProgressListener<UploadTask.TaskSnapshot> progressListener);
}
