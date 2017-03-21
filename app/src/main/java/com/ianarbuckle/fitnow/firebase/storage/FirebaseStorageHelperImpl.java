package com.ianarbuckle.fitnow.firebase.storage;

import android.app.Activity;
import android.net.Uri;
import android.os.Environment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ianarbuckle.fitnow.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Ian Arbuckle on 01/02/2017.
 *
 */

@SuppressWarnings("VisibleForTests")
public class FirebaseStorageHelperImpl implements FirebaseStorageHelper {

  private String currentPhotoPath;

  StorageReference storageReference;

  DatabaseReference databaseReference;

  private Activity activity;

  public FirebaseStorageHelperImpl(Activity activity) {
    this.activity = activity;
  }

  @Override
  public File createImageFile() throws IOException {
    String timeStamp = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH).format(new Date());
    String imageFileName = Constants.JPEG_PREFIX + timeStamp + "_";
    File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    File image = File.createTempFile(
        imageFileName,
        Constants.JPG_SUFFIX,
        storageDir
    );
    currentPhotoPath = image.getAbsolutePath();
    return image;
  }

  @Override
  public void uploadToStorage(OnSuccessListener<UploadTask.TaskSnapshot> listener, OnFailureListener failureListener, OnProgressListener<UploadTask.TaskSnapshot> progressListener) {
      File file = new File(currentPhotoPath);
      Uri uri = Uri.fromFile(file);

      storageReference = FirebaseStorage.getInstance().getReference();
      databaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_DATABASE_UPLOAD);

      StorageReference filePath = storageReference.child(Constants.FIREBASE_STORAGE_DIR).child(uri.getLastPathSegment());
      filePath.putFile(uri)
          .addOnSuccessListener(listener)
          .addOnFailureListener(failureListener)
          .addOnProgressListener(progressListener);
  }

}
