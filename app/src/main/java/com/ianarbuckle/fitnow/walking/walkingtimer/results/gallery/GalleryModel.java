package com.ianarbuckle.fitnow.walking.walkingtimer.results.gallery;

/**
 * Created by Ian Arbuckle on 16/03/2017.
 *
 */

public class GalleryModel {

  private String userName;
  private String imageUrl;

  public GalleryModel() {
  }

  public GalleryModel(String imageUrl, String userName) {
    this.imageUrl = imageUrl;
    this.userName = userName;
  }

  public String getName() {
    return userName;
  }

  public void setName(String userName) {
    this.userName = userName;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
