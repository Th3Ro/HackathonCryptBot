package ru.neoflex.cryptBot.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Setter
@Getter
public class CryptStateChanger {
    private final Map<Long, CryptState> cryptStateMap = new HashMap<>();

    public void saveCryptState(Long userId, CryptState cryptState) {
        cryptStateMap.put(userId, cryptState);
    }
}
