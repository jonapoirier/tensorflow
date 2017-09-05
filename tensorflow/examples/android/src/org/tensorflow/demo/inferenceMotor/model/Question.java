package org.tensorflow.demo.inferenceMotor.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
    
    private List<Material> possibleMaterials;

    private String question;

    private Characteristic characteritic;

    private String result;

    private ArrayList<String> proposals;

    public Question() {
        super();
    }

    public Question(List<Material> possibleMaterials, String question, Characteristic characteritic, String result,
            ArrayList<String> proposals) {
        super();
        this.possibleMaterials = possibleMaterials;
        this.question = question;
        this.characteritic = characteritic;
        this.result = result;
        this.proposals = proposals;
    }

    public List<Material> getPossibleMaterials() {
        return possibleMaterials;
    }

    public void setPossibleMaterials(ArrayList<Material> possibleMaterials) {
        this.possibleMaterials = possibleMaterials;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Characteristic getCharacteritic() {
        return characteritic;
    }

    public void setCharacteritic(Characteristic characteritic) {
        this.characteritic = characteritic;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ArrayList<String> getProposals() {
        return proposals;
    }

    public void setProposals(ArrayList<String> proposals) {
        this.proposals = proposals;
    }

    @Override
    public String toString() {
        return "Question [possibleMaterials=" + possibleMaterials + ", question=" + question + ", characteritic=" + characteritic + ", result="
                + result + ", proposals=" + proposals + "]";
    }

  
   
}
