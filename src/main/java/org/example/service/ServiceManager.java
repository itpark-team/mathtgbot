package org.example.service;

import org.example.service.logic.BotLogic;
import org.example.statemachine.State;
import org.example.statemachine.TransmittedData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.Map;

public class ServiceManager {
    private Map<String, Service> methods;
    private BotLogic botLogic;

    public ServiceManager() {
        methods = new HashMap<>();

        botLogic = new BotLogic();

        methods.put(State.WaitingCommandStart, botLogic::processWaitingCommandStart);

        methods.put(State.WaitingClickOnAnswerButton, botLogic::processWaitingClickOnAnswerButton);
    }

    public SendMessage callLogicMethod(String textFromUser, TransmittedData transmittedData) throws Exception {
        String state = transmittedData.getState();
        return methods.get(state).process(textFromUser, transmittedData);
    }

}
