package com.ianarbuckle.fitnow.firebase.storage;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ian Arbuckle on 01/02/2017.
 *
 */

public interface FirebaseStorageHelper {
  File createImageFile() throws IOException;
  void uploadToStorage(int requestCode, int resultCode);
}
