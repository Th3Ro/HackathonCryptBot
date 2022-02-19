package ru.neoflex.cryptBot.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.neoflex.cryptBot.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@Setter
public class MenuService {
    private final UserService userService;

    public MenuService(UserService userService) {
        this.userService = userService;
    }

    public SendMessage getMainMenuMessage(final long chatId, final String textMessage, final long userId) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard(userId);
        return createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
    }

    //Main menu
    private ReplyKeyboardMarkup getMainMenuKeyboard(long userId) {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        User user = userService.findById(userId);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        if(!user.isOn()) {
            row1.add((new KeyboardButton("Подписаться на криптовалюту")));
            row2.add(new KeyboardButton("Подписаться на падение курса валюты"));
        } else {
            row1.add((new KeyboardButton("Отписаться")));
        }
        row3.add(new KeyboardButton("Помощь"));

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public SendMessage getSelectCryptMenuMessage(final long chatId, final String textMessage, final long userId) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getSelectCryptMenuKeyboard(userId);
        return createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
    }

    //Crypt select menu
    private ReplyKeyboardMarkup getSelectCryptMenuKeyboard(long userId) {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        User user = userService.findById(userId);
        if(!user.isOn()){
            List<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow row1 = new KeyboardRow();
            KeyboardRow row2 = new KeyboardRow();
            KeyboardRow row3 = new KeyboardRow();
            row1.add((new KeyboardButton("BTC")));
            row1.add((new KeyboardButton("ETH")));
            row1.add((new KeyboardButton("BNB")));
            row2.add(new KeyboardButton("DOGE"));
            row2.add(new KeyboardButton("DOT"));
            row2.add(new KeyboardButton("ADA"));
            row3.add(new KeyboardButton("В меню"));
            keyboard.add(row1);
            keyboard.add(row2);
            keyboard.add(row3);
            replyKeyboardMarkup.setKeyboard(keyboard);
        } else {
            return getMainMenuKeyboard(userId);
        }
        return replyKeyboardMarkup;
    }

    public SendMessage getUnsubscribeMenuMessage(final long chatId, final String textMessage, final long userId) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getUnsubscribeMenuKeyboard(userId);
        return createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
    }

    //Unsubscribe select menu
    private ReplyKeyboardMarkup getUnsubscribeMenuKeyboard(long userId) {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        User user = userService.findById(userId);
        if(user.isOn()){
            List<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow row1 = new KeyboardRow();
            row1.add((new KeyboardButton("Да")));
            row1.add((new KeyboardButton("Нет")));
            keyboard.add(row1);
            replyKeyboardMarkup.setKeyboard(keyboard);
        } else {
            return getMainMenuKeyboard(userId);
        }
        return replyKeyboardMarkup;
    }

    private SendMessage createMessageWithKeyboard(final long chatId,
                                                  String textMessage,
                                                  final ReplyKeyboardMarkup replyKeyboardMarkup) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textMessage);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        return sendMessage;
    }
}
