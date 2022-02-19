package ru.neoflex.cryptBot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.neoflex.cryptBot.CryptBot;

@RestController
public class WebhookController {

    private final CryptBot cryptBot;

    public WebhookController(CryptBot cryptBot) {
        this.cryptBot = cryptBot;
    }

    // point for message
    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return cryptBot.onWebhookUpdateReceived(update);
    }
}
