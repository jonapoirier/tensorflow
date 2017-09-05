package org.tensorflow.demo.inferenceMotor.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.tensorflow.demo.env.Logger;
import org.tensorflow.demo.inferenceMotor.compute.ComputeList;
import org.tensorflow.demo.inferenceMotor.compute.ComputeQuestion;
import org.tensorflow.demo.inferenceMotor.model.Question;


public class AkinatorSimat {
    private static final Logger LOGGER = new Logger();

    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO Auto-generated method stub

        boolean rechercheActive = true;
        int nombreQuestion = 0;
        Question question = null;
        ArrayList<String> listID = new ArrayList<String>();

        question = ComputeQuestion.getInterestingQuestion(listID, true, null, null, null);
        nombreQuestion++;

        LOGGER.i(question.getQuestion()+question.getProposals().toString());
        if (question.getProposals().size()==1)
        {
            LOGGER.i("Répondre true or false !");
        }
        else
        {
            LOGGER.i("Répondre la valeur exacte");
        }

        Scanner scanner = new Scanner(System.in);
        question.setResult(scanner.nextLine());
        listID = ComputeList.updateList(question);
        

        if (listID.size() == 1) {
            LOGGER.i("Voici le matériel" + question.getPossibleMaterials().get(0));
            rechercheActive = false;
            LOGGER.i("je l'ai trouvé en  " + nombreQuestion + " questions");
        }

        while (rechercheActive) {
            question = ComputeQuestion.getInterestingQuestion(listID, false,null, null, null);
            nombreQuestion++;
            LOGGER.i(question.toString());
            LOGGER.i(question.getQuestion()+question.getProposals().toString());
            if (question.getProposals().size()==1)
            {
                LOGGER.i("Répondre true or false !");
            }
            else
            {
                LOGGER.i("Répondre la valeur exacte");
            }
            scanner = new Scanner(System.in);
            question.setResult(scanner.nextLine());
            listID = ComputeList.updateList(question);

            if (listID.size() == 1) {
                LOGGER.i("Voici le matériel" + question.getPossibleMaterials().get(0));
                rechercheActive = false;
                LOGGER.i("je l'ai trouvé en  " + nombreQuestion + " questions");
            }

        }

    }

}
