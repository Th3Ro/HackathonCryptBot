package ru.neoflex.cryptBot.model.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.neoflex.cryptBot.entity.User;
import ru.neoflex.cryptBot.model.BotState;
import ru.neoflex.cryptBot.model.BotStateChanger;
import ru.neoflex.cryptBot.model.CryptState;
import ru.neoflex.cryptBot.model.CryptStateChanger;
import ru.neoflex.cryptBot.service.MenuService;
import ru.neoflex.cryptBot.service.UserService;

@Component
public class EventHandler {
    private final UserService userService;
    private final BotStateChanger botStateChanger;
    private final CryptStateChanger cryptStateChanger;
    private final MenuService menuService;

    @Autowired
    public EventHandler(UserService userService, CryptStateChanger cryptStateChanger, BotStateChanger botStateChanger,
                        MenuService menuService) {
        this.userService = userService;
        this.cryptStateChanger = cryptStateChanger;
        this.botStateChanger = botStateChanger;
        this.menuService = menuService;
    }

    // create new user, if first time
    public SendMessage saveNewUser(Message message, long userId) {
        String userName = message.getFrom().getUserName();
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        user.setOn(false);
        userService.save(user);
        botStateChanger.saveBotState(userId, BotState.ENTER_TIME);
        cryptStateChanger.saveCryptState(userId, CryptState.NONE);
        return menuService.getMainMenuMessage(message.getChatId(),
                "Добро пожаловать, в данном боте Вы можете получать актуальную информацию о " +
                        "цене популярных криптовалют, для этого Вам необходимо подписаться. Чтобы это " +
                        "сделать, нажмите на cоответствующую кнопку из меню ниже и следуйте инструкции. " +
                        "Да прибудет с Вами богатство.", userId);
    }
}
