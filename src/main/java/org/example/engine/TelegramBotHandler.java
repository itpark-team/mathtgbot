package org.example.engine;

import org.example.statemachine.ChatsRouter;
import org.example.util.InlineKeyboardsUtil;
import org.example.service.logic.MathProblem;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class TelegramBotHandler extends TelegramLongPollingBot {
    private String botUsername;
    private String botToken;

    private ChatsRouter chatsRouter;

    public TelegramBotHandler() {
        botUsername = "mymath_smyslov_bot";
        botToken = "7318929503:AAF50lheGH3UEaIBq8xTQS79GFtmRrEnI_k";

        chatsRouter = new ChatsRouter();
    }

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
        try {
            long chatId = 0;
            String textFromUser = "";

            SendMessage messageToUser = null;

            if (update.hasMessage()) {
                chatId = update.getMessage().getChatId();
                textFromUser = update.getMessage().getText();


            } else if (update.hasCallbackQuery()) {
                chatId = update.getCallbackQuery().getMessage().getChatId();
                textFromUser = update.getCallbackQuery().getData();

//
            }

            messageToUser = chatsRouter.route(chatId, textFromUser);

            execute(messageToUser);
        } catch (Exception ee) {
            System.out.println("Ошибка отправки сообщения пользователю.  " + ee);
        }

    }
}
