package org.example;

import net.thauvin.erik.crypto.CryptoPrice;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Scanner;

public class MyBot extends TelegramLongPollingBot {
    public MyBot() {
        super("7267002804:AAEiro9hMjF3AR7j5KG7Orp8IaAuZDdOn8Q");
    }
    @Override
    public void onUpdateReceived(Update update) {
        var chatId = update.getMessage().getChatId();
        var text = update.getMessage().getText();
        Scanner lineScanner = new Scanner(text);
        try {
            var message = new SendMessage();
            message.setChatId(chatId);

            if (text.equals("/start")) {
                message.setText("Hello!");
            } else if (text.equals("btc")) {
                sendPicture("bitcoin.png",chatId);
                sendPrice("BTC",chatId);
            } else if (text.equals("eth")) {
                sendPicture("ethereum.png",chatId);
                sendPrice("ETH",chatId);
            } else if (text.equals("doge")) {
                sendPicture("dogecoin.png",chatId);
                sendPrice("DOGE",chatId);
            } else if (text.equals("/all")) {
                sendPrice("BTC",chatId);
                sendPrice("ETH",chatId);
                sendPrice("DOGE",chatId);
            } else if(lineScanner.hasNextDouble()){
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
            }  else if(lineScanner.hasNext()) {
                try {
                    String coin = lineScanner.next();
                    double amount = 0;
                    if (lineScanner.hasNextDouble()) {
                        amount = lineScanner.nextDouble();
                    } else {
                        throw new Exception();
                    }
                    if (coin.equals("btc")) {
                        sendPicture("bitcoin.png", chatId);
                        var BTCprice = CryptoPrice.spotPrice("BTC");
                        message.setText("You can buy " + amount / BTCprice.getAmount().doubleValue());
                    } else if (coin.equals("eth")) {
                        sendPicture("ethereum.png", chatId);
                        var ETHprice = CryptoPrice.spotPrice("ETH");
                        message.setText("You can buy " + amount / ETHprice.getAmount().doubleValue());
                    } else if (coin.equals("doge")) {
                        sendPicture("dogecoin.png", chatId);
                        var DOGEprice = CryptoPrice.spotPrice("DOGE");
                        message.setText("You can buy " + amount / DOGEprice.getAmount().doubleValue());
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    message.setText("Unknown command!");
                }
            }

            execute(message);
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    private void sendPrice(String name, long chatId) throws Exception{
        var price = CryptoPrice.spotPrice(name);
        sendMessage(chatId, name + "price: " + price.getAmount().doubleValue());
    }

    private void sendMessage(long chatId, String text) throws Exception{
        var message = new SendMessage();
        message.setText(text);
        message.setChatId(chatId);
        execute(message);
    }

    private void sendPicture(String name, long chatId) throws Exception{
        var photo = getClass().getClassLoader().getResourceAsStream(name);
        var message = new SendPhoto();
        message.setChatId(chatId);
        message.setPhoto(new InputFile(photo,name));
        execute(message);
    }

    @Override
    public String getBotUsername() {
        return "superbotcalc98_bot";
    }

}
