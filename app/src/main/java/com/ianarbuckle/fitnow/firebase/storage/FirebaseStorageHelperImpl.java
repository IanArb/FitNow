package com.ianarbuckle.fitnow.firebase.storage;

import android.app.Activity;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ian Arbuckle on 01/02/2017.
 *
 */

public class FirebaseStorageHelperImpl implements FirebaseStorageHelper {

  private String currentPhotoPath;

  private StorageReference storageReference;

  private FirebaseStorageView view;

  private Activity activity;

  public FirebaseStorageHelperImpl(FirebaseStorageView view, Activity activity) {
    storageReference = FirebaseStorage.getInstance().getReference();
    this.activity = activity;
    this.view = view;
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
  public void uploadToStorage(int requestCode, int resultCode) {
    if (requestCode == Constants.PERMISSION_REQUEST_CAMERA && resultCode == RESULT_OK) {
      File file = new File(currentPhotoPath);
      Uri uri = Uri.fromFile(file);

      StorageReference filePath = storageReference.child(Constants.FIREBASE_STORAGE_DIR).child(uri.getLastPathSegment());
      filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
          view.dismissLoading();
          view.setSuccessMessage();
        }
      }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
          view.dismissLoading();
          view.showStorageErrorMessage();
        }
      }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
          view.showLoading();
        }
      });
    }
  }
}
