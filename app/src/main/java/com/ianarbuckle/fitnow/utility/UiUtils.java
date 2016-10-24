package com.ianarbuckle.fitnow.utility;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ianarbuckle.fitnow.R;

/**
 * Created by Ian Arbuckle on 10/10/2016.
 *
 */

public class UiUtils {

  public static void customiseToolbar(View view) {
    Toolbar toolbar = (Toolbar) view;
    for(int i = 0; i < toolbar.getChildCount(); i++) {
      View childView = toolbar.getChildAt(i);

      if(childView instanceof TextView) {
        TextView tvToolbar = (TextView) childView;
        tvToolbar.setTextColor(ContextCompat.getColor(childView.getContext(), R.color.colorPrimaryDark));
      }
    }
  }

  public static Drawable colourAndStyleActionBar(View view) {
    final Drawable backArrow;
    backArrow = ContextCompat.getDrawable(view.getContext(), R.drawable.abc_ic_ab_back_material);
    backArrow.setColorFilter(ContextCompat.getColor(view.getContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
    return backArrow;
  }

}
