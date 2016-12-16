package com.ianarbuckle.fitnow.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Lincoln on 10/03/16.
 * Reference - http://www.androidhive.info/2013/11/android-sliding-menu-using-navigation-drawer/
 */
public class CircleTransform extends BitmapTransformation {

  public CircleTransform(Context context) {
    super(context);
  }

  @Override
  protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
    return circleCrop(pool, toTransform);
  }

  private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
    if (source == null) {
      return null;
    }

    int size = Math.min(source.getWidth(), source.getHeight());
    int xCor = (source.getWidth() - size) / 2;
    int yCor = (source.getHeight() - size) / 2;

    Bitmap squared = Bitmap.createBitmap(source, xCor, yCor, size, size);

    Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
    if (result == null) {
      result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
    }

    Canvas canvas = new Canvas(result);
    Paint paint = new Paint();
    paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
    paint.setAntiAlias(true);
    float radius = size / 2f;
    canvas.drawCircle(radius, radius, radius, paint);
    return result;
  }

  @Override
  public String getId() {
    return getClass().getName();
  }
}
