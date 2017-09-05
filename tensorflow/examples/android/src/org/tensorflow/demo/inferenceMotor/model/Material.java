package org.tensorflow.demo.inferenceMotor.model;

import java.util.List;

/**
 * Class Material : POJO of a material
 * 
 * @author guillaume.bertineau
 *
 */

public class Material {

    /**
     * ARTNG ?
     */
    private String id;

    /**
     * Type of object
     */
    private String type;

    /**
     * technical key for number of object in database
     */
    private int index;

    /**
     * name of object
     */
    private String title;

    /**
     * Path to object's picture
     */
    private String picture;

    /**
     * charateristics of object
     */
    private List<MaterialCharacteristic> characteristics;

    public Material() {
        super();
    }

    public Material(String id, String type, int index, String title, String picture, List<MaterialCharacteristic> characteristics) {
        super();
        this.id = id;
        this.type = type;
        this.index = index;
        this.title = title;
        this.picture = picture;
        this.characteristics = characteristics;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<MaterialCharacteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<MaterialCharacteristic> characteristics) {
        this.characteristics = characteristics;
    }

    @Override
    public String toString() {
        return "Material [id=" + id + ", type=" + type + ", index=" + index + ", title=" + title + ", picture=" + picture + ", charateristics="
                + characteristics + "]";
    }

}
