package org.tensorflow.demo.inferenceMotor.compute;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tensorflow.demo.env.Logger;
import org.tensorflow.demo.inferenceMotor.model.Characteristic;
import org.tensorflow.demo.inferenceMotor.model.CharacteristicStats;
import org.tensorflow.demo.inferenceMotor.model.Config;
import org.tensorflow.demo.inferenceMotor.model.Material;
import org.tensorflow.demo.inferenceMotor.model.MaterialCharacteristic;
import org.tensorflow.demo.inferenceMotor.model.Question;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class ComputeQuestion {

    private static final Logger LOGGER = new Logger();

    public static Question getInterestingQuestion(List<String> listID, boolean all,
                                                  List<Material> allMaterials,
                                                  Config config, List<Characteristic> characteristics)
            throws JsonSyntaxException, JsonIOException, FileNotFoundException {


        List<Material> filteredMaterial = null;

        if (all) {
            LOGGER.d("prise en compte de tous les materiels dès le début au lieu de la liste d'ID");
            filteredMaterial = new ArrayList<>(allMaterials);
        } else {


            LOGGER.d("filtrage à partir de la liste passé en paramètre");
            filteredMaterial = new ArrayList<Material>();
            for (int i = 0; i < allMaterials.size(); i++) {
                if (listID.contains(allMaterials.get(i).getId())) {
                    filteredMaterial.add(allMaterials.get(i));
                }

            }
            LOGGER.d(filteredMaterial.toString());

        }

        // création des statistiques de characteristique
        ArrayList<CharacteristicStats> listeCharacteristicsStats = new ArrayList<CharacteristicStats>();
        for (Characteristic characteristic : characteristics) {
            listeCharacteristicsStats.add(new CharacteristicStats(characteristic));
        }

        for (Material material : filteredMaterial) {
            for (MaterialCharacteristic materialCharacteristic : material.getCharacteristics()) {
                for (CharacteristicStats characteristicStats : listeCharacteristicsStats) {
                    if (characteristicStats.getCharacteristic().getEnglishName().equals(materialCharacteristic.getName())) {

                        characteristicStats.addNewEncontredValue(materialCharacteristic.getValue());

                    }
                }

            }

        }

        for (CharacteristicStats characteristicStats : listeCharacteristicsStats) {
            if (characteristicStats.getCharacteristic().getType().equals("numerical")) {
                characteristicStats.setNumberOfDifferentValue(computeNumberOfDifferentValue(characteristicStats.getEncontredValue(), true));
            } else if (characteristicStats.getCharacteristic().getType().equals("string")) {
                characteristicStats.setNumberOfDifferentValue(computeNumberOfDifferentValue(characteristicStats.getEncontredValue(), false));
            }
        }

        for (CharacteristicStats characteristicStats : listeCharacteristicsStats) {
            if (characteristicStats.getNumberOfDifferentValue() <= 1) {
                characteristicStats.setActive(false);
            }
        }

        for (CharacteristicStats characteristicStats : listeCharacteristicsStats) {
            if (characteristicStats.isActive()) {
                characteristicStats
                        .setNote((double) characteristicStats.getNumberOfDifferentValue() / characteristicStats.getEncontredValue().size());
            } else {
                characteristicStats.setNote(2);
            }
        }

        for (CharacteristicStats characteristicStats : listeCharacteristicsStats) {
            LOGGER.d(characteristicStats.getCharacteristic().getName() + " -> " + characteristicStats.getNote());
        }

        Question resultat = null;

        double maxNote = 1.1;
        CharacteristicStats best = null;

        for (CharacteristicStats characteristicStats : listeCharacteristicsStats) {
            if (characteristicStats.getNote() < maxNote) {
                best = characteristicStats;
                maxNote = characteristicStats.getNote();
            }
        }
        LOGGER.d("le plus descriminant est : " + best.getCharacteristic().getName());

        LOGGER.d("calcul de la question !");

        resultat = computeQuestion(best, filteredMaterial, config.isPermitOpenQuestion(), config.getQuestionPatern());

        return resultat;

    }

    private static Question computeQuestion(CharacteristicStats best, List<Material> filteredMaterial, boolean openQuestion,
            String questionPatern) {
        Question resultat = null;
        String question = questionPatern + best.getCharacteristic().getName();
        ArrayList<String> proposals = new ArrayList<String>();

        if (best.getCharacteristic().getType().equals("numerical")) {
            ArrayList<Double> tmplist = new ArrayList<Double>();
            for (Object objet : best.getEncontredValue()) {
                tmplist.add((Double) objet);
            }

            if (openQuestion && best.getCharacteristic().isPrefereOpenQuestion()) {
                Double tmpvalue = -1.;
                Collections.sort(tmplist);
                for (Double value : tmplist) {
                    if (value > tmpvalue) {
                        proposals.add(Double.toString(value));
                        tmpvalue = value;
                    }
                }
            } else {
                Double tmpvalue = 0.;
                Collections.sort(tmplist);

                tmpvalue = tmplist.get(((tmplist.size() - 1) / 2));
                if (best.getNumberOfDifferentValue() == 2) {
                    tmpvalue = tmplist.get(0);
                }
                System.out.println(tmpvalue);
                proposals.add(Double.toString(tmpvalue));
            }

        } else {
            ArrayList<String> tmplist = new ArrayList<String>();
            for (Object objet : best.getEncontredValue()) {
                tmplist.add((String) objet);
            }

            if (openQuestion && best.getCharacteristic().isPrefereOpenQuestion()) {
                String tmpvalue = "";
                Collections.sort(tmplist);
                for (String string : tmplist) {
                    if (!string.equals(tmpvalue)) {
                        proposals.add(string);
                        tmpvalue = string;
                    }

                }
            } else {
                String tmpvalue = "";
                int tmpScore = 0;
                Collections.sort(tmplist);
                for (String value : tmplist) {
                    if (Collections.frequency(tmplist, value) >= tmpScore) {
                        tmpScore = Collections.frequency(tmplist, value);
                        tmpvalue = value;
                    }
                }

                proposals.add(tmpvalue);
            }
        }
        if (best.getCharacteristic().getOperator().equals("=")) {
            if (proposals.size() == 1) {
                question = question + " du " + proposals.get(0) + " ?";
            } else {
                question = question + " :";
            }
        } else {
            if (proposals.size() == 1) {
                question = question + " une valeur inférieur ou égal à " + proposals.get(0) + " ?";
            } else {
                question = question + " :";
            }
        }

        resultat = new Question(filteredMaterial, question, best.getCharacteristic(), null, proposals);
        return resultat;

    }

    private static int computeNumberOfDifferentValue(List<Object> liste, boolean numerical) {

        int resultat = 0;
        if (numerical) {
            ArrayList<Double> tmplist = new ArrayList<Double>();
            for (Object objet : liste) {
                tmplist.add((Double) objet);
            }
            Double tmpvalue = 0.;
            for (Double value : tmplist) {
                if (value != (tmpvalue)) {
                    tmpvalue = value;
                    resultat++;
                }
            }
        } else {
            ArrayList<String> tmplist = new ArrayList<String>();
            for (Object objet : liste) {
                tmplist.add((String) objet);
            }
            String tmpvalue = "";

            for (String value : tmplist) {
                if (!value.equals(tmpvalue)) {
                    tmpvalue = value;
                    resultat++;
                }
            }
        }

        return resultat;
    }

}
