package TelegramSmartHome.TelegramIO;

import TelegramSmartHome.TelegramIO.apicom.HttpsHandler;

public class MessageSendService {

    private final HttpsHandler httpsHandler;

    public MessageSendService(String botToken){
        httpsHandler = new HttpsHandler(botToken);
    }

    public void sendMessage(long chatId, String message) {
        httpsHandler.httpsPushRequest(chatId, message);
    }
}
