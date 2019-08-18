package TelegramSmartHome.TelegramIO;

import TelegramSmartHome.TelegramIO.HttpsApiComm.HttpsHandler;

public class MessageSendService {

    private final HttpsHandler httpsHandler;

    public MessageSendService(HttpsHandler httpsHandler) {
        this.httpsHandler = httpsHandler;
    }

    public void sendMessage(long chatId, String message) {
        httpsHandler.httpsPushRequest(chatId, message);
    }
}
