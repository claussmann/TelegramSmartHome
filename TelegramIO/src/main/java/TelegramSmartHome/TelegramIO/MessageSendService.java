package TelegramSmartHome.TelegramIO;

import TelegramSmartHome.TelegramIO.apicom.HttpsHandler;

import java.util.logging.Logger;

public class MessageSendService {

    private final HttpsHandler httpsHandler;
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    public MessageSendService (HttpsHandler httpsHandler){
        this.httpsHandler = httpsHandler;
    }

    public void sendMessage(long chatId, String message) {
        httpsHandler.httpsPushRequest(chatId, message);
        logger.info("Pushed Message");
    }
}
