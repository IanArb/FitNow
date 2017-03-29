package com.ianarbuckle.fitnow.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ian Arbuckle on 22/03/2017.
 *
 */

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

  private OnItemClickListener listener;

  private GestureDetector gestureDetector;

  public interface OnItemClickListener {
    void onItemClick(View view, int position);
    void onLongItemClick(View view, int position);
  }

  public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, final OnItemClickListener listener) {
    this.listener = listener;
    gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
      @Override
      public boolean onSingleTapUp(MotionEvent event) {
        return true;
      }

      @Override
      public void onLongPress(MotionEvent event) {
        View child = recyclerView.findChildViewUnder(event.getX(), event.getY());
        if(child != null && listener != null) {
          listener.onLongItemClick(recyclerView, recyclerView.getChildAdapterPosition(child));
        }
      }
    });
  }

  @Override
  public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent event) {
    View childView = recyclerView.findChildViewUnder(event.getX(), event.getY());
    assert childView != null && listener != null;
    if(gestureDetector.onTouchEvent(event)) {
      listener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
      return true;
    }
    return false;
  }

  @Override
  public void onTouchEvent(RecyclerView recyclerView, MotionEvent event) {
    //Stub method
  }

  @Override
  public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    //Stub method
  }
}
