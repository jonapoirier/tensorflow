package org.tensorflow.demo.inferenceMotor.model;

public class Config {

    private boolean permitOpenQuestion;

    private String questionPatern;

    public Config() {
        super();
    }

    public Config(boolean permitOpenQuestion, String questionPatern) {
        super();
        this.permitOpenQuestion = permitOpenQuestion;
        this.questionPatern = questionPatern;
    }

    public boolean isPermitOpenQuestion() {
        return permitOpenQuestion;
    }

    public void setPermitOpenQuestion(boolean permitOpenQuestion) {
        this.permitOpenQuestion = permitOpenQuestion;
    }

    public String getQuestionPatern() {
        return questionPatern;
    }

    public void setQuestionPatern(String questionPatern) {
        this.questionPatern = questionPatern;
    }

    @Override
    public String toString() {
        return "Config [permitOpenQuestion=" + permitOpenQuestion + ", questionPatern=" + questionPatern + "]";
    }
    
    

}
