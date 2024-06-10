package org.example.service.logic;

import org.example.statemachine.State;
import org.example.statemachine.TransmittedData;
import org.example.util.InlineKeyboardsUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;

public class BotLogic {
    public SendMessage processWaitingCommandStart(String textFromUser, TransmittedData transmittedData) throws Exception {
        SendMessage messageToUser = new SendMessage();
        messageToUser.setChatId(transmittedData.getChatId());

        if (textFromUser.equals("/start") == false) {
            messageToUser.setText("Нераспознанное сообщение. Для генерации примера введите /start");
            return messageToUser;
        }

        MathProblem mathProblem = new MathProblem();
        transmittedData.getDataStorage().addOrUpdate("mathProblem", mathProblem);

        String textProblem = mathProblem.getProblem();
        ArrayList<Integer> answers = mathProblem.getAnswers();
        InlineKeyboardMarkup keyboardAnswers = InlineKeyboardsUtil.getAnswersKeyboard(answers);

        messageToUser.setText(textProblem);
        messageToUser.setReplyMarkup(keyboardAnswers);

        transmittedData.setState(State.WaitingClickOnAnswerButton);

        return messageToUser;
    }

    public SendMessage processWaitingClickOnAnswerButton(String textFromUser, TransmittedData transmittedData) throws Exception {
        SendMessage messageToUser = new SendMessage();
        messageToUser.setChatId(transmittedData.getChatId());

        if (textFromUser.startsWith("answer_button") == false) {
            messageToUser.setText("Ошибка. Для ответа на пример нажмите на кнопку с вариантом ответа");
            return messageToUser;
        }


        MathProblem mathProblem = (MathProblem) transmittedData.getDataStorage().get("mathProblem");


        textFromUser = textFromUser.replace("answer_button", "");

        int userAnswer = Integer.parseInt(textFromUser);
        boolean isRightAnswer = mathProblem.compareAnswer(userAnswer);

        String solveProblem = mathProblem.getSolveProblem();

        if (isRightAnswer) {
            messageToUser.setText(solveProblem + "\nВы ответили: " + textFromUser + "\nВаш ответ правильный. Для генерации нового примера введите /start");
        } else {
            messageToUser.setText(solveProblem + "\nВы ответили: " + textFromUser + "\nВаш ответ НЕ правильный. Для генерации нового примера введите /start");
        }

        transmittedData.setState(State.WaitingCommandStart);

        return messageToUser;
    }


}
