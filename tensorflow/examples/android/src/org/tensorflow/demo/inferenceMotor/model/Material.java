package org.tensorflow.demo.inferenceMotor.model;

import java.io.Serializable;
import java.util.List;

/**
 * Class Material : POJO of a material
 * 
 * @author guillaume.bertineau
 *
 */

public class Material implements Serializable {

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
     * Description of the object
     */
    private String description;

    /**
     * Article code
     */
    private String code;

    /**
     * charateristics of object
     */
    private List<MaterialCharacteristic> characteristics;

    public Material() {
        super();
    }

    public Material(String id, String type, int index, String title,
                    String picture, List<MaterialCharacteristic> characteristics,
                    String description,
                    String code) {
        super();
        this.id = id;
        this.type = type;
        this.index = index;
        this.title = title;
        this.picture = picture;
        this.characteristics = characteristics;
        this.description = description;
        this.code = code;
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

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getCode() { return this.code; }

    public void setCode(String code) { this.code = code; }

    @Override
    public String toString() {
        return "Material [id=" + id + ", type=" + type + ", code=" + code + ", description=" + description + ", index=" + index + ", title=" + title + ", picture=" + picture + ", charateristics="
                + characteristics + "]";
    }

}
