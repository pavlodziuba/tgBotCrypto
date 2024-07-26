package org.example;

import net.thauvin.erik.crypto.CryptoPrice;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MyBot extends TelegramLongPollingBot {
    public MyBot() {
        super("7267002804:AAEiro9hMjF3AR7j5KG7Orp8IaAuZDdOn8Q");
    }
    @Override
    public void onUpdateReceived(Update update) {
        var chatId = update.getMessage().getChatId();
        var text = update.getMessage().getText();

        try {
            var message = new SendMessage();
            message.setChatId(chatId);

            if (text.equals("/start")) {
                message.setText("Hello!");
            } else if (text.equals("btc")) {
                var price = CryptoPrice.spotPrice("BTC");
                message.setText("BTC price: " + price.getAmount().doubleValue());
            } else if (text.equals("eth")) {
                var price = CryptoPrice.spotPrice("ETH");
                message.setText("ETH price: " + price.getAmount().doubleValue());
            } else if (text.equals("doge")) {
                var price = CryptoPrice.spotPrice("DOGE");
                message.setText("DOGE price: " + price.getAmount().doubleValue());
            } else if (text.equals("/all")) {
                var ETHprice = CryptoPrice.spotPrice("ETH");
                var BTCprice = CryptoPrice.spotPrice("BTC");
                var DOGEprice = CryptoPrice.spotPrice("DOGE");

                message.setText("ETH price: " + ETHprice.getAmount().doubleValue() + "\n" + "BTC price: " + BTCprice.getAmount().doubleValue() + "\n"
                        + "DOGE price: " + DOGEprice.getAmount().doubleValue());
            } else {
                try {
                    double  amount = Double.parseDouble(text);
                    if(amount <= 0){
                        message.setText("Incorrect amount value!");
                    }else{
                        var ETHprice = CryptoPrice.spotPrice("ETH");
                        var BTCprice = CryptoPrice.spotPrice("BTC");
                        var DOGEprice = CryptoPrice.spotPrice("DOGE");
                        message.setText("ETH - " + amount/ETHprice.getAmount().doubleValue() + "\n" + "BTC - " + amount/BTCprice.getAmount().doubleValue() + "\n"
                                + "DOGE - " + amount/DOGEprice.getAmount().doubleValue());
                    }
                } catch (NumberFormatException e) {
                    message.setText("Unknown command!");
                }
            }

            execute(message);
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    @Override
    public String getBotUsername() {
        return "superbotcalc98_bot";
    }

}
