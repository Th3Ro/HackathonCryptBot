package ru.neoflex.cryptBot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import ru.neoflex.cryptBot.CryptBot;
import ru.neoflex.cryptBot.model.TelegramFacade;

@Configuration
public class AppConfig {
    private final CryptBotConfig botConfig;

    public AppConfig(CryptBotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(botConfig.getWebHookPath()).build();
    }

    @Bean
    public CryptBot springWebhookBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        CryptBot bot = new CryptBot(telegramFacade, setWebhook);
        bot.setBotToken(botConfig.getBotToken());
        bot.setBotUsername(botConfig.getUserName());
        bot.setBotPath(botConfig.getWebHookPath());

        return bot;
    }
}
