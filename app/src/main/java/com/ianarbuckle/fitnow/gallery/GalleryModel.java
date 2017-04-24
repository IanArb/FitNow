package com.ianarbuckle.fitnow.gallery;


import java.io.Serializable;

/**
 * Created by Ian Arbuckle on 16/03/2017.
 *
 */

public class GalleryModel implements Serializable {

  private String userName;
  private String imageUrl;
  private String date;

  public GalleryModel() {
  }

  public GalleryModel(String imageUrl, String userName, String date) {
    this.imageUrl = imageUrl;
    this.userName = userName;
    this.date = date;
  }


  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
