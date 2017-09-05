package org.tensorflow.demo.inferenceMotor.model;

public class MaterialCharacteristic {
    
    private String name;
    private Object value;
       
    public MaterialCharacteristic() {
        super();
    }

    public MaterialCharacteristic(String name, Object value) {
        super();
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MaterialCharateristic [name=" + name + ", value=" + value + "]";
    }
    
    

}
