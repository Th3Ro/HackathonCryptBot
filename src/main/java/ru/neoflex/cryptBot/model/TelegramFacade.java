package ru.neoflex.cryptBot.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.neoflex.cryptBot.entity.User;
import ru.neoflex.cryptBot.model.handler.MessageHandler;
import ru.neoflex.cryptBot.service.UserService;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramFacade {
    private final MessageHandler messageHandler;
    private final BotStateChanger botStateChanger;
    private final CryptStateChanger cryptStateChanger;
    private final UserService userService;

    public TelegramFacade(MessageHandler messageHandler,
                          BotStateChanger botStateChanger, CryptStateChanger cryptStateChanger,
                          UserService userService) {
        this.messageHandler = messageHandler;
        this.botStateChanger = botStateChanger;
        this.cryptStateChanger = cryptStateChanger;
        this.userService = userService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            return handleInputMessage(message);
        }
        return null;
    }

    private BotApiMethod<?> handleInputMessage(Message message) {
        String inputMsg = message.getText();
        long userId = message.getFrom().getId();
        //we process messages of the main menu and any other messages
        //set state
        switch (inputMsg) {
            case "/start":
                botStateChanger.saveBotState(userId, BotState.START);
                break;
            case "Подписаться на криптовалюту":
                botStateChanger.saveBotState(userId, BotState.SUBSCRIBE_FOR_3_SECONDS);
                break;
            case "Отписаться":
                botStateChanger.saveBotState(userId, BotState.UNSUBSCRIBE);
                break;
            case "Подписаться на падение курса валюты":
                botStateChanger.saveBotState(userId, BotState.SUBSCRIBE_FOR_CHANGE);
                break;
            case "Нет":
            case "В меню":
                botStateChanger.saveBotState(userId, BotState.MENU);
                break;
            case "Помощь":
                botStateChanger.saveBotState(userId, BotState.HELP);
                break;
            case "Да":
                if(botStateChanger.getBotStateMap().get(userId) == BotState.UNSUBSCRIBE &&
                        cryptStateChanger.getCryptStateMap().get(userId) != CryptState.NONE){
                    User user = userService.findById(userId);
                    user.setOn(false);
                    botStateChanger.saveBotState(userId, BotState.UNSUBSCRIBE_ACCEPTED);
                    cryptStateChanger.saveCryptState(userId, CryptState.NONE);
                }
                break;
            case "BTC":
                cryptStateChanger.saveCryptState(userId, CryptState.BTC);
                break;
            case "ETH":
                cryptStateChanger.saveCryptState(userId, CryptState.ETH);
                break;
            case "BNB":
                cryptStateChanger.saveCryptState(userId, CryptState.BNB);
                break;
            case "DOGE":
                cryptStateChanger.saveCryptState(userId, CryptState.DOGE);
                break;
            case "DOT":
                cryptStateChanger.saveCryptState(userId, CryptState.DOT);
                break;
            case "ADA":
                cryptStateChanger.saveCryptState(userId, CryptState.ADA);
                break;
            default:
                if(botStateChanger.getBotStateMap().get(userId) == BotState.SUBSCRIBE_FOR_CHANGE){
                    try{
                        // TODO USE IN THE FUTURE (MAYBEEE)
                        double percent = Double.parseDouble(message.getText());
                        botStateChanger.saveBotState(userId, BotState.SUBSCRIBE_FOR_CHANGE_ACCEPTED);
                    } catch (NumberFormatException ex){
                        return new SendMessage(String.valueOf(userId), "Вы ввели не число, введите, пожалуйста" +
                                " то, что от Вас просят.");
                    }
                } else botStateChanger.saveBotState(userId, BotState.UNKNOWN_COMMAND);
        }
        //we pass the corresponding state to the handler
        //the corresponding method will be called
        return messageHandler.handle(message, botStateChanger.getBotStateMap().get(userId),
                cryptStateChanger.getCryptStateMap().get(userId));
    }
}
