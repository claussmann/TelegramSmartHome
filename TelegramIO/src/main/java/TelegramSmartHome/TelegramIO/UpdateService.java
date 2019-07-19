package TelegramSmartHome.TelegramIO;

import TelegramSmartHome.TelegramIO.NewMessageHandler.JsonHandler;
import TelegramSmartHome.TelegramIO.NewMessageHandler.Update;
import  io.vavr.control.Try;

import java.util.ArrayList;
import java.util.List;

public class UpdateService {
    private String token;
    private int lastUpdateId;
    List<IMessageEvaluator> evaluators;
    private JsonHandler jsonHandler;

    public UpdateService(String botToken){
        this.token=botToken;
        jsonHandler = new JsonHandler(botToken);
        evaluators = new ArrayList<>();
    }

    public UpdateService(String botToken, int lastUpdateId){
        this.token=botToken;
        this.lastUpdateId = lastUpdateId;
        jsonHandler = new JsonHandler(botToken);
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
        lastUpdateId = (newMessages.size() > 0) ? newMessages.get(newMessages.size()-1).getUpdate_id() : lastUpdateId;
    }

    private void notifyUpdateListeners(Update update) {
        for (IMessageEvaluator evaluator : evaluators){
            evaluator.processMessage(update.getMessage());
        }
    }
}