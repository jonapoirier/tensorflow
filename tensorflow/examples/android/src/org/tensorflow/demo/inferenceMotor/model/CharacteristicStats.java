package org.tensorflow.demo.inferenceMotor.model;

import java.util.ArrayList;
import java.util.List;

public class CharacteristicStats {
    
    private Characteristic characteristic;
    private List<Object> encontredValue;
    private int numberOfDifferentValue;
    private double note;
    private boolean active;
    
    public CharacteristicStats(Characteristic characteristic) {
        super();
        this.characteristic = characteristic;
        this.encontredValue = new ArrayList<Object>();
        this.numberOfDifferentValue = 0;
        this.note=0;
        this.active=true;

    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(Characteristic characteristic) {
        this.characteristic = characteristic;
    }

    public List<Object> getEncontredValue() {
        return encontredValue;
    }

    public void setEncontredValue(List<Object> encontredValue) {
        this.encontredValue = encontredValue;
    }

    public int getNumberOfDifferentValue() {
        return numberOfDifferentValue;
    }

    public void setNumberOfDifferentValue(int numberOfDifferentValue) {
        this.numberOfDifferentValue = numberOfDifferentValue;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void addNewEncontredValue(Object o)
    {
        this.encontredValue.add(o);
    }

    @Override
    public String toString() {
        return "CharacteristicStats [characteristic=" + characteristic + ", encontredValue=" + encontredValue + ", numberOfDifferentValue="
                + numberOfDifferentValue + ", note=" + note + ", active=" + active + "]";
    }
    
    
    
    

}
