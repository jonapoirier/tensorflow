package org.tensorflow.demo.inferenceMotor.model;

public class Characteristic {

    private String name;

    private String englishName;

    private String sex;

    private String operator;

    private String type;

    private boolean prefereOpenQuestion;

    public Characteristic() {
        super();
    }

    public Characteristic(String name, String englishName, String sex, String operator, String type, boolean prefereOpenQuestion) {
        super();
        this.name = name;
        this.englishName = englishName;
        this.sex = sex;
        this.operator = operator;
        this.type = type;
        this.prefereOpenQuestion = prefereOpenQuestion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPrefereOpenQuestion() {
        return prefereOpenQuestion;
    }

    public void setPrefereOpenQuestion(boolean prefereOpenQuestion) {
        this.prefereOpenQuestion = prefereOpenQuestion;
    }

    @Override
    public String toString() {
        return "Characteristic [name=" + name + ", englishName=" + englishName + ", sex=" + sex + ", operator=" + operator + ", type=" + type
                + ", prefereOpenQuestion=" + prefereOpenQuestion + "]";
    }

}
