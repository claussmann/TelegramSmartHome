package TelegramSmartHome.TelegramIO;

import TelegramSmartHome.TelegramIO.HttpsApiComm.HttpsHandler;
import TelegramSmartHome.TelegramIO.NewMessageHandler.JsonHandler;
import TelegramSmartHome.TelegramIO.NewMessageHandler.Update;
import  io.vavr.control.Try;

import java.util.ArrayList;
import java.util.List;

public class UpdateService {
    private String token;
    private long lastUpdateId;
    List<IMessageEvaluator> evaluators;
    private JsonHandler jsonHandler;
    private HttpsHandler httpsHandler;

    public UpdateService(String botToken){
        this.token=botToken;
        this.httpsHandler = new HttpsHandler(this.token);
        jsonHandler = new JsonHandler(botToken, httpsHandler);
        evaluators = new ArrayList<>();
    }

    public UpdateService(String botToken, int lastUpdateId){
        this.token=botToken;
        this.httpsHandler = new HttpsHandler(this.token);
        this.lastUpdateId = lastUpdateId;
        jsonHandler = new JsonHandler(botToken, httpsHandler);
        evaluators = new ArrayList<>();

    }

    /**
     * Adds an Evaluator to the Queue.
     * All Evaluators will receive the updates from Telegram once the start() method is called.
     * @param evaluator
     */
    public void addUpdateListener(IMessageEvaluator evaluator){
        evaluators.add(evaluator);
    }

    /**
     * Starts listening for updates. Notifies all registered IMessageEvaluators.
     * Use addUpdateListener to add an Evaluator.
     */
    public void start(){
        while (true){
            getUpdates();
            Try.of(() -> {Thread.sleep(10000); return null;});
        }
    }

    private void getUpdates(){
        List<Update> newMessages = jsonHandler.getNewMessages(lastUpdateId);
        newMessages.forEach(this::notifyUpdateListeners);
        //Just echoes all new Messages 
        newMessages.forEach(this::echoInput);
        lastUpdateId = (newMessages.size() > 0) ? newMessages.get(newMessages.size()-1).getUpdate_id() : lastUpdateId;
    }

    private void echoInput(Update update) {
        long chatId = update.getMessage().getChat().getId();
        httpsHandler.httpsPushRequest(chatId, update.getMessage().getMessageText());
    }

    private void notifyUpdateListeners(Update update) {
        for (IMessageEvaluator evaluator : evaluators){
            evaluator.processMessage(update.getMessage());
        }
    }
}