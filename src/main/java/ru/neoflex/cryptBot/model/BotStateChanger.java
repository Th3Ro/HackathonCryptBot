package ru.neoflex.cryptBot.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Setter
@Getter
public class BotStateChanger {
    private final Map<Long, BotState> botStateMap = new HashMap<>();

    public void saveBotState(Long userId, BotState botState) {
        botStateMap.put(userId, botState);
    }
}
