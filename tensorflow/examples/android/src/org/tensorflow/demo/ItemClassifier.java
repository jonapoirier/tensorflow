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

import org.tensorflow.demo.inferenceMotor.model.Characteristic;
import org.tensorflow.demo.inferenceMotor.model.Material;
import org.tensorflow.demo.inferenceMotor.model.MaterialCharacteristic;

import java.io.Serializable;
import java.util.List;

/**
 * An immutable result returned by a Classifier describing what was recognized.
 */
public class ItemClassifier implements Serializable {
  /**
   * A unique identifier for what has been recognized. Specific to the class, not the instance of
   * the object.
   */
  private final String id;

  /**
   * Display name for the recognition.
   */
  private final String title;

  /**
   * A sortable score for how good the recognition is relative to others. Higher should be better.
   */
  private Float confidence;

  private Material material;

  private List<MaterialCharacteristic> characteristics;


  public ItemClassifier(
      final String id, final String title, final Float confidence,
      List<MaterialCharacteristic> characteristics,
      Material material) {
    this.id = id;
    this.title = title;
    this.confidence = confidence;
    this.characteristics = characteristics;
    this.material = material;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public Float getConfidence() {
    return confidence;
  }

  public List<MaterialCharacteristic> getCharacteristics() {
    return characteristics;
  }

  public void setCharacteristics(List<MaterialCharacteristic> characteristics) {
    this.characteristics = characteristics;
  }

  public  void setConfidence(Float confidence) {
    this.confidence = confidence;
  }

  public Material getMaterial() { return this.material; }

  public void setMaterial(Material material) { this.material = material; }

  @Override
  public String toString() {
    String resultString = "";
    if (id != null) {
      resultString += "[" + id + "] ";
    }

    if (title != null) {
      resultString += title + " ";
    }

    if (confidence != null) {
      resultString += String.format("(%.1f%%) ", confidence * 100.0f);
    }

    return resultString.trim();
  }
}

