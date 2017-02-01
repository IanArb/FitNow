package com.ianarbuckle.fitnow.network.firebase.auth;

/**
 * Created by Ian Arbuckle on 01/12/2016.
 * Reference - https://github.com/filbabic/FirebaseChatApp
 */

public interface RequestListener {

  void onSucessRequest();

  void onFailureRequest();
}
