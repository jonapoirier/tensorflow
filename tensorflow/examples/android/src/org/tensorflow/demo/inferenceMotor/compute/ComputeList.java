package org.tensorflow.demo.inferenceMotor.compute;

import java.util.ArrayList;
import org.tensorflow.demo.env.Logger;
import org.tensorflow.demo.inferenceMotor.model.Material;
import org.tensorflow.demo.inferenceMotor.model.MaterialCharacteristic;
import org.tensorflow.demo.inferenceMotor.model.Question;


public class ComputeList {

    private static final Logger LOGGER = new Logger();

    public static ArrayList<String> updateList(Question question) {
        LOGGER.i("mise à jour liste");
        ArrayList<String> results = new ArrayList<String>();

        for (Material material : question.getPossibleMaterials()) {
            // case egality
            if (question.getCharacteritic().getOperator().equals("=")) {
                for (MaterialCharacteristic materialCharacteristic : material.getCharacteristics()) {
                    if (materialCharacteristic.getName().equals(question.getCharacteritic().getEnglishName())) {
                        if (question.getCharacteritic().getType().equals("numerical")) {
                            if (question.getProposals().size()==1) {
                                //on a répondu true ou false
                                if (question.getResult().equals("true"))
                                {
                                    if (question.getProposals().get(0)==(materialCharacteristic.getValue()) && !results.contains(material.getId())) {
                                        results.add(material.getId());
                                    }
                                }
                                else
                                {
                                    if (question.getProposals().get(0)!=(materialCharacteristic.getValue()) && !results.contains(material.getId())) {
                                        results.add(material.getId());
                                    }
                                }
                            } else {
                                // a donc répondu la vrai valeur
                                if (question.getResult()==(materialCharacteristic.getValue()) && !results.contains(material.getId())) {
                                    results.add(material.getId());
                                }
                            }
                        } else {
                            if (question.getProposals().size()==1) {
                                //on a répondu true ou false
                                if (question.getResult().equals("true"))
                                {
                                    if (question.getProposals().get(0).equals(materialCharacteristic.getValue()) && !results.contains(material.getId())) {
                                        results.add(material.getId());
                                    }
                                }
                                else
                                {
                                    if (!question.getProposals().get(0).equals(materialCharacteristic.getValue()) && !results.contains(material.getId())) {
                                        results.add(material.getId());
                                    }
                                }
                            } else {
                                // a donc répondu la vrai valeur
                                if (question.getResult().equals(materialCharacteristic.getValue()) && !results.contains(material.getId())) {
                                    results.add(material.getId());
                                }
                            }
                        }

                    }
                }
            }
            // case lower or égal than
            else {
                for (MaterialCharacteristic materialCharacteristic : material.getCharacteristics()) {
                    if (materialCharacteristic.getName().equals(question.getCharacteritic().getEnglishName())) {
                        if (question.getCharacteritic().getType().equals("numerical")) {
                            if (question.getProposals().size()==1) {
                                //on a répondu true ou false
                                if (question.getResult().equals("true"))
                                {
                                    if (((Double)materialCharacteristic.getValue())<=(Double.parseDouble(question.getProposals().get(0))) && !results.contains(material.getId())) {
                                        results.add(material.getId());
                                    }
                                }
                                else
                                {
                                    if (((Double)materialCharacteristic.getValue())>(Double.parseDouble(question.getProposals().get(0))) && !results.contains(material.getId())) {
                                        results.add(material.getId());
                                    }
                                }
                            } else {
                                
                            }
                        }
                        else
                        {
                            
                        }

                    }
                }
            }
        }
        return results;

    }

}
