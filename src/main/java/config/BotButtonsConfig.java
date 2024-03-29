package config;

import langs.LanguageElement;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

class BotButtonsConfig {

    static void setButtons(SendMessage sendMessage, boolean langSelected, String lang, String phoneNum, BotConfig.ButtonsType ButtonsType) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup()
                .setSelective(true)
                .setResizeKeyboard(true)
                .setOneTimeKeyboard(false);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThirdRow = new KeyboardRow();

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        if (!langSelected) {
            keyboardFirstRow.add(new KeyboardButton("\ud83c\udde6\ud83c\uddff Azərbaycan dili"));
            keyboardSecondRow.add(new KeyboardButton("\ud83c\uddec\ud83c\udde7 English"));
            keyboardThirdRow.add(new KeyboardButton("\ud83c\uddf7\ud83c\uddfa Русский"));
            keyboardRowList.add(keyboardFirstRow);
            keyboardRowList.add(keyboardSecondRow);
            keyboardRowList.add(keyboardThirdRow);
            replyKeyboardMarkup.setKeyboard(keyboardRowList);
            return;
        }
        LanguageElement languageElement = new LanguageElement(lang);
        BotInlineButtonsConfig botInlineButtonsConfig = new BotInlineButtonsConfig();
        EditMessageText editMessageText = new EditMessageText();

        if (phoneNum == null) {

            KeyboardButton shareContactButton = new KeyboardButton().setText("\uD83D\uDCF1 " + languageElement.sharePhoneNumberText.trim())
                    .setRequestContact(true);
            keyboardFirstRow.add(shareContactButton);

            keyboardSecondRow.add(new KeyboardButton("\uD83C\uDFDB " + languageElement.bankBranches.trim()));
//            keyboardSecondRow.add(new KeyboardButton("\uD83D\uDCF0 " + languageElement.bankNews.trim()));
            keyboardSecondRow.add(new KeyboardButton("\uD83D\uDCDE " + languageElement.contactTheBank.trim()));
            keyboardThirdRow.add(new KeyboardButton("\uD83D\uDCB2 " + languageElement.currencyRatesText.trim()));
            keyboardThirdRow.add(new KeyboardButton("\u2699 " + languageElement.generalSettings.trim()));
            keyboardRowList.add(keyboardSecondRow);
            keyboardRowList.add(keyboardThirdRow);

            if (ButtonsType == BotConfig.ButtonsType.SETTINGS) {
                keyboardFirstRow.removeAll(keyboardFirstRow);
                keyboardSecondRow.removeAll(keyboardSecondRow);
                keyboardThirdRow.removeAll(keyboardThirdRow);
                keyboardFirstRow.add(new KeyboardButton("\uD83D\uDD19 " + languageElement.backFunction.trim()));
                keyboardSecondRow.add(new KeyboardButton("\uD83C\uDF0D " + languageElement.languageText.trim()));
            }

             if(ButtonsType == BotConfig.ButtonsType.BRANCHES){
                botInlineButtonsConfig.inlineKeyboardForBankBranches(sendMessage,languageElement);
            }
        }

        else {
            keyboardSecondRow.add(new KeyboardButton("\uD83D\uDCB8 " + languageElement.accountsViewNameText.trim()));
            keyboardSecondRow.add(new KeyboardButton("\uD83D\uDCB3 " + languageElement.creditsViewNameText.trim()));
            keyboardFirstRow.add(new KeyboardButton("\uD83D\uDD19 " + languageElement.backFunction.trim()));
            keyboardRowList.add(keyboardSecondRow);

            switch (ButtonsType) {
                case ACCOUNTS:
                    botInlineButtonsConfig.inlineKeyboardForCustomerAccounts(sendMessage);
                    break;
                case CREDITS:
                    botInlineButtonsConfig.inlineKeyboardForCustomerCredits(sendMessage);
                    break;
                default:
                    break;
            }
        }
        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }
}
