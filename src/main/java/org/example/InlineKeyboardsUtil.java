package org.example;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardsUtil {
    public static InlineKeyboardMarkup getAnswersKeyboard(ArrayList<Integer> answers) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row = null;
        InlineKeyboardButton button = null;

        for (int i = 0; i < answers.size(); i++) {
            int currentAnswer = answers.get(i);
            String currentAnswerAsString = Integer.toString(currentAnswer);

            row = new ArrayList<>();

            button = new InlineKeyboardButton();
            button.setText(currentAnswerAsString);
            button.setCallbackData(currentAnswerAsString);
            row.add(button);

            keyboard.add(row);
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }
}
