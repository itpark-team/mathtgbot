package org.example.service.logic;

import java.util.ArrayList;
import java.util.Random;

public class MathProblem {
    private Random random;

    private int number1;
    private int number2;
    private int compAnswer;

    private int leftSide;
    private int rightSide;

    private int getRandomNumberFromMinToMax(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public MathProblem() {
        random = new Random();

        leftSide = 0;
        rightSide = 10;
    }

    public String getProblem() {
        number1 = getRandomNumberFromMinToMax(leftSide, rightSide);
        number2 = getRandomNumberFromMinToMax(leftSide, rightSide);

        compAnswer = number1 + number2;

        return String.format("%d + %d = ?", number1, number2);
    }

    public String getSolveProblem() {
        return String.format("%d + %d = %d", number1, number2, compAnswer);
    }

    public ArrayList<Integer> getAnswers() {
        int countAnswers = 0;
        ArrayList<Integer> answers = new ArrayList<>();

        while (countAnswers < 4) {
            int randomAnswer = getRandomNumberFromMinToMax(compAnswer - 3, compAnswer + 3);
            if (answers.contains(randomAnswer) || randomAnswer == compAnswer) {
                continue;
            }
            answers.add(randomAnswer);
            countAnswers++;
        }

        int indexRightAnswer = getRandomNumberFromMinToMax(0, 3);

        answers.set(indexRightAnswer, compAnswer);

        return answers;
    }

    public boolean compareAnswer(int userAnswer) {
        return compAnswer == userAnswer;
    }

}
