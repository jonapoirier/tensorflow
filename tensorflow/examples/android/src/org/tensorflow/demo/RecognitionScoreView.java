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
import android.util.TypedValue;
import android.view.View;

import org.tensorflow.demo.Classifier.Recognition;
import org.tensorflow.demo.env.Logger;

import java.util.List;

public class RecognitionScoreView extends View implements ResultsView {

  private static final Logger LOGGER = new Logger();

  private static final float TEXT_SIZE_DIP = 24;
  private List<Recognition> results;
  private final float textSizePx;
  private final Paint fgPaint;
  private final Paint bgPaint;
  private final Paint rectPaint;

  public RecognitionScoreView(final Context context, final AttributeSet set) {
    super(context, set);

    textSizePx =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE_DIP, getResources().getDisplayMetrics());
    fgPaint = new Paint();
    fgPaint.setTextSize(textSizePx);

    bgPaint = new Paint();
    bgPaint.setColor(0xcc4285f4);

    rectPaint = new Paint();
    rectPaint.setColor(0xcc4285f4);
    rectPaint.setStyle(Paint.Style.STROKE);
    rectPaint.setStrokeWidth(3);
  }

  @Override
  public void setResults(final List<Recognition> results) {
    this.results = results;
    postInvalidate();
  }

  @Override
  public void onDraw(final Canvas canvas) {
    final int x = 10;
    int y = (int) (fgPaint.getTextSize() * 1.5f);

    canvas.drawPaint(bgPaint);

    // We set the size of the rect (90% area)
    LOGGER.i("heightPixels : " + getResources().getDisplayMetrics().heightPixels);
    LOGGER.i("widthPixels : " + getResources().getDisplayMetrics().widthPixels);

    int heightPixels = getResources().getDisplayMetrics().heightPixels;
    int widthPixels = getResources().getDisplayMetrics().widthPixels;

    int top = Double.valueOf(heightPixels * 0.05).intValue();
    int bottom = heightPixels - Double.valueOf(heightPixels * 0.05).intValue();
    int left = Double.valueOf(widthPixels * 0.05).intValue();
    int right = widthPixels - Double.valueOf(widthPixels * 0.05).intValue();

    LOGGER.i("top : " + top);
    LOGGER.i("bottom : " + bottom);
    LOGGER.i("left : " + left);
    LOGGER.i("right : " + right);

    canvas.drawRect(left, top, right, bottom, rectPaint);

    if (results != null) {
      for (final Recognition recog : results) {
        canvas.drawText(recog.getTitle() + ": " + recog.getConfidence(), x, y, fgPaint);
        y += fgPaint.getTextSize() * 1.5f;
      }
    }
  }
}
