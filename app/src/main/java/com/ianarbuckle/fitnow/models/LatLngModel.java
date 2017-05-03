package com.ianarbuckle.fitnow.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ian Arbuckle on 03/05/2017.
 *
 */

public class LatLngModel implements Parcelable {

  private double latitude;
  private double longitude;

  public LatLngModel() {

  }

  public LatLngModel(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeDouble(this.latitude);
    dest.writeDouble(this.longitude);
  }

  protected LatLngModel(Parcel parcelIn) {
    this.latitude = parcelIn.readDouble();
    this.longitude = parcelIn.readDouble();
  }

  public static final Creator<LatLngModel> CREATOR = new Creator<LatLngModel>() {
    @Override
    public LatLngModel createFromParcel(Parcel source) {
      return new LatLngModel(source);
    }

    @Override
    public LatLngModel[] newArray(int size) {
      return new LatLngModel[size];
    }
  };
}
