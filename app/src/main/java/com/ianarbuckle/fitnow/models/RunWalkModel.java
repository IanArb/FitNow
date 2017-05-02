package com.ianarbuckle.fitnow.models;

/**
 * Created by Ian Arbuckle on 13/03/2017.
 *
 */

public class RunWalkModel {

  private String username;
  private String desc;
  private float rating;
  private int time;
  private float distance;
  private float speed;
  private int steps;
  private int calories;
  private String date;

  public RunWalkModel() {

  }

  public RunWalkModel(String username, String desc, float rating, int time,
                      float distance, float speed, int steps,
                      int calories, String date) {
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

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
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

  public int getSteps() {
    return steps;
  }

  public void setSteps(int steps) {
    this.steps = steps;
  }

  public int getCalories() {
    return calories;
  }

  public void setCalories(int calories) {
    this.calories = calories;
  }

  public String getCurrentDate() {
    return date;
  }

  public void setCurrentDate(String currentDate) {
    this.date = currentDate;
  }
}
