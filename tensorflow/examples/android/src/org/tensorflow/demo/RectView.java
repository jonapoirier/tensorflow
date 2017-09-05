/* Copyright 2015 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package org.tensorflow.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import org.tensorflow.demo.env.Logger;

public class RectView extends View {

  private static final Logger LOGGER = new Logger();

  private final Paint rectPaint;

  public RectView(final Context context, final AttributeSet set) {
    super(context, set);

    rectPaint = new Paint();
    rectPaint.setColor(0xcc00ff00);
    rectPaint.setStyle(Paint.Style.STROKE);
    rectPaint.setStrokeWidth(7);
  }

  @Override
  public void onDraw(final Canvas canvas) {

    // We set the size of the rect (80% area)
    int heightPixels = getResources().getDisplayMetrics().heightPixels;
    int widthPixels = getResources().getDisplayMetrics().widthPixels;

    int top = Double.valueOf(heightPixels * 0.1).intValue();
    int bottom = heightPixels - Double.valueOf(heightPixels * 0.1).intValue();
    int left = Double.valueOf(widthPixels * 0.1).intValue();
    int right = widthPixels - Double.valueOf(widthPixels * 0.1).intValue();

    canvas.drawRect(left, top, right, bottom, rectPaint);
  }
}
