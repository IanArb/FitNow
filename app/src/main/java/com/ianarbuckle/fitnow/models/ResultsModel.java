package com.ianarbuckle.fitnow.models;

/**
 * Created by Ian Arbuckle on 13/03/2017.
 *
 */

public class ResultsModel {

  private String username;
  private String desc;
  private float rating;
  private String time;
  private String distance;
  private String speed;
  private String steps;
  private String calories;
  private String date;

  public ResultsModel() {

  }

  public ResultsModel(String username, String desc, float rating, String time,
                      String distance, String speed, String steps,
                      String calories, String date) {
    this.username = username;
    this.desc = desc;
    this.rating = rating;
    this.time = time;
    this.distance = distance;
    this.speed = speed;
    this.steps = steps;
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

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getDistance() {
    return distance;
  }

  public void setDistance(String distance) {
    this.distance = distance;
  }

  public String getSpeed() {
    return speed;
  }

  public void setSpeed(String speed) {
    this.speed = speed;
  }

  public String getSteps() {
    return steps;
  }

  public void setSteps(String steps) {
    this.steps = steps;
  }

  public String getCalories() {
    return calories;
  }

  public void setCalories(String calories) {
    this.calories = calories;
  }

  public String getCurrentDate() {
    return date;
  }

  public void setCurrentDate(String currentDate) {
    this.date = currentDate;
  }
}
