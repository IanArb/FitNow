package com.ianarbuckle.fitnow.models;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public class BikeModel {

  private String username;
  private String desc;
  private float rating;
  private int seconds;
  private float distance;
  private float speed;
  private float pedalSpeed;
  private int calories;
  private String date;

  public BikeModel() {
  }

  public BikeModel(String username, String desc, float rating, int seconds, float distance, float speed, float pedalSpeed, int calories, String date) {
    this.username = username;
    this.desc = desc;
    this.rating = rating;
    this.seconds = seconds;
    this.distance = distance;
    this.speed = speed;
    this.pedalSpeed = pedalSpeed;
    this.calories = calories;
    this.date = date;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public int getTime() {
    return seconds;
  }

  public void setTime(int time) {
    this.seconds = time;
  }

  public float getDistance() {
    return distance;
  }

  public void setDistance(float distance) {
    this.distance = distance;
  }

  public float getSpeed() {
    return speed;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  public float getPedalSpeed() {
    return pedalSpeed;
  }

  public void setPedalSpeed(float pedalSpeed) {
    this.pedalSpeed = pedalSpeed;
  }

  public int getCalories() {
    return calories;
  }

  public void setCalories(int calories) {
    this.calories = calories;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
