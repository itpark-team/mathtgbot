package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class TelegramBotHandler extends TelegramLongPollingBot {
    private String botUsername = "mymath_smyslov_bot";
    private String botToken = "7318929503:AAF50lheGH3UEaIBq8xTQS79GFtmRrEnI_k";

    private MathProblem mathProblem = new MathProblem();


    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {

        long chatId = 0;
        String textFromUser = "";

        SendMessage messageToUser = new SendMessage();

        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            textFromUser = update.getMessage().getText();

            if (textFromUser.equals("/start")) {
                String textProblem = mathProblem.getProblem();
                ArrayList<Integer> answers = mathProblem.getAnswers();
                InlineKeyboardMarkup keyboardAnswers = InlineKeyboardsUtil.getAnswersKeyboard(answers);

                messageToUser.setText(textProblem);
                messageToUser.setReplyMarkup(keyboardAnswers);
            } else {
                messageToUser.setText("Нераспознанное сообщение. Для генерации примера введите /start");
            }

        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            textFromUser = update.getCallbackQuery().getData();

            int userAnswer = Integer.parseInt(textFromUser);
            boolean isRightAnswer = mathProblem.compareAnswer(userAnswer);

            String solveProblem = mathProblem.getSolveProblem();

            if (isRightAnswer) {
                messageToUser.setText(solveProblem + "\nВы ответили: " + textFromUser + "\nВаш ответ правильный. Для генерации нового примера введите /start");
            } else {
                messageToUser.setText(solveProblem + "\nВы ответили: " + textFromUser + "\nВаш ответ НЕ правильный. Для генерации нового примера введите /start");
            }
        }

        messageToUser.setChatId(chatId);

        try {
            execute(messageToUser);
        } catch (TelegramApiException ee) {
            System.out.println("Ошибка отправки сообщения пользователю.  " + ee);
        }

    }
}
