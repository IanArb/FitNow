package com.ianarbuckle.fitnow.authentication.firebase;

import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by Ian Arbuckle on 30/11/2016.
 * Reference - https://github.com/filbabic/FirebaseChatApp
 */

public class AuthenticationHelperImpl implements AuthenticationHelper {

  private final FirebaseAuth firebaseAuth;

  public AuthenticationHelperImpl(FirebaseAuth firebaseAuth) {
    this.firebaseAuth = firebaseAuth;
  }

  @Override
  public void googleLogin(GoogleSignInAccount account, final RequestListener listener) {
    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
    firebaseAuth.signInWithCredential(credential)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            listener.onSucessRequest();
            if (!task.isSuccessful()) {
              listener.onFailureRequest();
            } else {
              listener.onSucessRequest();
            }
          }
        });
  }

  @Override
  public void registerUser(String email, String password, final RequestListener listener) {
    firebaseAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              FirebaseUser user = firebaseAuth.getCurrentUser();
              if (user != null) {
                listener.onSucessRequest();
              } else {
                listener.onFailureRequest();
              }
            }
          }
        });
  }

  @Override
  public void logInUser(String email, String password, final RequestListener listener) {
    firebaseAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()) {
              FirebaseUser user = firebaseAuth.getCurrentUser();
              if(user != null) {
                listener.onSucessRequest();
              }
            } else {
              listener.onFailureRequest();
            }
          }
        });
  }

  @Override
  public String getUserDisplayName() {
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    if (firebaseUser != null) {
      return firebaseUser.getDisplayName();
    }
    return null;
  }

  @Override
  public String getUserPhoto() {
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    if(firebaseUser != null && firebaseUser.getPhotoUrl() != null) {
      return firebaseUser.getPhotoUrl().toString();
    }
    return null;
  }

  @Override
  public String getUserEmail() {
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    if(firebaseUser != null) {
      return firebaseUser.getEmail();
    }
    return null;
  }

  @Override
  public void logOutUser() {
    firebaseAuth.signOut();
  }

  @Override
  public boolean isUserLoggedOut() {
    return firebaseAuth.getCurrentUser() != null;
  }
}
